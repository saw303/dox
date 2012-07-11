package ch.silviowangler.dox.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * @author Silvio Wangler
 * @since 0.1
 *        <div>
 *        Date: 11.07.12 13:07
 *        </div>
 */
public class DocumentRepositoryCustomImpl implements DocumentRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Document> findDocuments(Map<String, Object> indices) {

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Document> cq = cb.createQuery(Document.class);
        Root<Document> root = cq.from(Document.class);
        return em.createQuery(cq).getResultList();
    }
}
