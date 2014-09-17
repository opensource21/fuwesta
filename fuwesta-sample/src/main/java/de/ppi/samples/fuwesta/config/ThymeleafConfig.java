package de.ppi.samples.fuwesta.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import de.ppi.fuwesta.thymeleaf.bootstrap2.Bootstrap2Dialect;
import de.ppi.fuwesta.thymeleaf.mail.MailToDialect;

/**
 * Configuration of Thymeleaf.
 * 
 */
@Configuration
@PropertySource("classpath:/thymeleaf.properties")
public class ThymeleafConfig {

    /**
     * The templates will be chached.
     */
    @Value("${thymeleaf.cacheable}")
    private boolean cacheable;

    /**
     * Encoding of HTTP-Response and of the templates.
     */
    @Value("${thymeleaf.characterEncoding}")
    private String characterEncoding;

    /**
     * Configures the Thymeleaf view resolver.
     * 
     * @return the view resolver.
     */
    @Bean
    public ThymeleafViewResolver
            configureInternalThymeLeafResourceViewResolver() {
        final ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setOrder(1);
        resolver.setContentType(MediaType.TEXT_HTML_VALUE);
        resolver.setCharacterEncoding(characterEncoding);
        resolver.setViewNames(new String[] { "example/user/list",
                "example/user/userform", "example/post/list",
                "example/post/postform", "example/post/partialpostform",
                "example/tag/list", "example/tag/tagform", "example/login",
                "example/error" });
        return resolver;
    }

    /**
     * Thymeleaf template engine.
     * 
     * @return Thymeleaf template engine.
     */
    @Bean()
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine ste = new SpringTemplateEngine();
        ste.setTemplateResolver(templateResolver());
        // Only necessary if you use LayoutDialect.
        ste.addDialect(new LayoutDialect());
        ste.addDialect(new Bootstrap2Dialect());
        ste.addDialect(new MailToDialect());
        return ste;
    }

    /**
     * Thymeleaf template resolver.
     * 
     * @return Thymeleaf template resolver.
     */
    @Bean()
    public ServletContextTemplateResolver templateResolver() {
        final ServletContextTemplateResolver resolver =
                new ServletContextTemplateResolver();
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCacheable(cacheable);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

}
