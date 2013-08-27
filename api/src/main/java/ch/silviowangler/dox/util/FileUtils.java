package ch.silviowangler.dox.util;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
public class FileUtils {

    /**
     * Code from Stack Overflow (http://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java)
     *
     * @param bytes file size in bytes
     * @param si    use SI representation?
     * @return the file size as a human readable string
     */
    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
