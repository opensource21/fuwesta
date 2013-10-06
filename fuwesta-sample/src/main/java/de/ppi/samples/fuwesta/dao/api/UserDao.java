// UserDao.java
//
// (c) 2012 PPI AG Informationstechnologie im Auftrag der Finanz Informatik

package de.ppi.samples.fuwesta.dao.api;

import java.util.List;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import de.ppi.samples.fuwesta.model.User;

/**
 * Data-Access-Object to the {@linkplain User}-model.
 * 
 * @author niels
 * 
 */
public interface UserDao extends PagingAndSortingRepository<User, Long>,
        CustomUserDao {

    // Some methods just to show what's possible.

    /**
     * Find exactly one user by firstName.
     * 
     * @param firstName the first name of the user
     * @return one user which fits the condition, if more than one a
     * @throws IncorrectResultSizeDataAccessException is thrown.
     */
    User findByFirstName(String firstName);

    /**
     * Find all users with given first name.
     * 
     * @param firstName the first name
     * @return all users with the given first name.
     */
    List<User> findAllByFirstNameOrderByLastNameAsc(String firstName);

    /**
     * Find all users with given last name ordered by first name.
     * 
     * @param lastName the last name
     * @return all users with the given last name ordered by first name.
     */
    List<User> findAllByLastNameOrderByFirstNameDesc(String lastName);

    /**
     * Find a user by his id.
     * 
     * @param userId the user-id
     * @return the user.
     */
    User findByUserId(String userId);

    /**
     * Find all users with posts with a special title.
     * 
     * @param title the title of the post
     * @return all users which made a post with a given title.
     */
    List<User> findDistinctUserByPostingsTitleLikeOrderByLastNameAsc(
            String title);

    /**
     * Finder to demonstrate the @Query.
     * 
     * @param firstname1 the firstname1
     * @param firstname2 the firstname2
     * @param title the title of the post
     * @return all users which made a post with a given title and have one of
     *         the first names.
     */

    // We user JQL, but SQL is possible to with @Modifying you can even define
    // updates
    @Query("select u from de.ppi.samples.fuwesta.model.User u inner join u.postings Post "
            + "where (u.firstName = :firstname1 or "
            + "u.firstName = :firstname2) and Post.title = :title")
            List<User> nonsensFinder(@Param("firstname1") String firstname1,
                    @Param("firstname2") String firstname2,
                    @Param("title") String title);

    /**
     * Find all users ordered by userId.
     * 
     * @return all user ordered by userId.
     */
    @Query("select u from de.ppi.samples.fuwesta.model.User u order by u.userId")
            List<User> findAllOrderByUserId();
}
