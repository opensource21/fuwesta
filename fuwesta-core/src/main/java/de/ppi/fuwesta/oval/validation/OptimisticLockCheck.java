package de.ppi.fuwesta.oval.validation;

import static org.fest.reflect.core.Reflection.field;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;
import net.sf.oval.context.OValContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import de.ppi.fuwesta.jpa.helper.JpaQueryExecutor;

/**
 * Check which validates whether one property or a set of properties is unique.
 * 
 */
public class OptimisticLockCheck extends
        AbstractAnnotationCheck<OptimisticLock> {

    /** The primary key column. */
    private String versionColumn = "version";

    /** The primary key column. */
    private String primaryKeyColumn = "id";

    /**
     * The default message-key.
     */
    static final String MESSAGE = "validation.optimistic.lock";

    /**
     * Helper to runs JPA-Queries.
     */
    @Autowired
    private JpaQueryExecutor jpa;

    /**
     * Instantiates a new unique check.
     */
    public OptimisticLockCheck() {
        setMessage(MESSAGE);
    }

    @Override
    public void configure(OptimisticLock constraintAnnotation) {
        setMessage(constraintAnnotation.message());
        primaryKeyColumn = constraintAnnotation.primaryKeyColumn();
        versionColumn = constraintAnnotation.value();
    }

    @Override
    public Map<String, String> createMessageVariables() {
        Map<String, String> messageVariables = new HashMap<String, String>();
        messageVariables.put("reloadUrl", getReloadUrl());
        return messageVariables;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean isSatisfied(Object validatedObject, Object value,
            OValContext context, Validator validator) {

        requireMessageVariablesRecreation();

        final Object model = validatedObject;
        final Object keyValue =
                field(primaryKeyColumn).ofType(Object.class).in(model).get();
        final Long versionValue =
                field(versionColumn).ofType(Long.class).in(model).get();
        final String entityName = model.getClass().getName();

        if (keyValue != null) {
            if (versionValue == null) {
                return false;
            } else {
                String jpql =
                        "select m.version from " + entityName
                                + " as m where m.id = ?";
                final Long dbVersion =
                        (Long) jpa.queryWithSingleResult(jpql, keyValue);
                if (dbVersion.longValue() > versionValue.longValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Search for the current request-url.
     * 
     * @return the current request-url.
     */
    private String getReloadUrl() {
        final RequestAttributes rq =
                RequestContextHolder.currentRequestAttributes();
        String reloadURL = null;
        if (rq instanceof ServletRequestAttributes) {
            final ServletRequestAttributes sra = (ServletRequestAttributes) rq;
            final HttpServletRequest request = sra.getRequest();
            reloadURL = request.getHeader("referer");
            if (StringUtils.isEmpty(reloadURL)) {
                reloadURL = request.getRequestURL().toString();
            }
        }
        return reloadURL;
    }
}
