package de.ppi.samples.fuwesta.selophane.module;

import java.util.List;

import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selophane.page.PostFormPage;
import de.ppi.samples.fuwesta.selophane.page.PostListPage;
import de.ppi.samples.fuwesta.selophane.widget.ActionTable.Row;
import de.ppi.selenium.browser.WebBrowser;

/**
 * Helper methods for the post-table.
 *
 */
public class PostModule {

    /**
     * List-Page of Posts.
     */
    private final PostListPage listPage;

    /**
     * Form of a Post.
     */
    private final PostFormPage formPage;

    /**
     * Instance of the webbrowser.
     */
    private final WebBrowser browser;

    /**
     * Initiates an object of type PostModule.
     *
     * @param webBrowser the browser instance.
     */
    public PostModule(WebBrowser webBrowser) {
        this.browser = webBrowser;
        this.listPage = new PostListPage(webBrowser);
        this.formPage = new PostFormPage(webBrowser);
    }

    /**
     * Navigates to the create view.
     */
    public void navigateToCreate() {
        browser.getRelativeUrl(URL.Post.LIST);
        listPage.getCreateButton().click();
    }

    /**
     * Create a post.
     *
     * @param title the title of the post.
     * @param date the creationdate.
     */
    public void createPost(String title, String date) {
        browser.getRelativeUrl(URL.Post.CREATE);
        formPage.getTitleInput().set(title);
        formPage.getCreationTimeInput().set(date);
        formPage.getSave().click();
    }

    /**
     * Find the row in the list for the given title.
     *
     * @param title the title.
     * @return the row.
     */
    public Row getRow(String title) {
        browser.getRelativeUrl(URL.Post.LIST);
        List<Row> rows = listPage.getTable().getDataRows();
        for (Row row : rows) {
            if (row.getColumn(1).getText().equals(title)) {
                return row;
            }

        }
        throw new IllegalStateException("Can't found row with title " + title);
    }

    /**
     * Navigate to the edit-page of the post with the given title.
     *
     * @param title the title of the post.
     */
    public void navigateToEdit(String title) {
        browser.getRelativeUrl(URL.Post.LIST);
        getRow(title).getActions().get(PostListPage.INDEX_OF_EDIT_BUTTON)
                .click();
    }

    /**
     * Navigate to the partialedit-page of the post with the given title.
     *
     * @param title the title of the post.
     */
    public void navigateToPartialEdit(String title) {
        browser.getRelativeUrl(URL.Post.LIST);
        getRow(title).getActions()
                .get(PostListPage.INDEX_OF_PARTIALEDIT_BUTTON).click();
    }

    /**
     * Navigate to the show-page of the post with the given title.
     *
     * @param title the title of the post.
     */
    public void navigateToShow(String title) {
        browser.getRelativeUrl(URL.Post.LIST);
        getRow(title).getActions().get(PostListPage.INDEX_OF_SHOW_BUTTON)
                .click();

    }

    /**
     * Navigate to the delete confirm-page.
     * 
     * @param title ttile of the post
     */
    public void navigateToDelete(String title) {
        browser.getRelativeUrl(URL.Post.LIST);
        getRow(title).getActions().get(PostListPage.INDEX_OF_DELETE_BUTTON)
                .click();

    }
}
