package com.demo.springbatch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author sedki
 *
 */

public class PropertyFileUtils {

	static Properties prop;

	/**
	 * get fileds from application.properties 
	 * @return
	 */
	public static Properties loadPropreties() {

		if (prop == null) {
			InputStream is;
			prop = new Properties();

			File file = new File(PropertyFileUtils.class.getClassLoader()
					.getResource("application.properties").getFile());
			try {
				is = new FileInputStream(file);

				prop.load(is);

				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return prop;
	}
}
