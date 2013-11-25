package de.ppi.fuwesta.spring.mvc.oval;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.util.Collection;

import net.sf.oval.Check;

/**

 */
public class JPAAnnotationsConfigurer extends
        net.sf.oval.configuration.annotation.JPAAnnotationsConfigurer {

    private final boolean addValidConstraint;

    /**
     * Initiates an object of type JPAAnnotationsConfigurer.
     * 
     */
    public JPAAnnotationsConfigurer() {
        this(true);
    }

    /**
     * Initiates an object of type JPAAnnotationsConfigurer.
     * 
     * @param addValidConstraint true if the @Valid should be added.
     */
    public JPAAnnotationsConfigurer(boolean addValidConstraint) {
        super();
        this.addValidConstraint = addValidConstraint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addAssertValidCheckIfRequired(
            Annotation constraintAnnotation, Collection<Check> checks,
            AccessibleObject fieldOrMethod) {
        if (addValidConstraint) {
            super.addAssertValidCheckIfRequired(constraintAnnotation, checks,
                    fieldOrMethod);
        }
    }

}
