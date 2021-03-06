package de.ppi.fuwesta.spring.mvc.oval;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import de.ppi.fuwesta.spring.mvc.oval.SpringMvcMessageResolver;

/**
 * Test of {@link SpringMvcMessageResolver}.
 * 
 */
public class SpringMvcMessageResolverTest {

    /**
     * Messages-Mock.
     */
    @Mock
    private MessageSource messages;

    /**
     * Test-object.
     */
    @InjectMocks
    private SpringMvcMessageResolver testee;

    /** Test locale. */
    private Locale locale = Locale.CHINA;

    /**
     * Perpare test.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        LocaleContextHolder.setLocale(locale);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.oval.SpringMvcMessageResolver#getMessage(java.lang.String)}
     * .
     */
    @Test
    public void testGetMessage() {
        // Arrange
        final String value = "key.something";
        final String expectedResult = "Some Text";
        when(messages.getMessage(value, null, value, locale)).thenReturn(
                expectedResult);
        // Act
        final String testResult = testee.getMessage(value);
        // Assert
        assertThat(testResult).isEqualTo(expectedResult);
    }

}
