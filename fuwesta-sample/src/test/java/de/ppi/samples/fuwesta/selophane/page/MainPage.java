package de.ppi.samples.fuwesta.selophane.page;

import lombok.Getter;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.FindBy;

import de.ppi.samples.fuwesta.selophane.base.BasePage;
import de.ppi.samples.fuwesta.selophane.widget.Menu;
import de.ppi.selenium.browser.SessionManager;

/**
 * Page for to the main.html-template with is the basic layout.
 *
 */
@Getter
public class MainPage extends BasePage {

    /**
     * The menu.
     */
    @FindBy(className = "nav")
    private Menu menu;

    /**
     * Initiates an object of type MainPage.
     *
     * @param searchContext the search context.
     */
    public MainPage(SearchContext searchContext) {
        super(searchContext);
    }

    /**
     * Initiates an object of type MainPage.
     */
    public MainPage() {
        this(SessionManager.getSession());
    }
}
