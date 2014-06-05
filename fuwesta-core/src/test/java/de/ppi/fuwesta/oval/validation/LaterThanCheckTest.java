package de.ppi.fuwesta.oval.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.field;

import java.util.Date;
import java.util.List;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Test for the ConstraintCheck-Class {@link LaterThanCheck}.
 * 
 */
public class LaterThanCheckTest {

    /**
     * Comment for <code>MESSAGE_CODE</code>
     */
    private static final String MESSAGE_CODE = "validation.later.than";

    /**
     * Comment for <code>ERROR_CODE</code>
     */
    private static final String ERROR_CODE =
            "de.ppi.fuwesta.oval.validation.LaterThan";

    /** The Constant REFERENCE_COLUMN. */
    private static final String REFERENCE_COLUMN = "referenceColumn";

    /** The Constant MESSAGE. */
    private static final String MESSAGE = "message";

    /** The testee. */
    @InjectMocks
    private LaterThanCheck testee = new LaterThanCheck();

    /** The mock of {@link LaterThan}. */
    @Mock
    private LaterThan mockLaterThanAnnotation;

    /** The Constant for tests firstTime. */
    private static final Date EARLIER_TIME = new Date();

    /** The Constant for tests secondTime. */
    private static final Date LATER_TIME = DateUtils.addMilliseconds(
            EARLIER_TIME, 2);

    /**
     * Sets the up.
     * 
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(mockLaterThanAnnotation.value()).thenReturn(
                REFERENCE_COLUMN);
        Mockito.when(mockLaterThanAnnotation.message()).thenReturn(MESSAGE);
    }

    /**
     * Test for {@link LaterThanCheck#configure(LaterThan)}.
     */
    @Test
    public void testConfigurationOdCheck() {
        testee.configure(mockLaterThanAnnotation);

        assertThat(testee.getMessage()).isEqualTo(MESSAGE);
        assertThat(
                field("referencedField").ofType(String.class).in(testee).get())
                .isEqualTo(REFERENCE_COLUMN);
    }

    /**
     * Test start time is before endtime. Test for
     * {@link LaterThanCheck#isSatisfied(Object, Object, net.sf.oval.context.OValContext, Validator)}
     * .
     */
    @Test
    public void testStartTimeIsBeforeEndtime() {
        final TvShow tvShow = new TvShow();
        tvShow.setEndTime(LATER_TIME);
        tvShow.setHuhubalu(EARLIER_TIME);

        final Validator myValidator = new Validator();

        final List<ConstraintViolation> violations =
                myValidator.validate(tvShow);

        assertThat(violations).hasSize(0);

    }

    /**
     * Test start time is equalto endtime. Test for
     * {@link LaterThanCheck#isSatisfied(Object, Object, net.sf.oval.context.OValContext, Validator)}
     * .
     */
    @Test
    public void testStartTimeIsSameEndtime() {
        final TvShow tvShow = new TvShow();
        tvShow.setEndTime(EARLIER_TIME);
        tvShow.setHuhubalu(EARLIER_TIME);

        final Validator myValidator = new Validator();

        final List<ConstraintViolation> violations =
                myValidator.validate(tvShow);

        assertThat(violations).hasSize(1);
        final ConstraintViolation violation = violations.get(0);
        assertThat(violation.getErrorCode()).isEqualTo(ERROR_CODE);
        assertThat(violation.getMessage()).isEqualTo(MESSAGE_CODE);
    }

    /**
     * Test without time. Test for
     * {@link LaterThanCheck#isSatisfied(Object, Object, net.sf.oval.context.OValContext, Validator)}
     * .
     */
    @Test
    public void testWithoutTime() {
        final TvShow tvShow = new TvShow();

        final Validator myValidator = new Validator();

        final List<ConstraintViolation> violations =
                myValidator.validate(tvShow);

        assertThat(violations).hasSize(0);

    }

    /**
     * Test start time is after endtime. Test for
     * {@link LaterThanCheck#isSatisfied(Object, Object, net.sf.oval.context.OValContext, Validator)}
     * .
     */
    @Test
    public void testStartTimeIsAfterEndtime() {
        final TvShow tvShow = new TvShow();
        tvShow.setEndTime(EARLIER_TIME);
        tvShow.setHuhubalu(LATER_TIME);
        tvShow.getEndTime();
        tvShow.getHuhubalu();

        final Validator myValidator = new Validator();

        final List<ConstraintViolation> violations =
                myValidator.validate(tvShow);

        assertThat(violations).hasSize(1);
        final ConstraintViolation violation = violations.get(0);
        assertThat(violation.getErrorCode()).isEqualTo(ERROR_CODE);
        assertThat(violation.getMessage()).isEqualTo(MESSAGE_CODE);
    }

    /**
     * Test start time is equals endtime.
     */
    @Test
    public void testStartTimeIsEqualsEndTime() {
        final TvShow tvShow = new TvShow();
        tvShow.setEndTime(LATER_TIME);
        tvShow.setHuhubalu(LATER_TIME);
        tvShow.getEndTime();
        tvShow.getHuhubalu();

        final Validator myValidator = new Validator();

        final List<ConstraintViolation> violations =
                myValidator.validate(tvShow);

        assertThat(violations).hasSize(1);
        final ConstraintViolation violation = violations.get(0);
        assertThat(violation.getErrorCode()).isEqualTo(ERROR_CODE);
        assertThat(violation.getMessage()).isEqualTo(MESSAGE_CODE);
    }

    /**
     * ModelClass for Tests.
     * 
     */
    private class TvShow {

        /** The start time. */
        private Date huhubalu;

        /** The end time. */
        @LaterThan("huhubalu")
        private Date endTime;

        /**
         * Sets the end time.
         * 
         * @param endTime the new end time
         */
        public void setEndTime(final Date endTime) {
            this.endTime = endTime;
        }

        /**
         * Gets the end time.
         * 
         * @return the end time
         */
        public Date getEndTime() {
            return endTime;
        }

        /**
         * Gets the huhubalu.
         * 
         * @return the huhubalu
         */
        public Date getHuhubalu() {
            return huhubalu;
        }

        /**
         * Sets the huhubalu.
         * 
         * @param huhubalu the new huhubalu
         */
        public void setHuhubalu(final Date huhubalu) {
            this.huhubalu = huhubalu;
        }

    }
}
