package de.ppi.samples.fuwesta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//CSOFF: HideUtilityClassConstructor The method must be static, but it must be
//a spring-bean.
/**
 * Server to start FuWeSta-sample.
 *
 */
@SpringBootApplication
public class FuWeStaServer {

    /**
     * Main method to start the server.
     *
     * @param args start-arguments.
     */
    public static void main(String... args) {
        SpringApplication.run(FuWeStaServer.class, args);
    }

}
