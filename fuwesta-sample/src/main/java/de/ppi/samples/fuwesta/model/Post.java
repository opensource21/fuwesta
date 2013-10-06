// Post.java
//
// (c) SZE-Development-Team

package de.ppi.samples.fuwesta.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import de.ppi.fuwesta.jpa.helper.JPAList;
import de.ppi.fuwesta.jpa.helper.VersionedModel;
import de.ppi.fuwesta.spring.mvc.formatter.NonEmpty;

/**
 * An post in a blog.
 */
@Entity
public class Post extends VersionedModel {

    /** The title. */
    @Column(nullable = false, length = 30)
    @NonEmpty
    private String title;

    /** The content. */
    @Column(length = 2000)
    private String content;

    /** The creation time. */
    private Date creationTime;

    /**
     * The list of tags, which characterize this post.
     */
    @ManyToMany(mappedBy = "postings")
    private List<Tag> tags;

    /**
     * The user who creates the posting.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    /**
     * Gets the title.
     * 
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     * 
     * @param title the new title
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Gets the content.
     * 
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content.
     * 
     * @param content the new content
     */
    public void setContent(final String content) {
        this.content = content;
    }

    /**
     * Gets the creation time.
     * 
     * @return the creation time
     */
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * Sets the creation time.
     * 
     * @param creationTime the new creation time
     */
    public void setCreationTime(final Date creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * Gets the list of tags, which characterize this post.
     * 
     * @return the list of tags, which characterize this post
     */
    public List<Tag> getTags() {
        return getTagsInternal();
    }

    /**
     * Sets the list of tags, which characterize this post.
     * 
     * @param tags the new list of tags, which characterize this post
     */
    public void setTags(final List<Tag> tags) {
        getTagsInternal().set(tags);
    }

    /**
     * Delivers definitely a {@link TagList}.
     * 
     * @return a TagList which wraps the internal list.
     */
    private TagList getTagsInternal() {
        if (tags == null) {
            tags = new ArrayList<Tag>();
        }
        return new TagList(tags, this);
    }

    /**
     * Gets the user who creates the posting.
     * 
     * @return the user who creates the posting
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who creates the posting.
     * 
     * @param user the new user who creates the posting
     */
    public void setUser(final User user) {
        if (this.user != null && !this.user.equals(user)) {
            this.user.getPostings().remove(this);
        }
        this.user = user;
    }

    /**
     * List which handles the association between {@link Tag} and {@link Post}.
     * 
     */
    private static final class TagList extends JPAList<Tag, Post> {

        /**
         * Initiates an object of type TagList.
         * 
         * @param internalList the internal list
         * @param associatedEntity the entity which holds the list
         */
        public TagList(List<Tag> internalList, Post associatedEntity) {
            super(internalList, associatedEntity);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(Tag entity, Post associatedEntity) {
            entity.getPostings().add(associatedEntity);

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove(Tag entity, Post associatedEntity) {
            entity.getPostings().remove(associatedEntity);
        }

    }
}
