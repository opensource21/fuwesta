package de.ppi.fuwesta.oval.validation;

import static org.fest.reflect.core.Reflection.field;

import java.util.HashMap;
import java.util.Map;

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.FieldContext;
import net.sf.oval.context.OValContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import de.ppi.fuwesta.jpa.helper.JpaQueryExecutor;

/**
 * Check which validates whether one property or a set of properties is unique.
 * 
 */
public class UniqueCheck extends AbstractAnnotationCheck<Unique> {

    /** The unique key context. */
    private String uniqueKeyContext = "";

    /** The primary key column. */
    private String primaryKeyColumn = "id";

    /**
     * The default message-key.
     */
    static final String MESSAGE = "validation.unique";

    /**
     * Helper to runs JPA-Queries.
     */
    @Autowired
    private JpaQueryExecutor jpa;

    /**
     * Instantiates a new unique check.
     */
    public UniqueCheck() {
        setMessage(MESSAGE);
    }

    @Override
    public void configure(Unique constraintAnnotation) {
        uniqueKeyContext = constraintAnnotation.value();
        setMessage(constraintAnnotation.message());
        primaryKeyColumn = constraintAnnotation.primaryKeyColumn();
    }

    @Override
    public Map<String, String> createMessageVariables() {
        Map<String, String> messageVariables = new HashMap<String, String>();
        messageVariables.put("uk-context", uniqueKeyContext);
        return messageVariables;
    }

    /**
     * Returns all properties which are part of the unique-key.
     * 
     * @param uniqueKey the field which contains the unique-key annotation.
     * @return all properties which are part of the unique-key.
     */
    private String[] getPropertyNames(String uniqueKey) {
        String completeUniqueKey;
        if (StringUtils.isNotEmpty(uniqueKeyContext)) {
            completeUniqueKey = uniqueKeyContext + ";" + uniqueKey;
        } else {
            completeUniqueKey = uniqueKey;
        }
        return completeUniqueKey.split("[,;\\s][\\s]*");
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean isSatisfied(Object validatedObject, Object value,
            OValContext context, Validator validator) {

        requireMessageVariablesRecreation();
        if (value == null) {
            return true;
        }
        final String[] propertyNames =
                getPropertyNames(((FieldContext) context).getField().getName());
        final Object model = validatedObject;
        final Object keyValue =
                field(primaryKeyColumn).ofType(Object.class).in(model).get();
        // In case of an update make sure that we won't read the current record
        // from database.
        final boolean isUpdate = (keyValue != null);
        final String entityName = model.getClass().getName();
        final StringBuffer jpql = new StringBuffer("SELECT COUNT(o) FROM ");
        jpql.append(entityName).append(" AS o where ");
        final Object[] values =
                new Object[isUpdate ? propertyNames.length + 1
                        : propertyNames.length];
        for (int i = 0; i < propertyNames.length; i++) {
            values[i] =
                    field(propertyNames[i]).ofType(Object.class).in(model)
                            .get();
            if (i > 0) {
                jpql.append(" And ");
            }
            jpql.append("o.").append(propertyNames[i]).append(" = ? ");
        }
        if (isUpdate) {
            values[propertyNames.length] = keyValue;
            jpql.append(" and ").append(primaryKeyColumn).append(" <>  ?");
        }

        return Long.parseLong(jpa
                .queryWithSingleResult(jpql.toString(), values).toString()) == 0L;
    }
}
