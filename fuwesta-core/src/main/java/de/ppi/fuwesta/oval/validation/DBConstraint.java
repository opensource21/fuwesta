package de.ppi.fuwesta.oval.validation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.oval.configuration.annotation.AnnotationCheck;

/**
 * Annotations tagged with this annotation represent single-value constraints,
 * which needs to connect to DB for Validation.
 * 
 * @author niels
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface DBConstraint {
    /**
     * The class implementing the constraint logic. It can check if a value
     * satisfies the constraint.
     */
    Class<? extends AnnotationCheck<? extends Annotation>> checkWith();
}
