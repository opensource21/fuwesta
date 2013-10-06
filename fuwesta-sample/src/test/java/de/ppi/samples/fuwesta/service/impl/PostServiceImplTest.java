package de.ppi.samples.fuwesta.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import de.ppi.samples.fuwesta.dao.api.PostDao;
import de.ppi.samples.fuwesta.dao.api.TagDao;
import de.ppi.samples.fuwesta.dao.api.UserDao;
import de.ppi.samples.fuwesta.model.Post;
import de.ppi.samples.fuwesta.service.api.PostService;

/**
 * The Class PostServiceImplTest.
 */
public class PostServiceImplTest {

    /**
     * Mock for postDao.
     */
    @Mock
    private PostDao postDao;

    /**
     * Mock for userDao.
     */
    @Mock
    private UserDao userDao;

    /**
     * Mock for tagDao.
     */
    @Mock
    private TagDao tagDao;

    /**
     * The service to test.
     */
    @InjectMocks
    private PostService postService = new PostServiceImpl();

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
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.PostServiceImpl#save(de.ppi.fuwesta.samples.springmvc.model.Post)}
     * .
     */
    @Test
    public void testSave() {
        // Arange
        final Post aPost = new Post();
        when(postDao.save(any(Post.class))).thenReturn(aPost);

        // Act
        final Post testPost = postService.save(new Post());

        // Assert
        assertThat(testPost).isEqualTo(aPost);
        verify(postDao, times(1)).save(any(Post.class));
        // CSON: MagicNumber
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.PostServiceImpl#read(java.lang.Long)}
     * .
     */
    @Test
    public void testRead() {
        // Act
        postService.read(Long.valueOf(1L));

        // Assert
        verify(postDao, times(1)).findOne(Long.valueOf(1L));
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.PostServiceImpl#delete(java.lang.Long)}
     * .
     */
    @Test
    public void testDelete() {
        // Arrange
        Post post = new Post();
        when(postDao.findOne(Long.valueOf(1))).thenReturn(post);

        // Act
        postService.delete(Long.valueOf(1L));

        // Assert
        verify(postDao, times(1)).delete(post);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.PostServiceImpl#getNrOfPosts()}
     * .
     */
    @SuppressWarnings("boxing")
    @Test
    public void testNrOfPosts() {
        final long nrOfPost = 10;
        when(postDao.count()).thenReturn(nrOfPost);

        // Act
        long testValue = postService.getNrOfPosts();

        // Assert
        verify(postDao, times(1)).count();
        assertThat(testValue).isEqualTo(nrOfPost);
    }

    /**
     * Test method for {@link PostServiceImpl#getPost(Pageable)}.
     */
    @Test
    public void testGetPost() {
        // Arrange
        final Pageable page = mock(Pageable.class);

        // Act
        postService.getPost(page);

        // Assert
        verify(postDao, times(1)).findAll(page);
    }

    /**
     * Test method for {@link PostServiceImpl#getAllUsers()}.
     */
    @Test
    public void testGetAllUsers() {
        // Arrange
        // Act
        postService.getAllUsers();

        // Assert
        verify(userDao, times(1)).findAllOrderByUserId();

    }

    /**
     * Test method for {@link PostServiceImpl#getAllActiveTags()}.
     */
    @Test
    public void testGetAllActiveTags() {
        // Arrange
        // Act
        postService.getAllActiveTags();

        // Assert
        verify(tagDao, times(1)).findAllByActiveTrueOrderByNameDesc();
    }
}
