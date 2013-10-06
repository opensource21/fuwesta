package testdata;

import org.springframework.data.repository.CrudRepository;

/**
 * The Interface BookDao.
 */
public interface AuthorDao extends CrudRepository<Author, Long> {
    // No special methods needed.
}
