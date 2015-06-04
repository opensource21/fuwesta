// PostService.java
//

package de.ppi.samples.fuwesta.service.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

import de.ppi.samples.fuwesta.model.Post;
import de.ppi.samples.fuwesta.model.User;

/**
 * Service that handles work which must be done for user.
 *
 * @author niels
 *
 */
public interface UserService {

    /**
     * Deliver all existing users.
     *
     *
     * @return all existing users.
     */
    Page<User> getAllUser();

    /**
     * Deliver a page of users.
     *
     * @param skip the number of entries which should be skipped
     * @param count the number of entries a page should have.
     * @param order information about the ordering of the entries.
     * @return the page of users.
     */
    Page<User> getUser(int skip, int count, Order... order);

    /**
     * Deliver a page of users.
     *
     * @param page information about pagination.
     * @return the page of users.
     */
    Page<User> getUser(Pageable page);

    /**
     * Save the given user.
     *
     * @param user the user object.
     * @return the user object which may changed.
     *
     */
    User save(User user);

    /**
     * Read the user.
     *
     * @param userId the ID of the user object.
     * @return the user object.
     *
     */
    User read(Long userId);

    /**
     * Delete the user.
     *
     * @param userId the ID of the user object.
     *
     */
    void delete(Long userId);

    /**
     * Return the number of users.
     *
     * @return number of users.
     */
    long getNrOfUsers();

    /**
     * Return possible Postings in key-value form.
     *
     * @return Map with select options
     */
    List<Post> getPostingSelectOptions();

}
