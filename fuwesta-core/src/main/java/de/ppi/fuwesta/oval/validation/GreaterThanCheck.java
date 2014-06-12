package de.ppi.fuwesta.oval.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks, if a comparable value of the given field greater than the reference
 * field.
 * 
 */
public class GreaterThanCheck extends AbstractCompareableCheck<GreaterThan> {

    /** The Logger. */
    private static final Logger LOG = LoggerFactory
            .getLogger(GreaterThanCheck.class);

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant MESSAGE. */
    public static final String MESSAGE = "validation.greater.than";

    /** Instantiates a new check. */
    public GreaterThanCheck() {
        super(AbstractCompareableCheck.ComparableMode.GREATER);
        setMessage(MESSAGE);
    }

    @Override
    public void configure(final GreaterThan constraintAnnotation) {
        LOG.debug("GreaterThanCheck.configure");
        super.configure(constraintAnnotation);
        setMessage(constraintAnnotation.message());
        setReferencedField(constraintAnnotation.value());
    }
}
