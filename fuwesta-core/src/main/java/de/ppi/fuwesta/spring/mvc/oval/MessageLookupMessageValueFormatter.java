package de.ppi.fuwesta.spring.mvc.oval;

import net.sf.oval.localization.value.ToStringMessageValueFormatter;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;

/**
 * MessageValueFormatter which tries to resolve the String as a message code.
 * 
 */
public class MessageLookupMessageValueFormatter extends
        ToStringMessageValueFormatter {

    /**
     * Messages.
     */
    private MessageSourceAccessor messages;

    /**
     * Instantiates a new message lookup message value formatter.
     * 
     * @param messages the messages
     */
    public MessageLookupMessageValueFormatter(MessageSource messages) {
        super();
        Assert.notNull(messages);
        this.messages = new MessageSourceAccessor(messages);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String format(Object value) {
        String valueAsString = super.format(value);

        return messages.getMessage(valueAsString, valueAsString);

    }
}
