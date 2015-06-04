// PostService.java
//

package de.ppi.samples.fuwesta.service.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

import de.ppi.samples.fuwesta.model.Post;
import de.ppi.samples.fuwesta.model.Tag;

/**
 * Service that handles work which must be done for tag.
 *
 * @author niels
 *
 */
public interface TagService {

    /**
     * Deliver all existing tags.
     *
     *
     * @return all existing tags.
     */
    Page<Tag> getAllTag();

    /**
     * Deliver a page of tags.
     *
     * @param skip the number of entries which should be skipped
     * @param count the number of entries a page should have.
     * @param order information about the ordering of the entries.
     * @return the page of tags.
     */
    Page<Tag> getTag(int skip, int count, Order... order);

    /**
     * Deliver a page of tags.
     *
     * @param page information about pagination.
     * @return the page of tags.
     */
    Page<Tag> getTag(Pageable page);

    /**
     * Save the given tag.
     *
     * @param tag the tag object.
     * @return the tag object which may changed.
     *
     */
    Tag save(Tag tag);

    /**
     * Read the tag.
     *
     * @param tagId the ID of the tag-object.
     * @return the tag object.
     *
     */
    Tag read(Long tagId);

    /**
     * Delete the tag.
     *
     * @param tagId the ID of the tag-object.
     *
     */
    void delete(Long tagId);

    /**
     * Return the number of tags.
     *
     * @return number of tags.
     */
    long getNrOfTags();

    /**
     * Return possible Postings in key-value form.
     *
     * @return Map with select options
     */
    List<Post> getPostingSelectOptions();

}
