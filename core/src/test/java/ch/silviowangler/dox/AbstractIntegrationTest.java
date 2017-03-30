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
package ch.silviowangler.dox;

import static com.google.common.collect.Maps.newHashMapWithExpectedSize;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

import ch.silviowangler.dox.api.DescriptiveIndex;
import ch.silviowangler.dox.api.DocumentClass;
import ch.silviowangler.dox.api.DocumentClassNotFoundException;
import ch.silviowangler.dox.api.DocumentDuplicationException;
import ch.silviowangler.dox.api.DocumentNotFoundException;
import ch.silviowangler.dox.api.DocumentReference;
import ch.silviowangler.dox.api.DocumentService;
import ch.silviowangler.dox.api.PhysicalDocument;
import ch.silviowangler.dox.api.TranslatableKey;
import ch.silviowangler.dox.api.ValidationException;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@ContextConfiguration(classes = {IntegrationTestConfiguration.class, CoreConfiguration.class, DomainConfiguration.class, CoreSecurityConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
public abstract class AbstractIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected DocumentService documentService;

    protected void loginAsTestRoot() {
        loginAs("root_test");
    }

    protected void loginAs(String username) {
        SecurityContextHolder.clearContext();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(new User(username, "velo", new ArrayList<GrantedAuthority>()), "velo"));
    }


    protected File loadFile(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        final File file = FileUtils.toFile(resource);
        assert file != null && file.exists() : "File '" + fileName + "' does not exist. Resource " + resource.getFile();
        return file;
    }

    protected File createTestFile(final String fileName, final String content) throws IOException {
        File temp = new File(fileName);

        if (temp.exists()) FileUtils.forceDelete(temp);
        FileUtils.write(temp, content, Charset.forName("UTF-8"));
        assertTrue("Should exist", temp.exists());
        temp.deleteOnExit();

        return temp;
    }

    protected boolean isMacOsX() {
        return System.getProperty("os.name").equals("Mac OS X");
    }

    public static void assertByteArrayEquals(String message, byte[] expected, byte[] actual) {

        assertEquals(message, expected.length, actual.length);

        for (int i = 0; i < actual.length; i++) {
            assertEquals(message, expected[i], actual[i]);
        }
    }

    protected DocumentReference importDocument(String fileName, String documentClassShortname) throws IOException, ValidationException, DocumentDuplicationException, DocumentClassNotFoundException {

        Map<TranslatableKey, DescriptiveIndex> indexes = newHashMapWithExpectedSize(3);

        indexes.put(new TranslatableKey("company"), new DescriptiveIndex("Sunrise"));
        indexes.put(new TranslatableKey("invoiceDate"), new DescriptiveIndex("01.11.2012"));
        indexes.put(new TranslatableKey("invoiceAmount"), new DescriptiveIndex("2000"));

        return importDocument(fileName, indexes, documentClassShortname);
    }

    protected DocumentReference importDocument(String fileName, Map<TranslatableKey, DescriptiveIndex> indexes, String documentClassShortName) throws IOException, ValidationException, DocumentDuplicationException, DocumentClassNotFoundException {
        File singlePagePdf = loadFile(fileName);
        PhysicalDocument doc = new PhysicalDocument(new DocumentClass(documentClassShortName), readFileToByteArray(singlePagePdf), indexes, singlePagePdf.getName());
        doc.setClient("wangler");
        return documentService.importDocument(doc);
    }

    protected DocumentReference importFile(final String fileName, final String content, final String docClassShortName, final Map<TranslatableKey, DescriptiveIndex> indices) throws ValidationException, DocumentDuplicationException, IOException, DocumentNotFoundException, DocumentClassNotFoundException {
        return importFile(fileName, content, docClassShortName, indices, "wangler");
    }

    protected DocumentReference importFile(final String fileName, final String content, final String docClassShortName, final Map<TranslatableKey, DescriptiveIndex> indices, final String clientName) throws ValidationException, DocumentDuplicationException, IOException, DocumentNotFoundException, DocumentClassNotFoundException {
        File textFile01 = createTestFile(fileName, content);
        PhysicalDocument doc = new PhysicalDocument(new DocumentClass(docClassShortName), readFileToByteArray(textFile01), indices, fileName);
        doc.setClient(clientName);
        try {
            DocumentReference documentReference = documentService.importDocument(doc);
            logger.debug("File '{}' received id {}", fileName, documentReference.getId());
            return documentReference;

        } catch (DocumentDuplicationException e) {
            return documentService.findDocumentReference(e.getDocumentId());
        }
    }

    @After
    public void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }
}
