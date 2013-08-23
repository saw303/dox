package ch.silviowangler.dox.domain;

import ch.silviowangler.dox.domain.security.DoxUser;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Silvio Wangler
 * @since 0.2
 */
@Entity
@Table(name = "DOX_USER_SETTINGS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "key"})
})
public class UserSetting extends Setting {

    @ManyToOne(optional = false)
    private DoxUser user;

    public DoxUser getUser() {
        return user;
    }

    public void setUser(DoxUser user) {
        this.user = user;
    }
}
