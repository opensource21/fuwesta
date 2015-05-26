package de.ppi.samples.fuwesta.selophane.base;

import org.openqa.selenium.SearchContext;
import org.selophane.elements.factory.api.ElementFactory;

/**
 * Base class for all pages to have a hook and to do the work of the
 * ElementFactory.
 *
 */
public class BasePage {

    /**
     * The searchcontext of this page.
     */
    private final SearchContext searchContext;

    /**
     *
     * Initiates an object of type BasePage.
     *
     * @param searchContext the {@link SearchContext} to find the elements.
     */
    public BasePage(SearchContext searchContext) {
        this.searchContext = searchContext;
        reload();
    }

    /**
     * Reload the page, this is important if the weblements are chached.
     */
    public final void reload() {
        ElementFactory.initElements(searchContext, this);
    }

}
