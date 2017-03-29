/*
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
package ch.silviowangler.dox.document

import org.apache.commons.exec.DefaultExecutor
import org.apache.commons.io.FileUtils
import spock.lang.Specification

/**
 * @author Silvio Wangler
 * @since 0.3.2
 */
class DocumentInspectorTest extends Specification {

    def "test document inspectors"(DocumentInspector documentInspector, File file, int expectedPageCount) {

        expect:
        documentInspector.retrievePageCount(file) == expectedPageCount

        where:
        documentInspector                                                                     | file                         || expectedPageCount
        new DummyDocumentInspector()                                                          | null                         || -1
        new DummyDocumentInspector()                                                          | loadFile('hello.txt')        || -1
        new PdfDocumentInspector()                                                            | loadFile('document-1p.pdf')  || 1
        new PdfDocumentInspector()                                                            | loadFile('document-5p.pdf')  || 5
        new PdfDocumentInspector()                                                            | loadFile('document-16p.pdf') || 16
        new TiffDocumentInspector()                                                           | loadFile('document-1p.tif')  || 1
        new TiffDocumentInspector()                                                           | loadFile('document-20p.tif') || 20
        new MicrosoftWordDocumentInspector(new DefaultExecutor(), new PdfDocumentInspector()) | loadFile('word-1-page.doc')  || getPageCount(1)
        new MicrosoftWordDocumentInspector(new DefaultExecutor(), new PdfDocumentInspector()) | loadFile('word-6-page.doc')  || getPageCount(6)
        new MicrosoftWordDocumentInspector(new DefaultExecutor(), new PdfDocumentInspector()) | loadFile('word-1-page.docx') || getPageCount(1)
    }

    private File loadFile(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName)
        final File file = FileUtils.toFile(resource)
        assert file && file.exists(): "File '" + fileName + "' does not exist. Resource " + resource.getFile()
        return file
    }

    private int getPageCount(int pageCount) {
        if (isMac()) return -1
        return pageCount
    }

    private boolean isMac() {
        return System.getProperty("os.name").equals("Mac OS X")
    }
}
