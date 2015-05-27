package de.ppi.selenium.util;

import java.io.IOException;
import java.net.ServerSocket;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import de.ppi.selenium.junit.WebServer;

/**
 * Presentation of a webserver on port 7779, you can influence the port-number
 * with the systemproperty <code>testport</code>, for example
 * <code>-Dtestport=8080</code>.
 *
 */
public final class JettyWebServer implements WebServer {

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
    public JettyWebServer(int port, String contextPart) {
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
    public JettyWebServer(String contextPart) {
        this(Integer.parseInt(System.getProperty("testport", getFreePort())),
                contextPart);
    }

    /**
     * Initiates an object of type WebServer, for default port and context "/".
     */
    public JettyWebServer() {
        this("/");
    }

    /**
     * Find a free port for the server.
     * @return a free port.
     */
    private static String getFreePort() {
        String port = DEFAULT_PORT;
        ServerSocket s = null;
        try {
            s = new ServerSocket(0);
            port = "" + s.getLocalPort();
        } catch (IOException e) {
            // Ignore
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
        return port;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() throws Exception {
        if (server == null) {
            server = new Server(port);
            server.setStopAtShutdown(true);

            WebAppContext webAppContext = new WebAppContext();
            webAppContext.setContextPath(contextPart);
            webAppContext.setResourceBase("src/main/webapp");
            webAppContext.setClassLoader(JettyWebServer.class.getClassLoader());
            server.addHandler(webAppContext);
            server.start();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        return server != null && server.isRunning();
    }
}
