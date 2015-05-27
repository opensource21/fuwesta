package de.ppi.samples.fuwesta.selophane.page;

import lombok.Getter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.selophane.elements.widget.Button;
import org.selophane.elements.widget.CheckBox;
import org.selophane.elements.widget.TextInput;

import de.ppi.samples.fuwesta.selophane.base.BasePage;
import de.ppi.selenium.browser.SessionManager;

/**
 * Handles the login page.
 *
 */
@Getter
public class LoginPage extends BasePage {

    /**
     * Input for username.
     */
    @FindBy(id = "username")
    private TextInput username;

    /**
     * Input for password.
     */
    @FindBy(id = "password")
    private TextInput password;

    /**
     * Checkbox.
     */
    @FindBy(id = "remember-me")
    private CheckBox rememberMeCB;

    /** Login-Button. */
    @FindBy(className = "btn")
    private Button loginBtn;

    /**
     * Initiates an object of type LoginPage.
     *
     */
    public LoginPage() {
        this(SessionManager.getSession());
    }

    /**
     * Initiates an object of type LoginPage.
     *
     * @param webDriver a webdriver as searchcontext.
     */
    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    /**
     * Check if alle elements are visible.
     *
     * @return true if all elements are visible.
     */
    public boolean areAllElementsVisible() {
        return username.isDisplayed() && password.isDisplayed()
                && rememberMeCB.isDisplayed() && loginBtn.isDisplayed();
    }

}
