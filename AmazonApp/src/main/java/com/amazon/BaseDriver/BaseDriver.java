package com.amazon.BaseDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
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

public abstract class BaseDriver<File> {
	public AppiumDriver<?> driver;
	Map<String,Object> parms=new HashMap<>();
	int height=0;int width=0;
	Dimension dimension;
	int timeout=90,swipeDuration=0;
	HashMap<String ,String> data;
	protected String platform= "";
	
	protected BaseDriver(AppiumDriver<?> driver,HashMap<String,String>data)
	{
		this.driver=driver;
		dimension=driver.manage().window().getSize();
		height=dimension.height;
		width=dimension.width;
		this.data=data;
		platform=data.get("Platform");
		if(platform.equalsIgnoreCase("Android"))
			swipeDuration=1000;
		else
			swipeDuration=100;
	}
	protected void click(MobileElement element)throws Exception
	{
		MobileElement ele=element;
		try
		{
			new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(ele));
		}
		catch(Exception e)
		{
			throw new Exception("Click failed for element in page");
		}
		ele.click();
	}

	//This method is used to check the element is Displayed
	protected boolean isDisplayed(MobileElement element)
	{
		try
		{
			new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(element));
			return element.isDisplayed();
		}
		catch(TimeoutException e){
			return false;
		}
		catch(ElementNotVisibleException e) {
			return false;
		}
		catch(NoSuchElementException e){
			return false;
		}		
	}
		
	//This method is used to get a particular attribute for the specified element
	protected String getAttribute(MobileElement element,String propertyName)
	{
		try {
			return element.getAttribute(propertyName);
		}catch (Exception e) {
		return "Object Not Found No value is  retrived";
		}
	}
	//This method is used to get a text for the specified element
	protected String getText(MobileElement element)
	{
		try {
			return element.getText();
		}
		catch (Exception e) {
			return "Object Not Found -No text retrived";
		}
	}
	
	//This method is used to set the text for the specified element
	protected void setText(MobileElement element,String value)throws InterruptedException
	{
		try
		{
			if(isDisplayed(element))
			{
			element.sendKeys(value);
			Thread.sleep(10000);
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
	
	protected void clearText(MobileElement element)
	{
		try
		{
			if(element.isDisplayed())
			element.clear();
		}
		catch (Exception e) {
			
			System.out.println("The Element is not clear due to no such element found");
		}
		
		}
	
	
		//This method is used to hide keyboard in android	
	protected void Minimizekeyboard()throws Exception
	{
		try
		{
			driver.hideKeyboard();
			
		}
		catch (Exception e) {
			
		System.out.println("The hidekeyboard button for ios is not working");
		}
	}

		//This method is used to get the context of the page
	protected String getContext()
	{
		return driver.getContext();
	}
		

	//This method is used to set the context of the page
	protected void SetContext(String context)
	{
		driver.context(context);
	}	
}