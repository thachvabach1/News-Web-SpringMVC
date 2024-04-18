package com.laptrinhjavaweb.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {"com.laptrinhjavaweb.repository"})
@EnableTransactionManagement
public class JPAConfig {
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		// dataSource = driverName, url, userName, password trong jdbc
		em.setDataSource(dataSource());
		//presistence-data đã giải thích trong Theory.doc
		em.setPersistenceUnitName("persistence-data");
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		return em;
	}
	
	// Entity Transaction
	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/springmvcbasicfree");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		return dataSource;
	}
	
	Properties additionalProperties() {
		// Properties giúp tạo table từ @Entity vào database
		Properties properties = new Properties();
		/*create-drop là tạo database từ java class (@Entity), chỉ sử dụng khi tạo table lúc đầu, lúc sau muốn thêm comlum
		vào table hoặc thêm table mới thì dùng script vì có data rồi thì k thể create and drop table được nữa. Khi tắt server 
		thì sẽ xóa tất cả các bảng được tọa trong database*/
//		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
//		properties.setProperty("hibernate.hbm2ddl.auto", "update");
//		properties.setProperty("hibernate.hbm2ddl.auto", "create");
		properties.setProperty("hibernate.hbm2ddl.auto", "none");
		//để sài được @ManyToMany(fetch = FetchType.LAZY)
		properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
		return properties;
	}
}
