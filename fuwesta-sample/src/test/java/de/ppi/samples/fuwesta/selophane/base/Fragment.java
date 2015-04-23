package de.ppi.samples.fuwesta.selophane.base;

import org.openqa.selenium.WebElement;
import org.selophane.elements.base.ElementImpl;
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
     * @param webElement the {@link WebElement} which is the parent.
     */
    public Fragment(WebElement webElement) {
        super(webElement);
        ElementFactory.initElements(webElement, this);
    }

}
