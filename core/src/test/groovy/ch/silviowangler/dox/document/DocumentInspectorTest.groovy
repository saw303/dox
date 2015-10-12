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
