package ch.silviowangler.dox.domain.workflow;

import ch.silviowangler.dox.domain.security.DoxUser;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Silvio Wangler on 03/01/16.
 */
@Entity
@Table(name = "DOX_UPBX")
public class UserPostBox extends PostBox {

    @ManyToOne(optional = false)
    private DoxUser user;

    public DoxUser getUser() {
        return user;
    }

    public void setUser(DoxUser user) {
        this.user = user;
    }
}
