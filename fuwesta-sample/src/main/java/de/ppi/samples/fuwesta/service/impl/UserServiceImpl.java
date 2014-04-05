package de.ppi.samples.fuwesta.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import de.ppi.samples.fuwesta.dao.api.PostDao;
import de.ppi.samples.fuwesta.dao.api.UserDao;
import de.ppi.samples.fuwesta.model.Post;
import de.ppi.samples.fuwesta.model.User;
import de.ppi.samples.fuwesta.service.api.UserService;

/**
 * Implementation of {@link UserService}.
 */
@Transactional(readOnly = true)
@Service("userService")
public class UserServiceImpl implements UserService {

    /** The user-Dao. */
    @Resource
    private UserDao userDao;

    /** The post-Dao. */
    @Resource
    private PostDao postDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<User> getAllUser() {
        return userDao.findAll((PageRequest) null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<User> getUser(int skip, int count, Order... order) {
        final Sort sort = order.length > 0 ? new Sort(order) : null;
        final PageRequest pr = new PageRequest(skip / count, count, sort);
        // return userDao.findAll(pr);
        return this.getUser(pr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<User> getUser(Pageable page) {
        return userDao.findAll(page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public User save(User user) {
        if (user.getId() != null) {
            final User oldUser = userDao.findOne(user.getId());
            // This is need because post is the owning site.
            oldUser.setPostings(user.getPostings());
            // At the safe both are merged.
            user.getPostings().clear();
        }
        return userDao.save(user);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User read(Long userId) {
        final User user = userDao.findOne(userId);
        if (!CollectionUtils.isEmpty(user.getPostings())) {
            // Ensere all oostings are laoded to enable a test, where no
            // no Session is open.
            user.getPostings().size();
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Long userId) {
        final User oldUser = userDao.findOne(userId);
        // This is need because post is the owning site.
        oldUser.setPostings(null);
        userDao.delete(oldUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getNrOfUsers() {
        return userDao.count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Post> getPostingSelectOptions() {
        return postDao.findAllOrderByTitle();
    }

    /**
     * @return the userDao
     */
    protected UserDao getUserDao() {
        return userDao;
    }

}
