package com.amazon.Utilities;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class preRequestConfig {
	
	@BeforeSuite
	void startProject(){
		Reporting.StartReporting();
	}
	

	
	@AfterSuite
	void endProject(){
		Reporting.endReporting();

	}
}
