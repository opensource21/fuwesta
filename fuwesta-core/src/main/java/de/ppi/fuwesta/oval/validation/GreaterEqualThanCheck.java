package de.ppi.fuwesta.oval.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks, if a comparable value of the given field greater or equal than the
 * reference field.
 * 
 */
public class GreaterEqualThanCheck extends
        AbstractCompareableCheck<GreaterEqualThan> {

    /** The Logger. */
    private static final Logger LOG = LoggerFactory
            .getLogger(GreaterEqualThanCheck.class);

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant MESSAGE. */
    public static final String MESSAGE = "validation.greaterequal.than";

    /** Instantiates a new start end time check. */
    public GreaterEqualThanCheck() {
        super(AbstractCompareableCheck.ComparableMode.GREATER_EQUAL);
        setMessage(MESSAGE);
    }

    @Override
    public void configure(final GreaterEqualThan constraintAnnotation) {
        LOG.debug("GreaterEqualThanCheck.configure");
        super.configure(constraintAnnotation);
        setMessage(constraintAnnotation.message());
        setReferencedField(constraintAnnotation.value());
    }
}
