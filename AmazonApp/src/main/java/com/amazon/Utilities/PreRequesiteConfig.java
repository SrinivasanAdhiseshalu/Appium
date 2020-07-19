package com.amazon.Utilities;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class PreRequesiteConfig {
	
	/**
	 * This method is used to call the start the Reporting in Reporting.java class
	 * @param NA
	 */
	@BeforeSuite
	void startProject(){
		Reporting.startReporting();
	}
	
	/**
	 * This method is used to call the end the Reporting in Reporting.java class
	 * @param NA
	 */
	@AfterSuite
	void endProject(){
		Reporting.endReporting();

	}
}
