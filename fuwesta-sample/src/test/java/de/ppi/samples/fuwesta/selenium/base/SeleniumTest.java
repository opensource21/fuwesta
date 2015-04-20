package de.ppi.samples.fuwesta.selenium.base;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ppi.selenium.util.JettyWebServer;
import de.ppi.selenium.util.WebServer;

/**
 * Baseclass for tests running with selenium.
 *
 */
public abstract class SeleniumTest {

    /**
     * The Logger for the controller.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(SeleniumTest.class);

    /**
     * The Webserver instance.
     */
    private static WebServer webServer = new JettyWebServer(7777, "/fuwesta");

    /**
     * The {@link WebDriver} instance.
     */
    private static WebDriver driver;

    /**
     * Start the webserver.
     *
     * @throws Exception if something goes wrong.
     */
    @BeforeClass
    public static void startServer() throws Exception {
        webServer.start();
    }

    /**
     * Quit the driver.
     */
    @AfterClass
    public static void stopDriver() {
        driver.quit();
        driver = null;
    }

    /**
     * Initiate the driver and store it. This give sthe possibility to reinit
     * the driver for every 10th test or something similar.
     *
     * @param forceNewInstance true if definitely a new instance is needed.
     *
     */
    public static void initDriver(boolean forceNewInstance) {
        if (forceNewInstance || driver == null) {
            if (driver != null) {
                driver.close();
            }
            // driver = new FirefoxDriver();
            driver = new HtmlUnitDriver();
            ((HtmlUnitDriver) driver).setJavascriptEnabled(true);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            // driver = new PhantomJSDriver();
        }
    }

    /**
     * Open the Browser.
     */
    @Before
    public void openBrowser() {
        initDriver(false);

        driver.manage().deleteAllCookies();
    }

    /**
     * Return the base-url.
     *
     * @return the base-url.
     */
    public String getBaseUrl() {
        return webServer.getBaseUrl();
    }

    /**
     * Returns the webdriver.
     *
     * @return the webdriver.
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Call the relative url.
     *
     * @param relativeUrl the relative url.
     */
    public void callRelativeUrl(String relativeUrl) {
        driver.get(getBaseUrl() + relativeUrl);
    }

    /**
     * Call the absolute url.
     *
     * @param absoluteUrl the absolute url.
     */
    public void callAbsoluteUrl(String absoluteUrl) {
        driver.get(absoluteUrl);
    }

    /**
     * Assert that the current url ist base-url + relative url.
     *
     * @param relativeUrl the relative url.
     */
    public void assertCurrentUrl(String relativeUrl) {
        assertThat(getDriver().getCurrentUrl()).isEqualTo(
                getBaseUrl() + relativeUrl);
    }

    /**
     * Save a screenshot.
     *
     * @param screenshotFileName name of the file.
     */
    public void saveScreenshot(String screenshotFileName) {
        try {
            if (driver instanceof TakesScreenshot) {
                File screenshot =
                        ((TakesScreenshot) driver)
                                .getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot, new File(screenshotFileName));
            } else if (driver instanceof HtmlUnitDriver) {
                FileUtils.write(new File("sreenshot-" + screenshotFileName
                        + "." + System.currentTimeMillis() + ".html"),
                        driver.getPageSource());
            } else {
                LOG.warn("The current driver doesn't make screenshots");
            }
        } catch (IOException e) {
            LOG.error("IO-Error during creating of the screenshot ", e);
        }
    }

    /**
     * Watcher to make screenshots.
     *
     * @return the testwatcher.
     */
    @Rule
    public final TestRule getScreenshotWatcher() {
        return new TestWatcher() {

            /**
             * {@inheritDoc}
             */
            @Override
            protected void failed(Throwable e, Description description) {
                saveScreenshot("Failed" + description.getDisplayName());
            }

        };
    }
}
