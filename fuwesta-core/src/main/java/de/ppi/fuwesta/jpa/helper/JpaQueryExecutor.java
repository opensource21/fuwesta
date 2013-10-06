package de.ppi.fuwesta.jpa.helper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
 * Small wrapper which works directly on the {@link EntityManager}.
 */
@Repository
public class JpaQueryExecutor {

    /**
     * The {@link EntityManager}.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Runs the given query.
     *
     * @param jpql the query
     * @param parameters the parameters.
     * @return the result.
     */
    public Object queryWithSingleResult(String jpql, Object... parameters) {

        final Query q = em.createQuery(jpql, Long.class);
        for (int i = 0; i < parameters.length; i++) {
            q.setParameter(i + 1, parameters[i]);
        }
        return q.getSingleResult();
    }
}
