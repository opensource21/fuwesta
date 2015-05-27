package de.ppi.samples.fuwesta.selophane.module;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.selophane.elements.base.Element;

/**
 * {@link ExpectedCondition} which makes sure that the element isn't present any
 * more. It detect's if a page is reloaded after a click for example. This based
 * on http://www.obeythetestinggoat.com/how-to-get-selenium-to-wait
 * -for-page-load-after-a-click.html
 *
 */
public class ElementIsStaleExpected implements ExpectedCondition<Boolean> {

    /**
     * Element which should be tracked.
     */
    private final WebElement element;

    /**
     * Initiates an object of type ElementIsStateExpected.
     *
     * @param element the element which should became stale, be sure you don't
     *            use a proxy!
     */
    public ElementIsStaleExpected(WebElement element) {
        if (element instanceof Element) {
            this.element = ((Element) element).getWrappedElement();
        } else {
            this.element = element;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean apply(WebDriver input) {
        try {
            element.isEnabled();
            return Boolean.FALSE;
        } catch (StaleElementReferenceException e) {
            return Boolean.TRUE;
        }
    }

}
