package de.ppi.samples.fuwesta.selophane.login;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;

import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selenium.base.WebTestConstants;
import de.ppi.samples.fuwesta.selophane.pages.LoginPage;
import de.ppi.selenium.browser.SessionManager;
import de.ppi.selenium.browser.WebBrowser;
import de.ppi.selenium.util.Protocol;

/**
 * Test of login.
 *
 */
@FixMethodOrder
public class LoginIntegrationTest {

    @Rule
    public RuleChain webTest = WebTestConstants.WEBTEST;

    private WebBrowser browser = SessionManager.getSession();

    private LoginPage loginPage = new LoginPage();

    /**
     * Login as admin.
     *
     * @throws Exception is something
     */
    @Test
    public void loginAsAdmin() throws Exception {
        browser.getRelativeUrl(URL.HOME);
        loginPage.loginAsAdmin();
        Protocol.log("Test", "Zwischentest", browser);
        assertThat(browser.getCurrentUrl()).endsWith(URL.HOME);
        browser.getRelativeUrl("/logout");
    }

    /**
     * Login as user post.
     *
     */
    @Test
    public void loginAsPost() {
        browser.getRelativeUrl("/logout");
        browser.getRelativeUrl(URL.HOME);
        loginPage.login("post");
        Protocol.log("Test", "Zwischentest", browser);
        assertThat(browser.getCurrentUrl()).endsWith(URL.HOME);
    }

    /**
     * Login as post and call a site which isn't allowed.
     *
     */
    @Test
    public void loginAsPostDeny() {
        browser.getRelativeUrl("/user/");
        assertThat(loginPage.areAllElementsVisible()).isTrue();
        loginPage.login("post");
        Protocol.log("Test", "Zwischentest", browser);
        assertThat(browser.getCurrentUrl()).endsWith("/user/");
    }
}
