package com.amazon.Pages;

import java.util.HashMap;

import org.openqa.selenium.support.PageFactory;

import com.amazon.Utilities.Reporting;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SearchProduct extends BasePage{
	
	AppiumDriver<MobileElement> driver=null;
	HashMap<String,String> data = new HashMap<String,String>();
	Reporting report=null;
	ExtentTest logger = null;
	
	@AndroidFindBy(xpath="//*[@resource-id='in.amazon.mShop.android.shopping:id/rs_search_src_text' and (contains(@text,'Search'))]")
	protected MobileElement input_SearchBox;
	
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
	public SearchProduct(AppiumDriver<MobileElement> driver, HashMap<String, String> data, Reporting report,ExtentTest logger) {
		super(data);
		this.driver = driver;
		this.data = data;
		this.logger = logger;
		this.report = report;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	/**
	 * This method is used to Search product in amazon
	 * @param NA
	 * @throws Exception 
	 */
	public void searchProduct() throws Exception{
		try {
			if(validateFields(input_SearchBox, report, logger,data.get("Input Data"),true)) {
				click(input_SearchBox);
				setText(input_SearchBox, data.get("Input Data"));
				pressKeyboardEnter();
			}
			if(validateFields(selectMask,  report, logger,"F Gear Standard Size Mask for Adult Color Black F95",true)) {
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
}
