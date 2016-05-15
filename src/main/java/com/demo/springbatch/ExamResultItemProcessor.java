/**
 * 
 */
package com.demo.springbatch;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.demo.springbatch.model.ExamResult;

/**
 * @author sedki
 * this class is for processing the result of item Reader
 */
public class ExamResultItemProcessor implements ItemProcessor<ExamResult, ExamResult> {
	
	final static Logger logger = Logger.getLogger(ExamResultItemProcessor.class);
	
	/**
	 * filtering the result of ItemReader with percentage less than 80
	 */
	@Override
	public ExamResult process(ExamResult item) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Processing result::::>>>"+item);
		if(item.getPercentage()<80){
			return null;
		}
		return item;
	}

}
