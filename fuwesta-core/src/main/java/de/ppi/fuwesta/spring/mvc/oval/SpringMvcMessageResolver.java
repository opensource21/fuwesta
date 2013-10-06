package de.ppi.fuwesta.spring.mvc.oval;

import net.sf.oval.localization.message.MessageResolver;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;

/**
 * MessageResolver which delegates to message sources.
 * 
 */
public class SpringMvcMessageResolver implements MessageResolver {

    /**
     * Messages.
     */
    private MessageSourceAccessor messages;

    /**
     * Instantiates a new Spring MVC message resolver.
     * 
     * @param messages the messages
     */
    public SpringMvcMessageResolver(MessageSource messages) {
        super();
        Assert.notNull(messages);
        this.messages = new MessageSourceAccessor(messages);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage(String key) {
        return messages.getMessage(key, key);
    }

}
