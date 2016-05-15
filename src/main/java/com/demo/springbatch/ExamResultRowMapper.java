/**
 * 
 */
package com.demo.springbatch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;

import com.demo.springbatch.model.ExamResult;

/**
 * @author sedki
 *this class is for mapping the result of query to the object model 
 */
public class ExamResultRowMapper implements RowMapper<ExamResult>{
 
	/**
	 * method mapper :mapping the resultset to the ExamResult object
	 */
    @Override
    public ExamResult mapRow(ResultSet rs, int rowNum) throws SQLException {
 
        ExamResult result = new ExamResult();
        result.setStudentName(rs.getString("student_name"));
        result.setDob(new LocalDate(rs.getDate("dob")));
        result.setPercentage(rs.getDouble("percentage"));
             
        return result;
    } 
}
