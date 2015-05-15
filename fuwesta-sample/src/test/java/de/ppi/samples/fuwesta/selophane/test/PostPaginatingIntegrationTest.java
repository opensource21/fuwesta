package de.ppi.samples.fuwesta.selophane.test;

import java.util.List;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.junit.Test;
import org.selophane.elements.base.Element;

import de.ppi.samples.fuwesta.dbunit.dataset.TestData;
import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selophane.widget.PaginatingBar;

/**
 * Test for the post-page.
 *
 */
public class PostPaginatingIntegrationTest extends AbstractPostIntegrationTest {

    /**
     * {@inheritDoc}
     */
    @Override
    protected IDataSet getDataSet() throws DataSetException {
        return TestData.createPostData(100);
    }

    /**
     * Test if the table shows 100 entries.
     *
     */
    @Test
    public void testPostPaginating() {
        browser.getRelativeUrl(URL.Post.LIST);
        softly.assertThat(postListPage.getTable().getColumnCount())
                .isEqualTo(3);
        softly.assertThat(postListPage.getPaginatingBar().getNrOfButtons())
                .isEqualTo(9);
        softly.assertThat(postListPage.getPaginatingBar().getFirst()).hasClass(
                PaginatingBar.CLASS_DISABLED);
        softly.assertThat(postListPage.getPaginatingBar().getLast())
                .hasNotClass(PaginatingBar.CLASS_DISABLED);
        softly.assertThat(postListPage.getPaginatingBar().getPrevious())
                .hasClass(PaginatingBar.CLASS_DISABLED);
        softly.assertThat(postListPage.getPaginatingBar().getNext())
                .hasNotClass(PaginatingBar.CLASS_DISABLED);
        softly.assertThat(postListPage.getPaginatingBar().getButton("1"))
                .hasNotClass(PaginatingBar.CLASS_DISABLED)
                .hasClass(PaginatingBar.CLASS_ACTIVE);
        final List<Element> buttons =
                postListPage.getPaginatingBar().getAllButtons();
        for (int i = 0; i < buttons.size(); i++) {
            final Element element = buttons.get(i);
            switch (i) {
            case 0:
            case 1:
                softly.assertThat(element).hasClass(
                        PaginatingBar.CLASS_DISABLED);
                break;
            case 2:
                softly.assertThat(element).hasClass(PaginatingBar.CLASS_ACTIVE);
            default:
                softly.assertThat(element).hasNotClass(
                        PaginatingBar.CLASS_DISABLED);
            }

        }
        softly.assertThat(
                postListPage.getTable().getCellAtIndex(1, 1).getText())
                .isEqualTo("Title 1");
    }

}
