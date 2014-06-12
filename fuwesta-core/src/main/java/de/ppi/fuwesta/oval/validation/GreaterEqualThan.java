package de.ppi.fuwesta.oval.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.oval.configuration.annotation.Constraint;

/**
 * Check if value of this comparable field is greater or equal than the
 * referenced field.
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(checkWith = GreaterEqualThanCheck.class)
public @interface GreaterEqualThan {

    /**
     * Referenced field.
     */
    String value();

    /**
     * The MessageKey.
     */
    String message() default GreaterEqualThanCheck.MESSAGE;

}
