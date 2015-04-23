package de.ppi.samples.fuwesta.selophane.page;

import lombok.Getter;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.FindBy;

import de.ppi.samples.fuwesta.selophane.base.BasePage;
import de.ppi.samples.fuwesta.selophane.fragment.SampleMenu;
import de.ppi.selenium.browser.SessionManager;

/**
 * The page with the list of the post.
 *
 */
@Getter
public class PostListPage extends BasePage {

    @FindBy(className = "nav")
    private SampleMenu menu;

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
