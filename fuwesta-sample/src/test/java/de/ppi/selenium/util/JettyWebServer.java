package de.ppi.selenium.util;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Presentation of a webserver on port 7779, you can influence the port-number
 * with the systemproperty <code>testport</code>, for example
 * <code>-Dtestport=8080</code>.
 *
 */
public final class WebServer {

    /**
     * The default-port of the server.
     */
    private static final String DEFAULT_PORT = "7779";

    /**
     * Instance of a running server.
     */
    private Server server;

    /**
     * Port the serber listen.
     */
    private final int port;

    /**
     * Context of the app.
     */
    private final String contextPart;

    /**
     * Base Url to get the application.
     */
    private final String baseUrl;

    /**
     * Initiates an object of type WebServer, for given port and given context.
     *
     * @param port the port the server listen.
     * @param contextPart the context.
     */
    public WebServer(int port, String contextPart) {
        this.port = port;
        this.contextPart = contextPart;
        this.baseUrl = "http://localhost:" + port + contextPart;
    }

    /**
     * Initiates an object of type WebServer, for default port and given
     * context.
     *
     * @param contextPart the context.
     */
    public WebServer(String contextPart) {
        this(Integer.parseInt(System.getProperty("testport", DEFAULT_PORT)),
                contextPart);
    }

    /**
     * Initiates an object of type WebServer, for default port and context "/".
     */
    public WebServer() {
        this("/");
    }

    /**
     * Starts the server.
     *
     * @throws Exception something goes wrong.
     */
    public void start() throws Exception {
        if (server == null) {
            server = new Server(port);
            server.setStopAtShutdown(true);

            WebAppContext webAppContext = new WebAppContext();
            webAppContext.setContextPath(contextPart);
            webAppContext.setResourceBase("src/main/webapp");
            webAppContext.setClassLoader(WebServer.class.getClassLoader());
            server.addHandler(webAppContext);
            server.start();
        }

    }

    /**
     * Stop the server.
     *
     * @throws Exception stop the server.
     */
    public void stop() throws Exception {
        if (server != null) {
            server.stop();
            server = null;
        }

    }

    /**
     * Return the port of the server.
     *
     * @return the port of the server.
     */
    public int getPort() {
        return port;
    }

    /**
     * Return the base-url.
     *
     * @return the base-url.
     */
    public String getBaseUrl() {
        return baseUrl;
    }
}
