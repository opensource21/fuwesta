package de.ppi.samples.fuwesta.selophane.module;

import de.ppi.samples.fuwesta.selophane.page.LoginPage;
import de.ppi.selenium.browser.SessionManager;
import de.ppi.selenium.browser.WebBrowser;

/**
 * Helper-Methods for Login.
 *
 */
public class AuthModule {

    /**
     * Testpage.
     */
    private final LoginPage loginPage;

    /**
     * Instance of the webbrowser.
     */
    private final WebBrowser webBrowser;

    /**
     *
     * Initiates an object of type {@link AuthModule}.
     *
     * @param webBrowser the {@link WebBrowser}.
     */
    public AuthModule(WebBrowser webBrowser) {
        this.loginPage = new LoginPage(webBrowser);
        this.webBrowser = webBrowser;
    }

    /**
     * Initiates an object of type LoginModule.
     */
    public AuthModule() {
        this(SessionManager.getSession());
    }

    /**
     * Logout.
     */
    public void logout() {
        webBrowser.getRelativeUrl("/logout");
    }

    /**
     * Logout if the user is loggedin.
     * 
     * @return true if a logout was necessary.
     */
    public boolean logoutIfNecessary() {
        if (webBrowser.manage().getCookieNamed("JSESSIONID") != null) {
            logout();
            return true;
        } else {
            return false;
        }
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
        loginPage.getUsername().set(username);
        loginPage.getPassword().set(password);
        if (rememberMe) {
            loginPage.getRememberMeCB().check();
        }
        loginPage.getLoginBtn().click();
    }
}
