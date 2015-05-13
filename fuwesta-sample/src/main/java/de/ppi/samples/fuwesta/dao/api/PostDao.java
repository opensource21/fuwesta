// PostDao.java
//

package de.ppi.samples.fuwesta.dao.api;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.ppi.samples.fuwesta.model.Post;

/**
 * Data-Access-Object to the {@linkplain Post}-model.
 *
 * @author niels
 *
 */
public interface PostDao extends PagingAndSortingRepository<Post, Long> {

    /**
     * Delivers all posts ordered by title.
     *
     * @return all posts ordered by title.
     */
    @Query("select p from Post p order by p.title")
    List<Post> findAllOrderByTitle();
}
