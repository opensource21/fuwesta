package de.ppi.fuwesta.thymeleaf.bootstrap2;

import org.thymeleaf.dom.Element;

/**
 * Attribute processor which makes the work easier with Twitter Bootstrap.
 * 
 */
public class Bootstrap2NameAttrProcessor extends
        AbstractBootstrap2AttrProcessor {

    /** The attribute name which should trigger this processor. */
    public static final String ATTRIBUTE_NAME = "name";

    /**
     * The precedence of the Processor. Should by high to make sure that remove
     * or if - conditions are run first.
     */
    public static final int PRECEDENCE = 10001;

    /**
     * Instantiates a new bootstrap field attr processor.
     */
    public Bootstrap2NameAttrProcessor() {
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
        return "#{field." + fieldName + "}+':'";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void
            addAttributesToInputElement(Element element, String fieldName) {
        element.setAttribute("id", fieldName);
        element.setAttribute("name", fieldName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean showError() {
        return false;
    }
}
