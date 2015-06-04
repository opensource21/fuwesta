package de.ppi.fuwesta.spring.mvc.formatter;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

/**
 * Test {@link MessageSourceDateFormatter}.
 *
 */
public class MessageSourceDateFormatterTest {

    /**
     * German date format.
     */
    private static final String GERMAN_DATE_FORMAT = "dd.MM.yyyy";

    /**
     * The default date-format.
     */
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    private static final String KEY = "key";

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private MessageSourceDateFormatter testeeWithValidKey =
            new MessageSourceDateFormatter(KEY);

    @InjectMocks
    private MessageSourceDateFormatter testeeWithInvalidKey =
            new MessageSourceDateFormatter("");

    private final DateFormat germanDateFormat = new SimpleDateFormat(
            GERMAN_DATE_FORMAT);
    private final DateFormat defaultDateFormat = new SimpleDateFormat(
            DEFAULT_DATE_FORMAT);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(
                messageSource.getMessage(KEY, null, DEFAULT_DATE_FORMAT,
                        Locale.GERMAN)).thenReturn(GERMAN_DATE_FORMAT);
        Mockito.when(
                messageSource.getMessage("", null, DEFAULT_DATE_FORMAT,
                        Locale.GERMAN)).thenReturn(DEFAULT_DATE_FORMAT);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.formatter.MessageSourceDateFormatter#print(java.util.Date, java.util.Locale)}
     * .
     */
    @Test
    public void testPrint() throws Exception {
        // Arrange
        final String testDate = "30.06.2015";
        // Act
        final String result =
                testeeWithValidKey.print(germanDateFormat.parse(testDate),
                        Locale.GERMAN);
        // Assert
        assertThat(result).isEqualTo(testDate);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.formatter.MessageSourceDateFormatter#print(java.util.Date, java.util.Locale)}
     * .
     */
    @Test
    public void testPrintDefaultformat() throws Exception {
        // Arrange
        final String testDate = "2015-06-30";
        // Act
        final String result =
                testeeWithInvalidKey.print(defaultDateFormat.parse(testDate),
                        Locale.GERMAN);
        // Assert
        assertThat(result).isEqualTo(testDate);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.formatter.MessageSourceDateFormatter#parse(java.lang.String, java.util.Locale)}
     * .
     */
    @Test
    public void testParse() throws Exception {
        // Arrange
        final String testDateAsString = "30.06.2015";
        // Act
        final Date result =
                testeeWithValidKey.parse(testDateAsString, Locale.GERMAN);
        // Assert
        assertThat(result).isEqualTo(germanDateFormat.parse(testDateAsString));
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.formatter.MessageSourceDateFormatter#parse(java.lang.String, java.util.Locale)}
     * .
     */
    @Test
    public void testParseDefaultFormat() throws Exception {
        // Arrange
        final String testDateAsString = "2015-06-30";
        // Act
        final Date result =
                testeeWithInvalidKey.parse(testDateAsString, Locale.GERMAN);
        // Assert
        assertThat(result).isEqualTo(defaultDateFormat.parse(testDateAsString));
    }

}
