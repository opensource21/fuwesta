package de.ppi.fuwesta.spring.mvc.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Properties;

import org.junit.Test;

import de.ppi.fuwesta.spring.mvc.util.EntityPropertiesToMessages;

/**
 * Class EntityPropertiesToMessagesTest
 * 
 */
public class EntityPropertiesToMessagesTest {

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.util.EntityPropertiesToMessages#getProperties()}
     * .
     */
    @Test
    public void testGetProperties() {
        final EntityPropertiesToMessages testee =
                new EntityPropertiesToMessages("testdata");

        final Properties actual = testee.getProperties();

        assertThat(actual).hasSize(10);
        assertThat(actual).containsEntry("db.SubClass.test", "test")
                .containsEntry("db.SubClass.subTest", "subTest")
                .containsEntry("db.Book.author", "author")
                .containsEntry("db.Author.books", "books");

    }
}
