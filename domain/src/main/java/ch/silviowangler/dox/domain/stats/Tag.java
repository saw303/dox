package ch.silviowangler.dox.domain.stats;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Silvio Wangler on 12/10/15.
 */
@Entity
@Table(name = "DOX_TAG")
public class Tag extends AbstractPersistable<Long> {

    @Column(unique = true, length = 15, nullable = false)
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
