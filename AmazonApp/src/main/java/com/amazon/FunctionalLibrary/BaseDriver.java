package com.amazon.FunctionalLibrary;

import java.io.File;
import java.io.FileInputStream;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.amazon.Utilities.Reporting;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public abstract class BaseDriver {
	public AppiumDriver<?> driver;
	Map<String,Object> parms=new HashMap<>();
	int swipeDuration=0,timeOut=30;
	HashMap<String ,String> data;
	protected String platform= "";
	
	//Constructor
	protected BaseDriver(AppiumDriver<?> driver,HashMap<String,String>data)
	{
		this.driver=driver;
		this.data=data;
		platform=data.get("Platform");
		if(platform.equalsIgnoreCase("Android"))
			swipeDuration=1000;
		else
			swipeDuration=100;
	}
	
	//This method is used to click the element
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

	//This method is used to check the element is Displayed
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
	
	//This method is used to check the element is Displayed with dynamic time - Polymorphism
		protected boolean isDisplayed(MobileElement element,int time){
			try{
				new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOf(element));
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
		
	//This method is used to get a particular attribute for the specified element
	protected String getAttribute(MobileElement element,String propertyName){
		try {
			return element.getAttribute(propertyName);
		}catch (Exception e) {
		return "Object Not Found No value is  retrived";
		}
	}
	//This method is used to get a text for the specified element
	protected String getText(MobileElement element){
		try {
			return element.getText();
		}
		catch (Exception e) {
			return "Object Not Found -No text retrived";
		}
	}
	
	//This method is used to set the text for the specified element
	protected void setText(MobileElement element,String value)throws InterruptedException{
		try{
			if(isDisplayed(element))
			{
				element.sendKeys(value);
				Thread.sleep(2000);
			}
		else
		{
			System.out.println("Element not found for SetText");
		}
	}
		catch (Exception e1	) {
			e1.printStackTrace();
		}
	}
	
	///This method is used to swipe horizontal UP and break the loop using isDisplayed
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
				
				Thread.sleep(1000);
				if(isDisplayed(element))
					break;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//This method is used to swipe horizontal Down and break the loop using isDisplayed
	protected void swipeHorizontalDown(int swipeCount, MobileElement element) throws Exception{
		try {
			for(int i=1;i<=swipeCount;i++) {
				Dimension d = driver.manage().window().getSize();
				int width = d.getWidth();
				int height = d.getHeight();
				
				int middle = width/2;
				int startPoint = (int) (height*0.3);
				int endPoint = (int) (height*0.7);
				
				new TouchAction(driver)
				.press(PointOption.point(middle, startPoint))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(swipeDuration)))
				.moveTo(PointOption.point(middle, endPoint))
				.release().perform();
				
				Thread.sleep(1000);
				if(isDisplayed(element))
					break;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//This method is used to clear the text
	protected void clearText(MobileElement element){
		try{
			if(element.isDisplayed())
			element.clear();
		}
		catch (Exception e) {
			System.out.println("The Element is not clear due to no such element found");
		}
		
	}
	
	//This Method returns the value of the corresponding key provided in the parameter
	@SuppressWarnings("rawtypes")
	public String getValueFromPropertyFile(String propertyFile,String key) throws Exception{
		String value="";
		try {
			if(propertyFile.contains(".properties"))
				propertyFile = propertyFile.replace(".properties", "");// if a tester provides the property file with Extension this will remove the Extension
																	   // In order to avoid Exceptions
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			Properties properties = new Properties();
			File file = new File("./Files/"+propertyFile+".properties");
			FileInputStream reader=new FileInputStream(file); 
			properties.load(reader);
			Enumeration arr = properties.keys();
			while(arr.hasMoreElements()){
				if(arr.nextElement().toString().equalsIgnoreCase(key)){
					value = properties.getProperty(key);
					break;
				}
			}
		}catch(Exception e) {
			throw new Exception("The given Key "+key+" is Not found in the OR");
		}
		return value;
	}
	
	//This method is used to press Enter key in keyboard	
	protected void pressKeyboardEnter() throws Exception{
		try {
			driver.getKeyboard().sendKeys(Keys.ENTER);
		}catch(Exception e) {
			System.out.println("Press Enter using Keyboard is not working");
		}
	}
	
	//This method is used to hide keyboard in android	
	protected void Minimizekeyboard()throws Exception{
		try{
			driver.hideKeyboard();
		}
		catch (Exception e) {			
			System.out.println("The hidekeyboard button for ios is not working");
		}
	}

	//This method is used to get the context of the page Encapsulation
	protected String getContext(){
		return driver.getContext();
	}
		

	//This method is used to set the context of the page Encapsulation
	protected void SetContext(String context){
		driver.context(context);
	}	
}