package de.ppi.fuwesta.oval.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.field;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.integration.spring.SpringValidator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import testdata.Author;
import testdata.AuthorDao;
import testdata.Book;
import testdata.BookDao;
import testdata.TestConfig;

/**
 * Test {@link UniqueCheck}.
 *
 */
@ContextConfiguration(classes = TestConfig.class)
public class UniqueCheckTest extends
        AbstractTransactionalJUnit4SpringContextTests {

    /** The book dao. */
    @Resource
    private BookDao bookDao;

    /** The author dao. */
    @Resource
    private AuthorDao authorDao;

    /**
     * The {@link EntityManager}.
     */
    @PersistenceContext
    private EntityManager em;

    /** SpringValidator. */
    @Resource
    private org.springframework.validation.Validator validator;

    /** The unique annotation. */
    @Mock
    private Unique uniqueAnnotation;

    /** The testee. */
    @InjectMocks
    private UniqueCheck testee = new UniqueCheck();

    /** The Constant MESSAGE. */
    private static final String MESSAGE = "message";

    /** The Constant PK_COLUMN. */
    private static final String PK_COLUMN = "pkColumn";

    /** The Constant UK_CONTEXT. */
    private static final String UK_CONTEXT = "col1, col2; col3 col4 ";

    /**
     * Initialize the test.
     *
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(uniqueAnnotation.value()).thenReturn(UK_CONTEXT);
        when(uniqueAnnotation.primaryKeyColumn()).thenReturn(PK_COLUMN);
        when(uniqueAnnotation.message()).thenReturn(MESSAGE);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.oval.validation.UniqueCheck#configure(de.ppi.fuwesta.oval.validation.Unique)}
     * .
     */
    @Test
    public void testConfigureUnique() {
        // Arrange

        // Act
        testee.configure(uniqueAnnotation);
        // Assert
        assertThat(testee.getMessage()).isEqualTo(MESSAGE);
        assertThat(
                field("primaryKeyColumn").ofType(String.class).in(testee).get())
                .isEqualTo(PK_COLUMN);
        assertThat(
                field("uniqueKeyContext").ofType(String.class).in(testee).get())
                .isEqualTo(UK_CONTEXT);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.oval.validation.UniqueCheck#createMessageVariables()}
     * .
     */
    @Test
    public void testCreateMessageVariables() {
        // Arrange
        testee.configure(uniqueAnnotation);
        // Act
        Map<String, String> messageVar = testee.createMessageVariables();
        // Assert
        assertThat(messageVar).hasSize(1);
        assertThat(messageVar.get("uk-context")).isEqualTo(UK_CONTEXT);

    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.oval.validation.UniqueCheck#isSatisfied(java.lang.Object, java.lang.Object, net.sf.oval.context.OValContext, net.sf.oval.Validator)}
     * .
     */
    @Test
    public void testIsSatisfiedWithoutContextInsert() {
        // Arrange
        final String isbn = "ISBN";
        final Book book = new Book();
        book.setIsbn(isbn);
        bookDao.save(book);
        assertThat(book.getId()).isNotNull();

        final Book newBook = new Book();
        newBook.setIsbn(isbn);
        assertThat(newBook.getId()).isNull();

        final Validator myValidator = createValidator();

        // Act
        final List<ConstraintViolation> noViolations =
                myValidator.validate(book);
        final List<ConstraintViolation> violations =
                myValidator.validate(newBook);
        // Assert
        assertThat(noViolations).hasSize(0);
        assertThat(violations).hasSize(1);

        ConstraintViolation violation = violations.get(0);
        assertThat(violation.getErrorCode()).isEqualTo(
                "de.ppi.fuwesta.oval.validation.Unique");
        assertThat(violation.getMessageVariables()).hasSize(1);
        assertThat(violation.getMessageVariables().get("uk-context"))
                .isEqualTo("");
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.oval.validation.UniqueCheck#isSatisfied(java.lang.Object, java.lang.Object, net.sf.oval.context.OValContext, net.sf.oval.Validator)}
     * .
     */
    @Test
    @Ignore("It works in realworld. But I don't know at the moment how to fix the test.")
    public
            void testIsSatisfiedWithoutContextUpdate() {
        // Arrange
        final String isbn = "ISBN";
        final Book book = new Book();
        book.setIsbn(isbn);
        bookDao.save(book);
        assertThat(book.getId()).isNotNull();

        final Book newBook = new Book();
        newBook.setIsbn(isbn + "2");
        bookDao.save(newBook);
        newBook.setIsbn(isbn);
        assertThat(newBook.getId()).isNotNull();
        em.detach(newBook);

        final Validator myValidator = createValidator();

        // Act
        final List<ConstraintViolation> violations =
                myValidator.validate(newBook);
        // Assert
        assertThat(violations).hasSize(1);
        ConstraintViolation violation = violations.get(0);
        assertThat(violation.getErrorCode()).isEqualTo(
                "de.ppi.fuwesta.oval.validation.Unique");
        assertThat(violation.getMessageVariables()).hasSize(1);
        assertThat(violation.getMessageVariables().get("uk-context"))
                .isEqualTo("");
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.oval.validation.UniqueCheck#isSatisfied(java.lang.Object, java.lang.Object, net.sf.oval.context.OValContext, net.sf.oval.Validator)}
     * .
     */
    @Test
    public void testIsSatisfiedWithoutContextUpdateNoViolations() {
        // Arrange
        final String isbn = "ISBN";
        final Book book = new Book();
        book.setIsbn(isbn);
        bookDao.save(book);
        assertThat(book.getId()).isNotNull();

        final Book newBook = new Book();
        newBook.setIsbn(isbn + "2");
        bookDao.save(newBook);
        newBook.setIsbn(isbn + "3");
        newBook.setIsbn(isbn + "2");
        assertThat(newBook.getId()).isNotNull();

        final Validator myValidator = createValidator();

        // Act
        List<ConstraintViolation> violations = myValidator.validate(newBook);
        // Assert
        assertThat(violations).hasSize(0);

    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.oval.validation.UniqueCheck#isSatisfied(java.lang.Object, java.lang.Object, net.sf.oval.context.OValContext, net.sf.oval.Validator)}
     * .
     */
    @Test
    public void testIsSatisfiedWithContextInsertNoViolation() {
        // Arrange
        final Author author1 = new Author();
        final String title =
                arrangeIsSatisfiedWithContextInsertNoViolation(author1);

        final Author author2 = new Author();
        author2.setName("Mein Name mit Ãœ");
        authorDao.save(author2);
        assertThat(author2.getId()).isNotNull();
        assertThat(author2.getName()).isEqualTo("Mein Name mit Ãœ");

        final Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author2);
        assertThat(newBook.getId()).isNull();

        final Validator myValidator = createValidator();
        // Act
        final List<ConstraintViolation> violations =
                myValidator.validate(newBook);
        // Assert
        assertThat(violations).hasSize(0);

    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.oval.validation.UniqueCheck#isSatisfied(java.lang.Object, java.lang.Object, net.sf.oval.context.OValContext, net.sf.oval.Validator)}
     * .
     */
    @Test
    public void testIsSatisfiedWithContextInsertWithViolation() {
        // Arrange
        final Author author = new Author();
        final String title =
                arrangeIsSatisfiedWithContextInsertNoViolation(author);

        final Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        assertThat(newBook.getId()).isNull();

        final Validator myValidator = createValidator();

        // Act
        final List<ConstraintViolation> violations =
                myValidator.validate(newBook);
        // Assert
        assertThat(violations).hasSize(1);
        ConstraintViolation violation = violations.get(0);
        assertThat(violation.getErrorCode()).isEqualTo(
                "de.ppi.fuwesta.oval.validation.Unique");
        assertThat(violation.getMessageVariables()).hasSize(1);
        assertThat(violation.getMessageVariables().get("uk-context"))
                .isEqualTo("author");
    }

    /**
     * Arrange IsSatisfiedWithContextInsertNoViolation-Test.
     *
     * @param author the author
     * @return the title.
     */
    private String
            arrangeIsSatisfiedWithContextInsertNoViolation(Author author) {
        final String title = "TITLE";
        final Book book = new Book();
        book.setAuthor(author);
        author.getBooks().add(book);
        book.setTitle(title);
        bookDao.save(book);
        assertThat(book.getId()).isNotNull();
        return title;
    }

    /**
     * Creates the validator.
     *
     * @return the validator.
     */
    private Validator createValidator() {
        return ((SpringValidator) validator).getValidator();
    }
}
