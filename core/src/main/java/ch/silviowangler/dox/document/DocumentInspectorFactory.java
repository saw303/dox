package ch.silviowangler.dox.document;

/**
 * @author Silvio Wangler
 * @since 0.3.2
 */
public interface DocumentInspectorFactory {
    DocumentInspector findDocumentInspector(String mimeType);
}
