package de.ppi.samples.fuwesta.selophane.widget;

import org.selophane.elements.base.Element;
import org.selophane.elements.base.ImplementedBy;

/**
 * A simple menu-item.
 *
 */
@ImplementedBy(MenuItemImpl.class)
public interface MenuItem extends Element {

    String getUrl();

    boolean isActive();

}
