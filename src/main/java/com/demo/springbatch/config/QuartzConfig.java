/**
 * 
 */
package com.demo.springbatch.config;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.rmi.CORBA.Util;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.demo.springbatch.JobLauncherDetails;

/**
 * @author sedki
 *
 */
@Configuration
@EnableScheduling
@Import({ BatchConfig.class })
public class QuartzConfig {
	
	@Autowired
	private BatchConfig quartzBatchConfig ;
	
	/**
	 * @throws Exception 
	 * 
	 * 
	 **/
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws Exception {
		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
		scheduler.setTriggers(cronTriggerFactoryBean().getObject());
		return scheduler;
	}
 
	/**
	 * @throws Exception 
	 * @throws ParseException 
	 */
	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBean() throws  Exception  {
		CronTriggerFactoryBean cronTriggerBean = new CronTriggerFactoryBean();
		    cronTriggerBean.setJobDetail(jobDetail(quartzBatchConfig.jobRegistry(),quartzBatchConfig.jobLauncher()).getObject());
			cronTriggerBean.setCronExpression("*/10 * * * * ?");
		
		return cronTriggerBean;
	}

	@Bean
	public JobDetailFactoryBean jobDetail(JobRegistry jobRegistry,JobLauncher jobLauncher ) throws ParseException {
		
		JobDetailFactoryBean factory = new JobDetailFactoryBean();
		 factory.setName("jobClass");
		 factory.setJobClass(JobLauncherDetails.class);
		 factory.setGroup("quartz-batch");
		 JobDataMap jobDataMap = new JobDataMap();
		 jobDataMap.put("jobName", "examResultJob");
		 jobDataMap.put("jobLocator", jobRegistry);
		 jobDataMap.put("jobLauncher", jobLauncher);
		 factory.setJobDataAsMap(jobDataMap);
		return factory;
	}
	
	

	@Bean
	public JobDetailFactoryBean jobDetailFactoryBean() {
		JobDetailFactoryBean factory = new JobDetailFactoryBean();
		factory.setJobClass(JobLauncherDetails.class);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jobName", "examResultJob");
		map.put("jobLocator", "jobRegistry");
		map.put("jobLauncher", "jobLauncher");
		factory.setJobDataAsMap(map);
		factory.setGroup("quartz-batch");
		factory.setName("jobClass");
		return factory;
	}	
	
}
