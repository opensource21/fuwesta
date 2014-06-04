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
 * Check which checks that a field.compareTo(referenceField) > 0.
 * 
 */
public abstract class AbstractGreaterCompareableCheck<ConstraintAnnotation extends Annotation>
        extends AbstractAnnotationCheck<ConstraintAnnotation> {

    /** The Logger. */
    private static final Logger LOG = LoggerFactory
            .getLogger(AbstractGreaterCompareableCheck.class);

    /** The start field. */
    private String referencedField;

    /**
     * Flag if is OK if both values are equal.
     */
    private final boolean isEqualAllowed;

    /** The class context. */
    private String classContext;

    /**
     * Initiates an object of type CompareAbleCheck.
     * 
     * @param isEqualAllowed if equal is acceptable.
     */
    public AbstractGreaterCompareableCheck(final boolean isEqualAllowed) {
        super();
        this.isEqualAllowed = isEqualAllowed;

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
            if (isEqualAllowed) {
                return (referencedFieldValue.compareTo(fieldValue)) <= 0;
            } else {
                return (referencedFieldValue.compareTo(fieldValue)) < 0;
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
     * @return the referencedField
     */
    protected String getReferencedField() {
        return referencedField;
    }

    /**
     * @param referencedField the referencedField to set
     */
    protected void setReferencedField(String referencedField) {
        this.referencedField = referencedField;
    }
}
