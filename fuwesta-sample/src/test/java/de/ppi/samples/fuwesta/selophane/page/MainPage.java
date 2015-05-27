package de.ppi.samples.fuwesta.selophane.page;

import lombok.Getter;

import org.openqa.selenium.WebDriver;
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
     * @param webDriver the {@link WebDriver} for searches.
     */
    public MainPage(WebDriver webDriver) {
        super(webDriver);
    }

    /**
     * Initiates an object of type MainPage.
     */
    public MainPage() {
        this(SessionManager.getSession());
    }
}
