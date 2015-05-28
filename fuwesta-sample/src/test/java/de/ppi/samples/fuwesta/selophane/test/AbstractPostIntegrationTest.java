package de.ppi.samples.fuwesta.selophane.test;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.RuleChain;

import com.google.common.annotations.VisibleForTesting;

import de.ppi.samples.fuwesta.dbunit.AbstractFuWeStaSampleDbUnitTest;
import de.ppi.samples.fuwesta.selophane.base.WebTestConstants;
import de.ppi.samples.fuwesta.selophane.module.PostModule;
import de.ppi.samples.fuwesta.selophane.page.PostListPage;
import de.ppi.selenium.assertj.SeleniumSoftAssertions;
import de.ppi.selenium.browser.SessionManager;
import de.ppi.selenium.browser.WebBrowser;

/**
 * Base-Class for test for Post-List-Page.
 *
 */
public abstract class AbstractPostIntegrationTest extends
        AbstractFuWeStaSampleDbUnitTest {

    /**
     * Flag which indicate that the db is initialized.
     */
    private static boolean dbIsInitialiazed = false;

    /**
     * Reset {@link AbstractPostIntegrationTest#dbIsInitialiazed} to false, at
     * the begin of each sublcass.
     */
    @BeforeClass
    public static void reset() {
        dbIsInitialiazed = false;
    }

    /**
     * Initialized the database.
     *
     * @throws DataSetException if there are problems with the dataset.
     */
    @Before
    public void initDB() throws DataSetException {
        if (!dbIsInitialiazed) {
            dbIsInitialiazed = true;
            final IDataSet dataSet = getDataSet();
            if (dataSet != null) {
                super.cleanlyInsert(dataSet);
            }
        }
    }

    /**
     * Returns the dataset which should be put into the database.
     *
     * @return the dataset or <code>null</code> if no data should be inserted.
     * @throws DataSetException error creating the dataset.
     */
    protected abstract IDataSet getDataSet() throws DataSetException;

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
    @VisibleForTesting
    protected WebBrowser browser;

    /**
     * The postlist-page.
     */
    @VisibleForTesting
    protected PostListPage postListPage;

    /**
     * The postlist-page.
     */
    @VisibleForTesting
    protected PostModule postModule;

    /**
     * Init the instances, because the Session shouldn't be access before the
     * rule creates one.
     */
    @Before
    public void init() {
        browser = SessionManager.getSession();

        postListPage = new PostListPage(browser);
        postModule = new PostModule(browser);
    }
}
