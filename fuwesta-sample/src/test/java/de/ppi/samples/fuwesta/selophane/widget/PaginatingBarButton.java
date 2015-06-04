package de.ppi.samples.fuwesta.selophane.widget;

import org.selophane.elements.base.Element;
import org.selophane.elements.base.ImplementedBy;

/**
 * A representation of the paginating bar.
 *
 */
@ImplementedBy(PaginatingBarButtonImpl.class)
public interface PaginatingBarButton extends Element {

    /**
     * Checks if it is the active button, which is highlighted.
     *
     * @return true if it is the active button, which is highlighted.
     */
    boolean isActive();

    /**
     * Checks if the button is disabled.
     *
     * @return true if the button is disabled.
     */
    boolean isDisabled();

}
