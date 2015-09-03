package de.ppi.samples.fuwesta.config;

import java.util.List;
import java.util.Properties;

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AnnotationsConfigurer;
import net.sf.oval.integration.spring.SpringCheckInitializationListener;
import net.sf.oval.integration.spring.SpringValidator;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;

import de.ppi.fuwesta.spring.mvc.bind.ServletBindingService;
import de.ppi.fuwesta.spring.mvc.formatter.EnumConverter;
import de.ppi.fuwesta.spring.mvc.formatter.MessageSourceDateFormatter;
import de.ppi.fuwesta.spring.mvc.formatter.NonEmptyStringAnnotationFormatterFactory;
import de.ppi.fuwesta.spring.mvc.oval.DbCheckConfigurer;
import de.ppi.fuwesta.spring.mvc.oval.JPAAnnotationsConfigurer;
import de.ppi.fuwesta.spring.mvc.oval.MessageLookupContextRenderer;
import de.ppi.fuwesta.spring.mvc.oval.MessageLookupMessageValueFormatter;
import de.ppi.fuwesta.spring.mvc.oval.SpringMvcMessageResolver;
import de.ppi.fuwesta.spring.mvc.util.ApostropheEscapingPropertiesPersister;
import de.ppi.fuwesta.spring.mvc.util.EntityPropertiesToMessages;
import de.ppi.fuwesta.spring.mvc.util.RecursivePropertiesPersister;
import de.ppi.fuwesta.spring.mvc.util.UrlDefinitionsToMessages;
import de.ppi.samples.fuwesta.frontend.URL;

/**
 * The frontend configuration for Spring.
 *
 */
@Configuration
@ComponentScan(basePackages = { "net.sf.oval.integration.spring",
        "de.ppi.fuwesta.jpa.helper" })
public class WebMvcConfig extends WebMvcAutoConfigurationAdapter {

    /**
     * Page size if no other information is given.
     */
    private static final int FALLBACK_PAGE_SIZE = 5;

    /**
     * The time in seconds messages are cached.
     */
    private static final int MESSAGE_CACHE = 5;

    /** The Constant MESSAGE_SOURCE. */
    private static final String MESSAGE_SOURCE = "classpath:i18n/messages";

    /** The Constant MESSAGE_SOURCE_FOR_OVAL. */
    private static final String MESSAGE_SOURCE_OVAL =
            "classpath:/net/sf/oval/Messages";

    /** Messages from Spring-Security. */
    private static final String MESSAGE_SOURCE_SPRING_SECURITY =
            "classpath:org/springframework/security/messages";

    /**
     * Initiates the message resolver.
     *
     * @return a message source.
     */
    @Bean(name = "messageSource")
    public MessageSource configureMessageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(MESSAGE_SOURCE, MESSAGE_SOURCE_OVAL,
                MESSAGE_SOURCE_SPRING_SECURITY);
        messageSource.setCacheSeconds(MESSAGE_CACHE);
        messageSource.setFallbackToSystemLocale(false);
        // Make sure Apostrophs must always be doubled..
        messageSource.setAlwaysUseMessageFormat(true);
        // This persister doubles Apostoph
        messageSource.setPropertiesPersister(new RecursivePropertiesPersister(
                new ApostropheEscapingPropertiesPersister()));
        final Class<?>[] classes = URL.class.getDeclaredClasses();
        final UrlDefinitionsToMessages urlDefinitions =
                new UrlDefinitionsToMessages(classes);
        urlDefinitions.addParamGroupAsMessages();
        urlDefinitions.addParamsAsMessages();
        urlDefinitions.addUrlsAsMessagesWithPositionedParameters();
        urlDefinitions.addUrlsAsMessagesWithNamedParameters();
        Properties staticMessages = urlDefinitions.getMessages();
        final EntityPropertiesToMessages epm =
                new EntityPropertiesToMessages("de.ppi.samples.fuwesta.model");
        staticMessages.putAll(epm.getProperties());
        messageSource.setCommonMessages(staticMessages);
        return messageSource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addArgumentResolvers(
            List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver =
                new PageableHandlerMethodArgumentResolver();
        resolver.setFallbackPageable(new PageRequest(0, FALLBACK_PAGE_SIZE));
        argumentResolvers.add(resolver);
    }

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public org.springframework.validation.Validator getValidator() {
        final AnnotationsConfigurer annConfig = new AnnotationsConfigurer();
        annConfig
                .addCheckInitializationListener(SpringCheckInitializationListener.INSTANCE);
        final DbCheckConfigurer dbConfig = new DbCheckConfigurer();
        dbConfig.addCheckInitializationListener(SpringCheckInitializationListener.INSTANCE);
        final Validator ovalValidator =
                new Validator(annConfig, dbConfig,
                        new JPAAnnotationsConfigurer(false));
        Validator
                .setMessageValueFormatter(new MessageLookupMessageValueFormatter(
                        configureMessageSource()));
        Validator.setContextRenderer(new MessageLookupContextRenderer(
                configureMessageSource()));
        Validator.setMessageResolver(new SpringMvcMessageResolver(
                configureMessageSource()));
        return new SpringValidator(ovalValidator);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(new NonEmptyStringAnnotationFormatterFactory());
        registry.addFormatter(messageSourceDateFormatter());
        registry.addConverter(new EnumConverter(configureMessageSource()));
        // registry.addConverter(new StringTrimmerConverter(true));
        super.addFormatters(registry);
    }

    /**
     * Create the {@link MessageSourceDateFormatter}.
     *
     * @return the {@link MessageSourceDateFormatter}.
     */
    @Bean
    protected MessageSourceDateFormatter messageSourceDateFormatter() {
        return new MessageSourceDateFormatter("format.date");
    }

    /**
     * Creates a small service to bind request data to an object.
     *
     * @return the binding service.
     */
    @Bean
    public ServletBindingService servletBindingService() {
        return new ServletBindingService();
    }

}
