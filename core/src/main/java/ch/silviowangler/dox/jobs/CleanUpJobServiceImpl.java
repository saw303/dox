/**
 * Copyright 2012 - 2017 Silvio Wangler (silvio.wangler@gmail.com)
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
package ch.silviowangler.dox.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import ch.silviowangler.dox.domain.Document;
import ch.silviowangler.dox.repository.DocumentRepository;
import ch.silviowangler.dox.util.FileUtils;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@Service("cleanUp")
public class CleanUpJobServiceImpl implements CleanUpJobService {

    @Autowired
    private DocumentRepository documentRepository;
    @Value("#{systemEnvironment['DOX_STORE']}")
    private File archiveDirectory;

    private final Logger logger = LoggerFactory.getLogger(CleanUpJobServiceImpl.class);

    @Scheduled(initialDelayString = "${job.initial.delay}", fixedRateString = "${job.collect.file.size}")
    @Transactional
    public void collectFileSizeOnDocumentsThatHaveNotBeenAssignedYet() {

        logger.info("Starting file size correction job");

        final List<Document> documentsWithoutFileSize = documentRepository.findByFileSize(-1L);

        logger.debug("Found {} files that do not have a file size", documentsWithoutFileSize.size());

        for (Document document : documentsWithoutFileSize) {

            File file = new File(this.archiveDirectory, document.getHash());

            final String absolutePath = file.getAbsolutePath();
            if (!file.exists()) {
                logger.warn("File does not exist. Skipping this one. {}", absolutePath);
                continue;
            }

            try {
                logger.debug("Collecting file size of file {}", absolutePath);
                final long fileSize = Files.size(file.toPath());
                logger.trace("File {} has a size of {} (human readable {}) bytes", new Object[]{absolutePath, fileSize, FileUtils.humanReadableByteCount(fileSize, true)});

                document.setFileSize(fileSize);
                documentRepository.save(document);
            } catch (IOException e) {
                logger.error("Unable to collect file size of file {}", absolutePath, e);
            }
        }
        logger.info("File size correction job has ended");
    }
}
