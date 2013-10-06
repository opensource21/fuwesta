// PostDao.java
//
// (c) SZE-Development-Team

package de.ppi.samples.fuwesta.dao.api;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import de.ppi.samples.fuwesta.model.Tag;

/**
 * Data-Access-Object to the {@linkplain Tag}-model.
 * 
 * @author niels
 * 
 */
public interface TagDao extends PagingAndSortingRepository<Tag, Long> {

    /**
     * Find all active Tags ordered by name.
     * 
     * @return all Tags which are active.
     */
    List<Tag> findAllByActiveTrueOrderByNameDesc();

}
