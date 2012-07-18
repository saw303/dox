package ch.silviowangler.dox.api;

import java.io.Serializable;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
public class DocumentClass implements Serializable {

    private String shortName;

    public DocumentClass(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocumentClass that = (DocumentClass) o;

        if (!shortName.equals(that.shortName)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return shortName.hashCode();
    }
}
