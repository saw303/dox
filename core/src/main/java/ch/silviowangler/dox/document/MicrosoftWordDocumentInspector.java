/**
 * Copyright 2012 - 2018 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Silvio Wangler
 * @since 0.3.2
 */
@Component
public class MicrosoftWordDocumentInspector implements DocumentInspector {

    private static final Logger logger = LoggerFactory.getLogger(MicrosoftWordDocumentInspector.class);

    private final Executor executor;
    private final PdfDocumentInspector pdfDocumentInspector;

    @Autowired
    public MicrosoftWordDocumentInspector(Executor executor, PdfDocumentInspector pdfDocumentInspector) {
        this.executor = Objects.requireNonNull(executor);
        this.pdfDocumentInspector = Objects.requireNonNull(pdfDocumentInspector);
    }

    public int retrievePageCount(File source) {

        File target = new File(System.getProperty("java.io.tmpdir"), FilenameUtils.removeExtension(source.getName()) + ".pdf");

        Map<String, Object> args = new HashMap<>();
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
        if (target != null && target.exists()) {
            logger.trace("Deleting temporary file {}", target.getAbsolutePath());
            target.delete();
        }
    }
}
