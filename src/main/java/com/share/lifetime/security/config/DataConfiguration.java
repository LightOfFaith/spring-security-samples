package com.share.lifetime.security.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.transaction.PlatformTransactionManager;

import com.share.lifetime.security.domain.entity.Users;

@Profile("test")
@Configuration
@EnableJpaRepositories("com.share.lifetime.security.mvc.data")
public class DataConfiguration {

    @Bean
    public SecurityEvaluationContextExtension expressionEvaluationContextProvider() {
        return new SecurityEvaluationContextExtension();
    }

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.HSQL).build();
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.HSQL);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(Users.class.getPackage().getName());
        factory.setDataSource(dataSource());

        Map<String, Object> jpaProperties = new HashMap<String, Object>();
        // org.hibernate.cfg.AvailableSettings.class
        jpaProperties.put(AvailableSettings.SHOW_SQL, true);
        jpaProperties.put(AvailableSettings.FORMAT_SQL, true);
        factory.setJpaPropertyMap(jpaProperties);

        return factory;
    }

    @Bean
    @DependsOn("entityManagerFactory")
    public ResourceDatabasePopulator initDatabase(DataSource dataSource) throws Exception {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("data.sql"));
        populator.populate(dataSource.getConnection());
        return populator;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }
}
