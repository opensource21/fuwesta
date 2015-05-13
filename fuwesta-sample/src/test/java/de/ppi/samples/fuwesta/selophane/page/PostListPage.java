package de.ppi.samples.fuwesta.selophane.page;

import lombok.Getter;

import org.openqa.selenium.SearchContext;
import org.selophane.elements.widget.Table;

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
    private Table data;

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
     * @param searchContext the {@link SearchContext}
     */
    public PostListPage(SearchContext searchContext) {
        super(searchContext);
    }

}
