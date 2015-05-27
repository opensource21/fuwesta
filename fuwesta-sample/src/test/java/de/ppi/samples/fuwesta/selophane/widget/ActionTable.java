package de.ppi.samples.fuwesta.selophane.widget;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.ImplementedBy;
import org.selophane.elements.widget.Button;

import de.ppi.samples.fuwesta.selophane.widget.ActionTableImpl.RowImpl;

/**
 * A table with actions in the last column.
 *
 */
@ImplementedBy(ActionTableImpl.class)
public interface ActionTable extends Element {

    /**
     * Return the number of rows with data.
     *
     * @return the number of rows with data.
     */
    int getNrOfDataRows();

    /**
     * Return the number of columns with data.
     *
     * @return the number of columns with data.
     */
    int getNrOfDataColumns();

    /**
     * Returns the header.
     *
     * @return the header.
     */
    List<WebElement> getHeader();

    /**
     * Return a list of rows which contains the data.
     *
     * @return a list of rows which contains the data.
     */
    List<Row> getDataRows();

    /**
     * A row of an action-table.
     *
     */
    @ImplementedBy(RowImpl.class)
    public interface Row extends Element {

        /**
         * Returns the i-th element of the column starting by 0.
         *
         * @param i the nr of which element is wanted, index starts by 0.
         * @return the i-th element of the column starting by 0.
         */
        WebElement getColumn(int i);

        /**
         * Returns the list of {@link ActionButton}.
         *
         * @return the list of {@link ActionButton}.
         */
        List<Button> getActions();

    }
}
