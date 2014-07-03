package ch.silviowangler.dox.api;

import java.io.Serializable;

/**
 * Created by SWangler on 25.06.2014.
 */
public abstract class Index implements Serializable {

    private Object value;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Index index = (Index) o;

        if (value != null ? !value.equals(index.value) : index.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
