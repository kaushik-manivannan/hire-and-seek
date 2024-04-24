//package com.kaushikmanivannan.hireandseek.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
//@Configuration
//public class JpaConfig {
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
//        emfb.setDataSource(dataSource);
//        emfb.setPackagesToScan("com.kaushikmanivannan.hireandseek.model");
//
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        emfb.setJpaVendorAdapter(vendorAdapter);
//        return emfb;
//    }
//
//    @Bean
//    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
//        return entityManagerFactory.createEntityManager();
//    }
//}
