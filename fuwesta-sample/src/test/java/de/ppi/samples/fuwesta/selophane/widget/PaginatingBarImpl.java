package de.ppi.samples.fuwesta.selophane.widget;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.ElementImpl;

/**
 * Implementation for {@link PaginatingBar}.
 *
 */
public class PaginatingBarImpl extends ElementImpl implements PaginatingBar {

    /**
     * Initiates an object of type PaginatingBarImpl.
     *
     * @param element the parent-element
     */
    public PaginatingBarImpl(WebElement element) {
        super(element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element getFirst() {
        return getAllButtons().get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element getLast() {
        final List<Element> allButtons = getAllButtons();
        return allButtons.get(allButtons.size() - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element getNext() {
        final List<Element> allButtons = getAllButtons();
        return allButtons.get(allButtons.size() - 2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element getPrevious() {
        final List<Element> allButtons = getAllButtons();
        return allButtons.get(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrOfButtons() {
        return getAllButtonsInternal().size();
    }

    /**
     * Collects all buttons.
     *
     * @return a list of all buttons.
     */
    private List<WebElement> getAllButtonsInternal() {
        return this.findElements(By.tagName("li"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element getButton(String text) {
        final List<WebElement> allButtons = getAllButtonsInternal();
        for (WebElement webElement : allButtons) {
            if (text.equals(webElement.getText())) {
                return new ElementImpl(webElement);
            }
        }
        throw new NoSuchElementException("No button with text " + text
                + " can be found.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Element> getAllButtons() {
        final List<WebElement> allButtons = getAllButtonsInternal();
        final List<Element> result = new ArrayList<>(allButtons.size());
        for (WebElement webElement : allButtons) {
            result.add(new ElementImpl(webElement));
        }
        return result;
    }

}
