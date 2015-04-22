package de.ppi.samples.fuwesta.selenium.base;

import org.openqa.selenium.SearchContext;
import org.selophane.elements.factory.api.ElementFactory;

/**
 * Base class for all pages to have a hook and to do the work of the
 * ElementFactory.
 *
 */
public class BasePage {

    /**
     *
     * Initiates an object of type BasePage.
     *
     * @param searchContext the {@link SearchContext} to find the elements.
     */
    public BasePage(SearchContext searchContext) {
        ElementFactory.initElements(searchContext, this);
    }

}
