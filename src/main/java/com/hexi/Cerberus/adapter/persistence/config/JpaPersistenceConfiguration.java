package com.hexi.Cerberus.adapter.persistence.config;

import com.hexi.Cerberus.adapter.persistence.department.base.DepartmentModel;
import com.hexi.Cerberus.adapter.persistence.department.factory.JpaDepartmentFactoryImpl;
import com.hexi.Cerberus.adapter.persistence.department.repository.JpaDepartmentRepository;
import com.hexi.Cerberus.adapter.persistence.factorysite.repository.JpaFactorySiteRepository;
import com.hexi.Cerberus.adapter.persistence.group.factory.JpaGroupFactoryImpl;
import com.hexi.Cerberus.adapter.persistence.group.repository.JpaGroupRepository;
import com.hexi.Cerberus.adapter.persistence.item.factory.JpaItemFactoryImpl;
import com.hexi.Cerberus.adapter.persistence.item.repository.JpaItemRepository;
import com.hexi.Cerberus.adapter.persistence.product.factory.JpaProductFactoryImpl;
import com.hexi.Cerberus.adapter.persistence.product.repository.JpaProductRepository;
import com.hexi.Cerberus.adapter.persistence.report.factory.JpaReportFactoryImpl;
import com.hexi.Cerberus.adapter.persistence.report.repository.JpaReportRepository;
import com.hexi.Cerberus.adapter.persistence.report.repository.ReportSpecificationFactory;
import com.hexi.Cerberus.adapter.persistence.user.factory.JpaUserFactoryImpl;
import com.hexi.Cerberus.adapter.persistence.user.repository.JpaUserRepository;
import com.hexi.Cerberus.adapter.persistence.warehouse.factory.JpaWareHouseFactoryImpl;
import com.hexi.Cerberus.adapter.persistence.factorysite.factory.JpaFactorySiteFactoryImpl;
import com.hexi.Cerberus.adapter.persistence.warehouse.repository.JpaWareHouseRepository;
import com.hexi.Cerberus.domain.department.DepartmentFactory;
import com.hexi.Cerberus.domain.department.DepartmentID;
import com.hexi.Cerberus.domain.department.repository.DepartmentRepository;
import com.hexi.Cerberus.domain.factorysite.FactorySiteFactory;
import com.hexi.Cerberus.domain.factorysite.repository.FactorySiteRepository;
import com.hexi.Cerberus.domain.group.GroupFactory;
import com.hexi.Cerberus.domain.group.repository.GroupRepository;
import com.hexi.Cerberus.domain.item.ItemFactory;
import com.hexi.Cerberus.domain.item.repository.ItemRepository;
import com.hexi.Cerberus.domain.product.ProductFactory;
import com.hexi.Cerberus.domain.product.repository.ProductRepository;
import com.hexi.Cerberus.domain.report.ReportFactory;
import com.hexi.Cerberus.domain.report.repository.ReportRepository;
import com.hexi.Cerberus.domain.user.UserFactory;
import com.hexi.Cerberus.domain.user.repository.UserRepository;
import com.hexi.Cerberus.domain.warehouse.WareHouseFactory;
import com.hexi.Cerberus.domain.warehouse.repository.WareHouseRepository;
import com.zaxxer.hikari.HikariConfig;
//import liquibase.integration.spring.SpringLiquibase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Spring configuration class for spring data jpa adapter.
 *
 * @author Mate Karolyi
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.hexi.Cerberus.adapter.persistence")
@EnableTransactionManagement
//@ConditionalOnProperty(
//        value = "persistence",
//        havingValue = "spring-data-jpa",
//        matchIfMissing = true
//)
public class JpaPersistenceConfiguration {

    private static final HikariConfig config = new HikariConfig();


//
//    @Bean
//    public TransactionManager transactionManager(LocalSessionFactoryBean sessionFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(sessionFactory.getObject());
//        return transactionManager;
//    }






    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        LocalContainerEntityManagerFactoryBean factory = new
                LocalContainerEntityManagerFactoryBean();
        Properties props = new Properties();
        props.put("hibernate.dialect", "com.hexi.Cerberus.adapter.persistence.config.ExtendedPGSQLDialect");
        props.put("hibernate.cache.use_second_level_cache", "false");
        props.put("hibernate.cache.use.query_cache", "false");
//        props.put("org.hibernate.orm.query.sqm.ast", "debug");


        factory.setJpaProperties(props);

        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.hexi.Cerberus.adapter.persistence");
        factory.setDataSource(ds);



        return factory;
    }
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
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
    public UserFactory userFactory(PasswordEncoder encoder) {
        return new JpaUserFactoryImpl(encoder);
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

    @Bean
    public ReportFactory reportFactory(
            ItemRepository itemRepository,
            ProductRepository productRepository,
            FactorySiteRepository factorySiteRepository,
            WareHouseRepository wareHouseRepository,
            ReportRepository reportRepository,
            UserRepository userRepository
    ) {
        return new JpaReportFactoryImpl(itemRepository, productRepository, factorySiteRepository, wareHouseRepository, reportRepository, userRepository);
    }


    @Bean
    public DepartmentRepository departmentRepository(JpaDepartmentRepository<DepartmentModel, DepartmentID> rep) {
        return rep;
    }

    @Bean
    public FactorySiteRepository factorySiteRepository(JpaFactorySiteRepository rep) {
        return rep;
    }

    @Bean
    public WareHouseRepository wareHouseRepository(JpaWareHouseRepository rep) {
        return rep;
    }

    @Bean
    public UserRepository userRepository(JpaUserRepository rep) {
        return rep;
    }

    @Bean
    public GroupRepository groupRepository(JpaGroupRepository rep) {
        return rep;
    }

    @Bean
    public ItemRepository itemRepository(JpaItemRepository rep) {
        return rep;
    }

    @Bean
    public ProductRepository productRepository(JpaProductRepository rep) {
        return rep;
    }

    @Bean
    public ReportRepository reportRepository(JpaReportRepository rep, EntityManager entityManager) {
        ReportSpecificationFactory.setManager(entityManager);
        return rep;
    }

}

