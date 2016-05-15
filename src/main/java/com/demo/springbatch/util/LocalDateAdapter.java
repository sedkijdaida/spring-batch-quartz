/**
 * 
 */
package com.demo.springbatch.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.LocalDate;

/**
 * @author sedki
 *
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate>{
	 
    public LocalDate unmarshal(String v) throws Exception {
        return new LocalDate(v);
    }
 
    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
 
}