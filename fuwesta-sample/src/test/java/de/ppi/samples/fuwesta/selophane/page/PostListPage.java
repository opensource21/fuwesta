package de.ppi.samples.fuwesta.selophane.page;

import lombok.Getter;

import org.openqa.selenium.SearchContext;

import de.ppi.selenium.browser.SessionManager;

/**
 * The page with the list of the post.
 *
 */
@Getter
public class PostListPage extends MainPage {

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
