package testdata;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AnnotationsConfigurer;
import net.sf.oval.integration.spring.SpringCheckInitializationListener;
import net.sf.oval.integration.spring.SpringValidator;

import org.hibernate.cfg.Environment;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.jolbox.bonecp.BoneCPDataSource;

import de.ppi.fuwesta.spring.mvc.oval.DbCheckConfigurer;
import de.ppi.fuwesta.spring.mvc.oval.JPAAnnotationConfigLazy;
import de.ppi.fuwesta.spring.mvc.oval.MessageLookupContextRenderer;
import de.ppi.fuwesta.spring.mvc.oval.MessageLookupMessageValueFormatter;
import de.ppi.fuwesta.spring.mvc.oval.SpringMvcMessageResolver;
import de.ppi.fuwesta.spring.mvc.util.ApostropheEscapingPropertiesPersister;

/**
 * Spring-Configuration for the persistence.
 * 
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "testdata" })
@ComponentScan(basePackages = { "de.ppi.fuwesta.jpa.helper",
        "net.sf.oval.integration.spring" })
public class TestConfig implements TransactionManagementConfigurer {
    /**
     * The time in seconds messages are cached.
     */
    private static final int MESSAGE_CACHE = 5;

    /** The Constant MESSAGE_SOURCE. */
    private static final String MESSAGE_SOURCE = "classpath:i18n/messages";

    /** The Constant MESSAGE_SOURCE_FOR_OVAL. */
    private static final String MESSAGE_SOURCE_OVAL =
            "classpath:/net/sf/oval/Messages";

    /** The jdbc driver class name. */
    private String jdbcDriverClassName = "org.h2.Driver";

    /** The connection url. */
    private String connectionUrl =
            "jdbc:h2:mem:testdata;MODE=PostgreSQL;DB_CLOSE_DELAY=-1";

    /** The username. */
    private String username = "test";

    /** The password. */
    private String password = "test";

    /** The hibernate dialect. */
    private String hibernateDialect = "org.hibernate.dialect.H2Dialect";

    /** The hbm2ddl auto. */
    private String hbm2ddlAuto = "create";

    /** The default db-schema. */
    private String defaultSchema = "public";

    /** Flag that the DDL should be generated. */
    private boolean generateDDL = true;

    /**
     * Creates the datasource.
     * 
     * @return the datasource.
     */
    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource configureDataSource() {
        final BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(jdbcDriverClassName);
        dataSource.setJdbcUrl(connectionUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * Creates the {@linkplain EntityManager}.
     * 
     * @return the {@linkplain EntityManager}.
     */
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
            configureEntityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(configureDataSource());
        entityManagerFactoryBean.setPackagesToScan("testdata");
        final HibernateJpaVendorAdapter jpaVendorAdapter =
                new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(generateDDL);
        jpaVendorAdapter.setDatabasePlatform(hibernateDialect);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        Properties jpaProperties = new Properties();
        jpaProperties.put(Environment.HBM2DDL_AUTO, hbm2ddlAuto);
        jpaProperties.put(Environment.DEFAULT_SCHEMA, defaultSchema);
        jpaProperties.put(Environment.FORMAT_SQL, "true");
        // I prefer CamelCase ends in CAMEL_CASE.
        jpaProperties.put("hibernate.ejb.naming_strategy",
                "org.hibernate.cfg.ImprovedNamingStrategy");

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Override
    @Bean(name = "transactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new JpaTransactionManager();
    }

    /**
     * {@inheritDoc}
     */
    @Bean
    protected org.springframework.validation.Validator validator() {
        final AnnotationsConfigurer annConfig = new AnnotationsConfigurer();
        annConfig
                .addCheckInitializationListener(SpringCheckInitializationListener.INSTANCE);
        final DbCheckConfigurer dbConfig = new DbCheckConfigurer();
        dbConfig.addCheckInitializationListener(SpringCheckInitializationListener.INSTANCE);
        final Validator ovalValidator =
                new Validator(annConfig, dbConfig, new JPAAnnotationConfigLazy(
                        true));
        Validator
                .setMessageValueFormatter(new MessageLookupMessageValueFormatter(
                        configureMessageSource()));
        Validator.setContextRenderer(new MessageLookupContextRenderer(
                configureMessageSource()));
        Validator.setMessageResolver(new SpringMvcMessageResolver(
                configureMessageSource()));
        return new SpringValidator(ovalValidator);
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
        messageSource.setBasenames(MESSAGE_SOURCE, MESSAGE_SOURCE_OVAL);
        messageSource.setCacheSeconds(MESSAGE_CACHE);
        messageSource.setFallbackToSystemLocale(false);
        // Make sure Apostrophs must always be doubled..
        messageSource.setAlwaysUseMessageFormat(true);
        // This persister doubles Apostoph
        messageSource
                .setPropertiesPersister(new ApostropheEscapingPropertiesPersister());
        return messageSource;
    }

}
