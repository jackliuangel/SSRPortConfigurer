package com.securingweb.vpn.config.db;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//@Profile({"JWT", "OAuth2Github"})
@Profile("!JSession")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.securingweb.vpn.domain.internal"},
        entityManagerFactoryRef = "internalEntityManagerFactory",
        transactionManagerRef = "internalTransactionManager"
)
public class InternalDbConfiguration {
    @Autowired
    private Environment env;

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.internal")
    public DataSource internalDataSource() {
        return new HikariDataSource();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean internalEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            ConfigurableListableBeanFactory beanFactory,
            @Qualifier("internalDataSource") DataSource dataSource
    ) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        LocalContainerEntityManagerFactoryBean emfb = builder.dataSource(dataSource)
                                                             .packages("com.securingweb.vpn.domain.internal")
                                                             .properties(properties)
                                                             .persistenceUnit("dbInternal")
                                                             .build();

        emfb.getJpaPropertyMap().put(AvailableSettings.BEAN_CONTAINER, new SpringBeanContainer(beanFactory));
        return emfb;
    }

    @Primary
    @Bean
    public PlatformTransactionManager internalTransactionManager(
            @Qualifier("internalEntityManagerFactory") EntityManagerFactory internalEntityManagerFactory
    ) {
        return new JpaTransactionManager(internalEntityManagerFactory);
    }

    @Primary
    @Bean
    @Qualifier("internalJdbcTemplate")
    public JdbcTemplate internalJdbcTemplate() {
        return new JdbcTemplate(internalDataSource());
    }
}
