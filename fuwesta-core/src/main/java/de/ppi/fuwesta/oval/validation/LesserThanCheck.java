package de.ppi.fuwesta.oval.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks, if a comparable value of the given field lesser than the reference
 * field.
 * 
 */
public class LesserThanCheck extends AbstractCompareableCheck<LesserThan> {

    /** The Logger. */
    private static final Logger LOG = LoggerFactory
            .getLogger(LesserThanCheck.class);

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant MESSAGE. */
    public static final String MESSAGE = "validation.lesser.than";

    /** Instantiates a new check. */
    public LesserThanCheck() {
        super(ComparableMode.LESSER);
        setMessage(MESSAGE);
    }

    @Override
    public void configure(final LesserThan constraintAnnotation) {
        LOG.debug("LesserThanCheck.configure");
        super.configure(constraintAnnotation);
        setMessage(constraintAnnotation.message());
        setReferencedField(constraintAnnotation.value());
    }
}
