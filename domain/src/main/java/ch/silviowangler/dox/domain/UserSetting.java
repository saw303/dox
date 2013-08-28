package ch.silviowangler.dox.domain;

import ch.silviowangler.dox.domain.security.DoxUser;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
@Entity
@Table(name = "DOX_USER_SETTINGS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "SET_KEY"})
})
public class UserSetting extends Setting {

    public UserSetting() {
    }

    public UserSetting(String key, String value, DoxUser user) {
        super(key, value);
        this.user = user;
    }

    @ManyToOne(optional = false)
    private DoxUser user;

    public DoxUser getUser() {
        return user;
    }

    public void setUser(DoxUser user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSetting)) return false;
        if (!super.equals(o)) return false;

        UserSetting that = (UserSetting) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
