package ch.silviowangler.dox.document;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * @author Silvio Wangler
 * @since 0.3.2
 */
@Component
public class MicrosoftWordDocumentInspector implements DocumentInspector {

    protected static final Logger logger = LoggerFactory.getLogger(MicrosoftWordDocumentInspector.class);

    @Autowired
    private Executor executor;
    @Autowired
    private PdfDocumentInspector pdfDocumentInspector;

    public MicrosoftWordDocumentInspector() {
    }

    public MicrosoftWordDocumentInspector(Executor executor, PdfDocumentInspector pdfDocumentInspector) {
        this.executor = executor;
        this.pdfDocumentInspector = pdfDocumentInspector;
    }

    public int retrievePageCount(File source) {

        File target = new File(System.getProperty("java.io.tmpdir"), FilenameUtils.removeExtension(source.getName()) + ".pdf");

        Map<String, Object> args = newHashMap();
        args.put("source", source);
        args.put("targetDir", target.getParentFile());

        CommandLine cmd = new CommandLine("loffice");
        cmd.addArgument("--headless");
        cmd.addArgument("--convert-to");
        cmd.addArgument("pdf");
        cmd.addArgument("--outdir");
        cmd.addArgument("${targetDir}");
        cmd.addArgument("${source}");
        cmd.setSubstitutionMap(args);

        try {
            DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

            ExecuteWatchdog watchdog = new ExecuteWatchdog(10L * 1000L);
            executor.setWatchdog(watchdog);
            logger.trace("About to execute command {}", cmd.toString());
            executor.execute(cmd, resultHandler);

            resultHandler.waitFor();
            final int exitValue = resultHandler.getExitValue();

            if (exitValue != 0) {
                logger.error("Unable to convert Microsoft Word file {} to PDF. Exit code = {}", source.getAbsolutePath(), exitValue);
                return -1;
            }
            final int pageCount = pdfDocumentInspector.retrievePageCount(target);
            deleteFileIfNeeded(target);
            return pageCount;

        } catch (IOException | InterruptedException e) {
            logger.error("Unable to create a PDF from {}", source.getAbsolutePath(), e);
            deleteFileIfNeeded(target);
            return -1;
        }
    }

    private void deleteFileIfNeeded(File target) {
        if (target!= null && target.exists()) {
            logger.trace("Deleting temporary file {}", target.getAbsolutePath());
            target.delete();
        }
    }
}
