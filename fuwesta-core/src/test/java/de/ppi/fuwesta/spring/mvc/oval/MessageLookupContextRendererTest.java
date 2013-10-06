package de.ppi.fuwesta.spring.mvc.oval;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Locale;

import net.sf.oval.context.ClassContext;
import net.sf.oval.context.FieldContext;
import net.sf.oval.context.OValContext;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import de.ppi.fuwesta.spring.mvc.oval.MessageLookupContextRenderer;

/**
 * Test {@link MessageLookupContextRenderer}.
 * 
 */
public class MessageLookupContextRendererTest {

    /**
     * Messages-Mock.
     */
    @Mock
    private MessageSource messages;

    /**
     * Test-object.
     */
    @InjectMocks
    private MessageLookupContextRenderer testee;

    /** Test locale. */
    private Locale locale = Locale.CHINA;

    /**
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        LocaleContextHolder.setLocale(locale);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.oval.MessageLookupContextRenderer#render(net.sf.oval.context.OValContext)}
     * .
     * 
     * @throws Exception if something goes wrong.
     */
    @Test
    public void testRenderOValContext() throws Exception {
        // Arrange
        final String fieldName = "messages";
        final OValContext ovalContext =
                new FieldContext(
                        MessageLookupContextRendererTest.class
                                .getDeclaredField(fieldName));
        final String code =
                MessageLookupContextRendererTest.class.getName() + "."
                        + fieldName;
        final String expectedResult = "Teststring";
        when(messages.getMessage(code, null, code, locale)).thenReturn(
                expectedResult);

        // Act
        final String result = testee.render(ovalContext);
        // Assert

        assertThat(result).isEqualTo(expectedResult);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.oval.MessageLookupContextRenderer#render(net.sf.oval.context.OValContext)}
     * .
     */
    @Test
    public void testRenderOValContextNonFieldContext() {
        // Arrange
        final OValContext ovalContext =
                new ClassContext(MessageLookupContextRendererTest.class);

        final String expectedResult =
                MessageLookupContextRendererTest.class.getName();

        // Act
        final String result = testee.render(ovalContext);
        // Assert
        verify(messages, times(0)).getMessage(any(String.class),
                any(Object[].class), anyString(), any(Locale.class));
        assertThat(result).isEqualTo(expectedResult);
    }
}
