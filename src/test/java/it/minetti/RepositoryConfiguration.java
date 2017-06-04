package it.minetti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@org.springframework.context.annotation.Configuration
@EnableJpaRepositories(basePackages = "it.minetti")
@EnableTransactionManagement
public class RepositoryConfiguration {

  @Bean
  @Qualifier("dataSource")
  public EmbeddedDatabase dataSource() {
    return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
  }

  @Bean
  @Autowired
  public EntityManagerFactory entityManagerFactory(EmbeddedDatabase dataSource) {
    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

    // in-menory
    emf.setDataSource(dataSource);

    // realDb
    //emf.setDataSource(realDb());

    emf.setPackagesToScan("it.minetti.model");
    emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

    Properties properties = new Properties();
    properties.put("hibernate.hbm2ddl.auto", "update");
    properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
    properties.put("hibernate.naming_strategy", "org.hibernate.cfg.EJB3NamingStrategy");
    properties.put("hibernate.show_sql", "true");
    emf.setJpaProperties(properties);

    emf.afterPropertiesSet();

    return emf.getObject();
  }

  @Bean
  @Autowired
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(emf);
    return txManager;
  }

}
