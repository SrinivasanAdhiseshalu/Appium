package com.amazon.Pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.net.URL;
import java.time.Duration;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.amazon.Utilities.Reporting;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class BasePage {
	DesiredCapabilities dc=new DesiredCapabilities();
	public AppiumDriver<MobileElement> driver;
	Map<String,Object> parms=new HashMap<>();
	int swipeDuration=0,timeOut=30;
	HashMap<String ,String> data;
	protected String platform= "";
	FileWriter fw = null;

	/**
	 * This is Constructor
	 * @param HashMap : will define the values from Excel
	 */
	public BasePage(HashMap<String,String>data){
		this.data=data;
		platform=data.get("Platform");
		if(platform.equalsIgnoreCase("Android"))
			swipeDuration=1000;
		else
			swipeDuration=100;
	}
	/**
	 * his method is used to create Driver object
	 * @throws Exception 
	 * @return Returns the driver object
	 */
	public AppiumDriver<MobileElement> initiateDriver()throws Exception{
		try {
			if(data.get("Platform").equalsIgnoreCase("android"))
			{
				dc.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
				dc.setCapability("newCommandTimeout",60 * 5);
				dc.setCapability("appPackage",getValueFromPropertyFile("Config", "appPackage"));
				dc.setCapability("appActivity",getValueFromPropertyFile("Config", "appActivity"));
				dc.setCapability("platformName", "ANDROID");
				dc.setCapability("deviceName",data.get("deviceID"));
				dc.setCapability("automationName", "uiautomator2");
				driver=new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"),dc);
			}
			else if(data.get("Platform").equalsIgnoreCase("ios"))
			{
				dc.setCapability(MobileCapabilityType.AUTOMATION_NAME,"XCUITest");
				dc.setCapability("appium:platform", "MAC");
				dc.setCapability("platformName", "ios");
				dc.setCapability("deviceName", "iPhone simulator");
				dc.setCapability("bundleId", "");
				dc.setCapability("noReset", true);
				dc.setCapability("useNewWDA", true);
				dc.setCapability("autoLaunch", false);
				dc.setCapability("platformVersion", "12.0");
				driver=new IOSDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"),dc);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return driver;
	}
	
	
	/**
	 * This method is used to click the element 
	 * @param ele : will define MobileElement value
	 * @throws Exception 
	 */
	protected void click(MobileElement element)throws Exception{
		MobileElement ele=element;
		try{
			new WebDriverWait(driver, timeOut).until(ExpectedConditions.elementToBeClickable(ele));
		}
		catch(Exception e){
			throw new Exception("Click failed for element in page");
		}
		ele.click();
	}

	/**
	 * This method is used to check the element is Displayed
	 * @param ele : will define MobileElement value
	 * @throws Exception 
	 */
	protected boolean isDisplayed(MobileElement element){
		try{
			new WebDriverWait(driver, timeOut).until(ExpectedConditions.visibilityOf(element));
			System.out.println("The Object identifiaction completed for the element "+element);
			return element.isDisplayed();
		}
		catch(TimeoutException e){
			System.out.println("The Object identifiaction Failed for the element "+element+" due to TimeoutException");
			return false;
		}
		catch(ElementNotVisibleException e) {
			System.out.println("The Object identifiaction Failed for the element "+element+" due to ElementNotVisibleException");
			return false;
		}
		catch(NoSuchElementException e){
			System.out.println("The Object identifiaction Failed for the element "+element+" due to NoSuchElementException");
			return false;
		}		
	}
		
	/**
	 * This method is used to get a particular attribute for the specified element
	 * @param ele : will define MobileElement value
	 * @param String : will define Property name
	 * @throws Exception 
	 */
	protected String getAttribute(MobileElement element,String propertyName){
		try {
			return element.getAttribute(propertyName);
		}catch (Exception e) {
			return "Object Not Found No value is  retrived";
		}
	}

	/**
	 * This method is used to get a text for the specified element
	 * @param ele : will define MobileElement value
	 * @throws Exception 
	 */
	protected String getText(MobileElement element){
		try {
			return element.getText();
		}
		catch (Exception e) {
			return "Object Not Found -No text retrived";
		}
	}
	
	/**
	 * This method is used to set the text for the specified element
	 * @param ele : will define MobileElement value
	 * @param String : String value to be entered in Textbox
	 * @throws Exception 
	 */
	protected void setText(MobileElement element,String value)throws InterruptedException{
		try{
			if(isDisplayed(element)){
				element.sendKeys(value);
			}
			else{
				throw new Exception("Element not found for SetText");
			}
		}catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	

	/**
	 * This method is used to swipe horizontal UP and break the loop using isDisplayed
	 * @param ele : will define MobileElement value
	 * @param int : will define Integer value
	 * @throws Exception 
	 */
	protected void swipeHorizontalUP(int swipeCount, MobileElement element) throws Exception{
		try {
			for(int i=1;i<=swipeCount;i++) {
				Dimension d = driver.manage().window().getSize();
				int width = d.getWidth();
				int height = d.getHeight();
				
				int middle = width/2;
				int startPoint = (int) (height*0.7);
				int endPoint = (int) (height*0.3);
				
				new TouchAction(driver)
				.press(PointOption.point(middle, startPoint))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(swipeDuration)))
				.moveTo(PointOption.point(middle, endPoint))
				.release().perform();
				
				if(isDisplayed(element))
					break;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This Method returns the value of the corresponding key provided in the parameter
	 * @param String : will define String property file name
	 * @param String : will define String Key to be retrived from the same file
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public static String getValueFromPropertyFile(String propertyFile,String key) throws Exception{
		String value="";
		if(propertyFile.contains(".properties"))
			propertyFile = propertyFile.replace(".properties", "");// if a tester provides the property file with Extension this will remove the Extension
																	   // In order to avoid Exceptions
		try {
			Properties properties = new Properties();
			File file = new File("./DataFiles/"+propertyFile+".properties");
			FileInputStream reader=new FileInputStream(file); 
			properties.load(reader);
			value = properties.getProperty(key);
		}catch(Exception e) {
			throw new Exception("The given Key "+key+" is Not found in the OR");
		}
		return value;
	}
	
	/**
	 * This method is used to press Enter key in keyboard
	 * @param NA
	 * @throws Exception 
	 */
	@SuppressWarnings("deprecation")
	protected void pressKeyboardEnter() throws Exception{
		try {
			driver.getKeyboard().sendKeys(Keys.ENTER);
		}catch(Exception e) {
			throw new Exception("Press Enter using Keyboard is not working");
		}
	}
	
	
	//common field to validate all the fields
	/**
	 * This method is used for validating all the fields in the framework
	 * @param ele : will define MobileElement name
	 * @param report : will define to call the reportPass and reportFail method
	 * @param logger : will define for log the test step
	 * @param String : will define to append the details in the report
	 * @param boolean : will throw Exception if this flag is set as true 
	 * @throws Exception 
	 */
	protected boolean validateFields(MobileElement ele,Reporting report,ExtentTest logger,String desc,boolean exception) throws Exception{
		boolean flag = false;
		try {
			if(isDisplayed(ele)) {
				report.reportPass(desc, logger);
				Assert.assertEquals(desc, desc);
				flag = true;
			}
			else {
				report.reportFail(desc, logger);
				Assert.fail(ele+"is not available");
			}
		}catch(Exception e) {
			report.reportFail(desc, logger);
			Assert.fail(ele+"is not available");
			e.printStackTrace();
			if(exception)
				throw e;
		}
		return flag;
	}
	/**
	 * This method is used to capture the logs and add it in the text document
	 * @param String : will define the logs
	 * @throws Exception 
	 */
	protected void captureLogs(String logTExt) throws Exception{
		try {
			File file = Reporting.returnLogtFilePath();
			fw = new FileWriter(file.getAbsolutePath(),true);
			fw.write(logTExt+"\n");
			fw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}