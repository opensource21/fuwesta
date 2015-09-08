package de.ppi.samples.fuwesta.selophane.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import de.ppi.samples.fuwesta.selophane.base.BasePage;
import de.ppi.samples.fuwesta.selophane.widget.Menu;
import lombok.Getter;

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
     * @param pageName name of the page-
     */
    public MainPage(WebDriver webDriver, String pageName) {
        super(webDriver, pageName);
    }

}
