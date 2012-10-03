package ch.silviowangler.dox.domain.security;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Silvio Wangler
 * @since 0.1
 */
@Entity
@Table(name = "DOX_PERMISSION")
public class GrantedAuthority extends AbstractPersistable<Long> {

    @Column(nullable = false, unique = true)
    private String name;

    public GrantedAuthority() {
        super();
    }

    public GrantedAuthority(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
