package testdata;

import org.springframework.data.repository.CrudRepository;

/**
 * The Interface BookDao.
 */
public interface BookDao extends CrudRepository<Book, Long> {
    // No special methods needed.
}
