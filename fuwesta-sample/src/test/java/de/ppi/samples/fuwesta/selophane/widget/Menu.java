package de.ppi.samples.fuwesta.selophane.widget;

import java.util.List;

import org.selophane.elements.base.Element;
import org.selophane.elements.base.ImplementedBy;

/**
 * A menu.
 *
 */
@ImplementedBy(MenuImpl.class)
public interface Menu extends Element {

    /**
     * Returns the {@link MenuItem} with the given text.
     *
     * @param name the text which is shown on the menu.
     * @return the {@link MenuItem} or <code>null</code> if the text is
     *         <code>null</code> or not found.
     */
    MenuItem getMenuItem(String name);

    /**
     * Returns the {@link MenuItem} of the given position.
     *
     * @param pos of the given item see {@link List#get(int)}.
     * @return the {@link MenuItem} of the given position.
     */
    MenuItem getMenuItem(int pos);

    /**
     * Returns a list of all menu-items.
     *
     * @return a list of all menu-items.
     */
    List<MenuItem> getMenuItems();

}
