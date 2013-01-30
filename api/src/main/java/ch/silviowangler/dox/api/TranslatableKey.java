package ch.silviowangler.dox.api;

import java.io.Serializable;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
public class TranslatableKey implements Serializable, Translatable {

    private String key;
    private String translation;

    public TranslatableKey(String key, String translation) {
        this.key = key;
        this.translation = translation;
    }

    @Override
    public String retrieveKeyPostfix() {
        return this.key;
    }

    public TranslatableKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTranslation() {
        return translation;
    }

    @Override
    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TranslatableKey that = (TranslatableKey) o;

        if (key != null ? !key.equals(that.key) : that.key != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("TranslatableKey");
        sb.append("{key='").append(key).append('\'');
        sb.append(", translation='").append(translation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
