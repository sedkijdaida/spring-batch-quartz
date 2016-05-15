/**
 * 
 */
package com.demo.springbatch;

import java.util.Date;

import org.quartz.SchedulerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.demo.springbatch.config.QuartzConfig;


/**
 * @author sedki
 *
 */
public class Main {
	public static void main(String[] args) {
		
	/**
	 * loading the quartz java config
	 */
		ApplicationContext context = new AnnotationConfigApplicationContext(
				QuartzConfig.class);
		
    /**
     * loading the quarz xml config
     */
//		ApplicationContext context = new ClassPathXmlApplicationContext("quartz-context.xml");

	}

}
