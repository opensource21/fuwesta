package de.ppi.samples.fuwesta.selophane.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.junit.Test;

import de.ppi.samples.fuwesta.dbunit.dataset.TestData;
import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selophane.checks.PaginatingBarChecks;
import de.ppi.samples.fuwesta.selophane.widget.PaginatingBar;

/**
 * Test for the post-page.
 *
 */
public class PostPaginatingIntegrationTest extends AbstractPostIntegrationTest {

    /**
     * The number of pages.
     */
    private static final int NUMBER_OF_PAGES = 18;
    /**
     * The pagesize.
     */
    private static final int PAGE_SIZE = 5;

    /**
     * {@inheritDoc}
     */
    @Override
    protected IDataSet getDataSet() throws DataSetException {
        return TestData.createPostData(PAGE_SIZE * NUMBER_OF_PAGES);
    }

    /**
     * Test if the table shows 100 entries.
     *
     */
    @Test
    public void testPostPaginating() {
        browser.getRelativeUrl(URL.Post.LIST);
        softly.assertThat(postListPage.getTable().getRowCount()).isEqualTo(
                PAGE_SIZE + 1);
        PaginatingBarChecks.checkPaginating(softly,
                postListPage.getPaginatingBar(), 1, NUMBER_OF_PAGES);
    }

    /**
     * Test the next-button of {@link PaginatingBar}.
     *
     */
    @SuppressWarnings("boxing")
    @Test
    public void testPostPaginatingNext() {
        browser.getRelativeUrl(URL.Post.LIST);
        for (int i = 2; i < NUMBER_OF_PAGES; i++) {
            postListPage.getPaginatingBar().getNext().click();
            softly.assertThat(postListPage.getTable().getRowCount()).isEqualTo(
                    PAGE_SIZE + 1);
            PaginatingBarChecks.checkPaginating(softly,
                    postListPage.getPaginatingBar(), i, NUMBER_OF_PAGES);
            softly.assertThat(
                    postListPage.getTable().getCellAtIndex(1, 1).getText())
                    .as("Currentpage=%s, NumberOfPages=%s", i, NUMBER_OF_PAGES)
                    .isEqualTo("Title " + ((i - 1) * PAGE_SIZE + 1));
        }
    }

    /**
     * Test the last-button of {@link PaginatingBar}.
     *
     */
    @Test
    public void testPostPaginatingLast() {
        browser.getRelativeUrl(URL.Post.LIST);
        assertThat(postListPage.getPaginatingBar().getLast().isDisabled())
                .isFalse();
        postListPage.getPaginatingBar().getLast().click();
        PaginatingBarChecks.checkPaginating(softly,
                postListPage.getPaginatingBar(), NUMBER_OF_PAGES,
                NUMBER_OF_PAGES);
    }

    /**
     * Test the previous-button of {@link PaginatingBar}.
     *
     */
    @SuppressWarnings("boxing")
    @Test
    public void testPostPaginatingPrevious() {
        browser.getRelativeUrl(URL.Post.LIST);
        postListPage.getPaginatingBar().getLast().click();
        for (int i = NUMBER_OF_PAGES - 1; i > 1; i--) {
            postListPage.getPaginatingBar().getPrevious().click();
            softly.assertThat(postListPage.getTable().getRowCount()).isEqualTo(
                    PAGE_SIZE + 1);
            PaginatingBarChecks.checkPaginating(softly,
                    postListPage.getPaginatingBar(), i, NUMBER_OF_PAGES);
            softly.assertThat(
                    postListPage.getTable().getCellAtIndex(1, 1).getText())
                    .as("Currentpage=%s, NumberOfPages=%s", i, NUMBER_OF_PAGES)
                    .isEqualTo("Title " + ((i - 1) * PAGE_SIZE + 1));
        }
    }

    /**
     * Test the first-button of {@link PaginatingBar}.
     *
     */
    @Test
    public void testPostPaginatingFirst() {
        browser.getRelativeUrl(URL.Post.LIST);
        assertThat(postListPage.getPaginatingBar().getLast().isDisabled())
                .isFalse();
        postListPage.getPaginatingBar().getLast().click();
        assertThat(postListPage.getPaginatingBar().getFirst().isDisabled())
                .isFalse();
        postListPage.getPaginatingBar().getFirst().click();

        PaginatingBarChecks.checkPaginating(softly,
                postListPage.getPaginatingBar(), 1, NUMBER_OF_PAGES);
    }

}
