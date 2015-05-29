package de.ppi.samples.fuwesta.selophane.base;

import org.openqa.selenium.support.PageFactory;
import org.selophane.elements.base.ElementImpl;
import org.selophane.elements.base.UniqueElementLocator;
import org.selophane.elements.factory.api.ChainedElementLocatorFactory;
import org.selophane.elements.factory.api.ElementDecorator;

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
        PageFactory
                .initElements(
                        new ElementDecorator(
                                elementLocator.getWebDriver(),
                                new ChainedElementLocatorFactory(elementLocator)),
                        this);
    }
}
