package de.ppi.samples.fuwesta.config;

import java.util.List;
import java.util.Properties;

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AnnotationsConfigurer;
import net.sf.oval.integration.spring.SpringCheckInitializationListener;
import net.sf.oval.integration.spring.SpringValidator;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import de.ppi.fuwesta.spring.mvc.formatter.NonEmptyStringAnnotationFormatterFactory;
import de.ppi.fuwesta.spring.mvc.oval.DbCheckConfigurer;
import de.ppi.fuwesta.spring.mvc.oval.JPAAnnotationsConfigurer;
import de.ppi.fuwesta.spring.mvc.oval.MessageLookupContextRenderer;
import de.ppi.fuwesta.spring.mvc.oval.MessageLookupMessageValueFormatter;
import de.ppi.fuwesta.spring.mvc.oval.SpringMvcMessageResolver;
import de.ppi.fuwesta.spring.mvc.util.ApostropheEscapingPropertiesPersister;
import de.ppi.fuwesta.spring.mvc.util.EntityPropertiesToMessages;
import de.ppi.fuwesta.spring.mvc.util.UrlDefinitionsToMessages;
import de.ppi.samples.fuwesta.frontend.URL;

/**
 * The frontend configuration for Spring.
 * 
 */
@Configuration
@ComponentScan(basePackages = { "de.ppi.samples.fuwesta.frontend",
        "net.sf.oval.integration.spring", "de.ppi.fuwesta.jpa.helper" })
@Import({ RootConfig.class, ThymeleafConfig.class, FilterConfig.class,
        SecurityConfig.class })
public class WebMvcConfig extends WebMvcConfigurationSupport {

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

    /** The Constant RESOURCES_HANDLER. */
    private static final String RESOURCES_HANDLER = "/resources/";

    /** The Constant RESOURCES_LOCATION. */
    private static final String RESOURCES_LOCATION = RESOURCES_HANDLER + "**";

    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping requestMappingHandlerMapping =
                super.requestMappingHandlerMapping();
        requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
        requestMappingHandlerMapping.setUseTrailingSlashMatch(true);
        return requestMappingHandlerMapping;
    }

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
        messageSource
                .setPropertiesPersister(new ApostropheEscapingPropertiesPersister());
        final Class<?>[] classes = URL.class.getDeclaredClasses();
        final UrlDefinitionsToMessages urlDefinitions =
                new UrlDefinitionsToMessages(classes);
        urlDefinitions.addParamGroupAsMessages();
        urlDefinitions.addParamsAsMessages();
        urlDefinitions.addUrlsAsMessages();
        Properties staticMessages = urlDefinitions.getMessages();
        final EntityPropertiesToMessages epm =
                new EntityPropertiesToMessages("de.ppi.samples.fuwesta.model");
        staticMessages.putAll(epm.getProperties());
        messageSource.setCommonMessages(staticMessages);
        return messageSource;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(
                RESOURCES_LOCATION);
    }

    /**
     * Create an {@link OpenEntityManagerInViewInterceptor} to follow Open
     * Session in View Patten. This isn't optimal see
     * http://heapdump.wordpress.com
     * /2010/04/04/should-i-use-open-session-in-view/ or
     * http://java.dzone.com/articles/opensessioninview-antipattern but it's
     * very common in frameworks like Grails or Play. The reason is that you
     * doesn't need so much knowledge about JPA and there is no need to write
     * tons of specific Dao-methods which make eager fetching.
     * 
     * @return the {@link WebRequestInterceptor}.
     */
    @Bean
    public WebRequestInterceptor openEntityManagerInViewInterceptor() {
        return new OpenEntityManagerInViewInterceptor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(openEntityManagerInViewInterceptor());
        super.addInterceptors(registry);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addArgumentResolvers(
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
     * Configures the view resolver for JSPs.
     * 
     * @return the view resolver.
     */
    @Bean
    public InternalResourceViewResolver
            configureInternalJspResourceViewResolver() {
        final InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(2);
        return resolver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected org.springframework.validation.Validator getValidator() {
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
    protected void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(new NonEmptyStringAnnotationFormatterFactory());
        registry.addFormatter(new DateFormatter());
        super.addFormatters(registry);
    }

    /**
     * Register a mapper so that a model entity could be found by id.
     * 
     * @return a DomainClassConverter.
     */
    @Bean
    public DomainClassConverter<?> domainClassConverter() {
        return new DomainClassConverter<FormattingConversionService>(
                mvcConversionService());
    }
}
