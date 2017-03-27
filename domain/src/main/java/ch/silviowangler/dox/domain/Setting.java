package ch.silviowangler.dox.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@MappedSuperclass
public abstract class Setting extends AbstractPersistable<Long> implements Serializable {

    @Column(nullable = false, name = "SET_KEY")
    private String key;
    @Column(nullable = false, name = "SET_VAL")
    private String value;

    protected Setting() {
    }

    protected Setting(String key, String value) {
        this.key = key;
        this.value = value;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Setting setting = (Setting) o;

        return key != null && key.equals(setting.getKey()) && value != null && value.equals(setting.getValue());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
