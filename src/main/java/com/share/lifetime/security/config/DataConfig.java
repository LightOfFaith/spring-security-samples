package com.share.lifetime.security.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptException;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@PropertySources(value = {@PropertySource(value = "classpath:config/config.properties", encoding = "UTF-8"),
    @PropertySource(value = "classpath:config/db/mysql-common.properties", encoding = "UTF-8"),
    @PropertySource(value = "classpath:config/db/mysql-${spring.profiles.active}.properties", encoding = "UTF-8")})
public class DataConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean(name = "primaryDruidDataSource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource primaryDruidDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("primary.jdbc.url", String.class));
        dataSource.setUsername(env.getProperty("primary.jdbc.username", String.class));
        dataSource.setPassword(env.getProperty("primary.jdbc.password", String.class));
        setCommonProperties(dataSource);
        return dataSource;
    }

    @Bean(name = "secondaryDruidDataSource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource secondaryDruidDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("secondary.jdbc.url", String.class));
        dataSource.setUsername(env.getProperty("secondary.jdbc.username", String.class));
        dataSource.setPassword(env.getProperty("secondary.jdbc.password", String.class));
        setCommonProperties(dataSource);
        return dataSource;
    }

    private void setCommonProperties(DruidDataSource dataSource) throws SQLException {
        dataSource.setFilters(env.getProperty("jdbc.filters", String.class));

        dataSource.setMaxActive(env.getProperty("jdbc.maxActive", Integer.class));
        dataSource.setInitialSize(env.getProperty("jdbc.initialSize", Integer.class));
        dataSource.setMaxWait(env.getProperty("jdbc.maxWait", Integer.class));
        dataSource.setMinIdle(env.getProperty("jdbc.minIdle", Integer.class));

        dataSource
            .setTimeBetweenEvictionRunsMillis(env.getProperty("jdbc.timeBetweenEvictionRunsMillis", Integer.class));
        dataSource.setMinEvictableIdleTimeMillis(env.getProperty("jdbc.minEvictableIdleTimeMillis", Integer.class));

        dataSource.setTestWhileIdle(env.getProperty("jdbc.testWhileIdle", Boolean.class));
        dataSource.setTestOnBorrow(env.getProperty("jdbc.testOnBorrow", Boolean.class));
        dataSource.setTestOnReturn(env.getProperty("jdbc.testOnReturn", Boolean.class));

        dataSource.setPoolPreparedStatements(env.getProperty("jdbc.poolPreparedStatements", Boolean.class));
        dataSource.setMaxOpenPreparedStatements(
            env.getProperty("jdbc.maxPoolPreparedStatementPerConnectionSize", Integer.class));
        // asyncInit是1.1.4中新增加的配置，如果有initialSize数量较多时，打开会加快应用启动时间
        dataSource.setAsyncInit(true);
    }

    @Bean
    public ResourceDatabasePopulator initDatabase(DataSource dataSource) throws ScriptException, SQLException {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setSqlScriptEncoding("UTF-8");
        Boolean schema = env.getProperty("db.schema", Boolean.class);
        Boolean data = env.getProperty("db.data", Boolean.class);
        if (schema) {
            populator.addScript(new ClassPathResource("config/db/script/schema.sql"));
        }
        if (data) {
            populator.addScript(new ClassPathResource("config/db/script/data.sql"));
        }
        populator.populate(dataSource.getConnection());
        return populator;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
