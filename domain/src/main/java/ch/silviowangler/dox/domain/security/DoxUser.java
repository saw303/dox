package ch.silviowangler.dox.domain.security;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 02.10.12 18:07
 *        </div>
 */
@Entity
@Table(name = "DOX_USER")
public class DoxUser extends AbstractPersistable<Long> {

    public DoxUser() {
        super();
    }

    public DoxUser(String email, byte[] password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    @Column(unique = true, nullable = false, length = 25)
    private String username;
    @Column(nullable = false)
    private byte[] password;
    @Column(nullable = false)
    private String email;

    @OneToMany
    Set<Role> roles;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
