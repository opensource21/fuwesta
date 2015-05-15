package de.ppi.samples.fuwesta.selophane.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.dbunit.dataset.DataSetException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;

import de.ppi.samples.fuwesta.dbunit.AbstractFuWeStaSampleDbUnitTest;
import de.ppi.samples.fuwesta.dbunit.dataset.TestData;
import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selophane.base.AuthRule.Auth;
import de.ppi.samples.fuwesta.selophane.base.WebTestConstants;
import de.ppi.samples.fuwesta.selophane.page.PostListPage;
import de.ppi.samples.fuwesta.selophane.widget.PaginatingBar;
import de.ppi.selenium.assertj.SeleniumSoftAssertions;
import de.ppi.selenium.browser.SessionManager;
import de.ppi.selenium.browser.WebBrowser;

/**
 * Test for the pots-page.
 *
 */
public class PostIntegrationTest extends AbstractFuWeStaSampleDbUnitTest {

    /**
     * All WebTest-Actions.
     */
    @Rule
    public RuleChain webTest = WebTestConstants.WEBTEST;

    /**
     * Rule for SoftAssertions.
     */
    @Rule
    public final SeleniumSoftAssertions softly = new SeleniumSoftAssertions();

    /**
     * Browser instance.
     */
    private WebBrowser browser = SessionManager.getSession();

    /**
     * The postlist-page.
     */
    private PostListPage postListPage = new PostListPage();

    /**
     * Test if the post-menu is active.
     */
    @Test
    public void testMenuPostActive() {
        browser.getRelativeUrl(URL.Post.LIST);
        assertThat(browser.getCurrentRelativeUrl()).isEqualTo(URL.Post.LIST);
        assertThat(postListPage.getMenu().getMenuItem("Post").isActive())
                .isTrue();
        assertThat(postListPage.getMenu().getMenuItem("User").isActive())
                .isFalse();
    }

    /**
     * Test if only the post menu exists and is active, if a user is
     * authenticated as post.
     */
    @Test
    @Auth(user = "post")
    public void testMenuPost() {
        browser.getRelativeUrl(URL.Post.LIST);
        assertThat(browser.getCurrentRelativeUrl()).isEqualTo(URL.Post.LIST);
        assertThat(postListPage.getMenu().getMenuItem("Post").isActive())
                .isTrue();
        assertThat(postListPage.getMenu().getMenuItems()).hasSize(2);
    }

    /**
     * Test if the table shows 2 entries.
     *
     * @throws DataSetException if there are problems with the dataset.
     */
    @Test
    public void testPostList() throws DataSetException {
        super.cleanlyInsert(TestData.initWithSampleData());
        browser.getRelativeUrl(URL.Post.LIST);
        assertThat(browser.getCurrentRelativeUrl()).isEqualTo(URL.Post.LIST);
        softly.assertThat(postListPage.getTable().getRowCount()).isEqualTo(3);
        softly.assertThat(postListPage.getTable().getColumnCount())
                .isEqualTo(3);
        softly.assertThat(postListPage.getPaginatingBar().getNrOfButtons())
                .isEqualTo(5);
        softly.assertThat(postListPage.getPaginatingBar().getFirst()).hasClass(
                PaginatingBar.CLASS_DISABLED);
        softly.assertThat(postListPage.getPaginatingBar().getLast()).hasClass(
                PaginatingBar.CLASS_DISABLED);
        softly.assertThat(postListPage.getPaginatingBar().getPrevious())
                .hasClass(PaginatingBar.CLASS_DISABLED);
        softly.assertThat(postListPage.getPaginatingBar().getNext()).hasClass(
                PaginatingBar.CLASS_DISABLED);
        softly.assertThat(postListPage.getPaginatingBar().getButton("1"))
                .hasNotClass(PaginatingBar.CLASS_DISABLED);

        super.checkResult(TestData.initWithSampleData());
    }

    /**
     * Test if the table shows 2 entries.
     *
     * @throws DataSetException if there are problems with the dataset.
     */
    @Test
    public void testPostPaginating() throws DataSetException {
        super.cleanlyInsert(TestData.createPostData(100));
        browser.getRelativeUrl(URL.Post.LIST);
        softly.assertThat(postListPage.getTable().getColumnCount())
                .isEqualTo(3);
        softly.assertThat(postListPage.getPaginatingBar().getNrOfButtons())
                .isEqualTo(9);
        softly.assertThat(postListPage.getPaginatingBar().getFirst()).hasClass(
                PaginatingBar.CLASS_DISABLED);
        softly.assertThat(postListPage.getPaginatingBar().getLast())
                .hasNotClass(PaginatingBar.CLASS_DISABLED);
        softly.assertThat(postListPage.getPaginatingBar().getPrevious())
                .hasClass(PaginatingBar.CLASS_DISABLED);
        softly.assertThat(postListPage.getPaginatingBar().getNext())
                .hasNotClass(PaginatingBar.CLASS_DISABLED);
        softly.assertThat(postListPage.getPaginatingBar().getButton("1"))
                .hasNotClass(PaginatingBar.CLASS_DISABLED)
                .hasClass(PaginatingBar.CLASS_ACTIVE);
    }

}
