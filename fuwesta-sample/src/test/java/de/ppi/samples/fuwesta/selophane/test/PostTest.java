package de.ppi.samples.fuwesta.selophane.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selophane.base.WebTestConstants;
import de.ppi.samples.fuwesta.selophane.module.AuthModule;
import de.ppi.samples.fuwesta.selophane.page.LoginPage;
import de.ppi.samples.fuwesta.selophane.page.PostListPage;
import de.ppi.selenium.browser.SessionManager;
import de.ppi.selenium.browser.WebBrowser;

/**
 * Class PostTest
 *
 */
public class PostTest {

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
        authModule.logoutIfNecessary();
        browser.getRelativeUrl(URL.Post.LIST);
        authModule.login("admin");
        assertThat(browser.getCurrentRelativeUrl()).isEqualTo(URL.Post.LIST);
        assertThat(postListPage.getMenu()).isNotNull();
        assertThat(postListPage.getMenu().getPost().isActive()).isTrue();
        assertThat(postListPage.getMenu().getUser().isActive()).isFalse();
        assertThat(postListPage.getGenMenu().getMenuItems()).isNotEmpty()
                .hasSize(4);

    }

}
