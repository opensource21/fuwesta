package de.ppi.fuwesta.thymeleaf.mail;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

/**
 * The dialect for mailto-links.
 * 
 */
public class MailToDialect extends AbstractDialect {

    /**
     * The prefix of this Dialect.
     */
    public static final String PREFIX = "mail";

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrefix() {
        return PREFIX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLenient() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new MailAttrProcessor());
        return processors;
    }

}
