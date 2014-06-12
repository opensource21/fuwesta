package de.ppi.fuwesta.oval.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.field;

import java.util.List;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Test for the ConstraintCheck-Class {@link LesserEqualThanCheck}.
 * 
 */
public class LesserEqualThanCheckTest {

    /**
     * Comment for <code>MESSAGE_CODE</code>
     */
    private static final String MESSAGE_CODE = "validation.lesserequal.than";

    /**
     * Comment for <code>ERROR_CODE</code>
     */
    private static final String ERROR_CODE =
            "de.ppi.fuwesta.oval.validation.LesserEqualThan";

    /** The Constant REFERENCE_COLUMN. */
    private static final String REFERENCE_COLUMN = "referenceColumn";

    /** The Constant MESSAGE. */
    private static final String MESSAGE = "message";

    /** The testee. */
    @InjectMocks
    private LesserEqualThanCheck testee = new LesserEqualThanCheck();

    /** The mock of {@link LesserEqualThan}. */
    @Mock
    private LesserEqualThan mockLesserEqualThanAnnotation;

    /** The Constant for tests number. */
    private static final Double SMALL_NUMBER = Double.valueOf(12.5);

    /** The Constant for tests second number. */
    private static final Double GREATER_NUMBER = Double.valueOf(12.6);;

    /**
     * Sets the up.
     * 
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(mockLesserEqualThanAnnotation.value()).thenReturn(
                REFERENCE_COLUMN);
        Mockito.when(mockLesserEqualThanAnnotation.message()).thenReturn(
                MESSAGE);
    }

    /**
     * Test for {@link LesserEqualThanCheck#configure(LesserEqualThan)}.
     */
    @Test
    public void testConfigurationOdCheck() {
        testee.configure(mockLesserEqualThanAnnotation);

        assertThat(testee.getMessage()).isEqualTo(MESSAGE);
        assertThat(
                field("referencedField").ofType(String.class).in(testee).get())
                .isEqualTo(REFERENCE_COLUMN);
    }

    /**
     * Test refernce number is smaller than checked number. Test for
     * {@link LesserEqualThanCheck#isSatisfied(Object, Object, net.sf.oval.context.OValContext, Validator)}
     * .
     */
    @Test
    public void testReferenceNumberIsSmallerCheckedNumber() {
        final NumberTupel tvShow = new NumberTupel();
        tvShow.setLesserNumber(GREATER_NUMBER);
        tvShow.setHuhubalu(SMALL_NUMBER);

        final Validator myValidator = new Validator();

        final List<ConstraintViolation> violations =
                myValidator.validate(tvShow);

        assertThat(violations).hasSize(1);
        final ConstraintViolation violation = violations.get(0);
        assertThat(violation.getErrorCode()).isEqualTo(ERROR_CODE);
        assertThat(violation.getMessage()).isEqualTo(MESSAGE_CODE);

    }

    /**
     * Test without numbers. Test for
     * {@link LesserEqualThanCheck#isSatisfied(Object, Object, net.sf.oval.context.OValContext, Validator)}
     * .
     */
    @Test
    public void testWithoutNumber() {
        final NumberTupel tvShow = new NumberTupel();

        final Validator myValidator = new Validator();

        final List<ConstraintViolation> violations =
                myValidator.validate(tvShow);

        assertThat(violations).hasSize(0);

    }

    /**
     * Test refernce number is lesser checked number. Test for
     * {@link LesserEqualThanCheck#isSatisfied(Object, Object, net.sf.oval.context.OValContext, Validator)}
     * .
     */
    @Test
    public void testReferenceNumberIsLesserCheckedNumber() {
        final NumberTupel tvShow = new NumberTupel();
        tvShow.setLesserNumber(SMALL_NUMBER);
        tvShow.setHuhubalu(GREATER_NUMBER);
        tvShow.getLesserNumber();
        tvShow.getHuhubalu();

        final Validator myValidator = new Validator();

        final List<ConstraintViolation> violations =
                myValidator.validate(tvShow);

        assertThat(violations).hasSize(0);
    }

    /**
     * Test refernce number is equals checked number.
     */
    @Test
    public void testReferenceNumberIsEqualsCheckedNumber() {
        final NumberTupel tvShow = new NumberTupel();
        tvShow.setLesserNumber(SMALL_NUMBER);
        tvShow.setHuhubalu(SMALL_NUMBER);

        final Validator myValidator = new Validator();

        final List<ConstraintViolation> violations =
                myValidator.validate(tvShow);
        assertThat(violations).hasSize(0);
    }

    /**
     * ModelClass for Tests.
     * 
     */
    private class NumberTupel {

        /** The refernce number. */
        private Double huhubalu;

        /** The other number. */
        @LesserEqualThan("huhubalu")
        private Double lesserNumber;

        public void setLesserNumber(final Double checkedNumber) {
            this.lesserNumber = checkedNumber;
        }

        public Double getLesserNumber() {
            return lesserNumber;
        }

        public Double getHuhubalu() {
            return huhubalu;
        }

        public void setHuhubalu(final Double huhubalu) {
            this.huhubalu = huhubalu;
        }

    }
}
