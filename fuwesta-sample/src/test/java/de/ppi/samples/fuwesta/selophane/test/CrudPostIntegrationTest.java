package de.ppi.samples.fuwesta.selophane.test;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.selophane.elements.widget.Select;
import org.selophane.elements.widget.TextInput;

import de.ppi.samples.fuwesta.dbunit.dataset.PostTestData;
import de.ppi.samples.fuwesta.dbunit.dataset.TestData;
import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selophane.page.PostFormPage;
import de.ppi.selenium.browser.SessionManager;
import de.ppi.selenium.util.Protocol;

/**
 * Test for the post-page.
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrudPostIntegrationTest extends AbstractPostIntegrationTest {

    /**
     * TEST-Title.
     */
    private static final String TEST_TITLE1 = "Test-Title1";
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
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    public void test1Create() throws Exception {
        postModule.navigateToCreate();
        softly.assertThat(browser)
                .as("Expect that the new page is the create-url")
                .hasRelativeUrl(URL.Post.CREATE);

        final TextInput title = formPage.getTitleInput();
        softly.assertThat(formPage.getLabelFor(title)).hasText("Title:");
        title.set(TEST_TITLE1);

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

        formPage.getSave().click();

        softly.assertThat(browser).hasRelativeUrl(URL.Post.LIST);
        checkResult(PostTestData.buildPostCreatedDataSet());
    }

    /**
     * Test checks the validation-messages.
     */
    @Test
    public void test2Validation() {
        postModule.navigateToCreate();
        final TextInput creationTime = formPage.getCreationTimeInput();
        final TextInput title = formPage.getTitleInput();
        creationTime.sendKeys("11.11.2011");
        softly.assertThat(creationTime.getText()).isEqualTo("11112011");
        formPage.getSave().click();
        softly.assertThat(formPage.getError(title)).hasText(
                "Title cannot be null");
        softly.assertThat(formPage.getError(creationTime)).hasText(
                "Invalid Date (must be dd-MM-yyyy).");
        Protocol.log("ValidationErrors1", "Validation errors should be shown.",
                browser);
        title.set(TEST_TITLE1);
        formPage.getSave().click();
        softly.assertThat(formPage.getError(title)).hasText(
                "Title must be unique");
        softly.assertThat(formPage.getError(creationTime)).hasText(
                "Invalid Date (must be dd-MM-yyyy).");
        Protocol.log("ValidationErrors2", "Validation errors should be shown.",
                browser);
    }

    /**
     * Test edit.
     *
     * @throws DataSetException db-exceptions.
     */
    @Test
    public void test3Edit() throws DataSetException {
        postModule.navigateToEdit(TEST_TITLE1);
        softly.assertThat(browser).hasRalativeUrlMatching(
                URL.filledURLWithNamedParams(URL.Post.EDIT, URL.Post.P_POSTID,
                        ".*"));
        final TextInput title = formPage.getTitleInput();
        softly.assertThat(formPage.getLabelFor(title)).hasText("Title:");
        title.set(TEST_TITLE1 + "N");

        final TextInput content = formPage.getContentInput();
        softly.assertThat(formPage.getLabelFor(content)).hasText("Content:");
        content.set("This is an example text.\nIt contains newlines.Not really conntent.");

        final TextInput creationTime = formPage.getCreationTimeInput();
        softly.assertThat(formPage.getLabelFor(creationTime)).hasText(
                "Creation Time");
        creationTime.set("30-03-2015");

        final Select user = formPage.getUserInput();
        softly.assertThat(formPage.getLabelFor(user)).hasText("User:");
        user.selectByVisibleText("test(Bug, Finda)");

        final Select tags = formPage.getTagsSelect();
        softly.assertThat(formPage.getLabelFor(tags)).hasText("Tags:");
        tags.deselectByVisibleText("Test2");

        formPage.getSave().click();
        checkResult(PostTestData.buildPostEditedDataSet());
    }

    /**
     * Test partial edit.
     *
     * @throws DataSetException db-exceptions.
     */
    @Test
    public void test4PartialEdit() throws DataSetException {
        postModule.navigateToPartialEdit(TEST_TITLE1 + "N");
        softly.assertThat(browser).hasRalativeUrlMatching(
                URL.filledURLWithNamedParams(URL.Post.PARTIALEDIT,
                        URL.Post.P_POSTID, ".*"));
        final TextInput title = formPage.getTitleInput();
        softly.assertThat(formPage.getLabelFor(title)).hasText("Title:");
        title.set(TEST_TITLE1);

        final TextInput content = formPage.getContentInput();
        softly.assertThat(formPage.getLabelFor(content)).hasText("Content:");
        content.set("This is an example text.\nIt contains newlines.");

        final TextInput creationTime = formPage.getCreationTimeInput();
        softly.assertThat(formPage.getLabelFor(creationTime)).hasText(
                "Creation Time");
        creationTime.set("30-01-2015");

        formPage.getSave().click();
        checkResult(PostTestData.buildPostPartialEditedDataSet());
    }
}
