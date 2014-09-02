package de.ppi.fuwesta.spring.mvc.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * This is a basic version of a global exception handler, it should normally
 * activated by a subclass which is annotated with {@link ControllerAdvice}.
 * It's inspired by
 * http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc.
 * 
 * 
 */
public abstract class BasicGlobalExceptionHandler {

    /**
     * The Logger for the controller.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(BasicGlobalExceptionHandler.class);

    private final String defaultErrorView;

    /**
     * This array is a lookup table that translates 6-bit positive integer index
     * values into their "Base64 Alphabet" equivalents as specified in Table 1
     * of RFC 2045.
     */
    private static final char INT_TO_BASE_64[] = { 'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9', '+', '/' };

    private final String newLine = System.getProperty("line.separator");

    /**
     * Initiates an object of type BasicGlobalExceptionHandler.
     * 
     * @param defaultErrorView the default error view.
     */
    public BasicGlobalExceptionHandler(String defaultErrorView) {
        super();
        this.defaultErrorView = defaultErrorView;
    }

    /**
     * Log information about the request and the exception.
     * 
     * @param req the current request.
     * @param e the exception.
     * @return model and view information.
     * @throws Exception if the exception has defined a {@link ResponseStatus}.
     */
    protected ModelAndView handleException(HttpServletRequest req, Exception e)
            throws Exception {
        final String exceptionId = getUniqueId();
        final String logExceptionId = exceptionId + ":";
        LOG.error(logExceptionId
                + "Uncaught exception occured, user get's an error page.", e);
        LOG.error(logExceptionId + "Requested-URL: {}, method: {}",
                req.getRequestURL(), req.getMethod());
        final List<String> headerInfos = getHeaderInfos(req);
        final Date time = new Date();
        for (String header : headerInfos) {
            LOG.error(logExceptionId + header);
        }

        final List<String> parameterInfos = getParameterInfos(req);
        for (String parameter : parameterInfos) {
            LOG.error(logExceptionId + parameter);
        }

        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        final StringBuilder stackTrace = new StringBuilder();
        for (final StackTraceElement part : e.getStackTrace()) {
            stackTrace.append(part).append(newLine);
        }

        // Otherwise setup and send the user to a default error-view.
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("exceptionId", exceptionId);
        mav.addObject("stacktrace", stackTrace);
        mav.addObject("headerInfos", headerInfos);
        mav.addObject("parameterInfos", parameterInfos);
        mav.addObject("time", time);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(defaultErrorView);
        return mav;
    }

    /**
     * Extract all header-information from the request.
     * 
     * @param req the current request.
     * @return a list with header informations.
     */
    protected List<String> getHeaderInfos(HttpServletRequest req) {
        final List<String> result = new ArrayList<String>();
        @SuppressWarnings("unchecked")
        final Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames != null && headerNames.hasMoreElements()) {
            final String headerName = headerNames.nextElement();
            result.add("Header " + headerName + " = "
                    + req.getHeader(headerName));
        }
        return result;
    }

    /**
     * Extract all parameter from the request.
     * 
     * @param req the current request.
     * @return a list with parameter informations.
     */
    protected List<String> getParameterInfos(HttpServletRequest req) {
        final List<String> result = new ArrayList<String>();
        @SuppressWarnings("unchecked")
        final Map<String, String[]> parameters = req.getParameterMap();
        if (parameters == null) {
            return result;
        }
        for (Entry<String, String[]> parameter : parameters.entrySet()) {
            result.add("Parameter " + parameter.getKey() + " = "
                    + Arrays.toString(parameter.getValue()));
        }
        return result;
    }

    /**
     * Creates a uniqueId which should be unique in a year, if in one thread
     * there are no more then 1 per ms.
     * 
     * @return a uniqueId.
     */
    private final String getUniqueId() {
        StringBuilder result = new StringBuilder(8);
        Calendar now = Calendar.getInstance();
        result.append(INT_TO_BASE_64[(int) (Thread.currentThread().getId() % 64)]);
        result.append(INT_TO_BASE_64[now.get(Calendar.MONTH) % 64]);
        result.append(INT_TO_BASE_64[now.get(Calendar.DAY_OF_MONTH) % 64]);
        result.append(INT_TO_BASE_64[now.get(Calendar.HOUR_OF_DAY) % 64]);
        result.append(INT_TO_BASE_64[now.get(Calendar.MINUTE) % 64]);
        result.append(INT_TO_BASE_64[now.get(Calendar.SECOND) % 64]);
        result.append(now.get(Calendar.MILLISECOND));
        return result.toString();
    }
}
