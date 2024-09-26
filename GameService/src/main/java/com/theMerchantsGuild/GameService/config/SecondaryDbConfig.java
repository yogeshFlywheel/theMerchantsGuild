package com.theMerchantsGuild.GameService.config;

import com.theMerchantsGuild.GameService.repository.GameExternalRepository;
import com.theMerchantsGuild.GameService.repository.GameRepository;
import jakarta.persistence.EntityManagerFactory;  // Use jakarta.persistence for Spring Boot 3.x
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
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
                classes = GameExternalRepository.class
        ),
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager"
)
public class SecondaryDbConfig {

    @Bean(name = "secondaryDataSource")
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("secondaryDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.theMerchantsGuild.GameService.entity.ScreenData")
                .persistenceUnit("secondary")
                .build();
    }

    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("secondaryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
