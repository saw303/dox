package ch.silviowangler.dox.document;

import java.util.Map;

/**
 * @author Silvio Wangler
 * @since 0.3.2
 */
public class DocumentInspectorFactoryImpl implements DocumentInspectorFactory {

    private Map<String, DocumentInspector> documentInspectorMap;
    private DocumentInspector fallbackDocumentInspector;

    public Map<String, DocumentInspector> getDocumentInspectorMap() {
        return documentInspectorMap;
    }

    public void setDocumentInspectorMap(Map<String, DocumentInspector> documentInspectorMap) {
        this.documentInspectorMap = documentInspectorMap;
    }

    public DocumentInspector getFallbackDocumentInspector() {
        return fallbackDocumentInspector;
    }

    public void setFallbackDocumentInspector(DocumentInspector fallbackDocumentInspector) {
        this.fallbackDocumentInspector = fallbackDocumentInspector;
    }

    @Override
    public DocumentInspector findDocumentInspector(String mimeType) {
        if (this.documentInspectorMap.containsKey(mimeType)) {
            return this.documentInspectorMap.get(mimeType);
        }
        return fallbackDocumentInspector;
    }
}
