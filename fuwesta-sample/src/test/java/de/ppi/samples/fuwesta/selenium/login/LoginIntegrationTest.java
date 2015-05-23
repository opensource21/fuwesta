package de.ppi.samples.fuwesta.selenium.login;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selenium.base.SeleniumTest;
import de.ppi.samples.fuwesta.selenium.pages.LoginPage;

/**
 * Test of login.
 *
 */
public class LoginIntegrationTest extends SeleniumTest {

    private static LoginPage loginPage;

    @Before
    public void initLoginPage() {
        if (loginPage == null) {
            loginPage = LoginPage.get(getDriver());
        }
    }

    @Test
    public void aStartWebserver() {
    }

    @Test
    public void bStartWebDriver() {
        getDriver().get(getBaseUrl() + "/login");
    }

    /**
     * Login as admin.
     *
     * @throws Exception is something
     */
    @Test
    public void loginAsAdmin() throws Exception {
        callRelativeUrl(URL.HOME);
        loginPage.loginAsAdmin();
        saveScreenshot("Test.png");
        assertCurrentUrl(URL.HOME);
        callRelativeUrl("/logout");
    }

    /**
     * Login as user post.
     *
     * @throws IOException ..
     */
    @Test
    public void loginAsPost() throws IOException {
        callRelativeUrl(URL.HOME);
        loginPage.login("post");
        saveScreenshot("Test.png");
        assertCurrentUrl(URL.HOME);
    }

    /**
     * Login as post and call a site which isn't allowed.
     *
     * @throws IOException ..
     */
    @Test
    public void loginAsPostDeny() throws IOException {
        callRelativeUrl("/user/");
        assertThat(loginPage.areAllElementsVisible()).isTrue();
        loginPage.login("post");
        saveScreenshot("Test.png");
        assertCurrentUrl("/user/");
    }
}
