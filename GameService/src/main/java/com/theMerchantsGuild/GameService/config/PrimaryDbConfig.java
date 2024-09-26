package com.theMerchantsGuild.GameService.config;

import com.theMerchantsGuild.GameService.repository.GameRepository;
import jakarta.persistence.EntityManagerFactory;  // Use jakarta.persistence for Spring Boot 3.x
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;  // javax.sql is still correct for DataSource
import org.springframework.boot.context.properties.ConfigurationProperties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.theMerchantsGuild.GameService.repository",
        includeFilters = @ComponentScan.Filter(       // Include only the GameRepository for Database 1
                type = FilterType.ASSIGNABLE_TYPE,
                classes = GameRepository.class
        ),
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager"
)
public class PrimaryDbConfig {

    @Primary
    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("primaryDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.theMerchantsGuild.GameService.entity.Game")
                .persistenceUnit("primary")
                .build();
    }

    @Primary
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("primaryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

