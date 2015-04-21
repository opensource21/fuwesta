package de.ppi.samples.fuwesta.selophane.module;

import de.ppi.samples.fuwesta.selophane.page.LoginPage;

/**
 * Helper-Methods for Login.
 *
 */
public class LoginModule {

    /**
     * Testpage.
     */
    private LoginPage loginPage;

    /**
     *
     * Initiates an object of type LoginModule.
     *
     * @param loginPage the loginPage
     */
    public LoginModule(LoginPage loginPage) {
        this.loginPage = loginPage;
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
