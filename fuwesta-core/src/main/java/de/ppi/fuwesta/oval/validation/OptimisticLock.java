package de.ppi.fuwesta.oval.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Checks that the entity wasn't changed from other person since last read.
 * Message
 * <ul>
 * <li>key: validation.optimistic.lock</li>
 * <li>{context}: class name</li>
 * <li>{urlReload}: Url to reload the page.</li>
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@DBConstraint(checkWith = OptimisticLockCheck.class)
public @interface OptimisticLock {

    /**
     * The column which hold the version, normally "version".
     */
    String value() default "version";

    /**
     * The message, default "validation.optimistic.lock".
     * 
     */
    String message() default OptimisticLockCheck.MESSAGE;

    /**
     * The primary-key column, normally "id".
     */
    String primaryKeyColumn() default "id";
}
