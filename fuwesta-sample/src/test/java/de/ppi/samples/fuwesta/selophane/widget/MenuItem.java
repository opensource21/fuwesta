package de.ppi.samples.fuwesta.selophane.widget;

import org.selophane.elements.base.Element;
import org.selophane.elements.base.ImplementedBy;

/**
 * A simple menu-item.
 *
 */
@ImplementedBy(MenuItemImpl.class)
public interface MenuItem extends Element {

    /**
     * Gets the url which is this menu linked to.
     * 
     * @return the url which is this menu linked to.
     */
    String getUrl();

    /**
     * Information if this menuitem is active.
     * 
     * @return true if the menu-item is active.
     */
    boolean isActive();

}
