package de.ppi.fuwesta.spring.mvc.exception;

import java.util.ArrayList;
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
        LOG.error("Uncaught exception occured, user get's an error page.", e);
        LOG.error("Requested-URL: {}, method: {}", req.getRequestURL(),
                req.getMethod());
        final List<String> headerInfos = getHeaderInfos(req);
        final Date time = new Date();
        for (String header : headerInfos) {
            LOG.error(header);
        }

        final List<String> parameterInfos = getParameterInfos(req);
        for (String parameter : parameterInfos) {
            LOG.error(parameter);
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
        final Map<?, ?> parameters = req.getParameterMap();
        if (parameters == null) {
            return result;
        }
        for (Entry<?, ?> parameter : parameters.entrySet()) {
            result.add("Parameter " + parameter.getKey() + " = "
                    + parameter.getValue());
        }
        return result;
    }

}
