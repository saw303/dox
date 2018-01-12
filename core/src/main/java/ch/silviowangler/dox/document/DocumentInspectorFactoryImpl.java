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
