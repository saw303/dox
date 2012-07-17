package ch.silviowangler.dox.domain;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.List;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
@Entity
@Table(name = "DOX_DOMAIN")
public class Domain extends AbstractPersistable<Long> {

    @Column(unique = true, length = 15, nullable = false)
    private String shortName;

    @ElementCollection(fetch = FetchType.EAGER)
    @Sort(type = SortType.NATURAL)
    private List<String> values;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
