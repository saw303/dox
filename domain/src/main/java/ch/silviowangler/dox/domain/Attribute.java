package ch.silviowangler.dox.domain;

import ch.silviowangler.dox.api.AttributeDataType;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
@Entity
@Table(name = "DOX_ATTR")
public class Attribute extends AbstractPersistable<Long> {

    @Column(unique = true, length = 15, nullable = false)
    private String shortName;
    @Column(nullable = false)
    private boolean optional = false;
    @Enumerated(EnumType.STRING)
    private AttributeDataType dataType;
    @OneToOne
    private Domain domain;
    @ManyToMany(mappedBy = "attributes")
    private Set<DocumentClass> documentClasses;
    @Column(nullable = false, length = 4)
    private String mappingColumn;
    @Column(nullable = false)
    private boolean updateable;

    public boolean isUpdateable() {
        return updateable;
    }

    public void setUpdateable(boolean updateable) {
        this.updateable = updateable;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public AttributeDataType getDataType() {
        return dataType;
    }

    public void setDataType(AttributeDataType dataType) {
        this.dataType = dataType;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Set<DocumentClass> getDocumentClasses() {
        return documentClasses;
    }

    public void setDocumentClasses(Set<DocumentClass> documentClasses) {
        this.documentClasses = documentClasses;
    }

    public String getMappingColumn() {
        return mappingColumn;
    }

    public void setMappingColumn(String mappingColumn) {
        this.mappingColumn = mappingColumn;
    }
}
