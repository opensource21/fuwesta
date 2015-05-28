package de.ppi.samples.fuwesta.selophane.test;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.junit.Before;
import org.junit.Test;
import org.selophane.elements.widget.Button;
import org.selophane.elements.widget.Select;
import org.selophane.elements.widget.TextInput;

import de.ppi.samples.fuwesta.dbunit.dataset.PostTestData;
import de.ppi.samples.fuwesta.dbunit.dataset.TestData;
import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selophane.page.PostFormPage;
import de.ppi.selenium.browser.SessionManager;

/**
 * Test for the post-page.
 *
 */
public class CrudPostIntegrationTest extends AbstractPostIntegrationTest {

    /**
     * The formPage.
     */
    private PostFormPage formPage;

    /**
     * Init the private members.
     */
    @Before
    public void setUp() {
        formPage = new PostFormPage(SessionManager.getSession());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected IDataSet getDataSet() throws DataSetException {
        return TestData.initWithSampleData();
    }

    /**
     * Test creating a new entry.
     */
    @Test
    public void testCreate() throws Exception {
        browser.getRelativeUrl(URL.Post.LIST);
        postListPage.getCreateButton().click();
        softly.assertThat(browser)
                .as("Expect that the new page is the create-url")
                .hasRelativeUrl(URL.Post.CREATE);

        final TextInput title = formPage.getTitleInput();
        softly.assertThat(formPage.getLabelFor(title)).hasText("Title:");
        title.set("Test-Title1");

        final TextInput content = formPage.getContentInput();
        softly.assertThat(formPage.getLabelFor(content)).hasText("Content:");
        content.set("This is an example text.\nIt contains newlines.");

        final TextInput creationTime = formPage.getCreationTimeInput();
        softly.assertThat(formPage.getLabelFor(creationTime)).hasText(
                "Creation Time");
        creationTime.set("30-01-2015");

        final Select user = formPage.getUserInput();
        softly.assertThat(formPage.getLabelFor(user)).hasText("User:");
        user.selectByVisibleText("ben(Nutzer, Ben)");

        final Select tags = formPage.getTagsSelect();
        softly.assertThat(formPage.getLabelFor(tags)).hasText("Tags:");
        tags.selectByVisibleText("Test2");
        tags.selectByVisibleText("Test1");

        final Button save = formPage.getSave();
        save.click();

        softly.assertThat(browser).hasRelativeUrl(URL.Post.LIST);
        checkResult(PostTestData.buildPostCreatedDataSet());
    }

    public void testValidation() {

    }
}
