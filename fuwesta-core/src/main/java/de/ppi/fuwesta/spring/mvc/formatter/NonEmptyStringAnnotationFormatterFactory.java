package de.ppi.fuwesta.spring.mvc.formatter;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.util.StringUtils;

/**
 * Factory to create {@linkplain Printer} and {@linkplain Parser} for Strings.
 * The implementation doesn't matter because the behavior is created whenever a
 * Printer or Parser is used.
 */
public class NonEmptyStringAnnotationFormatterFactory implements
        AnnotationFormatterFactory<NonEmpty> {

    /**
     * A set of types of fields that may be annotated with the @link
     * {@link NonEmpty} annotation.
     */
    private final Set<Class<?>> fieldTypes;

    /**
     * 
     * Initiates an object of type NonEmptyStringAnnotationFormatterFactory.
     */
    public NonEmptyStringAnnotationFormatterFactory() {
        Set<Class<?>> rawFieldTypes = new HashSet<Class<?>>(1);
        rawFieldTypes.add(String.class);
        this.fieldTypes = Collections.unmodifiableSet(rawFieldTypes);
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public final Set<Class<?>> getFieldTypes() {
        return this.fieldTypes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Printer<String> getPrinter(NonEmpty annotation, Class<?> fieldType) {
        return new Printer<String>() {

            @Override
            public String print(String object, Locale locale) {
                if (object == null) {
                    return "null";
                } else {
                    return object;
                }
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Parser<String> getParser(NonEmpty annotation, Class<?> fieldType) {
        return new Parser<String>() {
            @Override
            public String parse(String text, Locale locale)
                    throws ParseException {
                if (!StringUtils.hasText(text)) {
                    return null;
                } else {
                    return text;
                }

            }
        };
    }

}
