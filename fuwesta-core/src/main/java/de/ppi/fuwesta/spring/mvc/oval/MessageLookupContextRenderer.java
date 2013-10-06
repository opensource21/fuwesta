package de.ppi.fuwesta.spring.mvc.oval;

import net.sf.oval.context.FieldContext;
import net.sf.oval.context.OValContext;
import net.sf.oval.localization.context.ToStringValidationContextRenderer;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;

/**
 * ValidationContextRenderer which tries to resolve the String as a message
 * code.
 * 
 */
public class MessageLookupContextRenderer extends
        ToStringValidationContextRenderer {

    /**
     * Messages.
     */
    private MessageSourceAccessor messages;

    /**
     * Instantiates a new message lookup context renderer.
     * 
     * @param messages the messages.
     */
    public MessageLookupContextRenderer(MessageSource messages) {
        super();
        Assert.notNull(messages);
        this.messages = new MessageSourceAccessor(messages);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String render(OValContext context) {
        if (context instanceof FieldContext) {
            final FieldContext ctx = (FieldContext) context;
            final String className =
                    ctx.getField().getDeclaringClass().getName();
            final String fieldName = ctx.getField().getName();
            final String key = className + "." + fieldName;
            return messages.getMessage(key, key);
        }
        return super.render(context);
    }
}
