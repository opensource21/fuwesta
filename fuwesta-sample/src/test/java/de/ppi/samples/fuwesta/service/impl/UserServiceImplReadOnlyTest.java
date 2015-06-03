package de.ppi.samples.fuwesta.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import de.ppi.samples.fuwesta.config.RootConfig;
import de.ppi.samples.fuwesta.dao.api.PostDao;
import de.ppi.samples.fuwesta.dao.api.TagDao;
import de.ppi.samples.fuwesta.dao.api.UserDao;
import de.ppi.samples.fuwesta.model.User;
import de.ppi.samples.fuwesta.service.api.UserService;

/**
 * Class for testing @link {@link UserServiceImpl} in with attach and detach.
 *
 */
@ContextConfiguration(classes = RootConfig.class)
public class UserServiceImplReadOnlyTest extends
        AbstractJUnit4SpringContextTests {

    /** The user service. */
    @Resource(name = "readOnlyUserService")
    private UserService userService;

    /** The user dao. */
    @Resource
    private UserDao userDao;

    /** The user dao. */
    @Resource
    private PostDao postDao;

    /** The user dao. */
    @Resource
    private TagDao tagDao;

    /**
     * The {@link EntityManager}.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Makes a cleanup.
     */
    @Before
    public void tearDown() {
        postDao.deleteAll();
        tagDao.deleteAll();
        userDao.deleteAll();
    }

    /**
     * Test that Entities are inserted.
     */
    @Test
    public void testInsertUser() {
        // Arrange
        User testUser = new User("testId");
        Long id = userService.save(testUser).getId();
        // Act
        User rereadTestUser = userService.read(id);

        // Assert
        // SURPRISE!
        assertThat(rereadTestUser).isNotNull();
        assertThat(rereadTestUser).isNotSameAs(testUser);
    }

    /**
     * Test that Entities are not updated.
     */
    @Test
    public void testChangeUser() {
        // Arrange
        final String firstName = "FirstName";

        User testUser = new User("testId");
        Long id = userService.save(testUser).getId();
        testUser = userService.read(id);
        testUser.setFirstName(firstName);
        userService.save(testUser);

        // Act
        User rereadTestUser = userService.read(id);

        // Assert
        assertThat(rereadTestUser.getFirstName()).isNullOrEmpty();
        assertThat(rereadTestUser).isNotSameAs(testUser);
    }

    /**
     * Test how the Entities are not deleted.
     */
    @Test
    public void testDeleteUser() {
        // Arrange
        User testUser = new User("testId");
        Long id = userService.save(testUser).getId();
        userService.delete(id);

        // Act
        User rereadTestUser = userService.read(id);

        // Assert
        assertThat(rereadTestUser).isNotNull();
        assertThat(rereadTestUser).isNotSameAs(testUser);
    }
}
