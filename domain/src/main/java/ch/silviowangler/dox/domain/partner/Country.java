package ch.silviowangler.dox.domain.partner;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created on 21.02.15.
 *
 * @author Silvio Wangler
 * @since 0.5
 */
@Entity
@Table(name = "DOX_COUNTRY", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"numericCode3", "code3"}),
        @UniqueConstraint(columnNames = {"code2", "code3"})
})
public class Country extends AbstractPersistable<Long> {

    /**
     * ISO 3166-1 numeric-3
     * <p/>
     * {@link https://en.wikipedia.org/wiki/ISO_3166-1_numeric}
     */
    @Column(nullable = false)
    private Integer numericCode3;

    /**
     * ISO 3166-1 alpha-3 code3
     * <p/>
     * {@link https://en.wikipedia.org/wiki/ISO_3166-1_alpha-3}
     */
    @Column(nullable = false, unique = true)
    private String code3;

    /**
     * ISO 3166-1 A2
     */
    @Column(nullable = false, unique = true)
    private String code2;

    @Column(nullable = false)
    private String name;

    public Integer getNumericCode3() {
        return numericCode3;
    }

    public void setNumericCode3(Integer numericCode3) {
        this.numericCode3 = numericCode3;
    }

    public String getCode3() {
        return code3;
    }

    public void setCode3(String code3) {
        this.code3 = code3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }
}
