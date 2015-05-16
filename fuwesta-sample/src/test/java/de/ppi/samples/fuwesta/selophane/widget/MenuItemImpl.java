package de.ppi.samples.fuwesta.selophane.widget;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.selophane.elements.base.ElementImpl;

import de.ppi.selenium.util.CSSHelper;

/**
 * Implementation of an {@link MenuItemImpl}.
 *
 */
public class MenuItemImpl extends ElementImpl implements MenuItem {

    /**
     * Initiates an object of type MenuItemImpl.
     *
     * @param element element to wrap up
     */
    public MenuItemImpl(WebElement element) {
        super(element);
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
