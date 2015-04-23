package de.ppi.samples.fuwesta.selophane.widget;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.ppi.samples.fuwesta.selophane.base.Fragment;

/**
 * Fragment Menu.
 *
 */
public class MenuImpl extends Fragment implements Menu {

    /**
     * List of all menu-items.
     */
    @FindBy(tagName = "li")
    private List<MenuItem> menuItems;

    /**
     *
     * Initiates an object of type Menu.
     *
     * @param webElement the parent-element.
     */
    public MenuImpl(WebElement webElement) {
        super(webElement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItem getMenuItem(String name) {
        if (name == null) {
            return null;
        }
        for (MenuItem menuItem : menuItems) {
            if (name.equals(menuItem.getText())) {
                return menuItem;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItem getMenuItem(int pos) {
        return menuItems.get(pos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

}
