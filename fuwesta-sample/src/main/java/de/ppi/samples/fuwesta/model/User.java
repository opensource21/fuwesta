// User.java
//
// (c) SZE-Development-Team

package de.ppi.samples.fuwesta.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import de.ppi.fuwesta.jpa.helper.JPAList;
import de.ppi.fuwesta.jpa.helper.VersionedModel;
import de.ppi.fuwesta.spring.mvc.formatter.NonEmpty;

/**
 * A user who can create blog entries.
 * 
 * @author niels
 */
@Entity(name = "T_USER")
public class User extends VersionedModel {

    /**
     * Unique identifier for the user.
     */
    @Column(nullable = false, unique = true, length = 10)
    @NonEmpty
    private String userId;

    /** The first name of the user. */
    @Column(length = 50)
    private String firstName;

    /** The last name of the user. */
    @Column(length = 100)
    private String lastName;

    /**
     * The sex of the user.
     */
    @Column(length = 1)
    private Character sex;

    /**
     * The list of postings the user has created.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postings;

    /**
     * Initiates an object of type User.
     * 
     * @param userId the userId
     */
    public User(String userId) {
        super();
        this.userId = userId;
    }

    /**
     * Initiates an object of type User.
     * 
     */
    public User() {
        super();
    }

    /**
     * Gets the unique identifier for the user.
     * 
     * @return the unique identifier for the user
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets the first name of the user.
     * 
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     * 
     * @param firstName the new first name of the user
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     * 
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     * 
     * @param lastName the new last name of the user
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the sex of the user.
     * 
     * @return the sex of the user
     */
    public Sex getSex() {
        return Sex.parse(this.sex);
    }

    /**
     * Sets the sex of the user.
     * 
     * @param sex the new sex of the user
     */
    public void setSex(Sex sex) {
        this.sex = sex.getId();
    }

    /**
     * Sets the unique identifier for the user.
     * 
     * @param userId the new unique identifier for the user
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * Gets the list of postings the user has created.
     * 
     * @return the list of postings the user has created
     */
    public List<Post> getPostings() {
        return getPostingsInternal();
    }

    /**
     * Delivers definitely a {@link PostList}.
     * 
     * @return a PostList which wraps the internal list.
     */
    private PostList getPostingsInternal() {
        if (postings == null) {
            postings = new ArrayList<Post>();
        }
        if (!(postings instanceof PostList)) {
            return new PostList(postings, this);
        } else {
            return (PostList) postings;
        }
    }

    /**
     * Sets the list of postings the user has created.
     * 
     * @param postings the new list of postings the user has created
     */
    public void setPostings(final List<Post> postings) {
        getPostingsInternal().set(postings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "User [id=" + getId() + ", userId=" + userId + ", firstName="
                + firstName + ", lastName=" + lastName + ", postings="
                + postings + "]";
    }

    /**
     * List which handles the association between {@link User} and {@link Post}.
     * 
     */
    private static final class PostList extends JPAList<Post, User> {

        /**
         * Initiates an object of type PostList.
         * 
         * @param associatedEntity the user-object
         */
        public PostList(User associatedEntity) {
            super(associatedEntity);
        }

        /**
         * Initiates an object of type PostList.
         * 
         * @param associatedEntity the user-object
         * @param internalList the internalList to store the entries.
         */
        public PostList(List<Post> internalList, User associatedEntity) {
            super(internalList, associatedEntity);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(Post entity, User associatedEntity) {
            entity.setUser(associatedEntity);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove(Post entity, User associatedEntity) {
            entity.setUser(null);
        }

    }

}
