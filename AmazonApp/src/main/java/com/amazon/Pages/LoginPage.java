package com.amazon.Pages;

import java.util.HashMap;

import org.openqa.selenium.support.PageFactory;

import com.amazon.PageObjects.BasePage;
import com.amazon.Utilities.Reporting;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPage extends BasePage{
	AppiumDriver<MobileElement> driver=null;
	HashMap<String,String> data = new HashMap<String,String>();
	Reporting report=null;
	ExtentTest logger = null;
	
	@AndroidFindBy(xpath="//android.widget.GridLayout/descendant::android.widget.ImageView[1]")
	protected MobileElement chooseLang;
	
	@AndroidFindBy(xpath="//android.widget.Button[contains(@text,'Skip sign in')]")
	protected MobileElement btn_SkipSignIn;
	
	@AndroidFindBy(xpath="//*[@resource-id='in.amazon.mShop.android.shopping:id/sign_in_button']")
	protected MobileElement btn_SignIn;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LoginPage(AppiumDriver driver, HashMap<String,String> data,Reporting report,ExtentTest logger) {
		super(data);
		this.driver = driver;
		this.data = data;
		this.logger = logger;
		this.report = report;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	/**
	 * This method is used for login or skip login
	 * @param NA
	 * @throws Exception 
	 */
	public void launchtoSearchScreen() throws Exception{
		try {
			if(validateFields(chooseLang,  report, logger,"Language Selection",true))
				click(chooseLang);
			if(validateFields(btn_SkipSignIn,  report, logger,"Skip Sign IN",true))
				click(btn_SkipSignIn);
		}catch(Exception e) {
			System.out.println();
			e.printStackTrace();
		}
	}
}
