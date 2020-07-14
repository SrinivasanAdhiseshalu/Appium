package com.amazon.BaseDriver;

import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class IntiateDriver {
	
	public static HashMap<String,String>data=null;
	AppiumDriver<?>driver=null;
	DesiredCapabilities dc=new DesiredCapabilities();
	
	
	public IntiateDriver(String deviceid,String devicePlatform) throws Exception
	{
		
		try
		{
				//DesiredCapabilities dc=new DesiredCapabilities();
				if(!deviceid.contentEquals(""))
					dc.setCapability("appium:version", deviceid);
				if(devicePlatform.equalsIgnoreCase("android"))
				{
				dc.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
				dc.setCapability("newCommandTimeout",60 * 5);
				dc.setCapability("appPackage","com.google.android.apps.messaging" );
				dc.setCapability("appActivity","com.google.android.apps.messaging.ui.ConversationListActivity" );
				dc.setCapability("appium:platform", "ANDROID");
				dc.setCapability("deviceName", "Android Simulator");
				dc.setCapability("automationName", "uiautomator2");
				
				driver=new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"),dc);
			}
			else if(devicePlatform.equalsIgnoreCase("ios"))
			{
				dc.setCapability(MobileCapabilityType.AUTOMATION_NAME,"XCUITest");
				dc.setCapability("appium:platform", "MAC");
				dc.setCapability("platformName", "ios");
				dc.setCapability("deviceName", "iPhone simulator");
				dc.setCapability("bundleId", "com.hidglobal.pacs.readermanager");
				dc.setCapability("appName", "Reader Manager");
				dc.setCapability("noReset", true);
				dc.setCapability("useNewWDA", true);
				dc.setCapability("autoLaunch", false);
				dc.setCapability("platformVersion", "12.0");
				
					}
				}
		catch(Exception e )
		{
		e.printStackTrace();
		throw e;	
		}
	
	}
	
	public AppiumDriver<?> getDriver()
	{
		return driver;	
	}
	
	
}