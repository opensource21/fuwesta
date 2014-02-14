package de.ppi.fuwesta.thymeleaf.bootstrap2;

import org.thymeleaf.dom.Element;
import org.thymeleaf.spring3.processor.attr.AbstractSpringFieldAttrProcessor;
import org.thymeleaf.standard.StandardDialect;

/**
 * Attribute processor which makes the work easier with Twitter Bootstrap.
 * 
 */
public class Bootstrap2FieldAttrProcessor extends AbstractBootstrap2AttrProcessor {

    /** The attribute name which should trigger this processor. */
    public static final String ATTRIBUTE_NAME = "field";

    /**
     * The precedence of the Processor. Should by high to make sure that remove
     * or if - conditions are run first.
     **/
    public static final int PRECEDENCE = 10000;

    /**
     * Instantiates a new bootstrap field attr processor.
     */
    public Bootstrap2FieldAttrProcessor() {
        super(ATTRIBUTE_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPrecedence() {
        return PRECEDENCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String createLabelExpression(String fieldName) {
        return "#{__*{class.name}__." + fieldName + "}+':'";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void
            addAttributesToInputElement(Element element, String fieldName) {
        element.setAttribute(StandardDialect.PREFIX + ":"
                + AbstractSpringFieldAttrProcessor.ATTR_NAME, "*{" + fieldName
                + "}");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean showError() {
        return true;
    }
}
