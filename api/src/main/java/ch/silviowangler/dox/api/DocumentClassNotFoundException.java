package ch.silviowangler.dox.api;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 12.07.12 09:54
 *        </div>
 */
public class DocumentClassNotFoundException extends Exception {

    public DocumentClassNotFoundException(String documentClassShortName) {
        super("No document class with name '" + documentClassShortName + "' found");
    }
}
