package ch.silviowangler.dox;

import ch.silviowangler.dox.api.DocumentClass;

/**
 * Created by Silvio Wangler on 24.12.14.
 *
 * @author Silvio Wangler
 * @since 0.4
 */
public class DocumentClassBuilder {

    private DocumentClass documentClass;

    private DocumentClassBuilder() {
        this.documentClass = new DocumentClass();
    }

    public static DocumentClassBuilder newDocumentClass() {
        return new DocumentClassBuilder();
    }

    public DocumentClassBuilder withShortName(String shortName) {
        this.documentClass.setShortName(shortName);
        return this;
    }

    public DocumentClassBuilder withClient(String clientName) {
        this.documentClass.setClient(clientName);
        return this;
    }

    public DocumentClass build() {
        return this.documentClass;
    }
}
