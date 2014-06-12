package de.ppi.fuwesta.oval.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks, if a comparable time of the given field earlier than the reference
 * field.
 * 
 */
public class EarlierThanCheck extends AbstractCompareableCheck<EarlierThan> {

    /** The Logger. */
    private static final Logger LOG = LoggerFactory
            .getLogger(EarlierThanCheck.class);

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant MESSAGE. */
    public static final String MESSAGE = "validation.earlier.than";

    /** Instantiates a new check. */
    public EarlierThanCheck() {
        super(ComparableMode.LESSER);
        setMessage(MESSAGE);
    }

    @Override
    public void configure(final EarlierThan constraintAnnotation) {
        LOG.debug("BeforeThanCheck.configure");
        super.configure(constraintAnnotation);
        setMessage(constraintAnnotation.message());
        setReferencedField(constraintAnnotation.value());
    }
}
