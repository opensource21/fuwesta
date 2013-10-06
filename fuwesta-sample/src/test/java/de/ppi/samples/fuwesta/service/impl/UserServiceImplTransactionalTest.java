package de.ppi.samples.fuwesta.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.BeforeTransaction;

import de.ppi.samples.fuwesta.config.RootConfig;
import de.ppi.samples.fuwesta.model.User;
import de.ppi.samples.fuwesta.service.api.UserService;

/**
 * Class for testing @link {@link UserServiceImpl} in a transactional context.
 * 
 */
@ContextConfiguration(classes = RootConfig.class)
public class UserServiceImplTransactionalTest extends
        AbstractTransactionalJUnit4SpringContextTests {
    /**
     * Name of the table user.
     */
    private static final String USER_TABLE_NAME = "T_USER";

    /**
     * Number of users in testdata.
     */
    private static final int NUMBER_OF_USERS = 4;

    /** The user dao. */
    @Resource
    private UserService userService;

    /**
     * Inserting Testdata.
     * 
     * @throws Exception if something fails.
     */
    @BeforeTransaction
    public void setupData() throws Exception {
        if (countRowsInTable(USER_TABLE_NAME) != NUMBER_OF_USERS) {
            deleteFromTables(USER_TABLE_NAME, "POST", "TAG");
            executeSqlScript("classpath:user-data.sql", false);

        }
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.UserServiceImpl#getAllUser()}
     * .
     */
    @Test
    public void testGetAllUser() {
        final Page<User> result = userService.getAllUser();
        assertThat(countRowsInTable(USER_TABLE_NAME))
                .isEqualTo(NUMBER_OF_USERS);
        assertThat(result.getNumber()).isEqualTo(0);
        assertThat(result.getNumberOfElements()).isEqualTo(NUMBER_OF_USERS);
        assertThat(result.getSize()).isEqualTo(0);
        assertThat(result.getTotalElements()).isEqualTo(NUMBER_OF_USERS);
        assertThat(result.getTotalPages()).isEqualTo(1);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.UserServiceImpl#getUser(int, int, org.springframework.data.domain.Sort.Order[])}
     * .
     */
    // CSOFF: MagicNumber
    @Test
    public void testGetUserSkip0Page2() {
        testGetUser(0, 2);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.UserServiceImpl#getUser(int, int, org.springframework.data.domain.Sort.Order[])}
     * .
     */
    @Test
    public void testGetUserSkip0Page4() {
        testGetUser(0, 4);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.UserServiceImpl#getUser(int, int, org.springframework.data.domain.Sort.Order[])}
     * .
     */
    @Test
    public void testGetUserSkip3Page2() {
        testGetUser(3, 2);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.UserServiceImpl#getUser(int, int, org.springframework.data.domain.Sort.Order[])}
     * .
     */
    @Test
    public void testGetUserSkip2Page2() {
        testGetUser(3, 3);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.UserServiceImpl#getUser(int, int, org.springframework.data.domain.Sort.Order[])}
     * .
     */
    @Test
    public void testGetUserSkip3Page3() {
        testGetUser(3, 3);
    }

    // CSON: module_name

    /**
     * Test method for
     * {@link de.ppi.fuwesta.samples.springmvc.service.impl.UserServiceImpl#getUser(int, int, org.springframework.data.domain.Sort.Order[])}
     * .
     * 
     * @param skipSize the size of users which should be skipped
     * @param pageSize the pagesize
     */
    private void testGetUser(int skipSize, int pageSize) {
        final Page<User> result = userService.getUser(skipSize, pageSize);

        int nrOfEntriesOnPage = pageSize;
        if (NUMBER_OF_USERS - (skipSize / pageSize) * pageSize < pageSize) {
            nrOfEntriesOnPage = NUMBER_OF_USERS % pageSize;
        }

        assertThat(result.getNumber()).isEqualTo(skipSize / pageSize);
        assertThat(result.getNumberOfElements()).isEqualTo(nrOfEntriesOnPage);
        assertThat(result.getSize()).isEqualTo(pageSize);
        assertThat(result.getTotalElements()).isEqualTo(NUMBER_OF_USERS);
        final int nrOfTotalPages =
                (NUMBER_OF_USERS % pageSize == 0) ? NUMBER_OF_USERS / pageSize
                        : NUMBER_OF_USERS / pageSize + 1;
        assertThat(result.getTotalPages()).isEqualTo(nrOfTotalPages);
    }

}
