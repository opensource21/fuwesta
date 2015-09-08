package de.ppi.samples.fuwesta.selophane.widget;

import java.util.List;

import lombok.Getter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selophane.elements.base.Fragment;
import org.selophane.elements.base.UniqueElementLocator;
import org.selophane.elements.widget.Button;

/**
 * Implememtation of an ActionTable.
 *
 */
@Getter
public class ActionTableImpl extends Fragment implements ActionTable {

    /**
     * Initiates an object of type ActionTableImpl.
     *
     * @param elementLocator the locator of the webelement.
     */
    public ActionTableImpl(final UniqueElementLocator elementLocator) {
        super(elementLocator);
    }

    /** The data rows. */
    @FindBy(css = "tbody tr")
    private List<ActionTable.Row> dataRows;

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrOfDataRows() {
        return dataRows.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrOfDataColumns() {
        return findElement(By.cssSelector("tr")).findElements(
                By.cssSelector("*")).size() - 1;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WebElement> getHeader() {
        final List<WebElement> headerRows =
                findElements(By.cssSelector("thead tr"));
        if (headerRows.size() != 1) {
            throw new IllegalStateException(
                    "A action table should have exactly 1 header-row, and not "
                            + headerRows.size());
        }
        return headerRows.get(0).findElements(By.tagName("td"));
    }

    /**
     * The default implementation of a {@link ActionTable.Row}.
     *
     */
    public static final class RowImpl extends Fragment implements Row {

        /**
         * List of actions.
         */
        @FindBy(xpath = "td[last()]/a")
        private List<Button> actions;

        /**
         * Initiates an object of type RowImpl.
         *
         * @param elementLocator the locator of the webelement.
         */
        public RowImpl(final UniqueElementLocator elementLocator) {
            super(elementLocator);

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public WebElement getColumn(int i) {

            return findElements(By.tagName("td")).get(i);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<Button> getActions() {
            return actions;
        }

    }
}
