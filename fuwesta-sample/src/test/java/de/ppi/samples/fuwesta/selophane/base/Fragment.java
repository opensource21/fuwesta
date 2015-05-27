package de.ppi.samples.fuwesta.selophane.base;

import org.selophane.elements.base.ElementImpl;
import org.selophane.elements.base.UniqueElementLocator;
import org.selophane.elements.factory.api.ChainedElementLocatorFactory;
import org.selophane.elements.factory.api.ElementFactory;

/**
 * Baseclass of a fragment which handles the initialization.
 *
 */
public class Fragment extends ElementImpl {

    /**
     *
     * Initiates an fragment.
     *
     * @param elementLocator the locator of the webelement.
     */
    public Fragment(final UniqueElementLocator elementLocator) {
        super(elementLocator);
        ElementFactory.initElements(elementLocator.getWebDriver(),
                new ChainedElementLocatorFactory(elementLocator), this);
    }

}
