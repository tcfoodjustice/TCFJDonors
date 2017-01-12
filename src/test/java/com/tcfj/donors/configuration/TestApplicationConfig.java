package com.tcfj.donors.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by andrew.larsen on 1/1/2017.
 */
@Configuration
@Profile("test")
public class TestApplicationConfig {

    @Bean
    @Primary
    public EmbeddedDatabase embeddedDatabase(){
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:create_donors_table.sql")
                .addScript("classpath:create_recipients_table.sql")
                .addScript("classpath:create_shifts_table.sql")
                .addScript("classpath:insert_donor_data.sql")
                .addScript("classpath:insert_recipients_data.sql")
                .addScript("classpath:insert_shift_data.sql")
                .build();
        return db;
    }
    //define the JdbcTemplate bean, which is injected into the ShiftDaoImpl
    //via the autowired tag

    @Autowired
    JpaVendorAdapter jpaVendorAdapter;

    @Bean
    public DataSource dataSource() {
        return embeddedDatabase();
    }

    @Bean
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource());
        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setPackagesToScan("com.tcfj.*");
        lef.afterPropertiesSet();
        return lef.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }

}
