package de.ppi.fuwesta.thymeleaf.bootstrap2;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

/**
 * The dialect for Twitter Bootstrap.
 * 
 */
public class Bootstrap2Dialect extends AbstractDialect {

    /**
     * The prefix of this Dialect.
     */
    public static final String PREFIX = "bs";

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
        processors.add(new BootstrapFieldAttrProcessor());
        processors.add(new BootstrapNameAttrProcessor());
        return processors;
    }

}
