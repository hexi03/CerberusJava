package com.hexi.Cerberus.adapter.persistence.config;

import com.hexi.Cerberus.adapter.persistence.department.factory.JpaDepartmentFactoryImpl;
import com.hexi.Cerberus.adapter.persistence.group.factory.JpaGroupFactoryImpl;
import com.hexi.Cerberus.adapter.persistence.item.factory.JpaItemFactoryImpl;
import com.hexi.Cerberus.adapter.persistence.product.factory.JpaProductFactoryImpl;
import com.hexi.Cerberus.adapter.persistence.user.factory.JpaUserFactoryImpl;
import com.hexi.Cerberus.adapter.persistence.warehouse.factory.JpaWareHouseFactoryImpl;
import com.hexi.Cerberus.adapter.persistence.factorysite.factory.JpaFactorySiteFactoryImpl;
import com.hexi.Cerberus.domain.department.DepartmentFactory;
import com.hexi.Cerberus.domain.department.repository.DepartmentRepository;
import com.hexi.Cerberus.domain.factorysite.FactorySite;
import com.hexi.Cerberus.domain.factorysite.FactorySiteFactory;
import com.hexi.Cerberus.domain.group.GroupFactory;
import com.hexi.Cerberus.domain.item.ItemFactory;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.domain.product.ProductFactory;
import com.hexi.Cerberus.domain.user.UserFactory;
import com.hexi.Cerberus.domain.warehouse.WareHouse;
import com.hexi.Cerberus.domain.warehouse.WareHouseFactory;
import com.zaxxer.hikari.HikariConfig;
//import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Spring configuration class for spring data jpa adapter.
 *
 * @author Mate Karolyi
 */
@Configuration
@EnableJpaRepositories(basePackages = "hexi.SpringSecurityTestApp.demo.models")
@EnableTransactionManagement
//@ConditionalOnProperty(
//        value = "persistence",
//        havingValue = "spring-data-jpa",
//        matchIfMissing = true
//)
@PropertySource("classpath:application.yml")
public class JpaPersistenceConfiguration {

    private static HikariConfig config = new HikariConfig();

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource ds) {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(ds);
        factoryBean.setPackagesToScan("com.hexi.Cerberus.adapter.persistence");
        return factoryBean;
    }

    @Bean
    public TransactionManager transactionManager(LocalSessionFactoryBean sessionFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(sessionFactory.getObject());
        return transactionManager;
    }

//    @Bean("liquibaseForPersistence")
//    SpringLiquibase initializeDatabaseMigration() {
//        final SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setDataSource(ds);
//        liquibase.setChangeLog(BUNDLE_INIT_PATH);
//
//        return liquibase;
//    }

    @Bean
    public DepartmentFactory departmentFactory() {
        return new JpaDepartmentFactoryImpl();
    }

    @Bean
    public FactorySiteFactory factorySiteFactory(DepartmentRepository departmentRepository) {
        return new JpaFactorySiteFactoryImpl(departmentRepository);
    }

    @Bean
    public WareHouseFactory wareHouseFactory(DepartmentRepository departmentRepository) {
        return new JpaWareHouseFactoryImpl(departmentRepository);
    }

    @Bean
    public UserFactory userFactory() {
        return new JpaUserFactoryImpl();
    }

    @Bean
    public GroupFactory groupFactory() {
        return new JpaGroupFactoryImpl();
    }

    @Bean
    public ItemFactory itemFactory() {
        return new JpaItemFactoryImpl();
    }

    @Bean
    public ProductFactory productFactory(ItemRepository itemRepository) {
        return new JpaProductFactoryImpl(itemRepository);
    }

}

