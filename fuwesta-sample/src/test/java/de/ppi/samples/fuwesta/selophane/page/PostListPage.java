package de.ppi.samples.fuwesta.selophane.page;

import lombok.Getter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.selophane.elements.widget.Button;

import de.ppi.samples.fuwesta.selophane.widget.ActionTable;
import de.ppi.samples.fuwesta.selophane.widget.PaginatingBar;
import de.ppi.selenium.browser.SessionManager;

/**
 * The page with the list of the post.
 *
 */
@Getter
public class PostListPage extends MainPage {

    /** Index of the button to show the post. */
    public static final int INDEX_OF_SHOW_BUTTON = 0;
    /** Index of the button to edit the post. */
    public static final int INDEX_OF_EDIT_BUTTON = 1;
    /** Index of the button to partial edit the post. */
    public static final int INDEX_OF_PARTIALEDIT_BUTTON = 2;
    /** Index of the button to delete the post. */
    public static final int INDEX_OF_DELETE_BUTTON = 3;

    /**
     * Home-Button.
     */
    @FindBy(id = "homeBtn")
    private Button homeButton;

    /**
     * Create Button.
     */
    @FindBy(id = "createBtn")
    private Button createButton;

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
