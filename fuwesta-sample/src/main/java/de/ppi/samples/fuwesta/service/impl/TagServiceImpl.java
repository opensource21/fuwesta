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

import de.ppi.samples.fuwesta.dao.api.PostDao;
import de.ppi.samples.fuwesta.dao.api.TagDao;
import de.ppi.samples.fuwesta.model.Post;
import de.ppi.samples.fuwesta.model.Tag;
import de.ppi.samples.fuwesta.service.api.TagService;

/**
 * Implementation of {@link TagService}.
 */
@Transactional(readOnly = true)
@Service
public class TagServiceImpl implements TagService {

    /** The tag-Dao. */
    @Resource
    private TagDao tagDao;

    /** The post-Dao. */
    @Resource
    private PostDao postDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Tag> getAllTag() {
        return tagDao.findAll((PageRequest) null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Tag> getTag(int skip, int count, Order... order) {
        final Sort sort = order.length > 0 ? new Sort(order) : null;
        final PageRequest pr = new PageRequest(skip / count, count, sort);
        return this.getTag(pr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Tag> getTag(Pageable page) {
        return tagDao.findAll(page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public Tag save(Tag tag) {
        return tagDao.save(tag);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tag read(Long tagId) {
        return tagDao.findOne(tagId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Long tagId) {
        tagDao.delete(tagId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getNrOfTags() {
        return tagDao.count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Post> getPostingSelectOptions() {
        return postDao.findAllOrderByTitle();
    }

}
