// Tag.java
//

package de.ppi.samples.fuwesta.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import de.ppi.fuwesta.jpa.helper.VersionedModel;

/**
 * A tag which is used to mark a posting.
 *
 */
@Entity
public class Tag extends VersionedModel {

    /** The name of the Tag. */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Flag if the tag is active.
     */
    private boolean active = true;

    /**
     * The list of postings which have this tag.
     */
    @ManyToMany()
    private List<Post> postings;

    /**
     * Gets the name of the Tag.
     *
     * @return the name of the Tag
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Tag.
     *
     * @param name the new name of the Tag
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Checks if is flag if the tag is active.
     *
     * @return the flag if the tag is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the flag if the tag is active.
     *
     * @param active the new flag if the tag is active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets the list of postings which have this tag.
     *
     * @return the list of postings which have this tag
     */
    public List<Post> getPostings() {
        return postings;
    }

    /**
     * Sets the list of postings which have this tag.
     *
     * @param postings the new list of postings which have this tag
     */
    public void setPostings(final List<Post> postings) {
        this.postings = postings;
    }
}
