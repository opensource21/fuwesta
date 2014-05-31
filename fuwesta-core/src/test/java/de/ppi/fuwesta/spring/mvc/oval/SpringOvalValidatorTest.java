package de.ppi.fuwesta.spring.mvc.oval;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.context.FieldContext;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

/**
 * Test for {@link SpringOvalValidator}.
 * 
 * @author niels
 * 
 */
public class SpringOvalValidatorTest {

    /**
     * The Oval-Validator.
     */
    @Mock
    private Validator ovalValidator;

    /**
     * The test object.
     */
    private SpringOvalValidator testee;

    /**
     * Test setup.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        testee = new SpringOvalValidator(ovalValidator);
    }

    /**
     * Test if every class is supported.
     */
    @Test
    public void testSupports() {
        assertThat(testee.supports(Object.class)).isTrue();
    }

    /**
     * Test if the nested object graph followed.
     * 
     * @throws Exception
     *             if something goes wrong
     */
    @Test
    public void testValidate() throws Exception {
        // Arrange
        final Errors errors = mock(Errors.class);
        final Container container = new Container();
        final ConstraintViolation violation = mock(ConstraintViolation.class);
        final List<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
        violations.add(violation);
        final FieldContext context = mock(FieldContext.class);
        final Field field = Container.class.getDeclaredField("innerObject");
        when(context.getField()).thenReturn(field);
        when(violation.getContext()).thenReturn(context);
        when(ovalValidator.validate(any())).thenReturn(violations);
        // Act
        testee.validate(container, errors);
        // Assert
        verify(ovalValidator).validate(container);
        verify(ovalValidator).validate(container.getInnerObject());
        for (final InnerObject io : container.getInnerObjectList()) {
            verify(ovalValidator).validate(io);
        }
        for (final InnerObject io : container.getInnerObjectArray()) {
            verify(ovalValidator).validate(io);
        }
        verify(errors).rejectValue("innerObject", null, null);
        verify(errors).rejectValue("innerObject.innerObject", null, null);
        verify(errors).rejectValue("innerObjectList[0].innerObject", null, null);
        verify(errors).rejectValue("innerObjectList[1].innerObject", null, null);
        verify(errors).rejectValue("innerObjectArray[0].innerObject", null, null);
        verify(errors).rejectValue("innerObjectArray[1].innerObject", null, null);

    }

    /**
     * Test that {@link SpringOvalValidator#afterPropertiesSet()} doesn't throw an exception.
     * 
     * @throws Exception
     *             it something goes wrong.
     */
    @Test
    public void testAfterPropertiesSet() throws Exception {
        testee.afterPropertiesSet();
    }

    /**
     * The Class Container.
     */
    @SuppressWarnings("unused")
    private static class Container {

        /** The inner object. */
        @SpringValidateNestedProperty
        private InnerObject innerObject = new InnerObject();

        /** The inner object2. */
        @SpringValidateNestedProperty
        private InnerObject innerObject2 = null;

        /** The inner object list. */
        @SpringValidateNestedProperty
        private List<InnerObject> innerObjectList = new ArrayList<>();

        /** The inner object array. */
        @SpringValidateNestedProperty
        private InnerObject[] innerObjectArray = new InnerObject[2];

        /**
         * Instantiates a new container.
         */
        public Container() {
            super();
            innerObjectList.add(new InnerObject());
            innerObjectList.add(new InnerObject());
            innerObjectArray[0] = new InnerObject();
            innerObjectArray[1] = new InnerObject();
        }

        /**
         * Gets the inner object array.
         * 
         * @return the inner object array
         */
        public InnerObject[] getInnerObjectArray() {
            return innerObjectArray;
        }

        /**
         * Sets the inner object array.
         * 
         * @param innerObjectArray
         *            the new inner object array
         */
        public void setInnerObjectArray(final InnerObject[] innerObjectArray) {
            this.innerObjectArray = innerObjectArray;
        }

        /**
         * Gets the inner object.
         * 
         * @return the inner object
         */
        public InnerObject getInnerObject() {
            return innerObject;
        }

        /**
         * Sets the inner object.
         * 
         * @param innerObject
         *            the new inner object
         */
        public void setInnerObject(final InnerObject innerObject) {
            this.innerObject = innerObject;
        }

        /**
         * Gets the inner object list.
         * 
         * @return the inner object list
         */
        public List<InnerObject> getInnerObjectList() {
            return innerObjectList;
        }

        /**
         * Sets the inner object list.
         * 
         * @param innerObjectList
         *            the new inner object list
         */
        public void setInnerObjectList(final List<InnerObject> innerObjectList) {
            this.innerObjectList = innerObjectList;
        }

    }

    /**
     * The Class InnerObject.
     */
    @SuppressWarnings("unused")
    private static class InnerObject {

        /** The test. */
        @NotNull
        private String test;

        /**
         * Gets the test.
         * 
         * @return the test
         */
        public String getTest() {
            return test;
        }

        /**
         * Sets the test.
         * 
         * @param test
         *            the new test
         */
        public void setTest(final String test) {
            this.test = test;
        }

    }

}
