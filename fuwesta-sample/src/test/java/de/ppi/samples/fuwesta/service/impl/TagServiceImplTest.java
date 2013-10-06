package de.ppi.samples.fuwesta.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import de.ppi.samples.fuwesta.dao.api.PostDao;
import de.ppi.samples.fuwesta.dao.api.TagDao;
import de.ppi.samples.fuwesta.model.Post;
import de.ppi.samples.fuwesta.model.Tag;
import de.ppi.samples.fuwesta.service.api.TagService;

/**
 * The Class TagServiceImplTest.
 */
public class TagServiceImplTest {

    /**
     * Mock for tagDao.
     */
    @Mock
    private TagDao tagDao;

    /**
     * Mock for postDao.
     */
    @Mock
    private PostDao postDao;

    /**
     * The service to test.
     */
    @InjectMocks
    private TagService tagService = new TagServiceImpl();

    /**
     * Initialize the test.
     * 
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.TagServiceImpl#getAllTag()}
     * .
     */
    @Test
    public void testGetAllTag() {
        // Arange

        // Act
        tagService.getAllTag();

        // Assert
        verify(tagDao, times(1)).findAll((Pageable) null);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.TagServiceImpl#getTag(int, int, org.springframework.data.domain.Sort.Order[])}
     * .
     */
    @Test
    public void testGetTag() {
        // CSOFF: MagicNumber
        // Arange
        PageRequest pr = new PageRequest(2, 4);

        // Act
        tagService.getTag(10, 4);

        // Assert
        verify(tagDao, times(1)).findAll(pr);
        // CSON: MagicNumber
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.TagServiceImpl#save(de.ppi.fuwesta.samples.springmvc.model.Tag)}
     * .
     */
    @Test
    public void testSave() {
        // Arange
        final Tag aTag = new Tag();
        when(tagDao.save(any(Tag.class))).thenReturn(aTag);

        // Act
        final Tag testTag = tagService.save(new Tag());

        // Assert
        assertThat(testTag).isEqualTo(aTag);
        verify(tagDao, times(1)).save(any(Tag.class));
        // CSON: MagicNumber
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.TagServiceImpl#read(java.lang.Long)}
     * .
     */
    @Test
    public void testRead() {
        // Act
        tagService.read(Long.valueOf(1L));

        // Assert
        verify(tagDao, times(1)).findOne(Long.valueOf(1L));
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.TagServiceImpl#delete(java.lang.Long)}
     * .
     */
    @Test
    public void testDelete() {
        // Act
        tagService.delete(Long.valueOf(1L));

        // Assert
        verify(tagDao, times(1)).delete(Long.valueOf(1L));
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.TagServiceImpl#getNrOfTags()}
     * .
     */
    @SuppressWarnings("boxing")
    @Test
    public void testNrOfTags() {
        final long nrOfTag = 10;
        when(tagDao.count()).thenReturn(nrOfTag);

        // Act
        long testValue = tagService.getNrOfTags();

        // Assert
        verify(tagDao, times(1)).count();
        assertThat(testValue).isEqualTo(nrOfTag);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.TagServiceImpl#getPostingSelectOptions()}
     * .
     */
    @Test
    public void testGetPostingSelectOptions() {
        // Arrange
        final List<Post> postings = new ArrayList<Post>();
        final int numberOfPosts = 5;
        for (long i = 0; i < numberOfPosts; i++) {
            Post post = new Post();
            post.setId(Long.valueOf(i));
            post.setTitle("Title " + i);
            postings.add(post);
        }
        when(postDao.findAllOrderByTitle()).thenReturn(postings);
        // Act
        final List<Post> result = tagService.getPostingSelectOptions();
        // Assert
        assertThat(result).hasSize(numberOfPosts).isEqualTo(postings);

    }
}
