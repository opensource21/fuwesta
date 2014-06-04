package de.ppi.fuwesta.oval.validation;

import static org.fest.reflect.core.Reflection.*;

import java.util.HashMap;
import java.util.Map;

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks, if the time of the given field later than the reference field.
 * 
 */
public class LaterThanCheck extends AbstractAnnotationCheck<LaterThan> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant MESSAGE. */
    public static final String MESSAGE = "validation.later.then";

    /** The start field. */
    private String referencedField;

    /** The class context. */
    private String classContext;

    /** Instantiates a new start end time check. */
    public LaterThanCheck() {
        setMessage(MESSAGE);
    }

    /** The Logger. */
    private static final Logger LOG = LoggerFactory
            .getLogger(LaterThanCheck.class);

    @Override
    public void configure(final LaterThan constraintAnnotation) {
        LOG.debug("LaterThanCheck.configure");
        super.configure(constraintAnnotation);
        setMessage(constraintAnnotation.message());
        referencedField = constraintAnnotation.value();

    };

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
            return (referencedFieldValue.compareTo(fieldValue)) < 0;
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
}
