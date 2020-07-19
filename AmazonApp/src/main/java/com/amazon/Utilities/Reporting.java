package com.amazon.Utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.google.gson.JsonObject;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;


/**
 * @author ${SrinivasanAdhiseshalu}
 *
 */


public class Reporting {
	AppiumDriver<MobileElement>driver=null;
	
	
	public static ExtentReports report;
	public static ExtentTest logger;
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_yyy_h_mm_s");
	public static Date date = new Date();
	public static File path;public File createPath;
	public static File logFile;
	
	String name;
	HashMap<String,Integer> count = new HashMap<String,Integer>();
	
	public Reporting(AppiumDriver<MobileElement>driver) {
		this.driver=driver;
		count.put("sno", 1);
	}
	
	File screenshot() throws Exception{
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File imagePath = new File(createPath.getAbsolutePath()+"/"+count.get("sno")+".png");
		count.put("sno", count.get("sno")+1);
		FileUtils.copyFile(screenshot, imagePath.getAbsoluteFile());
		return imagePath;
	}
	
	public static void startReporting(){
		path=new File(System.getProperty("user.dir")+"/Report/"+sdf.format(date));
		path.mkdirs();
		report=new ExtentReports(path.getAbsolutePath()+"/OverallReport.html");
	
	}

	public ExtentTest startTest(String name){
		try {
			this.name=name;
			logFile = new File(path.getAbsolutePath()+"/"+"Logs for "+name+".txt");
			logFile.createNewFile();
			createPath= new File(path+"/Screenshots_"+name);
			createPath.mkdirs();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return logger = report.startTest(name);
	}
	
	public static File returnLogtFilePath() throws Exception{
		return logFile;
	}
	
	public void endTest(){
		report.endTest(logger);
	}
	
	public void reportPass(String desc, ExtentTest logger) throws Exception{
		String desc1 = "<font color ='blue'><b>Description - </font></b>"+"Validation of "+desc+" Icon"+"\n"+"<br>"+
				"<font color ='blue'><b>Expected - </font></b>"+desc+" Should be available"+"\n"+"<br>"+
				"<font color ='blue'><b>Actual - </font></b>"+desc+" Icon is available"+"\n"+"<br>";
		desc = desc1;
		String temp = logger.addScreenCapture(screenshot().getAbsolutePath());
		logger.log(LogStatus.PASS, desc, temp);
	}
	
	public void reportFail(String desc, ExtentTest logger) throws Exception{
		String desc1 = "<font color ='blue'><b>Description - </font></b>"+"Validation of "+desc+" Icon"+"\n"+"<br>"+
				"<font color ='blue'><b>Expected - </font></b>"+desc+" Should be available"+"\n"+"<br>"+
				"<font color ='red'><b>Failed - </font></b>"+desc+" Icon is NOT available"+"\n"+"<br>";
		desc = desc1;
		String temp = logger.addScreenCapture(screenshot().getAbsolutePath());
		logger.log(LogStatus.FAIL, desc, temp);
	}
	
	public static void endReporting(){
		report.endTest(logger);
		report.flush();
	}
}
