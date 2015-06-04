package de.ppi.samples.fuwesta.selophane.base;

import org.junit.rules.RuleChain;

import de.ppi.selenium.junit.DelegatingWebServer;
import de.ppi.selenium.junit.ProtocolRule;
import de.ppi.selenium.junit.WebDriverRule;
import de.ppi.selenium.junit.WebServerRule;
import de.ppi.webttest.util.JettyWebServer;

/**
 * Constants for webtest.
 *
 */
// CSOFFALL:
public interface WebTestConstants {

    /**
     * Standard_Rule for WebTests.
     */
    RuleChain WEBTEST_WITHOUT_AUTHENTICATION = RuleChain
            .outerRule(
                    new WebServerRule(new DelegatingWebServer(
                            new JettyWebServer("/fuwesta"))))
            .around(new WebDriverRule()).around(new ProtocolRule("weblog"));
    /**
     * Standard_Rule for WebTests.
     */
    RuleChain WEBTEST = RuleChain.outerRule(WEBTEST_WITHOUT_AUTHENTICATION)
            .around(new AuthRule());
}
