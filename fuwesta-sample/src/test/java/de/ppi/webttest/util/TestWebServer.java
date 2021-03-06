package de.ppi.webttest.util;

import java.io.IOException;
import java.net.ServerSocket;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import de.ppi.samples.fuwesta.FuWeStaServer;
import de.ppi.selenium.junit.WebServer;

/**
 * Presentation of a webserver on port 7779, you can influence the port-number
 * with the systemproperty <code>testport</code>, for example
 * <code>-Dtestport=8080</code>.
 *
 */
public final class TestWebServer implements WebServer {

    /**
     * The default-port of the server.
     */
    private static final String DEFAULT_PORT = "7779";

    /**
     * Instance of a running server.
     */
    private ConfigurableApplicationContext server;

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
    public TestWebServer(int port, String contextPart) {
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
    public TestWebServer(String contextPart) {
        this(Integer.parseInt(System.getProperty("testport", getFreePort())),
                contextPart);
    }

    /**
     * Initiates an object of type WebServer, for default port and context "/".
     */
    public TestWebServer() {
        this("/");
    }

    /**
     * Find a free port for the server.
     *
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
            server =
                    SpringApplication.run(FuWeStaServer.class, "--server.port="
                            + port, "--server.context-path=" + contextPart);
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
