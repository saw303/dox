package ch.silviowangler.dox.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@MappedSuperclass
public abstract class Setting extends AbstractPersistable<Long> implements Serializable {

    protected Setting() {
    }

    protected Setting(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Column(nullable = false, name = "SET_KEY")
    private String key;
    @Column(nullable = false, name = "SET_VAL")
    private String value;

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

        if (key != null ? !key.equals(setting.key) : setting.key != null) return false;
        if (value != null ? !value.equals(setting.value) : setting.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
