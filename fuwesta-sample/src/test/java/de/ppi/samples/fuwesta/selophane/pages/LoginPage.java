package de.ppi.samples.fuwesta.selophane.pages;

import static org.assertj.core.api.Assertions.assertThat;
import lombok.Getter;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selophane.elements.factory.api.ElementFactory;

import de.ppi.samples.fuwesta.selenium.base.BasePage;
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
    private WebElement username;

    /**
     * Input for password.
     */
    @FindBy(id = "password")
    private WebElement password;

    /**
     * Checkbox.
     */
    @FindBy(id = "remember-me")
    private WebElement rememberMeCB;

    /** Login-Button. */
    @FindBy(className = "btn")
    private WebElement loginBtn;

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
     * @param context a searchcontext most the webdriver.
     */
    public LoginPage(SearchContext context) {
        ElementFactory.initElements(context, this);
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

    /**
     * Login as admin.
     */
    public void loginAsAdmin() {
        login("admin");
    }

    /**
     * Login as given user.
     *
     * @param username name of the user.
     */
    public void login(String username) {
        login(username, "123", false);
    }

    /**
     * Login with username and password.
     *
     * @param username the name of the user.
     * @param password the password.
     * @param rememberMe if the rememberMe-Checkbox is selected.
     */
    public void login(String username, String password, boolean rememberMe) {
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        if (rememberMe) {
            this.rememberMeCB.click();
        }
        assertThat(this.rememberMeCB.isSelected()).isEqualTo(rememberMe);
        loginBtn.click();
    }

}
