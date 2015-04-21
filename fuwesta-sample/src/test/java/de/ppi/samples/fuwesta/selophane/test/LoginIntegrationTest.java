package de.ppi.samples.fuwesta.selophane.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;

import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selenium.base.WebTestConstants;
import de.ppi.samples.fuwesta.selophane.module.LoginModule;
import de.ppi.samples.fuwesta.selophane.page.LoginPage;
import de.ppi.selenium.browser.SessionManager;
import de.ppi.selenium.browser.WebBrowser;
import de.ppi.selenium.util.Protocol;

/**
 * Test of login.
 *
 */
@FixMethodOrder
public class LoginIntegrationTest {

    /**
     * All WebTest-Actions.
     */
    @Rule
    public RuleChain webTest = WebTestConstants.WEBTEST;

    private WebBrowser browser = SessionManager.getSession();

    private LoginPage loginPage = new LoginPage();

    private LoginModule loginModule = new LoginModule(loginPage);

    /**
     * Login as admin.
     *
     * @throws Exception is something
     */
    @Test
    public void loginAsAdmin() throws Exception {
        browser.getRelativeUrl(URL.HOME);
        loginModule.loginAsAdmin();
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
        if (browser.manage().getCookieNamed("JSESSIONID") != null) {
            browser.getRelativeUrl("/logout");
        }
        browser.getRelativeUrl(URL.HOME);
        loginModule.login("post");
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
        loginModule.login("post");
        Protocol.log("Test", "Zwischentest", browser);
        assertThat(browser.getCurrentUrl()).endsWith("/user/");
    }

    /**
     * Login as user post.
     *
     */
    @Test
    public void loginAsPostWithRememberMe() {
        if (browser.manage().getCookieNamed("JSESSIONID") != null) {
            browser.getRelativeUrl("/logout");
        }
        browser.getRelativeUrl(URL.HOME);
        loginModule.login("post", "123", true);
        assertThat(browser.manage().getCookieNamed("rememberMe")).isNotNull();

    }
}
