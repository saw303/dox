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
import ch.silviowangler.dox.api.Attribute;
import ch.silviowangler.dox.api.DocumentClassNotFoundException;
import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.DocumentService;
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
import java.util.Set;
import java.util.SortedSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
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
    private DoxVersion version;

    @Override
    public ZipFile export() throws IOException {

        logger.info("About to export repository");

        final Set<ch.silviowangler.dox.api.DocumentClass> documentClasses = documentService.findDocumentClasses();

        if (!documentClasses.isEmpty()) {
            File target = new File(new File(System.getProperty("java.io.tmpdir")), "DOX-Export-" + System.currentTimeMillis() + ".zip");

            logger.debug("Creating new ZIP file '{}'", target.getAbsolutePath());
            target.createNewFile();

            try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(target))) {
                out.putNextEntry(new ZipEntry("repository.xml"));
                Repository repository = new Repository();
                repository.setVersion(version);

                for (ch.silviowangler.dox.api.DocumentClass documentClass : documentClasses) {
                    logger.debug("Processing document class {}", documentClass);

                    try {
                        SortedSet<Attribute> attributes = documentService.findAttributes(documentClass);
                        Set<ch.silviowangler.dox.export.Attribute> exportAttributes = Sets.newHashSetWithExpectedSize(attributes.size());

                        for (Attribute attribute : attributes) {
                            ch.silviowangler.dox.export.Attribute exportAttribute = new ch.silviowangler.dox.export.Attribute();
                            BeanUtils.copyProperties(attribute, exportAttribute, new String[]{ "domain"});

                            if (attribute.containsDomain()) {
                                Domain domain = new Domain(attribute.getDomain().getShortName());

                                for ( String domainValue : attribute.getDomain().getValues()) {
                                    domain.getValues().add(domainValue);
                                }
                                exportAttribute.setDomain(domain);
                            }

                            exportAttributes.add(exportAttribute);
                        }
                        repository.add(new ch.silviowangler.dox.export.DocumentClass(exportAttributes, documentClass.getShortName()));
                    } catch (DocumentClassNotFoundException e) {
                        logger.error("Unexpected error. That document class must exist {}", documentClass, e);
                    }
                }

                // do work on current file
                XStream xStream = new XStream(new StaxDriver());
                xStream.alias("repository", Repository.class);
                xStream.alias("documentClass", DocumentClass.class);
                xStream.alias("attribute", ch.silviowangler.dox.export.Attribute.class);
                xStream.alias("domainValues", Domain.class);
                xStream.useAttributeFor(DoxVersion.class, "version");

                final String data = xStream.toXML(repository);
                if (logger.isTraceEnabled()) {
                    Writer writer = new StringWriter();
                    xStream.marshal(repository, new PrettyPrintWriter(writer));
                    logger.trace("Created XML for repository.xml '{}'", writer.toString());
                }
                final byte[] dataBytes = data.getBytes();
                out.write(dataBytes, 0, dataBytes.length);

                out.closeEntry(); // end of entry

                // Process files
                xStream = new XStream(new StaxDriver());
                final Set<DocumentReference> documentReferences = documentService.retrieveAllDocumentReferences();

                for (DocumentReference documentReference : documentReferences) {
                    logger.debug("Processing document reference {}", documentReference.getId());

                    out.putNextEntry(new ZipEntry("repository/" + documentReference.getHash() + ".xml"));
                    final byte[] documentRefDataBytes = xStream.toXML(documentReference).getBytes();
                    out.write(documentRefDataBytes, 0, documentRefDataBytes.length);
                    out.closeEntry();
                }
                return null;

            } catch (IOException e) {
                logger.error("Unable to create ZIP file {}", target.getAbsolutePath(), e);
                throw e;
            }
        }
        logger.warn("There are no document classes. There is nothing to export. Aborting");
        throw new IllegalStateException("There is nothing to export");
    }
}
