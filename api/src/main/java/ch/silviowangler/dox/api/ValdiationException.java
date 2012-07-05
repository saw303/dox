package ch.silviowangler.dox.api;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
public class ValdiationException extends Exception {

    public ValdiationException(String message) {
        super(message);
    }

    public ValdiationException(String message, Throwable cause) {
        super(message, cause);
    }
}
