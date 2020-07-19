package com.amazon.Pages;

import java.util.HashMap;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.amazon.Utilities.Reporting;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class AddToCart extends BasePage{
	
	@SuppressWarnings("rawtypes")
	AppiumDriver driver=null;
	HashMap<String,String> data = new HashMap<String,String>();
	Reporting report=null;
	ExtentTest logger = null;
	
	@AndroidFindBy(xpath="//android.widget.Button[@resource-id='add-to-cart-button']")
	protected MobileElement btn_AddToCart;
	
	@AndroidFindBy(xpath="//*[@resource-id='in.amazon.mShop.android.shopping:id/action_bar_cart']")
	protected MobileElement btn_CartIcon;
	
	@AndroidFindBy(xpath="//android.view.View[contains(@content-desc,'F Gear Standard Size Mask for Adult Color Black F95')]")
	protected MobileElement validateMask;
	
	@AndroidFindBy(xpath="//android.view.View[@resource-id='search']/descendant::android.view.View[contains(@content-desc,'F Gear Standard Size Mask for Adult Color Black F95 Safeguard 7 layer')]")
	protected MobileElement selectMask;
	
	/**
	 * This is Constructor
	 * @param AppiumDriver : will define the driver object
	 * @param HashMap<String,String> : used to get the data from the excel
	 * @param Reporting : will define the pass and fail step in report
	 * @param logger : is used to log the test step in reporting
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public AddToCart(AppiumDriver driver, HashMap<String,String> data,ExtentTest logger,Reporting report) {
		super(data);
		this.driver = driver;
		this.data = data;
		this.logger = logger;
		this.report = report;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	/**
	 * This method is used to Add an item to cart in amazon method
	 * @param NA
	 * @throws Exception 
	 */
	public void addItemToCart() throws Exception{
		try {
			swipeHorizontalUP(5, btn_AddToCart);
			if(validateFields(btn_AddToCart, report, logger, "Add To Cart",true))
				click(btn_AddToCart);
			
		}catch(Exception e) {
			report.reportFail("Error in validation", logger);
			e.printStackTrace();
		}
	}
	
	/**
	 * validate cart in amazon method
	 * @param NA
	 * @throws Exception 
	 */
	public void validateCart() throws Exception{
		try {
			
			if(validateFields(btn_CartIcon,  report, logger,"Cart on the top",false))
				click(btn_CartIcon);
			if(validateFields(validateMask,  report, logger,"Added Item",false)) {
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
}
