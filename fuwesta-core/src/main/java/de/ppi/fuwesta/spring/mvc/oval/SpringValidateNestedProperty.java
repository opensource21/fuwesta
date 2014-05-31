package de.ppi.fuwesta.spring.mvc.oval;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation tells custom spring validator to follow the path and validate
 * a nested property object.
 * 
 * @author niels based on the work of SergeyZ
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SpringValidateNestedProperty {
    // Marker-Annotation.
}
