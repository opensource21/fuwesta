package de.ppi.samples.fuwesta.selophane.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.selophane.elements.base.ByUniqueElementLocator;
import org.selophane.elements.base.Element;
import org.selophane.elements.widget.Link;
import org.selophane.elements.widget.LinkImpl;
import org.selophane.elements.widget.TextInput;

import de.ppi.samples.fuwesta.dbunit.dataset.TestData;
import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selophane.module.AuthModule;
import de.ppi.samples.fuwesta.selophane.module.PostModule;
import de.ppi.samples.fuwesta.selophane.page.PartialPostFormPage;
import de.ppi.samples.fuwesta.selophane.page.PostFormPage;
import de.ppi.selenium.browser.SessionManager;
import de.ppi.selenium.browser.WebBrowser;
import de.ppi.selenium.util.Protocol;

/**
 * Test for the post-page.
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OptimisticLockPostIntegrationTest extends
        AbstractPostIntegrationTest {

    /**
     * An instance of the {@link SessionManager}.
     */
    private static final SessionManager SESSION_MANAGER = SessionManager
            .getInstance();

    /**
     * {@inheritDoc}
     */
    @Override
    protected IDataSet getDataSet() throws DataSetException {
        return TestData.initWithSampleData();
    }

    /**
     * Test edit in two sessions.
     *
     * @throws Exception when something goes wrong.
     */
    @Test
    public void testOptimisticLock() throws Exception {
        // Prepare first browser and goto the edit-page
        final WebBrowser firstBrowser = browser;
        final PostFormPage firstFormPage = new PostFormPage(firstBrowser);
        final PostModule firstPostModule = postModule;

        final String originalTitle = "Title 1";
        firstPostModule.navigateToEdit(originalTitle);
        softly.assertThat(firstBrowser).hasRalativeUrlMatching(
                URL.filledURLWithNamedParams(URL.Post.EDIT, URL.Post.P_POSTID,
                        "101"));

        // Prepare second browser and goto the edit-page
        final WebBrowser secondBrowser = SESSION_MANAGER.getNewSession();
        final PartialPostFormPage secondForm =
                new PartialPostFormPage(secondBrowser);
        final PostModule secondPostModule = new PostModule(secondBrowser);
        final AuthModule secondAuthModule = new AuthModule(secondBrowser);
        SESSION_MANAGER.switchToSession(secondBrowser);
        secondBrowser.getRelativeUrl(URL.Post.LIST);
        secondAuthModule.login("post");
        secondPostModule.navigateToEdit(originalTitle);
        softly.assertThat(browser).hasRalativeUrlMatching(
                URL.filledURLWithNamedParams(URL.Post.EDIT, URL.Post.P_POSTID,
                        "101"));
        // Change the title and save.
        final String changedTitle = "OptimisticLock";
        secondForm.getTitleInput().set(changedTitle);
        secondForm.getSave().click();
        softly.assertThat(secondBrowser).hasRelativeUrl(URL.Post.LIST);

        // Back to the first browser
        SESSION_MANAGER.switchToSession(firstBrowser);
        final TextInput content = firstFormPage.getContentInput();
        content.set(changedTitle);
        final String url = firstBrowser.getCurrentUrl();
        firstFormPage.getSave().click();
        firstFormPage.reload();
        Protocol.log(changedTitle, "Show optimistic lock-error", firstBrowser);
        final List<Element> messages =
                firstFormPage.getGlobalErrors().getMessages();
        softly.assertThat(messages).hasSize(1);
        final Element error = messages.get(0);
        softly.assertThat(error).hasText(
                "The data was changed by another user. Reload.");
        final Link reloadlink =
                new LinkImpl(new ByUniqueElementLocator(secondBrowser, error,
                        By.tagName("a")));
        assertThat(reloadlink.getAttribute("href")).isEqualTo(url);
        // reload and set the title back.
        reloadlink.click();
        firstFormPage.reload();
        final TextInput titleInput = firstFormPage.getTitleInput();
        softly.assertThat(titleInput.getText()).isEqualTo(changedTitle);
        titleInput.set(originalTitle);
        firstFormPage.getSave().click();
        secondBrowser.quit();
        checkResult(TestData.initWithSampleData());
    }
}
