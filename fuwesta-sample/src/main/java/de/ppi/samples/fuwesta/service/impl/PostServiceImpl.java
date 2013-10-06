package de.ppi.samples.fuwesta.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.ppi.samples.fuwesta.dao.api.PostDao;
import de.ppi.samples.fuwesta.dao.api.TagDao;
import de.ppi.samples.fuwesta.dao.api.UserDao;
import de.ppi.samples.fuwesta.model.Post;
import de.ppi.samples.fuwesta.model.Tag;
import de.ppi.samples.fuwesta.model.User;
import de.ppi.samples.fuwesta.service.api.PostService;

/**
 * Implementation of {@link PostService}.
 * 
 * @author niels
 * 
 */
@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    /** The post-Dao. */
    @Resource
    private PostDao postDao;

    /** The user-Dao. */
    @Resource
    private UserDao userDao;

    /** The tag-Dao. */
    @Resource
    private TagDao tagDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Post> getPost(Pageable page) {
        return postDao.findAll(page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public Post save(Post post) {
        if (post.getId() != null) {
            final Post oldPost = postDao.findOne(post.getId());
            // This is need because till know there is no knowledge about the
            // Number of Tags in the database.
            oldPost.setTags(post.getTags());
            // Now remove all tags from the changed post, otherwise we have
            // them twice in relation table.
            post.getTags().clear();
            // At the safe both are merged.
        }
        return postDao.save(post);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Post read(Long postId) {
        return postDao.findOne(postId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Long postId) {
        final Post oldPost = postDao.findOne(postId);
        // This is need because tag is the owning site.
        oldPost.setTags(null);
        postDao.delete(oldPost);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getNrOfPosts() {
        return postDao.count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAllUsers() {
        return userDao.findAllOrderByUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tag> getAllActiveTags() {
        return tagDao.findAllByActiveTrueOrderByNameDesc();
    }
}
