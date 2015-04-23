package de.ppi.samples.fuwesta.selophane.widget;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.selophane.elements.base.ElementImpl;

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
        final String classAttribute = getAttribute("class");
        return classAttribute != null && getClasses().contains("active");
    }

    /**
     * Returns a list of all css-classes defined for this element.
     *
     * @return a list of all css-classes defined for this element.
     */
    private List<String> getClasses() {
        final String[] primitiveList = this.getAttribute("class").split(" ");
        return Arrays.asList(primitiveList);
    }

}
