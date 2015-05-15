package de.ppi.samples.fuwesta.selophane.test;

import java.util.List;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.junit.Test;

import de.ppi.samples.fuwesta.dbunit.dataset.TestData;
import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selophane.widget.PaginatingBar;
import de.ppi.samples.fuwesta.selophane.widget.PaginatingBarButton;

/**
 * Test for the post-page.
 *
 */
public class PostPaginatingIntegrationTest extends AbstractPostIntegrationTest {

    /**
     * The pagesize.
     */
    private static final int PAGE_SIZE = 5;

    /**
     * {@inheritDoc}
     */
    @Override
    protected IDataSet getDataSet() throws DataSetException {
        return TestData.createPostData(PAGE_SIZE * 20);
    }

    /**
     * Test if the table shows 100 entries.
     *
     */
    @Test
    public void testPostPaginating() {
        browser.getRelativeUrl(URL.Post.LIST);
        checkTableAndPaginating(1);
    }

    /**
     * Test if the table shows 100 entries.
     *
     */
    @Test
    public void testPostPaginatingNext() {
        browser.getRelativeUrl(URL.Post.LIST);
        for (int i = 2; i < 20; i++) {
            postListPage.getPaginatingBar().getNext().click();
            checkTableAndPaginating(i);
        }
    }

    /**
     * Checks if the paginating is shown correct and that the table starts with
     * the correct item and has the expected number of items.
     *
     * @param currentPage the number of the current page.
     *
     */
    @SuppressWarnings("boxing")
    private void checkTableAndPaginating(final int currentPage) {
        final PaginatingBar paginatingBar = postListPage.getPaginatingBar();
        softly.assertThat(postListPage.getTable().getRowCount()).isEqualTo(
                PAGE_SIZE + 1);
        softly.assertThat(paginatingBar.getNrOfButtons()).isEqualTo(9);
        boolean isFirstPage = currentPage == 1;
        boolean isLastPage = currentPage == 20;
        softly.assertThat(paginatingBar.getFirst().isDisabled())
                .as("Currentpage=%s", currentPage).isEqualTo(isFirstPage);
        softly.assertThat(paginatingBar.getPrevious().isDisabled())
                .as("Currentpage=%s", currentPage).isEqualTo(isFirstPage);
        softly.assertThat(paginatingBar.getLast().isDisabled())
                .as("Currentpage=%s", currentPage).isEqualTo(isLastPage);
        softly.assertThat(paginatingBar.getNext().isDisabled())
                .as("Currentpage=%s", currentPage).isEqualTo(isLastPage);
        final List<PaginatingBarButton> buttons = paginatingBar.getAllButtons();

        final int currentPageIndex;
        final int startLabel;
        if (currentPage < 3) {
            startLabel = 1;
            currentPageIndex = currentPage + 1;
        } else if (currentPage > 18) {
            startLabel = 16;
            currentPageIndex = 6 + currentPage - 20;
        } else {
            startLabel = currentPage - 2;
            currentPageIndex = 4;
        }
        for (int i = 2; i < buttons.size() - 2; i++) {
            final PaginatingBarButton element = buttons.get(i);
            softly.assertThat(element.getText()).isEqualTo(
                    "" + (startLabel + i - 2));
            if (i == currentPageIndex) {
                softly.assertThat(element.isActive())
                        .as("Currentpage=%s and i=%s", currentPage, i).isTrue();
                softly.assertThat(element.isDisabled())
                        .as("Currentpage=%s and i=%s", currentPage, i)
                        .isFalse();
            } else {
                softly.assertThat(element.isDisabled())
                        .as("Currentpage=%s and i=%s", currentPage, i)
                        .isFalse();
                softly.assertThat(element.isActive())
                        .as("Currentpage=%s and i=%s", currentPage, i)
                        .isFalse();
            }

        }
        softly.assertThat(
                postListPage.getTable().getCellAtIndex(1, 1).getText())
                .as("Currentpage=%s", currentPage)
                .isEqualTo("Title " + ((currentPage - 1) * PAGE_SIZE + 1));
    }
}
