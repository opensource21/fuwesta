package de.ppi.fuwesta.spring.mvc.oval;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.context.FieldContext;
import net.sf.oval.context.OValContext;
import net.sf.oval.exception.ValidationFailedException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Implements spring's validator. Uses Oval {@link net.sf.oval.Validator} for actually validating the objects.
 * Supports validating nested complex properties, which should be marked with {@link ValidateNestedProperty}.
 * 
 * @author niels based on the implementation of SergeyZ
 * 
 */
public class SpringOvalValidator implements Validator, InitializingBean {

    /**
     * The Oval-Validator which makes the work.
     */
    private final net.sf.oval.Validator validator;

    /**
     * Create an Spring-Validator which delegates the work to the OVal-Validator.
     * 
     * @param validator
     *            the oval-validator.
     */
    public SpringOvalValidator(final net.sf.oval.Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        doValidate(target, errors, "");
    }

    /**
     * Validated recursive all fields of an object-graph.
     * 
     * @param target
     *            the object which should be validated.
     * @param errors
     *            the error container.
     * @param fieldPrefix
     *            the context-path.
     */
    private void doValidate(final Object target, final Errors errors, final String fieldPrefix) {
        try {
            for (final ConstraintViolation violation : validator.validate(target)) {
                final OValContext ctx = violation.getContext();
                final String errorCode = violation.getErrorCode();
                final String errorMessage = violation.getMessage();

                if (ctx instanceof FieldContext) {
                    final String fieldName = fieldPrefix + ((FieldContext) ctx).getField().getName();
                    errors.rejectValue(fieldName, errorCode, errorMessage);
                } else {
                    errors.reject(errorCode, errorMessage);
                }
            }

            final List<Field> fields = getFieldsWithNestedValidation(target.getClass());
            for (final Field field : fields) {
                final String name = field.getName();
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                final Object nestedProperty = field.get(target);
                if (nestedProperty != null) {
                    if (nestedProperty instanceof Collection<?>) {
                        // valueToValidate is a collection
                        final Collection<?> col = (Collection<?>) nestedProperty;
                        int i = 0;
                        for (final Object object : col) {
                            doValidate(object, errors, name + "[" + i + "].");
                            i++;
                        }
                    } else if (nestedProperty.getClass().isArray()) {
                        // valueToValidate is an array
                        final int length = Array.getLength(nestedProperty);
                        for (int i = 0; i < length; i++) {
                            final Object object = Array.get(nestedProperty, i);
                            doValidate(object, errors, name + "[" + i + "].");
                        }
                    } else {
                        doValidate(nestedProperty, errors, name + ".");
                    }
                }
            }
        } catch (final ValidationFailedException ex) {
            errors.reject(ex.getMessage());
        } catch (final IllegalAccessException e) {
            throw new IllegalStateException("Can't access field although the accessibility is set to true!",
                    e);
        }
    }

    /**
     * List all field of the given class and super-classes which has the annotation.
     * {@link SpringValidateNestedProperty}
     * 
     * @param clazz
     *            the class which should be analyzed.
     * @return a list with all properties.
     */
    private List<Field> getFieldsWithNestedValidation(final Class<?> clazz) {
        final List<Field> fieldsWithAnnotation = new ArrayList<Field>();
        final Field[] fields = clazz.getDeclaredFields();
        for (final Field field : fields) {
            if (field.isAnnotationPresent(SpringValidateNestedProperty.class)) {
                fieldsWithAnnotation.add(field);
            }
        }
        if (clazz.getSuperclass() != null) {
            fieldsWithAnnotation.addAll(getFieldsWithNestedValidation(clazz.getSuperclass()));
        }
        return fieldsWithAnnotation;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(validator, "Property [validator] is not set");
    }

}
