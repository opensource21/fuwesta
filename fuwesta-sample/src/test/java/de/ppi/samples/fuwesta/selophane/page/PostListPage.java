package de.ppi.samples.fuwesta.selophane.page;

import lombok.Getter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import de.ppi.samples.fuwesta.selophane.widget.ActionTable;
import de.ppi.samples.fuwesta.selophane.widget.PaginatingBar;
import de.ppi.selenium.browser.SessionManager;

/**
 * The page with the list of the post.
 *
 */
@Getter
public class PostListPage extends MainPage {

    /**
     * The table.
     */
    @FindBy(id = "data")
    private ActionTable table;

    /**
     * The pagination bar.
     */
    @FindBy(className = "pagination")
    private PaginatingBar paginatingBar;

    /**
     *
     * Initiates an object of type PostListPage.
     */
    public PostListPage() {
        this(SessionManager.getSession());
    }

    /**
     * Initiates an object of type PostList.
     *
     * @param webDriver the {@link WebDriver}
     */
    public PostListPage(WebDriver webDriver) {
        super(webDriver);
    }

}
