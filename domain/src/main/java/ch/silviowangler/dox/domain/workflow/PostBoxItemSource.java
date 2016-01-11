package ch.silviowangler.dox.domain.workflow;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.net.URL;

/**
 * Created by Silvio Wangler on 03/01/16.
 */
@Entity
@Table(name = "DOX_PBX_ITM_SRC")
public class PostBoxItemSource extends AbstractPersistable<Long> {

    @Column(nullable = false)
    private URL source;

    public URL getSource() {
        return source;
    }

    public void setSource(URL source) {
        this.source = source;
    }
}
