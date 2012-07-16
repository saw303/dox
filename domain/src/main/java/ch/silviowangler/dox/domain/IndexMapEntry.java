package ch.silviowangler.dox.domain;

import org.hibernate.annotations.Index;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 15.07.12 18:49
 *        </div>
 */
@Entity
@Table(name = "DOX_IDX_MAP")
@org.hibernate.annotations.Table(appliesTo = "DOX_IDX_MAP", indexes = {@Index(name = "IDX_STR_VAL", columnNames = {"stringRepresentation"})})
public class IndexMapEntry extends AbstractPersistable<Long> {

    @Column(nullable = false, length = 15)
    private String attributeName;
    @Column(nullable = false, length = 255)
    private String stringRepresentation;
    @OneToOne
    @JoinColumn
    private Document document;

    public IndexMapEntry(String attributeName, String stringRepresentation, Document document) {
        this.attributeName = attributeName;
        this.stringRepresentation = stringRepresentation;
        this.document = document;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getStringRepresentation() {
        return stringRepresentation;
    }

    public void setStringRepresentation(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
