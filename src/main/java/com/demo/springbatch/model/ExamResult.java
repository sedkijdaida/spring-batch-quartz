package com.demo.springbatch.model;
 
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
 
import org.joda.time.LocalDate;
 
/**
 * 
 * @author sedki
 *
 */
@XmlRootElement(name = "ExamResult")
public class ExamResult {
 
    private String studentName;
 
    private LocalDate dob;
 
    private double percentage;
 
    /**
     * 
     * @return
     */
    @XmlElement(name = "studentName")
    public String getStudentName() {
        return studentName;
    }
 
    /**
     * 
     * @param studentName
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
 
    /**
     * 
     * @return
     */
    @XmlElement(name = "dob")
    @XmlJavaTypeAdapter(type = LocalDate.class, value = com.demo.springbatch.util.LocalDateAdapter.class)
    public LocalDate getDob() {
        return dob;
    }
 
    /**
     * 
     * @param dob
     */
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
    
    /**
     * 
     * @return
     */
    @XmlElement(name = "percentage")
    public double getPercentage() {
        return percentage;
    }
 
    /**
     * 
     * @param percentage
     */
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
 
    @Override
    public String toString() {
        return "ExamResult [studentName=" + studentName + ", dob=" + dob
                + ", percentage=" + percentage + "]";
    }
 
}