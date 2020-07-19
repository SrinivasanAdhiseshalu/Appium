package com.amazon.testCases;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.amazon.PageObjects.BasePage;
import com.amazon.Pages.AddToCart;
import com.amazon.Pages.LoginPage;
import com.amazon.Pages.SearchProduct;
import com.amazon.Utilities.ExcelDataExtraction;
import com.amazon.Utilities.Reporting;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class AddToCart_Testcase {
	
	AppiumDriver<MobileElement> driver = null;
	HashMap<String,String> data;
	ExtentTest logger= null;
	Reporting report=null;
	BasePage basepage=null;
	
	@BeforeTest
	public void beforeTest() throws Exception{
		ArrayList<HashMap<String,String>> arrHashMap = new ArrayList<HashMap<String,String>>();
		ExcelDataExtraction excel = new ExcelDataExtraction();
		arrHashMap = excel.readExcel(BasePage.getValueFromPropertyFile("Config","Excelpath"),"Login");
		for(HashMap<String,String> data:arrHashMap) {
			if(data.get("Testrun").equalsIgnoreCase("yes") && data.get("TestCase Name").equalsIgnoreCase("add to cart")) {
				this.data = data;
				basepage = new BasePage(data);
				break;
			}
		}
		driver = basepage.initiateDriver();
		report = new Reporting(driver);
		logger = report.startTest("Amazon Add To Cart");
	}
	
	@Test(dataProvider="amazon")
	public void test(HashMap<String,String> data) throws Exception{
		if(data.get("Testrun").equalsIgnoreCase("yes") && data.get("TestCase Name").equalsIgnoreCase("add to cart")) {
			try {
				AddToCart cart = new AddToCart(driver, data, logger, report);
				LoginPage login = new LoginPage(driver, data, report, logger);
				SearchProduct searchProduct = new SearchProduct(driver, data, report, logger);
				login.launchtoSearchScreen();
				searchProduct.searchProduct();
				cart.addItemToCart();
				cart.validateCart();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	
	}
	
	@AfterTest
	public void afterTest() throws Exception{
		driver.closeApp();
		driver.close();
	}
	
	@SuppressWarnings("rawtypes")
	@DataProvider(name="amazon",parallel = false)
	public Iterator<Object[]> getData()throws Exception{
		ArrayList<HashMap<String,String>> arrHashMap = new ArrayList<HashMap<String,String>>();
		ExcelDataExtraction excel = new ExcelDataExtraction();
		arrHashMap = excel.readExcel(BasePage.getValueFromPropertyFile("Config","Excelpath"),"Login");
		List<Object[]> dataArr = new ArrayList<Object[]>();
		for(HashMap data:arrHashMap) {
			dataArr.add(new Object[] {
					data
			});
		}
		return dataArr.iterator();
	}
}
