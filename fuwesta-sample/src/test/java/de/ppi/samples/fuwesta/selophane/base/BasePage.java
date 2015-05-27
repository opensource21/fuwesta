package de.ppi.samples.fuwesta.selophane.base;

import org.openqa.selenium.WebDriver;
import org.selophane.elements.factory.api.ElementFactory;

/**
 * Base class for all pages to have a hook and to do the work of the
 * ElementFactory.
 *
 */
public class BasePage {

    /**
     * The webdriver for this page.
     */
    private final WebDriver webDriver;

    /**
     *
     * Initiates an object of type BasePage.
     *
     * @param webDriver the {@link WebDriver} to find the elements.
     */
    public BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        reload();
    }

    /**
     * Reload the page, this is important if the weblements are chached.
     */
    public final void reload() {
        ElementFactory.initElements(webDriver, this);
    }

    /**
     * Gets the underlying webdriver.
     *
     * @return the underlying {@link WebDriver}.
     */
    protected WebDriver getWebDriver() {
        return webDriver;
    }

}
