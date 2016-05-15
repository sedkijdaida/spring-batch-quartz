/**
 * 
 */
package com.demo.springbatch.config;


import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.demo.springbatch.util.PropertyFileUtils;

/**
 * @author sedki
 *
 */
@Configuration
@EnableTransactionManagement
public class DataBaseConfig {
	    
	    @Resource
        private Environment env;
         
	    static Properties prop = PropertyFileUtils.loadPropreties();
	    
        @Bean
        public DataSource dataSource() throws IOException {
                DriverManagerDataSource dataSource = new DriverManagerDataSource();
                PropertiesFactoryBean props = new PropertiesFactoryBean();
                props.setLocation(new ClassPathResource("/application.properties"));
                dataSource.setDriverClassName(prop.getProperty("PROPERTY_NAME_DATABASE_DRIVER"));
                dataSource.setUrl(prop.getProperty("PROPERTY_NAME_DATABASE_URL"));
                dataSource.setUsername(prop.getProperty("PROPERTY_NAME_DATABASE_USERNAME"));
                dataSource.setPassword(prop.getProperty("PROPERTY_NAME_DATABASE_PASSWORD"));
                 
                return dataSource;
        }
       
         
     }
