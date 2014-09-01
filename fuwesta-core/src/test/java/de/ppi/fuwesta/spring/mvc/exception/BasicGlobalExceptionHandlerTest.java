package de.ppi.fuwesta.spring.mvc.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import de.ppi.fuwesta.spring.mvc.util.ResourceNotFoundException;

/**
 * Class BasicGlobalExceptionHandlerTest
 * 
 */
public class BasicGlobalExceptionHandlerTest {

    /**
     * Comment for <code>VIEW_NAME</code>
     */
    private static final String VIEW_NAME = "Test";

    private final HttpServletRequest requestMock =
            mock(HttpServletRequest.class);

    private final BasicGlobalExceptionHandler testee =
            new BasicGlobalExceptionHandler(VIEW_NAME) {
                // Nothing to overwrite.
            };

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.exception.BasicGlobalExceptionHandler#handleException(javax.servlet.http.HttpServletRequest, java.lang.Exception)}
     * .
     */
    @Test
    public void testHandleExceptionWithResponseCode() throws Exception {
        final Exception e = new ResourceNotFoundException();

        try {
            testee.handleException(requestMock, e);
            fail("Exception should happend");
        } catch (ResourceNotFoundException ce) {
            assertThat(ce).isSameAs(e);
        }

    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.exception.BasicGlobalExceptionHandler#handleException(javax.servlet.http.HttpServletRequest, java.lang.Exception)}
     * .
     */
    @Test
    public void testHandleException() throws Exception {
        final Exception e = new Exception();

        final ModelAndView mav = testee.handleException(requestMock, e);

        assertThat(mav.getViewName()).isEqualTo(VIEW_NAME);
        assertThat(mav.getModel()).containsKeys("stacktrace", "exception",
                "headerInfos", "parameterInfos", "url", "time");
        assertThat(mav.getModel().get("exception")).isSameAs(e);
        assertThat(mav.getModel().get("headerInfos")).isNotNull();
        assertThat(mav.getModel().get("parameterInfos")).isNotNull();
        assertThat(mav.getModel().get("stacktrace")).isNotNull();
        final Date time = (Date) mav.getModel().get("time");
        assertThat(time).isNotNull();
        assertThat(System.currentTimeMillis() - time.getTime()).isLessThan(100);
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.exception.BasicGlobalExceptionHandler#getHeaderInfos(javax.servlet.http.HttpServletRequest)}
     * .
     */
    @Test
    public void testGetHeaderInfos() {
        when(requestMock.getHeaderNames()).thenReturn(
                Collections.enumeration(Arrays.asList("A", "B")));
        when(requestMock.getHeader("A")).thenReturn("Value A");
        when(requestMock.getHeader("B")).thenReturn("Value B");

        final List<String> headerInfos = testee.getHeaderInfos(requestMock);

        assertThat(headerInfos).hasSize(2).containsOnly("Header A = Value A",
                "Header B = Value B");
    }

    /**
     * Test method for
     * {@link de.ppi.fuwesta.spring.mvc.exception.BasicGlobalExceptionHandler#getParameterInfos(javax.servlet.http.HttpServletRequest)}
     * .
     */
    @Test
    public void testGetParameterInfos() {
        final Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("A", "Value A");
        paramMap.put("B", "Value B");
        when(requestMock.getParameterMap()).thenReturn(paramMap);

        final List<String> headerInfos = testee.getParameterInfos(requestMock);

        assertThat(headerInfos).hasSize(2).containsOnly(
                "Parameter A = Value A", "Parameter B = Value B");

    }

}
