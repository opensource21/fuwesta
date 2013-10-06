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
import de.ppi.samples.fuwesta.dao.api.UserDao;
import de.ppi.samples.fuwesta.model.Post;
import de.ppi.samples.fuwesta.model.User;
import de.ppi.samples.fuwesta.service.api.UserService;

/**
 * The Class UserServiceImplTest.
 */
public class UserServiceImplTest {

    /**
     * Mock for userDao.
     */
    @Mock
    private UserDao userDao;

    /**
     * Mock for postDao.
     */
    @Mock
    private PostDao postDao;

    /**
     * The service to test.
     */
    @InjectMocks
    private UserService userService = new UserServiceImpl();

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
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.UserServiceImpl#getAllUser()}
     * .
     */
    @Test
    public void testGetAllUser() {
        // Arange

        // Act
        userService.getAllUser();

        // Assert
        verify(userDao, times(1)).findAll((Pageable) null);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.UserServiceImpl#getUser(int, int, org.springframework.data.domain.Sort.Order[])}
     * .
     */
    @Test
    public void testGetUser() {
        // CSOFF: MagicNumber
        // Arange
        PageRequest pr = new PageRequest(2, 4);

        // Act
        userService.getUser(10, 4);

        // Assert
        verify(userDao, times(1)).findAll(pr);
        // CSON: MagicNumber
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.UserServiceImpl#save(de.ppi.fuwesta.samples.springmvc.model.User)}
     * .
     */
    @Test
    public void testSave() {
        // Arange
        final User aUser = new User();
        when(userDao.save(any(User.class))).thenReturn(aUser);

        // Act
        final User testUser = userService.save(new User());

        // Assert
        assertThat(testUser).isEqualTo(aUser);
        verify(userDao, times(1)).save(any(User.class));
        // CSON: MagicNumber
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.UserServiceImpl#read(java.lang.Long)}
     * .
     */
    @Test
    public void testRead() {
        // Act
        userService.read(Long.valueOf(1L));

        // Assert
        verify(userDao, times(1)).findOne(Long.valueOf(1L));
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.UserServiceImpl#delete(java.lang.Long)}
     * .
     */
    @Test
    public void testDelete() {
        // Arrange
        final User user = new User();
        when(userDao.findOne(Long.valueOf(1L))).thenReturn(user);

        // Act
        userService.delete(Long.valueOf(1L));

        // Assert
        verify(userDao, times(1)).delete(user);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.UserServiceImpl#getNrOfUsers()}
     * .
     */
    @SuppressWarnings("boxing")
    @Test
    public void testNrOfUsers() {
        final long nrOfUser = 10;
        when(userDao.count()).thenReturn(nrOfUser);

        // Act
        long testValue = userService.getNrOfUsers();

        // Assert
        verify(userDao, times(1)).count();
        assertThat(testValue).isEqualTo(nrOfUser);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.UserServiceImpl#getPostingSelectOptions()}
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
        final List<Post> result = userService.getPostingSelectOptions();
        // Assert
        assertThat(result).hasSize(numberOfPosts).isEqualTo(postings);

    }
}
