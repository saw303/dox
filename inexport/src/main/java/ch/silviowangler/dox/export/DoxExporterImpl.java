/*
 * Copyright 2012 - 2013 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.silviowangler.dox.export;

import ch.silviowangler.dox.DoxVersion;
import ch.silviowangler.dox.api.*;
import com.google.common.collect.Sets;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@Component("doxExporter")
@Scope(SCOPE_PROTOTYPE)
public final class DoxExporterImpl implements DoxExporter {

    private static final Logger logger = LoggerFactory.getLogger(DoxExporterImpl.class);

    @Autowired
    private DocumentService documentService;
    @Autowired
    private TranslationService translationService;
    @Autowired
    private DoxVersion version;

    @Override
    public File export() throws IOException {

        logger.info("About to export repository");

        final Set<ch.silviowangler.dox.api.DocumentClass> documentClasses = documentService.findDocumentClasses();

        if (!documentClasses.isEmpty()) {
            File target = new File(new File(System.getProperty("java.io.tmpdir")), "DOX-Export-" + System.currentTimeMillis() + ".zip");

            logger.debug("Creating new ZIP file '{}'", target.getAbsolutePath());
            target.createNewFile();

            try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(target))) {

                Repository repository = retrieveRepository(documentClasses);

                // do work on current file
                XStream xStream = getXStreamForRepository();

                final String data = xStream.toXML(repository);
                logXml(xStream, repository);
                writeToZipOutputStream(out, data, "repository.xml");

                // Process files
                xStream = getXStreamDocument();
                final Set<DocumentReference> documentReferences = documentService.retrieveAllDocumentReferences();

                logger.debug("Found {} document references to export", documentReferences.size());

                for (DocumentReference documentReference : documentReferences) {
                    logger.debug("Processing document reference {}", documentReference.getId());
                    logXml(xStream, documentReference);
                    writeToZipOutputStream(out, xStream.toXML(documentReference), "repository/" + documentReference.getHash() + ".xml");

                    try {
                        PhysicalDocument physicalDocument = documentService.findPhysicalDocument(documentReference.getId());
                        writeToZipOutputStream(out, physicalDocument.getContent(), "repository/" + documentReference.getHash() + ".bin");
                    } catch (DocumentNotFoundException | DocumentNotInStoreException e) {
                        logger.error("Unable to retrieve physical document for document with id {} (hash: '{}')", documentReference.getId(), documentReference.getHash());
                    }
                }
                out.finish();
                return target;

            } catch (IOException e) {
                logger.error("Unable to create ZIP file {}", target.getAbsolutePath(), e);
                throw e;
            }
        }
        logger.warn("There are no document classes. There is nothing to export. Aborting");
        throw new IllegalStateException("There is nothing to export");
    }

    private XStream getXStreamForRepository() {
        XStream xStream = new XStream(new StaxDriver());
        xStream.alias("repository", Repository.class);
        xStream.alias("documentClass", DocumentClass.class);
        xStream.alias("attribute", Attribute.class);
        xStream.alias("domainValues", Domain.class);
        xStream.alias("translations", Translation.class);
        xStream.useAttributeFor(DoxVersion.class, "version");
        return xStream;
    }

    private XStream getXStreamDocument() {
        XStream xStream = new XStream(new StaxDriver());
        xStream.useAttributeFor(ch.silviowangler.dox.api.DocumentClass.class, "shortName");
        xStream.alias("document", DocumentReference.class);
        return xStream;
    }

    private void logXml(XStream xStream, Serializable instanceToDump) {
        if (logger.isTraceEnabled()) {
            Writer writer = new StringWriter();
            xStream.marshal(instanceToDump, new PrettyPrintWriter(writer));
            logger.trace("Created XML for repository.xml '{}'", writer.toString());
        }
    }

    private void writeToZipOutputStream(ZipOutputStream out, byte[] dataBytes, String path) throws IOException {
        logger.trace("Writing to ZIP output stream using path '{}' and data length '{}'", path, dataBytes.length);
        out.putNextEntry(new ZipEntry(path));
        out.write(dataBytes, 0, dataBytes.length);
        out.closeEntry(); // end of entry
    }

    private void writeToZipOutputStream(ZipOutputStream out, String data, String path) throws IOException {
        logger.trace("Writing to ZIP output stream using path '{}' and data '{}'", path, data);
        writeToZipOutputStream(out, data.getBytes(), path);
    }

    private Repository retrieveRepository(Set<ch.silviowangler.dox.api.DocumentClass> documentClasses) {
        Repository repository = new Repository();
        repository.setVersion(version);
        repository.getDocumentClasses().addAll(processDocumentClasses(documentClasses));
        repository.getTranslations().addAll(processTranslations());
        return repository;
    }


    private Set<Translation> processTranslations() {
        Set<ch.silviowangler.dox.api.Translation> translations = translationService.findAll();
        Set<Translation> translationApi = new HashSet<>(translations.size());

        for (ch.silviowangler.dox.api.Translation translation : translations) {
            translationApi.add(new Translation(translation.getKey(), translation.getLocale(), translation.getTranslation()));
        }
        return translationApi;
    }

    private Set<DocumentClass> processDocumentClasses(Set<ch.silviowangler.dox.api.DocumentClass> documentClasses) {
        Set<DocumentClass> docClasses = new HashSet<>(documentClasses.size());
        for (ch.silviowangler.dox.api.DocumentClass documentClass : documentClasses) {
            logger.debug("Processing document class {}", documentClass);

            try {
                SortedSet<ch.silviowangler.dox.api.Attribute> attributes = documentService.findAttributes(documentClass);
                Set<Attribute> exportAttributes = Sets.newHashSetWithExpectedSize(attributes.size());

                for (ch.silviowangler.dox.api.Attribute attribute : attributes) {
                    Attribute exportAttribute = new Attribute();
                    BeanUtils.copyProperties(attribute, exportAttribute, new String[]{"domain"});

                    if (attribute.containsDomain()) {
                        Domain domain = new Domain(attribute.getDomain().getShortName());

                        for (String domainValue : attribute.getDomain().getValues()) {
                            domain.getValues().add(domainValue);
                        }
                        exportAttribute.setDomain(domain);
                    }

                    exportAttributes.add(exportAttribute);
                }
                docClasses.add(new DocumentClass(exportAttributes, documentClass.getShortName()));
            } catch (DocumentClassNotFoundException e) {
                logger.error("Unexpected error. That document class must exist {}", documentClass, e);
            }
        }
        return docClasses;
    }
}
