package de.ppi.samples.fuwesta.selophane.widget;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.selophane.elements.base.ElementImpl;
import org.selophane.elements.base.UniqueElementLocator;

import de.ppi.selenium.util.CSSHelper;

/**
 * Implementation of an {@link MenuItemImpl}.
 *
 */
public class MenuItemImpl extends ElementImpl implements MenuItem {

    /**
     * Initiates an object of type MenuItemImpl.
     *
     * @param elementLocator the locator of the webelement.
     */
    public MenuItemImpl(final UniqueElementLocator elementLocator) {
        super(elementLocator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUrl() {
        final WebElement link = findElement(By.tagName("a"));
        return link.getAttribute("href");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
        return CSSHelper.getClasses(getWrappedElement()).contains("active");
    }

}
