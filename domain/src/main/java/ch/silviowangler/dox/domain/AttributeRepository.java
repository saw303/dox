package ch.silviowangler.dox.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Silvio Wangler
 * @version 0.1
 */
public interface AttributeRepository extends CrudRepository<Attribute, Long> {

    /**
     * Finds all global attribute definitions. This attributes are available on
     * every document class within DOX.
     *
     * @return all global attribute
     * @see DocumentClass
     */
    @Query("from Attribute a where a.documentClasses IS EMPTY")
    List<Attribute> findGlobalAttributes();

    @Query("from Attribute a where a.documentClasses IS EMPTY OR ? in elements(a.documentClasses)")
    List<Attribute> findAttributesForDocumentClass(DocumentClass documentClass);

    Attribute findByShortName(String shortName);
}
