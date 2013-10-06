package de.ppi.samples.fuwesta.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;

import org.junit.After;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import de.ppi.samples.fuwesta.config.RootConfig;
import de.ppi.samples.fuwesta.dao.api.UserDao;
import de.ppi.samples.fuwesta.model.User;
import de.ppi.samples.fuwesta.service.api.UserService;

/**
 * Class for testing @link {@link UserServiceImpl} in with attach and detach.
 * 
 */
@ContextConfiguration(classes = RootConfig.class)
public class UserServiceImplControllerTest extends
        AbstractTransactionalJUnit4SpringContextTests {

    /** The user service. */
    @Resource
    private UserService userService;

    /** The user dao. */
    @Resource
    private UserDao userDao;

    /**
     * The {@link EntityManager}.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Makes a cleanup.
     */
    @After
    public void tearDown() {
        userDao.deleteAll();
    }

    /**
     * Test how the Entities are attached and detached.
     */
    @Test
    public void testDetach() {
        // Arrange
        User testUser = new User("testId");
        Long id = userService.save(testUser).getId();
        resetEntityManager();
        testUser = userService.read(id);
        testUser.setFirstName("FirstName");
        resetEntityManager();

        // Act
        testUser = userService.read(id);

        // Assert
        assertThat(testUser.getFirstName()).isNullOrEmpty();
    }

    /**
     * Test how the Entities are attached and detached.
     */
    @Test
    public void testAttach() {
        // Arrange
        final String firstName = "FirstName";

        User testUser = new User("testId");
        Long id = userService.save(testUser).getId();
        resetEntityManager();
        testUser = userService.read(id);
        testUser.setFirstName(firstName);
        userService.save(testUser);
        em.flush();
        resetEntityManager();

        // Act
        testUser = userService.read(id);

        // Assert
        assertThat(testUser.getFirstName()).isEqualTo(firstName);
    }

    /**
     * Reset entity-Manager this should fail (no-transaction), but to be sure.
     */
    private void resetEntityManager() {
        try {
            em.clear();
        } catch (TransactionRequiredException e) {
            // expected.
        }
    }
}
