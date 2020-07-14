package com.amazon.pages;

import java.util.HashMap;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.amazon.PageObjects.OR_Amazon;
import com.amazon.Utilities.Reporting;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class AddToCart extends OR_Amazon{
	@SuppressWarnings("rawtypes")
	AppiumDriver driver=null;
	HashMap<String,String> data = new HashMap<String,String>();
	Reporting report=null;
	ExtentTest logger = null;
	
	@SuppressWarnings("rawtypes")
	public AddToCart(AppiumDriver driver, HashMap<String,String> data,ExtentTest logger,Reporting report) {
		super(driver, data);
		this.driver = driver;
		this.data = data;
		this.logger = logger;
		this.report = report;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	public void launchtoSearchScreen() throws Exception{
		try {
			if(validateFields(chooseLang, "Language Selection",true))
				click(chooseLang);
			if(validateFields(btn_SkipSignIn, "Skip Sign IN",true))
				click(btn_SkipSignIn);
			validateFields(input_SearchBox, "input_SearchBox",true);
		}catch(Exception e) {
			System.out.println();
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void searchProduct() throws Exception{
		try {
			if(validateFields(input_SearchBox, data.get("Input Data"),true)) {
				click(input_SearchBox);
				Thread.sleep(1500);
				setText(input_SearchBox, data.get("Input Data"));
				Thread.sleep(1000);
				driver.getKeyboard().sendKeys(Keys.ENTER);
			}
			if(validateFields(selectMask, "F Gear Standard Size Mask for Adult Color Black F95",true)) {
				data.put("expected", getAttribute(selectMask, "content-desc"));
				click(selectMask);
			}
			else {
				swipeHorizontalUP(10, selectMask);
				if(isDisplayed(selectMask)) {
					report.reportPass("F Gear Standard Size Mask for Adult Color Black F95", logger);
					click(selectMask);
				}
				else {
					report.reportFail("Expected Mask", logger);
					throw new Exception();
				}
			}
			
		}catch(Exception e) {
			System.out.println();
			report.reportFail("Error in validation", logger);
			e.printStackTrace();
		}
	}
	
	public void addItemToCart() throws Exception{
		try {
			/*if(validateFields(btn_CurrentLoc, "Current Location",false))
				click(btn_CurrentLoc);
			if(validateFields(btn_UseCurrentLoc, "Use Current Location",false))
				click(btn_UseCurrentLoc);
			if(validateFields(btn_AllowOnce, "Allow Once",false))
				click(btn_AllowOnce);
			Thread.sleep(6000);*/
			swipeHorizontalUP(5, btn_AddToCart);
			if(validateFields(btn_AddToCart, "Add To Cart",true))
				click(btn_AddToCart);
			
		}catch(Exception e) {
			report.reportFail("Error in validation", logger);
			e.printStackTrace();
		}
	}
	
	public void validateCart() throws Exception{
		try {
			
			if(validateFields(btn_CartIcon, "Cart on the top",false))
				click(btn_CartIcon);
			Thread.sleep(7500);
			if(validateFields(validateMask, "Added Item",false)) {
				String expected = data.get("expected");
				String actual = getAttribute(selectMask, "content-desc");
				if(expected.contains(actual))
					report.reportPass("Mask Validation are success", logger);
				else
					report.reportFail("Mask Validation is NOT success", logger);
			}
		}catch(Exception e) {
			report.reportFail("Error in validation", logger);
			e.printStackTrace();
		}
	}
	
	boolean validateFields(MobileElement ele, String desc,boolean exception) throws Exception{
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
	
}
