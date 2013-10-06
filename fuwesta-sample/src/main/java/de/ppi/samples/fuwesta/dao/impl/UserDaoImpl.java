package de.ppi.samples.fuwesta.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.ppi.samples.fuwesta.dao.api.CustomUserDao;
import de.ppi.samples.fuwesta.model.User;

/**
 * Class UserDaoImpl which provides a custom implementation of UserDao, so
 * <b>UserDao</b>Impl is the correct name, not CustomUserDaoImpl (this is
 * important, otherwise Spring data won't find the class).
 * 
 */
public class UserDaoImpl implements CustomUserDao {

    /** The {@linkplain EntityManager}. */
    @PersistenceContext
    private EntityManager em;

    /**
     * Useless implementation of count, because the default is fine. But it will
     * be used, you can test it: simply add +1 and the tests will fail.
     * 
     * @return the number of user.
     */
    public long count() {
        return countNumberOfUsers().longValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long countNumberOfUsers() {
        final Query query =
                em.createQuery("select count(*) from " + User.class.getName());
        return (Long) query.getSingleResult();
    }
}
