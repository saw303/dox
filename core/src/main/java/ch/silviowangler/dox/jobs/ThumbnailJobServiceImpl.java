package ch.silviowangler.dox.jobs;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@Service("thumbnailJobService")
public class ThumbnailJobServiceImpl implements ThumbnailJobService, InitializingBean {

    @Value("#{systemEnvironment['DOX_STORE']}")
    private File archiveDirectory;

    private File thumbnailDirectory;

    private Logger logger = LoggerFactory.getLogger(ThumbnailJobServiceImpl.class);

    public void afterPropertiesSet() throws Exception {

        notNull(this.archiveDirectory, "DOX document store is not set");

        this.thumbnailDirectory = new File(this.archiveDirectory, "thumbnails");

        final String absolutePath = this.thumbnailDirectory.getAbsolutePath();
        isTrue(this.thumbnailDirectory.exists(), absolutePath + " does not exist");
        isTrue(this.thumbnailDirectory.canWrite(), "Cannot not write to " + absolutePath);
        isTrue(this.thumbnailDirectory.canRead(), "Cannot read from " + absolutePath);
    }

    @Override
    @Scheduled(initialDelayString = "${job.initial.delay}", fixedRateString = "${job.thumbnail.generation}")
    public void createThumbnails() {

        final File[] doxDocuments = archiveDirectory.listFiles(new DoxDocumentFileFilter());

        logger.debug("Found {} files to possibly generate thumbnails", doxDocuments.length);

        DefaultExecutor executor = new DefaultExecutor();

        for (File doxDocument : doxDocuments) {

            final String baseName = FilenameUtils.getBaseName(doxDocument.getName());
            File jpg = new File(this.thumbnailDirectory, baseName + ".jpg");
            File webp = new File(this.thumbnailDirectory, baseName + ".webp");

            if (jpg.exists() && webp.exists()) {
                logger.debug("File '{}' already has a JPEG and WebP thumbnail representation", doxDocument.getAbsolutePath());
                continue;
            }

            if (!jpg.exists()) {
                logger.debug("No JPEG thumbnail exists. Trying to generate {}", jpg.getAbsolutePath());

                Map<String, String> args = newHashMap();
                args.put("source", doxDocument.getAbsolutePath());
                args.put("target", jpg.getAbsolutePath());
                CommandLine cmd = new CommandLine("convert");
                cmd.addArgument("-flatten");
                cmd.addArgument("-thumbnail");
                cmd.addArgument("200x");
                cmd.addArgument("${source}[0]");
                cmd.addArgument("${target}");
                cmd.setSubstitutionMap(args);

                try {
                    logger.trace("About to execute command {}", cmd.toString());
                    final int exitValue = executor.execute(cmd);

                    if (exitValue != 0) {
                        logger.error("Unable to convert file {} to JPEG. Exit code = {}", doxDocument.getAbsolutePath(), exitValue);
                    }

                } catch (IOException e) {
                    logger.error("Unable to create JPEG from {}", doxDocument.getAbsolutePath(), e);
                }
            }

            if (!webp.exists() && jpg.exists()) {
                logger.debug("No WebP thumbnail exists. Trying to generate {}", webp.getAbsolutePath());

                Map<String, String> args = newHashMap();
                args.put("source", jpg.getAbsolutePath());
                args.put("target", webp.getAbsolutePath());
                CommandLine cmd = new CommandLine("cwebp");
                cmd.addArgument("${source}");
                cmd.addArgument("-o");
                cmd.addArgument("${target}");
                cmd.setSubstitutionMap(args);

                try {
                    logger.trace("About to execute command {}", cmd.toString());
                    final int exitValue = executor.execute(cmd);

                    if (exitValue != 0) {
                        logger.error("Unable to convert JPEG file {} to WebP. Exit code = {}", jpg.getAbsolutePath(), exitValue);
                    }

                } catch (IOException e) {
                    logger.error("Unable to create WebP from {}", jpg.getAbsolutePath(), e);
                }
            }
        }

    }
}
