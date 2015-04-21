package de.ppi.samples.fuwesta.dao.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.BeforeTransaction;

import de.ppi.samples.fuwesta.config.RootConfig;
import de.ppi.samples.fuwesta.model.Post;
import de.ppi.samples.fuwesta.model.User;

/**
 * Class UserDaoTest. This test is more for demonstration what and how you can
 * test with spring-data. It tries to show the possibilities. How ever in a real
 * project it's useless to test generated source.
 *
 */
// @ContextConfiguration("classpath:test-context.xml")
@ContextConfiguration(classes = RootConfig.class)
public class UserDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

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
    private UserDao userDao;

    /** Flag that testdata are deleted. */
    private boolean deleted = false;

    /**
     * The {@link EntityManager}.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Inserting Testdata.
     *
     * @throws Exception if something fails.
     */
    @BeforeTransaction
    public void setupData() throws Exception {
        if (!deleted && countRowsInTable(USER_TABLE_NAME) > 0) {
            deleteFromTables(USER_TABLE_NAME);
            deleteFromTables("POST");
            deleted = true;
        }
        if (countRowsInTable(USER_TABLE_NAME) == 0) {
            setSqlScriptEncoding("UTF-8");
            executeSqlScript("classpath:user-data.sql", false);

        }
    }

    /**
     * Test method for {@link UserDao#findByFirstName(String)}.
     */
    @Test
    public void testFindByFirstName() {
        User testUser = userDao.findByFirstName("Erwin");
        assertThat(testUser).isNotNull();
        assertThat(testUser.getFirstName()).isEqualTo("Erwin");
    }

    /**
     * Test method for {@link UserDao#findDistinctUserByPostingsTitle(String)}.
     */
    @Test
    @Rollback(true)
    public void testFindByPostingsTitle() {
        // Check that precondition are as expected.
        assertNumberOfEntries(NUMBER_OF_USERS);
        List<User> checkUsers =
                userDao.findDistinctUserByPostingsTitleLikeOrderByLastNameAsc("Test");
        assertThat(checkUsers).isEmpty();

        // arrange test-data
        final User dummyUser = userDao.findByUserId("emum");
        List<User> testUser =
                userDao.findAllByFirstNameOrderByLastNameAsc("Erna");
        assertThat(testUser).hasSize(2);
        for (User user : testUser) {
            final Post post = new Post();
            post.setTitle("SomeTestTitel" + user.getId());
            post.setUser(dummyUser);
            user.getPostings().add(post);
        }

        // act
        checkUsers =
                userDao.findDistinctUserByPostingsTitleLikeOrderByLastNameAsc("%Test%");

        // assert that every thing is as expected
        assertThat(checkUsers).isNotNull();
        assertThat(checkUsers).isEqualTo(testUser);
        assertNumberOfEntries(NUMBER_OF_USERS);
        assertThat(countRowsInTable("POST")).isEqualTo(2);
    }

    /**
     * Test method for {@link UserDao#nonsensFinder(String, String, String)}.
     */
    @Test
    @Rollback(true)
    public void testNonsensFinder() {
        // Check that precondition are as expected.
        assertNumberOfEntries(NUMBER_OF_USERS);
        List<User> checkUsers = userDao.nonsensFinder("Erna", "Erwin", "Test");
        assertThat(checkUsers).isEmpty();

        // arrange test-data
        List<User> testUser =
                userDao.findAllByFirstNameOrderByLastNameAsc("Erna");
        assertThat(testUser).hasSize(2);
        for (User user : testUser) {
            final Post post = new Post();
            post.setTitle("Test");
            user.getPostings().add(post);
        }

        // act
        checkUsers = userDao.nonsensFinder("Erna", "Erwin", "Test");

        // assert that every thing is as expected
        assertThat(checkUsers).isNotNull();
        assertThat(checkUsers).isEqualTo(testUser);
        assertNumberOfEntries(NUMBER_OF_USERS);
        assertThat(countRowsInTable("POST")).isEqualTo(2);
    }

    /**
     * Test method for
     * {@link UserDao#findAllByFirstNameOrderByLastNameAsc(String)} .
     */
    @Test
    public void testFindAllByFirstNameOrderByLastNameAsc() {
        List<User> testUser =
                userDao.findAllByFirstNameOrderByLastNameAsc("Erna");
        assertThat(testUser).isNotNull();
        assertThat(testUser.get(0).getLastName()).isEqualTo("Musterfrau");
        assertThat(testUser.get(1).getLastName()).isEqualTo("Müller");
    }

    /**
     * Test method for
     * {@link UserDao#findAllByLastNameOrderByFirstNameDesc(String)}.
     */
    @Test
    public void testFindAllByLastNameOrderByFirstNameDesc() {
        List<User> testUsers =
                userDao.findAllByLastNameOrderByFirstNameDesc("Mustermann");
        assertThat(testUsers).isNotNull().hasSize(2);
        assertThat(testUsers.get(0).getFirstName()).isEqualTo("Max");
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.PagingAndSortingRepository#findAll(org.springframework.data.domain.Sort)}
     * .
     */
    @Test
    public void testFindAllSort() {
        final Sort sort = new Sort(new Sort.Order(Direction.DESC, "userId"));
        Iterator<User> userList = userDao.findAll(sort).iterator();
        User user = userList.next();
        assertThat(user.getUserId()).isEqualTo("mmum");
        user = userList.next();
        assertThat(user.getUserId()).isEqualTo("emüf");
        user = userList.next();
        assertThat(user.getUserId()).isEqualTo("emum");
        user = userList.next();
        assertThat(user.getUserId()).isEqualTo("emuf");
        assertThat(userList.hasNext()).isFalse();
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.PagingAndSortingRepository#findAll(org.springframework.data.domain.Pageable)}
     * .
     */
    @Test
    public void testFindAllPageable() {
        final Sort sort = new Sort(new Sort.Order(Direction.DESC, "userId"));
        final int page = 0;
        final int pageSize = 2;
        final Page<User> testusers =
                userDao.findAll(new PageRequest(page, pageSize, sort));
        assertThat(testusers).isNotNull();
        assertThat(testusers.getNumber()).isEqualTo(page);
        assertThat(testusers.getSize()).isEqualTo(pageSize);
        assertThat(testusers.getNumberOfElements()).isEqualTo(2);
        assertThat(testusers.getTotalElements()).isEqualTo(NUMBER_OF_USERS);
        assertThat(testusers.getTotalPages()).isEqualTo(2);
        Iterator<User> userList = testusers.iterator();
        User user = userList.next();
        assertThat(user.getUserId()).isEqualTo("mmum");
        user = userList.next();
        assertThat(user.getUserId()).isEqualTo("emüf");
        assertThat(userList.hasNext()).isFalse();

    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.PagingAndSortingRepository#findAll(org.springframework.data.domain.Pageable)}
     * .
     */
    @Test
    public void testFindAllPageableWithNull() {

        final Page<User> testusers = userDao.findAll((PageRequest) null);
        assertThat(testusers).isNotNull();
        assertThat(testusers.getNumber()).isEqualTo(0);
        assertThat(testusers.getSize()).isEqualTo(0);
        assertThat(testusers.getNumberOfElements()).isEqualTo(NUMBER_OF_USERS);
        assertThat(testusers.getTotalElements()).isEqualTo(NUMBER_OF_USERS);
        assertThat(testusers.getTotalPages()).isEqualTo(1);

    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#save(S)}.
     */
    @Test
    @Rollback(true)
    public void testSaveS() {
        User testUser = new User();
        testUser.setUserId("test");
        Post post1 = new Post();
        post1.setTitle("Test");
        Post post2 = new Post();
        post2.setTitle("Test2");
        testUser.getPostings().add(post1);
        testUser.getPostings().add(post2);
        userDao.save(testUser);
        em.flush();
        assertThat(userDao.findByUserId("test")).isEqualTo(testUser);
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#save(java.lang.Iterable)}
     * .
     */
    @Test
    public void testSaveIterableOfS() {
        // Not tested
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)}
     * .
     */
    @Test
    public void testFindOne() {
        User testUser = userDao.findOne(Long.valueOf(1));
        assertThat(testUser.getLastName()).isEqualTo("Mustermann");
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)}
     * .
     */
    @Test
    public void testExists() {
        assertThat(userDao.exists(Long.valueOf(1))).isTrue();
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#findAll()}.
     */
    @Test
    public void testFindAll() {
        // implicit tested with testDeleteIterableOfQextendsT
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#findAll(java.lang.Iterable)}
     * .
     */
    @Test
    public void testFindAllIterableOfID() {
        // Not tested
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#count()}.
     */
    @Test
    public void testCount() {
        assertThat(userDao.count()).isEqualTo(NUMBER_OF_USERS);
    }

    /**
     * Test method for {@link CustomUserDao#countNumberOfUsers()}.
     */
    @Test
    public void testCountNumberOfUsers() {
        assertThat(userDao.countNumberOfUsers()).isEqualTo(NUMBER_OF_USERS);
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#delete(java.io.Serializable)}
     * .
     */
    @Test
    public void testDeleteID() {
        assertNumberOfEntries(NUMBER_OF_USERS);
        userDao.delete(Long.valueOf(1));
        assertNumberOfEntries(NUMBER_OF_USERS - 1);
        assertThat(userDao.findByUserId("emum")).isNull();
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#delete(java.lang.Object)}
     * .
     */
    @Test
    public void testDeleteT() {
        assertNumberOfEntries(NUMBER_OF_USERS);
        userDao.delete(userDao.findOne(Long.valueOf(1)));
        assertNumberOfEntries(NUMBER_OF_USERS - 1);
        assertThat(userDao.findByUserId("emum")).isNull();
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#delete(java.lang.Iterable)}
     * .
     */
    @Test
    public void testDeleteIterableOfQextendsT() {
        assertNumberOfEntries(NUMBER_OF_USERS);
        userDao.delete(userDao.findAll());
        assertNumberOfEntries(0);
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#deleteAll()}.
     */
    @Test
    public void testDeleteAll() {
        assertNumberOfEntries(NUMBER_OF_USERS);
        userDao.deleteAll();
        assertNumberOfEntries(0);
    }

    /**
     * Assert that the number of entries in the table is the given numbers.
     *
     * @param expectedNumbers the number of expected entries.
     */
    private void assertNumberOfEntries(int expectedNumbers) {
        em.flush();
        assertThat(countRowsInTable(USER_TABLE_NAME))
                .isEqualTo(expectedNumbers);
    }

}
