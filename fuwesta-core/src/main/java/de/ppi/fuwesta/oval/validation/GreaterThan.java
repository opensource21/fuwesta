package de.ppi.fuwesta.oval.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.oval.configuration.annotation.Constraint;

/**
 * Check if value of this comparable field is greater than the referenced field.
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(checkWith = GreaterThanCheck.class)
public @interface GreaterThan {

    /**
     * Referenced field.
     */
    String value();

    /**
     * The MessageKey.
     */
    String message() default GreaterThanCheck.MESSAGE;

}
