package de.ppi.samples.fuwesta.selophane.base;

import org.junit.rules.RuleChain;

import de.ppi.selenium.junit.DelegatingWebServer;
import de.ppi.selenium.junit.EventLogRule;
import de.ppi.selenium.junit.ProtocolRule;
import de.ppi.selenium.junit.WebDriverRule;
import de.ppi.selenium.junit.WebServerRule;
import de.ppi.webttest.util.TestWebServer;
import de.ppi.selenium.logevent.backend.H2EventStorage;

/**
 * Constants for webtest.
 *
 */
// CSOFFALL:
public interface WebTestConstants {

    TestWebServer WEB_SERVER = new TestWebServer("/fuwesta");

    /**
     * The system to store the events.
     */
    H2EventStorage EVENT_STORAGE = new H2EventStorage(
            "jdbc:h2:./dbs/testlog;MODE=PostgreSQL;AUTO_SERVER=TRUE", "sa", "");

    /**
     * Standard_Rule for WebTests.
     */
    RuleChain WEBTEST_WITHOUT_AUTHENTICATION = RuleChain
            .outerRule(new WebServerRule(new DelegatingWebServer(WEB_SERVER)))
            .around(new EventLogRule(EVENT_STORAGE))
            .around(new WebDriverRule()).around(new ProtocolRule("weblog"));
    /**
     * Standard_Rule for WebTests.
     */
    RuleChain WEBTEST = RuleChain.outerRule(WEBTEST_WITHOUT_AUTHENTICATION)
            .around(new AuthRule());

}
