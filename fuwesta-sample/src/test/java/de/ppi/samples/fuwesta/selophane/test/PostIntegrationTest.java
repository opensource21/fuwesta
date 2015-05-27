package de.ppi.samples.fuwesta.selophane.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.junit.Test;
import org.selophane.elements.widget.Button;

import de.ppi.samples.fuwesta.dbunit.dataset.TestData;
import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selophane.base.AuthRule.Auth;
import de.ppi.samples.fuwesta.selophane.widget.ActionTable;
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
     * Test if the table shows 2 entries and that the label are fine.
     *
     *
     */
    @Test
    public void testPostList() {
        browser.getRelativeUrl(URL.Post.LIST);
        assertThat(browser.getCurrentRelativeUrl()).isEqualTo(URL.Post.LIST);
        final ActionTable table = postListPage.getTable();
        softly.assertThat(table.getNrOfDataRows()).isEqualTo(2);
        softly.assertThat(table.getNrOfDataColumns()).isEqualTo(2);
        final ActionTable.Row firstRow = table.getDataRows().get(0);
        final List<Button> actions = firstRow.getActions();
        softly.assertThat(actions).hasSize(4);
        softly.assertThat(actions.get(0).getText()).isEqualTo("Show");
        softly.assertThat(actions.get(1).getText()).isEqualTo("Edit");
        softly.assertThat(actions.get(2).getText()).isEqualTo("Partialedit");
        softly.assertThat(actions.get(3).getText()).isEqualTo("Delete");

        final PaginatingBar paginatingBar = postListPage.getPaginatingBar();
        softly.assertThat(paginatingBar.getNrOfButtons()).isEqualTo(5);
        softly.assertThat(paginatingBar.getFirst()).hasClass(
                PaginatingBar.CLASS_DISABLED);
        softly.assertThat(paginatingBar.getLast()).hasClass(
                PaginatingBar.CLASS_DISABLED);
        softly.assertThat(paginatingBar.getPrevious()).hasClass(
                PaginatingBar.CLASS_DISABLED);
        softly.assertThat(paginatingBar.getNext()).hasClass(
                PaginatingBar.CLASS_DISABLED);
        softly.assertThat(paginatingBar.getButton("1")).hasNotClass(
                PaginatingBar.CLASS_DISABLED);
        final String postId = firstRow.getColumn(0).getText();
        Button delete = actions.get(3);
        actions.get(0).click();
        softly.assertThat(browser).hasRelativeUrl(
                URL.filledURLWithNamedParams(URL.Post.SHOW, URL.Post.P_POSTID,
                        postId));
        browser.navigate().back();
        // firstRow = postListPage.getTable().getDataRows().get(0);
        actions.get(1).click();
        softly.assertThat(browser).hasRelativeUrl(
                URL.filledURLWithNamedParams(URL.Post.EDIT, URL.Post.P_POSTID,
                        postId));
        browser.navigate().back();
        // firstRow = table.getDataRows().get(0);
        actions.get(2).click();
        softly.assertThat(browser).hasRelativeUrl(
                URL.filledURLWithNamedParams(URL.Post.PARTIALEDIT,
                        URL.Post.P_POSTID, postId));
        browser.navigate().back();
        // firstRow = postListPage.getTable().getDataRows().get(0);
        delete.click();
        // actions.get(3).click();
        softly.assertThat(browser).hasRelativeUrl(
                URL.filledURLWithNamedParams(URL.Post.DELETE,
                        URL.Post.P_POSTID, postId));

    }
}
