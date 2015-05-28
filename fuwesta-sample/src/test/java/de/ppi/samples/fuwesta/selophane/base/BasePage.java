package de.ppi.samples.fuwesta.selophane.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selophane.elements.base.ByUniqueElementLocator;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.ElementImpl;
import org.selophane.elements.factory.api.ElementFactory;
import org.selophane.elements.widget.Label;
import org.selophane.elements.widget.LabelImpl;

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
    public WebDriver getWebDriver() {
        return webDriver;
    }

    /**
     * Get a label for the given element which must have an id.
     *
     * @param element element for which the element is searched.
     * @return the Label.
     */
    public Label getLabelFor(Element element) {
        final String id = element.getAttribute("id");
        if (id == null) {
            return null;
        }
        return new LabelImpl(new ByUniqueElementLocator(getWebDriver(),
                By.xpath("//label[@for='" + id + "']")));
    }

    /**
     * Get a the validation error-message to an given element.
     *
     * @param element element for which the error is searched must have an id.
     * @return the Label.
     */
    public Element getError(Element element) {
        final String id = element.getAttribute("id");
        if (id == null) {
            return null;
        }
        return new ElementImpl(new ByUniqueElementLocator(getWebDriver(),
                By.id("error_" + id)));
    }

}
