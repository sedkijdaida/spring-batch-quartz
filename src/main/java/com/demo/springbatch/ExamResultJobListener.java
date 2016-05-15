/**
 * 
 */
package com.demo.springbatch;

import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecutionListener;

/**
 * @author sedki
 *  this class is Optional and provide the opportunity to execute some business 
 *  logic before job start and after job completed.For example setting
 *   up environment can be done before job and cleanup can be done after job completed
 */

public class ExamResultJobListener implements JobExecutionListener{
	
	private DateTime startTime,stopTime;
	
	final static Logger logger = Logger.getLogger(ExamResultJobListener.class);
    
	/**
	 * logging the current date and time before the job is up to execute
	 */
	
	@Override
	public void beforeJob(
			org.springframework.batch.core.JobExecution jobExecution) {
		// TODO Auto-generated method stub
		startTime = new DateTime();
		logger.info("ExamResult Job starts at :"+startTime);
	}

	
	/**
	 * logging the state of the job ,the current date and time after the job is completed
	 */
	
	@Override
	public void afterJob(
			org.springframework.batch.core.JobExecution jobExecution) {
		// TODO Auto-generated method stub
		stopTime = new DateTime();
		logger.info("ExamResult Job stops at :"+stopTime);
		logger.info("Total time take in millis :"+getTimeInMillis(startTime , stopTime));
	        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
	        	logger.info("ExamResult job completed successfully");
	        }else if(jobExecution.getStatus()== BatchStatus.FAILED){
	        	logger.info("ExamResult job failed with following exceptions ");
	        	List<Throwable> exceptionList = jobExecution.getAllFailureExceptions();
	            for(Throwable th : exceptionList){
	            	logger.info("exception :" +th.getLocalizedMessage());
	            }
	        }
		
	}
	
    /**
     * calculate the time used by job to completed their task
     * @param startTime2
     * @param stopTime2
     * @return
     */
	private long getTimeInMillis(DateTime startTime2, DateTime stopTime2) {
		// TODO Auto-generated method stub
		return startTime2.getMillis()-stopTime2.getMillis();
	}

	
	/**
	 * @return the startTime
	 */
	public DateTime getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the stopTime
	 */
	public DateTime getStopTime() {
		return stopTime;
	}

	/**
	 * @param stopTime the stopTime to set
	 */
	public void setStopTime(DateTime stopTime) {
		this.stopTime = stopTime;
	}
	
}
