package ch.silviowangler.dox.api;

/**
 * Created by saw303 on 25.06.14.
 */
@TranslateProperties(value = {"attribute"})
public class DescriptiveIndex extends Index {

    private Attribute attribute;

    public DescriptiveIndex(Object value) {
        super.setValue(value);
    }

    public DescriptiveIndex(Object value, Attribute attribute) {
        super.setValue(value);
        this.attribute = attribute;
    }

    public DescriptiveIndex() {
        super();
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DescriptiveIndex)) return false;
        if (!super.equals(o)) return false;

        DescriptiveIndex that = (DescriptiveIndex) o;

        if (attribute != null ? !attribute.equals(that.attribute) : that.attribute != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
        return result;
    }
}
