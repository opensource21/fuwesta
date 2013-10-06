package de.ppi.fuwesta.spring.mvc.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception that shows that a resource is missing and a 404 is sent back.
 * 
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Initiates an object of type ResourceNotFoundException.
     */
    public ResourceNotFoundException() {
        super();
    }

    /**
     * Initiates an object of type ResourceNotFoundException.
     * 
     * @param message the detail message.
     * @param cause the cause. (A {@code null} value is permitted, and indicates
     *            that the cause is nonexistent or unknown.)
     * @param enableSuppression whether or not suppression is enabled or
     *            disabled
     * @param writableStackTrace whether or not the stack trace should be
     *            writable
     */
    public ResourceNotFoundException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Initiates an object of type ResourceNotFoundException.
     * 
     * @param message the detail message.
     * @param cause the cause. (A {@code null} value is permitted, and indicates
     *            that the cause is nonexistent or unknown.)
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Initiates an object of type ResourceNotFoundException.
     * 
     * @param message the detail message.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Initiates an object of type ResourceNotFoundException.
     * 
     * @param cause the cause. (A {@code null} value is permitted, and indicates
     *            that the cause is nonexistent or unknown.)
     */
    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

}
