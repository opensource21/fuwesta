package de.ppi.samples.fuwesta.config;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * Spring configuration for persistence.
 * 
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "de.ppi.samples.fuwesta.dao")
@PropertySource("classpath:/db.properties")
public class PersistenceConfig implements TransactionManagementConfigurer {

    /** The jdbc driver class name. */
    @Value("${db.driver}")
    private String jdbcDriverClassName;

    /** The connection url. */
    @Value("${db.url}")
    private String connectionUrl;

    /** The username. */
    @Value("${db.username}")
    private String username;

    /** The password. */
    @Value("${db.password}")
    private String password;

    /** The hibernate dialect. */
    @Value("${db.dialect}")
    private String hibernateDialect;

    /** The hbm2ddl auto. */
    @Value("${db.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    /** The default db-schema. */
    @Value("${db.schema}")
    private String defaultSchema;

    /** Flag that the DDL should be generated. */
    @Value("${db.generateDdl}")
    private boolean generateDDL;

    /** The idle connection test period in minutes. */
    @Value("${db.idleConnectionTestPeriodInMinutes}")
    private final int idleConnectionTestPeriodInMinutes = 240;

    /** The release helper threads. */
    @Value("${db.releaseHelperThreads}")
    private final int releaseHelperThreads = 3;

    /** The statements cache size. */
    @Value("${db.statementsCacheSize}")
    private final int statementsCacheSize = 100;

    /** The acquire increment. */
    @Value("${db.acquireIncrement}")
    private final int acquireIncrement = 2;

    /** The partition count. */
    @Value("${db.partitionCount}")
    private final int partitionCount = 3;

    /** The max connections per partition. */
    @Value("${db.maxConnectionsPerPartition}")
    private final int maxConnectionsPerPartition = 20;

    /** The min connections per partition. */
    @Value("${db.minConnectionsPerPartition}")
    private final int minConnectionsPerPartition = 10;

    /** The idle max age in minutes. */
    @Value("${db.idleMaxAgeInMinutes}")
    private final int idleMaxAgeInMinutes = 60;

    /**
     * Creates the {@link DataSource}.
     * 
     * @return the {@link DataSource}.
     */
    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource configureDataSource() {
        final BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(jdbcDriverClassName);
        dataSource.setJdbcUrl(connectionUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource
                .setIdleConnectionTestPeriodInMinutes(idleConnectionTestPeriodInMinutes);
        dataSource.setIdleMaxAgeInMinutes(idleMaxAgeInMinutes);
        dataSource.setMinConnectionsPerPartition(minConnectionsPerPartition);
        dataSource.setMaxConnectionsPerPartition(maxConnectionsPerPartition);
        dataSource.setPartitionCount(partitionCount);
        dataSource.setAcquireIncrement(acquireIncrement);
        dataSource.setStatementsCacheSize(statementsCacheSize);
        dataSource.setReleaseHelperThreads(releaseHelperThreads);

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
        entityManagerFactoryBean
                .setPackagesToScan("de.ppi.samples.fuwesta.model");
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

}
