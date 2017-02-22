package com.run.server;

import com.service.EmployeeService;
import com.vo.Employee;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(value = "com.*")
@PropertySource({"server.properties"})
@EnableTransactionManagement
public class ServerConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource apacheConnectionPool() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        dataSource.setInitialSize(10);
        return dataSource;
    }

   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
       LocalContainerEntityManagerFactoryBean jpaFactoryBean = new LocalContainerEntityManagerFactoryBean();
       jpaFactoryBean.setDataSource(dataSource);
       jpaFactoryBean.setPackagesToScan(Employee.class.getPackage().getName());
       jpaFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
       jpaFactoryBean.setJpaProperties(createJpaProperties());
       return jpaFactoryBean;
   }

    private Properties createJpaProperties() {
        return new Properties() {
            {
                setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
                setProperty("hibernate.show_sql", "true");
                setProperty("hibernate.globally_quoted_identifiers", "true");
            }
        };
    }

    @Bean
    public JpaTransactionManager JpaTransactionManager(EntityManagerFactory emf){
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(emf);
        return manager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}