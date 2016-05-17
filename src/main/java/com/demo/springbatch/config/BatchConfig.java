package com.demo.springbatch.config;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;

import org.joda.time.LocalDate;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.scope.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.builder.TaskletStepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.demo.springbatch.ExamResultItemProcessor;
import com.demo.springbatch.ExamResultRowMapper;
import com.demo.springbatch.JobLauncherDetails;
import com.demo.springbatch.model.ExamResult;

/**
 * @author sedki
 *
 */
@Configuration
@EnableBatchProcessing
@Import({ DataBaseConfig.class })
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilders;

	@Autowired
	private StepBuilderFactory stepBuilders;

	@Autowired
	private DataBaseConfig dataBaseConfig ;

	@Bean
	public ResourcelessTransactionManager transactionManager() {
		return new ResourcelessTransactionManager();
	}

	@Bean
	public JobRepository jobRepository() throws Exception {
		MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
		return (JobRepository) factory.getObject();
	}
	
	@Bean
    public JobBuilderFactory jobBuilderFactory() throws Exception {
        return new JobBuilderFactory(jobRepository());
    }
	
	 @Bean
	    public StepBuilderFactory stepBuilderFactory(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
	        return new StepBuilderFactory(jobRepository, transactionManager);
	    }

	@Bean
	public StepScope stepScope() {
		final StepScope stepScope = new StepScope();
		stepScope.setAutoProxy(false);
		return stepScope;
	}

	@Bean
	public JobLauncher jobLauncher() throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository());
		return jobLauncher;
	}

	@Bean
	public JobRegistry jobRegistry() {
		return new MapJobRegistry();

	}

	@Bean
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
		JobRegistryBeanPostProcessor beanPostProcessor = new JobRegistryBeanPostProcessor();
		beanPostProcessor.setJobRegistry(jobRegistry());
		return beanPostProcessor;
	}

	@Bean
	@Scope(value = "step", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public FlatFileItemWriter<ExamResult> flatFileItemWriter() {
		FlatFileItemWriter<ExamResult> dFileItemWriter = new FlatFileItemWriter<ExamResult>();
		DelimitedLineAggregator<ExamResult> delimitedLineAggregator = new DelimitedLineAggregator<ExamResult>();
		BeanWrapperFieldExtractor<ExamResult> fieldExtractor = new BeanWrapperFieldExtractor<ExamResult>();
		fieldExtractor
				.setNames(new String[] { "studentName", "percentage", "dob" });
		delimitedLineAggregator.setDelimiter("|");
		delimitedLineAggregator.setFieldExtractor(fieldExtractor);
		dFileItemWriter.setResource(new FileSystemResource(
				"csv/examResult.csv"));
		dFileItemWriter.setLineAggregator(delimitedLineAggregator);
		return dFileItemWriter;
	}

	@Bean
	public ItemReader<ExamResult> databaseItemReader() throws IOException {

		JdbcCursorItemReader<ExamResult> reader = new JdbcCursorItemReader<ExamResult>();
		String sql = "SELECT STUDENT_NAME, DOB, PERCENTAGE FROM EXAM_RESULT";
		reader.setSql(sql);
		reader.setDataSource(dataBaseConfig.dataSource());
		reader.setRowMapper(new ExamResultRowMapper());
		return reader;
	}

	@Bean
	public JobExecutionListener jobListener() throws Exception {
		return new com.demo.springbatch.ExamResultJobListener();
	}

	@Bean
	public ExamResultItemProcessor itemProcessor() {
		return new ExamResultItemProcessor();
	}

	@Bean
	public Job examResultJob() throws Exception {
		return jobBuilderFactory().get("examResultJob").start(step()).build();
	}

	@Bean
	public Step step() throws Exception {
		return stepBuilderFactory(jobRepository(), transactionManager()).get("step").<ExamResult, ExamResult> chunk(5)
				.reader(databaseItemReader()).processor(itemProcessor())
				.writer(flatFileItemWriter())
				.listener((JobExecutionListener) jobListener()).build();
	}

	

}