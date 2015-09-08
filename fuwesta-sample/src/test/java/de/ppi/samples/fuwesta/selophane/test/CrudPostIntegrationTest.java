package de.ppi.samples.fuwesta.selophane.test;

import java.util.List;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.selophane.elements.widget.Link;
import org.selophane.elements.widget.Select;
import org.selophane.elements.widget.TextInput;

import de.ppi.samples.fuwesta.dbunit.dataset.PostTestData;
import de.ppi.samples.fuwesta.dbunit.dataset.TestData;
import de.ppi.samples.fuwesta.frontend.URL;
import de.ppi.samples.fuwesta.selophane.page.PartialPostFormPage;
import de.ppi.samples.fuwesta.selophane.page.PostFormPage;
import de.ppi.selenium.browser.SessionManager;
import de.ppi.selenium.logevent.api.EventActions;
import de.ppi.selenium.logevent.api.Priority;
import de.ppi.selenium.util.Protocol;

/**
 * Test for the post-page.
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrudPostIntegrationTest extends AbstractPostIntegrationTest {

    /**
     * String representation of the first user.
     */
    private static final String BEN_NUTZER = "ben(Nutzer, Ben)";

    /**
     * TEST-Title.
     */
    private static final String TEST_TITLE1 = "Test-Title1";

    /**
     * The formPage.
     */
    private PostFormPage formPage;

    /**
     * The formPage.
     */
    private PartialPostFormPage partialFormPage;

    /**
     * Init the private members.
     */
    @Before
    public void setUp() {
        formPage = new PostFormPage(SessionManager.getSession());
        partialFormPage = new PartialPostFormPage(SessionManager.getSession());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected IDataSet getDataSet() throws DataSetException {
        return TestData.initWithSampleData();
    }

    /**
     * Test the show page.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    public void test0Show() throws Exception {
        postModule.navigateToShow("Title 1");
        softly.assertThat(browser)
                .as("Expect that the show page is the show-url")
                .hasRalativeUrlMatching(
                        URL.filledURLWithNamedParams(URL.Post.SHOW,
                                URL.Post.P_POSTID, ".*"));

        final TextInput title = formPage.getTitleInput();
        softly.assertThat(formPage.getLabelFor(title)).hasText("Title:");
        softly.assertThat(title).isDisplayed().isNotEnabled()
                .hasText("Title 1");

        final TextInput content = formPage.getContentInput();
        softly.assertThat(formPage.getLabelFor(content)).hasText("Content:");
        softly.assertThat(content).isDisplayed().isNotEnabled()
                .hasText("Ein erster Inhalt");

        final TextInput creationTime = formPage.getCreationTimeInput();
        softly.assertThat(formPage.getLabelFor(creationTime)).hasText(
                "Creation Time");
        softly.assertThat(creationTime).isDisplayed().isNotEnabled()
                .hasText("12-03-2014");

        final Link user = formPage.getUserLink();
        softly.assertThat(formPage.getLabelFor(user)).hasText("User:");
        softly.assertThat(user).hasText(BEN_NUTZER);
        user.click();
        softly.assertThat(browser).hasRelativeUrl(
                URL.filledURLWithNamedParams(URL.User.SHOW, URL.User.P_USERID,
                        "11"));
        browser.navigate().back();
        formPage.isReloaded();
        List<Link> tags = formPage.getTagList();
        softly.assertThat(tags).hasSize(2);
        softly.assertThat(tags.get(0)).hasText("Test2");
        tags.get(0).click();
        softly.assertThat(browser)
                .hasRelativeUrl(
                        URL.filledURLWithNamedParams(URL.Tag.SHOW,
                                URL.Tag.P_TAGID, "2"));
        browser.navigate().back();
        formPage.isReloaded();
        tags = formPage.getTagList();
        softly.assertThat(tags.get(1)).hasText("Test1");
        tags.get(1).click();
        softly.assertThat(browser)
                .hasRelativeUrl(
                        URL.filledURLWithNamedParams(URL.Tag.SHOW,
                                URL.Tag.P_TAGID, "1"));
        browser.navigate().back();
        formPage.isReloaded();
        formPage.getList().click();

        softly.assertThat(browser).hasRelativeUrl(URL.Post.LIST);
        checkResult(TestData.initWithSampleData());
    }

    /**
     * Test creating a new entry.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    public void test10Create() throws Exception {
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
        user.selectByVisibleText(BEN_NUTZER);

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
    public void test20Validation() {
        postModule.navigateToCreate();
        validateTitleAndCreationTime(formPage);
        TextInput title = formPage.getTitleInput();
        title.set(TEST_TITLE1);
        formPage.getSave().click();
        formPage.isReloaded();
        final TextInput creationTime = formPage.getCreationTimeInput();
        title = formPage.getTitleInput();
        softly.assertThat(formPage.getError(title)).hasText(
                "Title must be unique");
        softly.assertThat(formPage.getError(creationTime)).hasText(
                "Invalid Date (must be dd-MM-yyyy).");
        EVENT_LOGGER
                .onDoku(CrudPostIntegrationTest.class.getName(),
                        "test20Validation")
                .withScreenshot(Priority.DOCUMENTATION, browser)
                .log(EventActions.TEST_SCREENSHOT, "ValidationError2");
        Protocol.log("ValidationErrors2", "Validation errors should be shown.",
                browser);
    }

    /**
     * Test if the title and the creationTime field has the right validation
     * error.
     *
     * @param thePage page where the validation should be checked.
     */
    private void validateTitleAndCreationTime(PartialPostFormPage thePage) {
        TextInput title = thePage.getTitleInput();
        TextInput creationTime = thePage.getCreationTimeInput();
        title.clear();
        creationTime.clear();
        creationTime.sendKeys("11.11.2011");
        softly.assertThat(creationTime.getText()).isEqualTo("11112011");
        thePage.getSave().click();
        thePage.isReloaded();
        title = thePage.getTitleInput();
        creationTime = thePage.getCreationTimeInput();
        softly.assertThat(thePage.getError(title)).hasText(
                "Title cannot be null");
        softly.assertThat(thePage.getError(creationTime)).hasText(
                "Invalid Date (must be dd-MM-yyyy).");
        EVENT_LOGGER
                .onDoku(CrudPostIntegrationTest.class.getName(),
                        "validateTitleAndCreationTime")
                .withScreenshot(Priority.DOCUMENTATION, browser)
                .log(EventActions.TEST_SCREENSHOT, "ValidationErrors1");
        Protocol.log("ValidationErrors1", "Validation errors should be shown.",
                browser);
    }

    /**
     * Test edit and validation.
     *
     * @throws DataSetException db-exceptions.
     */
    @Test
    public void test30EditAndValidation() throws DataSetException {
        postModule.navigateToEdit(TEST_TITLE1);
        softly.assertThat(browser).hasRalativeUrlMatching(
                URL.filledURLWithNamedParams(URL.Post.EDIT, URL.Post.P_POSTID,
                        ".*"));
        TextInput title = formPage.getTitleInput();
        softly.assertThat(formPage.getLabelFor(title)).hasText("Title:");
        validateTitleAndCreationTime(formPage);
        formPage.getReset().click();
        softly.assertThat(formPage.getLabelFor(formPage.getTitleInput()))
                .hasText("Title:");
        title = formPage.getTitleInput();
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
     * Test partial edit and validation.
     *
     * @throws DataSetException db-exceptions.
     */
    @Test
    public void test40PartialEditAndValidation() throws DataSetException {
        postModule.navigateToPartialEdit(TEST_TITLE1 + "N");
        softly.assertThat(browser).hasRalativeUrlMatching(
                URL.filledURLWithNamedParams(URL.Post.PARTIALEDIT,
                        URL.Post.P_POSTID, ".*"));
        TextInput title = partialFormPage.getTitleInput();
        softly.assertThat(partialFormPage.getLabelFor(title)).hasText("Title:");
        softly.assertThat(formPage.getLabelFor(title)).hasText("Title:");
        validateTitleAndCreationTime(partialFormPage);
        title = partialFormPage.getTitleInput();
        title.set(TEST_TITLE1);

        final TextInput content = partialFormPage.getContentInput();
        softly.assertThat(partialFormPage.getLabelFor(content)).hasText(
                "Content:");
        content.set("This is an example text.\nIt contains newlines.");

        final TextInput creationTime = partialFormPage.getCreationTimeInput();
        softly.assertThat(partialFormPage.getLabelFor(creationTime)).hasText(
                "Creation Time");
        creationTime.set("30-01-2015");

        partialFormPage.getSave().click();
        checkResult(PostTestData.buildPostPartialEditedDataSet());
    }

    /**
     * Test delete.
     *
     * @throws DataSetException db-exceptions.
     */
    @Test
    public void test90Delete() throws DataSetException {
        postModule.navigateToDelete(TEST_TITLE1);
        // This is the hacky version. Normally there should be a DeletePage.
        browser.findElement(By.tagName("form")).submit();
        checkResult(TestData.initWithSampleData());

    }
}
