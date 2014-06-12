package de.ppi.fuwesta.oval.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks, if a comparable value of the given field lesser or equal than the
 * reference field.
 * 
 */
public class LesserEqualThanCheck extends
        AbstractCompareableCheck<LesserEqualThan> {

    /** The Logger. */
    private static final Logger LOG = LoggerFactory
            .getLogger(LesserEqualThanCheck.class);

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant MESSAGE. */
    public static final String MESSAGE = "validation.lesserequal.than";

    /** Instantiates a new check. */
    public LesserEqualThanCheck() {
        super(ComparableMode.LESSER_EQUAL);
        setMessage(MESSAGE);
    }

    @Override
    public void configure(final LesserEqualThan constraintAnnotation) {
        LOG.debug("LesserEqualThanCheck.configure");
        super.configure(constraintAnnotation);
        setMessage(constraintAnnotation.message());
        setReferencedField(constraintAnnotation.value());
    }
}
