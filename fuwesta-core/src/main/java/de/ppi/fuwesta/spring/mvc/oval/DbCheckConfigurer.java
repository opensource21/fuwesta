package de.ppi.fuwesta.spring.mvc.oval;

import static net.sf.oval.Validator.getCollectionFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.sf.oval.Check;
import net.sf.oval.collection.CollectionFactory;
import net.sf.oval.configuration.CheckInitializationListener;
import net.sf.oval.configuration.Configurer;
import net.sf.oval.configuration.annotation.AnnotationCheck;
import net.sf.oval.configuration.annotation.AnnotationsConfigurer;
import net.sf.oval.configuration.pojo.elements.ClassConfiguration;
import net.sf.oval.configuration.pojo.elements.ConstraintSetConfiguration;
import net.sf.oval.configuration.pojo.elements.FieldConfiguration;
import net.sf.oval.configuration.pojo.elements.ObjectConfiguration;
import net.sf.oval.exception.ReflectionException;
import net.sf.oval.guard.Guarded;
import net.sf.oval.internal.util.Assert;
import net.sf.oval.internal.util.ReflectionUtils;
import de.ppi.fuwesta.oval.validation.DBConstraint;

/**
 * Configurer that configures constraints based on annotations tagged with
 * {@link DBConstraint}. Based on {@link AnnotationsConfigurer}.
 * 
 * @author niels
 * 
 * 
 * @see AnnotationsConfigurer
 */
public class DbCheckConfigurer implements Configurer {

    /**
     * Set of {@link CheckInitializationListener} to give Spring or Guice a
     * chance to work.
     */
    private final Set<CheckInitializationListener> listeners =
            new LinkedHashSet<CheckInitializationListener>(2);

    /**
     * Add an object to the set of {@link CheckInitializationListener} to give
     * Spring or Guice a chance to work.
     * 
     * @param listener a listener.
     * @return true if this set did not already contain the specified element
     */
    public boolean addCheckInitializationListener(
            final CheckInitializationListener listener) {
        Assert.argumentNotNull("listener", "[listener] must not be null");
        return listeners.add(listener);
    }

    /**
     * Initialiaze all field-defined checks.
     * 
     * @param classCfg the {@link ClassConfiguration}.
     */
    protected void configureFieldChecks(final ClassConfiguration classCfg) {
        final CollectionFactory cf = getCollectionFactory();

        List<Check> checks = cf.createList(2);

        for (final Field field : classCfg.type.getDeclaredFields()) {
            // loop over all annotations of the current field
            for (final Annotation annotation : field.getAnnotations()) {
                // check if the current annotation is a constraint annotation
                if (annotation.annotationType().isAnnotationPresent(
                        DBConstraint.class)) {
                    checks.add(initializeCheck(annotation));
                }
            }

            if (checks.size() > 0) {
                if (classCfg.fieldConfigurations == null) {
                    classCfg.fieldConfigurations = cf.createSet(2);
                }

                final FieldConfiguration fc = new FieldConfiguration();
                fc.name = field.getName();
                fc.checks = checks;
                classCfg.fieldConfigurations.add(fc);
                checks = cf.createList(2); // create a new list for the next
                                           // field with checks
            }
        }
    }

    /**
     * Initialiaze all class-defined checks.
     * 
     * @param classCfg the {@link ClassConfiguration}.
     */
    protected void
            configureObjectLevelChecks(final ClassConfiguration classCfg) {
        final List<Check> checks = getCollectionFactory().createList(2);

        for (final Annotation annotation : ReflectionUtils.getAnnotations(
                classCfg.type, Boolean.TRUE.equals(classCfg.inspectInterfaces))) {
            // check if the current annotation is a constraint annotation
            if (annotation.annotationType().isAnnotationPresent(
                    DBConstraint.class)) {
                checks.add(initializeCheck(annotation));
            }
        }

        if (checks.size() > 0) {
            classCfg.objectConfiguration = new ObjectConfiguration();
            classCfg.objectConfiguration.checks = checks;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClassConfiguration getClassConfiguration(final Class<?> clazz) {
        final ClassConfiguration classCfg = new ClassConfiguration();
        classCfg.type = clazz;

        final Guarded guarded = clazz.getAnnotation(Guarded.class);

        if (guarded == null) {
            classCfg.applyFieldConstraintsToConstructors = Boolean.FALSE;
            classCfg.applyFieldConstraintsToSetters = Boolean.FALSE;
            classCfg.assertParametersNotNull = Boolean.FALSE;
            classCfg.checkInvariants = Boolean.FALSE;
            classCfg.inspectInterfaces = Boolean.FALSE;
        } else {
            classCfg.applyFieldConstraintsToConstructors =
                    Boolean.valueOf(guarded
                            .applyFieldConstraintsToConstructors());
            classCfg.applyFieldConstraintsToSetters =
                    Boolean.valueOf(guarded.applyFieldConstraintsToSetters());
            classCfg.assertParametersNotNull =
                    Boolean.valueOf(guarded.assertParametersNotNull());
            classCfg.checkInvariants =
                    Boolean.valueOf(guarded.checkInvariants());
            classCfg.inspectInterfaces =
                    Boolean.valueOf(guarded.inspectInterfaces());
        }

        configureObjectLevelChecks(classCfg);
        configureFieldChecks(classCfg);
        return classCfg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConstraintSetConfiguration getConstraintSetConfiguration(
            final String constraintSetId) {
        return null;
    }

    /**
     * Initialize the check.
     * 
     * @param constraintAnnotation the {@link DBConstraint}.
     * @param <ConstraintAnnotation> a subclass of annotation which defines an
     *            constraint.
     * @return the {@link Check}.
     */
    protected <ConstraintAnnotation extends Annotation>
            AnnotationCheck<ConstraintAnnotation> initializeCheck(
                    final ConstraintAnnotation constraintAnnotation) {
        assert constraintAnnotation != null;

        final DBConstraint constraint =
                constraintAnnotation.annotationType().getAnnotation(
                        DBConstraint.class);

        // determine the check class
        @SuppressWarnings("unchecked")
        final Class<AnnotationCheck<ConstraintAnnotation>> checkClass =
                (Class<AnnotationCheck<ConstraintAnnotation>>) constraint
                        .checkWith();

        // instantiate the appropriate check for the found constraint
        final AnnotationCheck<ConstraintAnnotation> check =
                newCheckInstance(checkClass);
        check.configure(constraintAnnotation);

        for (final CheckInitializationListener listener : listeners) {
            listener.onCheckInitialized(check);
        }
        return check;
    }

    /**
     * Creates an new {@link Check} instance.
     * 
     * @param checkClass the class which should be instantiated.
     * @param <ConstraintAnnotation> a subclass of annotation which defines an
     *            constraint.
     * 
     * @return a new instance of the given constraint check implementation class
     */
    protected
            <ConstraintAnnotation extends Annotation>
            AnnotationCheck<ConstraintAnnotation>
            newCheckInstance(
                    final Class<AnnotationCheck<ConstraintAnnotation>> checkClass) {
        try {
            return checkClass.newInstance();
        } catch (final InstantiationException ex) {
            throw new ReflectionException("Cannot initialize constraint check "
                    + checkClass.getName(), ex);
        } catch (final IllegalAccessException ex) {
            throw new ReflectionException("Cannot initialize constraint check "
                    + checkClass.getName(), ex);
        }
    }

}
