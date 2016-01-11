package ch.silviowangler.dox.domain.workflow;

import ch.silviowangler.dox.domain.Client;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Silvio Wangler on 03/01/16.
 */
@Entity
@Table(name = "DOX_PBX")
@Inheritance(strategy = InheritanceType.JOINED)
public class PostBox extends AbstractPersistable<Long> implements Serializable {

    @Column(nullable = false, unique = true)
    private String shortName;
    @ManyToOne(optional = false)
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PARENT_PB")
    private PostBox parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<PostBox> children;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public PostBox getParent() {
        return parent;
    }

    public void setParent(PostBox parent) {
        this.parent = parent;
    }

    public Set<PostBox> getChildren() {
        return children;
    }

    public void setChildren(Set<PostBox> children) {
        this.children = children;
    }
}
