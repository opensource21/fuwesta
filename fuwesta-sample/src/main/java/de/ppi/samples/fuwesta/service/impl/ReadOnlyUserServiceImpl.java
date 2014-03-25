package de.ppi.samples.fuwesta.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.ppi.samples.fuwesta.model.User;
import de.ppi.samples.fuwesta.service.api.UserService;

/**
 * Implementation of {@link UserService} where the write transactions are
 * readonly.
 */
@Transactional(readOnly = true)
@Service("readOnlyUserService")
public class ReadOnlyUserServiceImpl extends UserServiceImpl {

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public User save(User user) {
        return super.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public void delete(Long userId) {
        super.delete(userId);
    }
}
