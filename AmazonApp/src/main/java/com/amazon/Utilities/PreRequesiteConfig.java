package com.amazon.Utilities;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class PreRequesiteConfig {
	
	@BeforeSuite
	void startProject(){
		Reporting.startReporting();
	}

	@AfterSuite
	void endProject(){
		Reporting.endReporting();

	}
}
