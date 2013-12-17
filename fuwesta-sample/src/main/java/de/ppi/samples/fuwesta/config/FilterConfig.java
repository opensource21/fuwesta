package de.ppi.samples.fuwesta.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * The webfilter-Configuration.
 * 
 */
@Configuration
@PropertySource("classpath:/thymeleaf.properties")
public class FilterConfig {

    /**
     * Encoding of HTTP-Response and of the templates.
     */
    @Value("${thymeleaf.characterEncoding}")
    private String characterEncoding;

    /**
     * build a filter chain.
     * 
     * @return a filter chain.
     */
    public FilterChainProxy filterChainProxy() {
        final FilterChainProxy filterChainProxy = new FilterChainProxy();
        final CharacterEncodingFilter encodingFilter =
                new CharacterEncodingFilter();
        encodingFilter.setEncoding(characterEncoding);
        encodingFilter.setForceEncoding(true);

        return filterChainProxy;
    }
}
