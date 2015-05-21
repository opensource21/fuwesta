package de.ppi.samples.fuwesta.selophane.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.junit.Test;

import de.ppi.samples.fuwesta.dbunit.dataset.TestData;
import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selophane.base.AuthRule.Auth;
import de.ppi.samples.fuwesta.selophane.widget.PaginatingBar;

/**
 * Test for the post-page.
 *
 */
public class PostIntegrationTest extends AbstractPostIntegrationTest {

    /**
     * {@inheritDoc}
     */
    @Override
    protected IDataSet getDataSet() throws DataSetException {
        return TestData.initWithSampleData();
    }

    /**
     * Test if the post-menu is active.
     */
    @Test
    public void testMenuPostActive() {
        browser.getRelativeUrl(URL.Post.LIST);
        assertThat(browser.getCurrentRelativeUrl()).isEqualTo(URL.Post.LIST);
        assertThat(postListPage.getMenu().getMenuItem("Post").isActive())
                .isTrue();
        assertThat(postListPage.getMenu().getMenuItem("User").isActive())
                .isFalse();
    }

    /**
     * Test if only the post menu exists and is active, if a user is
     * authenticated as post.
     */
    @Test
    @Auth(user = "post")
    public void testMenuPost() {
        browser.getRelativeUrl(URL.Post.LIST);
        assertThat(browser.getCurrentRelativeUrl()).isEqualTo(URL.Post.LIST);
        assertThat(postListPage.getMenu().getMenuItem("Post").isActive())
                .isTrue();
        assertThat(postListPage.getMenu().getMenuItems()).hasSize(2);
    }

    /**
     * Test if the table shows 2 entries.
     *
     *
     */
    @Test
    public void testPostList() {
        browser.getRelativeUrl(URL.Post.LIST);
        assertThat(browser.getCurrentRelativeUrl()).isEqualTo(URL.Post.LIST);
        softly.assertThat(postListPage.getTable().getNrOfDataRows()).isEqualTo(
                2);
        softly.assertThat(postListPage.getTable().getNrOfDataColumns())
                .isEqualTo(2);
        softly.assertThat(postListPage.getPaginatingBar().getNrOfButtons())
                .isEqualTo(5);
        softly.assertThat(postListPage.getPaginatingBar().getFirst()).hasClass(
                PaginatingBar.CLASS_DISABLED);
        softly.assertThat(postListPage.getPaginatingBar().getLast()).hasClass(
                PaginatingBar.CLASS_DISABLED);
        softly.assertThat(postListPage.getPaginatingBar().getPrevious())
                .hasClass(PaginatingBar.CLASS_DISABLED);
        softly.assertThat(postListPage.getPaginatingBar().getNext()).hasClass(
                PaginatingBar.CLASS_DISABLED);
        softly.assertThat(postListPage.getPaginatingBar().getButton("1"))
                .hasNotClass(PaginatingBar.CLASS_DISABLED);
    }
}
