package de.ppi.samples.fuwesta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * Configuration of Thymeleaf.
 *
 */
@Configuration
public class ThymeleafConfig {

    /**
     * Register dialect for shiro security.
     *
     * @return the dialect.
     */
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }

}
