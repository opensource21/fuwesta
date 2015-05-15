package de.ppi.samples.fuwesta.selophane.widget;

import java.util.List;

import org.selophane.elements.base.Element;
import org.selophane.elements.base.ImplementedBy;

/**
 * A representation of the paginating bar.
 *
 */
@ImplementedBy(PaginatingBarImpl.class)
public interface PaginatingBar extends Element {

    /**
     * Class of a disabled button.
     */
    String CLASS_DISABLED = "disabled";

    /**
     * Class of the active button.
     */
    String CLASS_ACTIVE = "active";

    /**
     * Get the button to jump to the first-page.
     *
     * @return the button to jump to the first-page.
     */
    Element getFirst();

    /**
     * Get the button to jump to the last-page.
     *
     * @return the button to jump to the last-page.
     */
    Element getLast();

    /**
     * Get the button to jump to the next-page.
     *
     * @return the button to jump to the next-page.
     */
    Element getNext();

    /**
     * Get the button to jump to the previous-page.
     *
     * @return the button to jump to the previous-page.
     */
    Element getPrevious();

    /**
     * The number of buttons.
     *
     * @return the number of buttons.
     */
    int getNrOfButtons();

    /**
     * Get the button with the given text.
     *
     * @param text the text which is shown for the button.
     * @return the button with the given text.
     */
    Element getButton(String text);

    /**
     * Return all buttons.
     *
     * @return a {@link List} with all buttons.
     */
    List<Element> getAllButtons();

}
