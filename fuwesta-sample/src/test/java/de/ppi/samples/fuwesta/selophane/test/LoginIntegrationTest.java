package de.ppi.samples.fuwesta.selophane.test;

import static org.assertj.core.api.Assertions.assertThat;
import static de.ppi.selenium.assertj.SeleniumAssertions.assertThat;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;

import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selophane.base.WebTestConstants;
import de.ppi.samples.fuwesta.selophane.module.AuthModule;
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

    private AuthModule authModule = new AuthModule();

    /**
     * Login as admin.
     *
     * @throws Exception is something
     */
    @Test
    public void loginAsAdmin() throws Exception {
        authModule.logoutIfNecessary();
        browser.getRelativeUrl(URL.HOME);
        authModule.loginAsAdmin();
        Protocol.log("Test", "Zwischentest", browser);
        assertThat(browser).hasRelativeUrl(URL.HOME);
        authModule.logout();
    }

    /**
     * Login as user post.
     *
     */
    @Test
    public void loginAsPost() {
        authModule.logoutIfNecessary();
        browser.getRelativeUrl(URL.HOME);
        authModule.login("post");
        Protocol.log("Test", "Zwischentest", browser);
        assertThat(browser).hasRelativeUrl(URL.HOME);
    }

    /**
     * Login as post and call a site which isn't allowed.
     *
     */
    @Test
    public void loginAsPostDeny() {
        authModule.logoutIfNecessary();
        browser.getRelativeUrl("/user/");
        assertThat(loginPage.areAllElementsVisible()).isTrue();
        authModule.login("post");
        Protocol.log("Test", "Zwischentest", browser);
        assertThat(browser).hasRelativeUrl("/user/");
    }

    /**
     * Login as user post.
     *
     */
    @Test
    public void loginAsPostWithRememberMe() {
        authModule.logoutIfNecessary();
        browser.getRelativeUrl(URL.HOME);
        authModule.login("post", "123", true);
        assertThat(browser.manage().getCookieNamed("rememberMe")).isNotNull();

    }
}
