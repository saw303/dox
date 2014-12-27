package ch.silviowangler.dox.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Created by Silvio Wangler on 24.12.14.
 *
 * @author Silvio Wangler
 * @since 0.4
 */
@Entity
@Table(name = "DOX_CLIENT")
public class Client extends AbstractPersistable<Long> {

    @Column(unique = true, nullable = false, length = 25)
    private String shortName;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("shortName", shortName)
                .toString();
    }
}
