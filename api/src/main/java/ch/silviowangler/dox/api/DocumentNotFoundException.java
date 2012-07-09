package ch.silviowangler.dox.api;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 09.07.12 07:26
 *        </div>
 */
public class DocumentNotFoundException extends Exception {

    public DocumentNotFoundException(Long id) {
        super("Document with id " + id + " not found");
    }
}
