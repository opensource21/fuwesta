package de.ppi.samples.fuwesta.selophane.test;

import static de.ppi.selenium.assertj.SeleniumAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;

import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selophane.base.WebTestConstants;
import de.ppi.samples.fuwesta.selophane.module.AuthModule;
import de.ppi.samples.fuwesta.selophane.page.LoginPage;
import de.ppi.samples.fuwesta.selophane.page.MainPage;
import de.ppi.selenium.browser.SessionManager;
import de.ppi.selenium.browser.WebBrowser;

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
    public final RuleChain webTest =
            WebTestConstants.WEBTEST_WITHOUT_AUTHENTICATION;

    /**
     * webbrowser.
     */
    private WebBrowser browser;

    /**
     * Loginpage.
     */
    private LoginPage loginPage;

    /**
     * Authenticationmodul.
     */
    private AuthModule authModule;

    /**
     * Init the instances after the rule has run.
     */
    @Before
    public void init() {
        browser = SessionManager.getSession();

        loginPage = new LoginPage();

        authModule = new AuthModule();
        authModule.logout();
    }

    /**
     * Logout after a test.
     */
    @After
    public void logout() {
        authModule.logout();
    }

    /**
     * Login as admin.
     *
     * @throws Exception is something
     */
    @Test
    public void loginAsAdmin() throws Exception {
        browser.getRelativeUrl(URL.HOME);
        authModule.loginAsAdmin();
        assertThat(browser).hasRelativeUrl(URL.HOME);
    }

    /**
     * Login as user post.
     *
     */
    @Test
    public void loginAsPost() {
        browser.getRelativeUrl(URL.Post.HOME);
        authModule.login("post");
        assertThat(browser).hasRelativeUrl(URL.Post.HOME);
        final MainPage mainPage =
                new MainPage(SessionManager.getSession(), "MainPage");
        assertThat(mainPage.getMenu().getMenuItems()).hasSize(2);
        assertThat(mainPage.getMenu().getMenuItem(0).getText())
                .isEqualTo("Post");
        assertThat(mainPage.getMenu().getMenuItem(0).isActive()).isTrue();
        assertThat(mainPage.getMenu().getMenuItem(1).getText())
                .isEqualTo("Logout");
        assertThat(mainPage.getMenu().getMenuItem(1).isActive()).isFalse();
    }

    /**
     * Login as post and call a site which isn't allowed.
     *
     */
    @Test
    public void loginAsPostDeny() {
        browser.getRelativeUrl(URL.User.HOME);
        assertThat(loginPage.areAllElementsVisible()).isTrue();
        authModule.login("post");
        assertThat(browser).hasRelativeUrl(URL.Auth.UNAUTHORIZED);
        assertThat(browser.getTitle()).contains("UNAUTHORIZED");
    }

    /**
     * Login as user post.
     *
     */
    @Test
    public void loginAsPostWithRememberMe() {
        browser.getRelativeUrl(URL.HOME);
        authModule.login("post", "123", true);
        assertThat(browser).hasCookies("rememberMe");
    }
}
