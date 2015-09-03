package de.ppi.samples.fuwesta.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import de.ppi.samples.fuwesta.FuWeStaServer;
import de.ppi.samples.fuwesta.dbunit.FuWeStaSampleDatabase;
import de.ppi.samples.fuwesta.model.User;
import de.ppi.samples.fuwesta.service.api.UserService;

/**
 * Class for testing @link {@link UserServiceImpl} in with attach and detach.
 *
 */
@SpringApplicationConfiguration(classes = FuWeStaServer.class)
@WebAppConfiguration
public class UserServiceImplReadOnlyTest extends
        AbstractJUnit4SpringContextTests {

    /** The user service. */
    @Resource(name = "readOnlyUserService")
    private UserService userService;

    /**
     * The datasource.
     */
    @Resource
    private DataSource dataSource;

    /**
     * Datasource configuration.
     */
    @Resource
    private DataSourceProperties dataSourceProperties;

    /**
     * Makes a cleanup.
     *
     * @throws Exception problems in init.
     */
    @Before
    public void init() throws Exception {
        final FuWeStaSampleDatabase db =
                new FuWeStaSampleDatabase(dataSource.getConnection(),
                        dataSourceProperties.getSchema());
        db.initDatabase();
        db.clean();
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
