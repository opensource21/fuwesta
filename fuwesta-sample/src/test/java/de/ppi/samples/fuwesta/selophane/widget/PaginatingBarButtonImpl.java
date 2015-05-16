package de.ppi.samples.fuwesta.selophane.widget;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.selophane.elements.base.ElementImpl;

import de.ppi.selenium.util.CSSHelper;

/**
 * Implementation of a {@link PaginatingBarButton}.
 *
 */
public class PaginatingBarButtonImpl extends ElementImpl implements
        PaginatingBarButton {
    /**
     * Class of a disabled button.
     */
    private static final String CLASS_DISABLED = "disabled";

    /**
     * Class of the active button.
     */
    private static final String CLASS_ACTIVE = "active";

    /**
     *
     * Initiates an object of type PaginatingBarButtonImpl.
     *
     * @param element element to wrap up
     */
    public PaginatingBarButtonImpl(WebElement element) {
        super(element);
    }

    @Override
    public void click() {
        findElement(By.tagName("a")).click();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
        return CSSHelper.getClasses(getWrappedElement()).contains(CLASS_ACTIVE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDisabled() {
        return CSSHelper.getClasses(getWrappedElement()).contains(
                CLASS_DISABLED);
    }

}
