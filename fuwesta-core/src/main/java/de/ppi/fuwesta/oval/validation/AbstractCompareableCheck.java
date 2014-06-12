package de.ppi.fuwesta.oval.validation;

import static org.fest.reflect.core.Reflection.field;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Check which checks that a field.compareTo(referenceField) > 0, < 0 or = 0.
 * 
 */
public abstract class AbstractCompareableCheck<ConstraintAnnotation extends Annotation>
        extends AbstractAnnotationCheck<ConstraintAnnotation> {

    /** The Logger. */
    private static final Logger LOG = LoggerFactory
            .getLogger(AbstractCompareableCheck.class);

    /** The start field. */
    private String referencedField;

    /**
     * Information which compare should be valid.
     */
    private final ComparableMode comparableMode;

    /** The class context. */
    private String classContext;

    /**
     * Initiates an object of type CompareAbleCheck.
     * 
     * @param comparableMode Information which compare should be valid..
     */
    public AbstractCompareableCheck(final ComparableMode comparableMode) {
        super();
        this.comparableMode = comparableMode;

    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isSatisfied(final Object validatedObject,
            final Object valueToValidate, final OValContext context,
            final Validator validator) {
        classContext = validatedObject.getClass().getName();
        requireMessageVariablesRecreation();
        final Object model = validatedObject;
        final Comparable<Comparable<?>> referencedFieldValue =
                field(referencedField).ofType(Comparable.class).in(model).get();
        final Comparable<?> fieldValue = (Comparable<?>) valueToValidate;
        LOG.debug("Validate: " + referencedFieldValue + " and " + fieldValue);
        if (referencedFieldValue != null && fieldValue != null) {
            switch (comparableMode) {
            case GREATER:
                return (referencedFieldValue.compareTo(fieldValue)) < 0;
            case GREATER_EQUAL:
                return (referencedFieldValue.compareTo(fieldValue)) <= 0;
            case LESSER_EQUAL:
                return (referencedFieldValue.compareTo(fieldValue)) >= 0;
            case LESSER:
                return (referencedFieldValue.compareTo(fieldValue)) > 0;
            default:
                throw new IllegalArgumentException("Mode " + comparableMode
                        + " is unknown.");

            }
        } else {
            return true;
        }
    }

    @Override
    public Map<String, String> createMessageVariables() {
        final Map<String, String> messageVariables =
                new HashMap<String, String>();
        messageVariables.put("refColumn", classContext + "." + referencedField);
        return messageVariables;
    }

    /**
     * @param referencedField the referencedField to set
     */
    protected void setReferencedField(String referencedField) {
        this.referencedField = referencedField;
    }

    /**
     * Modes of comparison.
     */
    static enum ComparableMode {
        /** Greater and not equal.*/
        GREATER,
        /** Greater or equal*/
        GREATER_EQUAL, 
        /** Lesser or equal*/
        LESSER_EQUAL, 
        /** Lesser and not equal*/
        LESSER;
    }
}
