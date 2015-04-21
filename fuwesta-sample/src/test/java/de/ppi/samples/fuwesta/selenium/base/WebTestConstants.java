package de.ppi.samples.fuwesta.selenium.base;

import org.junit.rules.RuleChain;

import de.ppi.selenium.junit.ProtocolRule;
import de.ppi.selenium.junit.WebDriverRule;
import de.ppi.selenium.junit.WebServerRule;
import de.ppi.selenium.util.JettyWebServer;

/**
 * Constants for webtest.
 *
 */
public interface WebTestConstants {

    /**
     * Standard_Rule for WebTests.
     */
    RuleChain WEBTEST = RuleChain
            .outerRule(new WebServerRule(new JettyWebServer(7779, "/fuwesta")))
            .around(new WebDriverRule()).around(new ProtocolRule("weblog"));

}
