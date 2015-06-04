package de.ppi.samples.fuwesta.dao.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.BeforeTransaction;

import de.ppi.samples.fuwesta.config.RootConfig;
import de.ppi.samples.fuwesta.model.Post;
import de.ppi.samples.fuwesta.model.Tag;
import de.ppi.samples.fuwesta.model.User;

/**
 * Test some functionality of {@link PostDao} to get an better understanding of
 * JPA.
 *
 */
@ContextConfiguration(classes = RootConfig.class)
public class PostDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    /**
     * Name of the table user.
     */
    private static final String POST_TABLE_NAME = "POST";

    /** The user dao. */
    @Resource
    private PostDao postDao;

    /** The user dao. */
    @Resource
    private UserDao userDao;

    /** The user dao. */
    @Resource
    private TagDao tagDao;

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
        if (!deleted
                && (countRowsInTable(POST_TABLE_NAME) > 0 || countRowsInTable("T_USER") > 0)) {
            deleteFromTables("TAG_POSTINGS", "TAG", "POST", "T_USER");
            deleted = true;
        }
        if (countRowsInTable(POST_TABLE_NAME) == 0) {
            setSqlScriptEncoding("UTF-8");
            executeSqlScript("classpath:post-data.sql", false);

        }
    }

    // /**
    // * @throws java.lang.Exception
    // */
    // @Before
    // public void setUp() throws Exception {
    // }
    //
    // /**
    // * @throws java.lang.Exception
    // */
    // @After
    // public void tearDown() throws Exception {
    // }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#save(S)}.
     */
    @SuppressWarnings("boxing")
    @Test
    @Rollback(true)
    public void testSaveInsert() {
        // Arrange
        User user = userDao.findOne(1L);
        Tag tag = tagDao.findOne(1L);

        Post post = new Post();
        post.setContent("Blafasel");
        post.setCreationTime(new Date());
        post.setTitle("JUnit");
        post.setUser(user);
        post.getTags().add(tag);
        // Act
        postDao.save(post);
        // Assert
        em.flush();
        assertThat(countRowsInTable("TAG_POSTINGS")).isEqualTo(1);
    }

    /**
     * Test method for
     * {@link org.springframework.data.repository.CrudRepository#save(S)}.
     */
    @SuppressWarnings("boxing")
    @Test
    @Rollback(true)
    public void testSaveUpdate() {
        // Arrange
        User user = userDao.findOne(1L);
        Tag tag = tagDao.findOne(2L);

        final Post post = postDao.findOne(1L);
        post.setUser(user);
        post.getTags().add(tag);
        final Post newPost = new Post();
        newPost.setContent(post.getContent());
        newPost.setCreationTime(post.getCreationTime());
        newPost.setId(post.getId());
        newPost.setTags(post.getTags());
        newPost.setTitle(post.getTitle());
        newPost.setUser(post.getUser());
        newPost.setVersion(post.getVersion());
        newPost.getTags().clear();
        // Act
        postDao.save(post);
        // Assert
        em.flush();
        assertThat(countRowsInTable("TAG_POSTINGS")).isEqualTo(1);
    }
}
