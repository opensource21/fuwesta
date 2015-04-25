package de.ppi.samples.fuwesta.selophane.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;

import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selophane.base.AuthRule.Auth;
import de.ppi.samples.fuwesta.selophane.base.WebTestConstants;
import de.ppi.samples.fuwesta.selophane.module.AuthModule;
import de.ppi.samples.fuwesta.selophane.page.PostListPage;
import de.ppi.selenium.browser.SessionManager;
import de.ppi.selenium.browser.WebBrowser;

/**
 * Test for the pots-page.
 *
 */
public class PostIntegrationTest {

    /**
     * All WebTest-Actions.
     */
    @Rule
    public RuleChain webTest = WebTestConstants.WEBTEST;

    private WebBrowser browser = SessionManager.getSession();

    private PostListPage postListPage = new PostListPage();

    private AuthModule authModule = new AuthModule();

    @Test
    public void testMenuPostActive() {
        browser.getRelativeUrl(URL.Post.LIST);
        assertThat(browser.getCurrentRelativeUrl()).isEqualTo(URL.Post.LIST);
        assertThat(postListPage.getMenu().getMenuItem("Post").isActive())
                .isTrue();
        assertThat(postListPage.getMenu().getMenuItem("User").isActive())
                .isFalse();
    }

    @Test
    @Auth(user = "post")
    public void testMenuPost() {
        browser.getRelativeUrl(URL.Post.LIST);
        assertThat(browser.getCurrentRelativeUrl()).isEqualTo(URL.Post.LIST);
        assertThat(postListPage.getMenu().getMenuItem("Post").isActive())
                .isTrue();
        assertThat(postListPage.getMenu().getMenuItems()).hasSize(2);
    }

}
