package de.ppi.fuwesta.spring.mvc.formatter;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.util.Locale;
import java.util.Set;

import org.junit.Test;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import de.ppi.fuwesta.spring.mvc.formatter.NonEmptyStringAnnotationFormatterFactory;

/**
 * Test {@link NonEmptyStringAnnotationFormatterFactory}.
 * 
 */
public class NonEmptyStringAnnotationFormatterFactoryTest {

    /** Testobjekt. */
    private final NonEmptyStringAnnotationFormatterFactory testee =
            new NonEmptyStringAnnotationFormatterFactory();

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.formatter.NonEmptyStringAnnotationFormatterFactory#getFieldTypes()}
     * .
     */
    @Test
    public void testGetFieldTypes() {
        // Arrange

        // Act
        final Set<Class<?>> fieldTypes = testee.getFieldTypes();
        // Assert
        assertThat(fieldTypes).hasSize(1).contains(String.class);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.formatter.NonEmptyStringAnnotationFormatterFactory#getPrinter(de.ppi.fuwesta.spring.mvc.formatter.NonEmpty, java.lang.Class)}
     * .
     */
    @Test
    public void testGetPrinter() {
        // Arrange
        final String anyString = "Foo";
        // Act
        Printer<String> printer = testee.getPrinter(null, String.class);
        // Assert
        assertThat(printer.print(null, Locale.getDefault())).isEqualTo("null");

        assertThat(printer.print(anyString, null)).isEqualTo(anyString);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.formatter.NonEmptyStringAnnotationFormatterFactory#getParser(de.ppi.fuwesta.spring.mvc.formatter.NonEmpty, java.lang.Class)}
     * .
     * 
     * @throws ParseException if a parse-error happens.
     */
    @Test
    public void testGetParser() throws ParseException {
        // Arrange
        final String anyString = "Foo";
        // Act
        Parser<String> parser = testee.getParser(null, String.class);
        // Assert
        assertThat(parser.parse(null, Locale.getDefault())).isEqualTo(null);

        assertThat(parser.parse(anyString, null)).isEqualTo(anyString);

        assertThat(parser.parse("  ", null)).isEqualTo(null);
    }

}
