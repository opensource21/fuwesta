package de.ppi.selenium.util;

/**
 * Implementation of a WebServer or some information about an existing running
 * webserver.
 *
 */
public interface WebServer {

    /**
     * Starts the server.
     *
     * @throws Exception something goes wrong.
     */
    void start() throws Exception;

    /**
     * Stop the server.
     *
     * @throws Exception stop the server.
     */
    void stop() throws Exception;

    /**
     * Return the base-url.
     *
     * @return the base-url.
     */
    String getBaseUrl();

}
