package ch.silviowangler.dox.api.settings;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
public class Setting implements Serializable {

    private String key;
    private String value;
    private String description;


    public Setting() {
        super();
    }

    public Setting(String key, String value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("key", key)
                .add("value", value)
                .add("description", description)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Setting)) return false;

        Setting setting = (Setting) o;

        if (key != null ? !key.equals(setting.key) : setting.key != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }
}
