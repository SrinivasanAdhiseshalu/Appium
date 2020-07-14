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
	
	AppiumDriver<MobileElement>driver=null;
	DesiredCapabilities dc=new DesiredCapabilities();
	
	
	public IntiateDriver(String deviceid,String devicePlatform) throws Exception
	{
		try
		{
			if(devicePlatform.equalsIgnoreCase("android"))
			{
				dc.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
				dc.setCapability("newCommandTimeout",60 * 5);
				dc.setCapability("appPackage","in.amazon.mShop.android.shopping" );
				dc.setCapability("appActivity","com.amazon.mShop.home.HomeActivity" );
				dc.setCapability("platformName", "ANDROID");
				dc.setCapability("deviceName",deviceid);
				dc.setCapability("automationName", "uiautomator2");
				
				driver=new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"),dc);
			}
			else if(devicePlatform.equalsIgnoreCase("ios"))
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
				
			}
		}
		catch(Exception e ){
			e.printStackTrace();
			throw e;	
		}
	
	}
	
	public AppiumDriver<?> getDriver()
	{
		return driver;	
	}
	
	
}