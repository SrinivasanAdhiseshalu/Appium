package com.amazon.testCases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.amazon.BaseDriver.IntiateDriver;
import com.amazon.Pages.AddToCart;
import com.amazon.Utilities.ExcelComplete;
import com.amazon.Utilities.Reporting;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;

public class AddToCart_Testcase {
	
	AppiumDriver<?> driver = null;
	HashMap<String,String> data;
	IntiateDriver iDriver=null;
	ExtentTest logger= null;
	Reporting report=null;
	
	@Test(dataProvider="amazon")
	public void myTest(HashMap<String,String> data) throws Exception{
		if(data.get("Testrun").equalsIgnoreCase("yes")) {
			try {
				iDriver = new IntiateDriver(data.get("deviceID"), data.get("Platform"));
				driver = iDriver.getDriver();
				report = new Reporting(driver);
				logger = report.startTest("Amazon Add To Cart");
				AddToCart cart = new AddToCart(driver, data, logger, report);
				cart.launchtoSearchScreen();
				cart.searchProduct();
				cart.addItemToCart();
				cart.validateCart();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				driver.closeApp();
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	@DataProvider(name="amazon")
	public Iterator<Object[]> getData()throws Exception{
		ArrayList<HashMap<String,String>> arrHashMap = new ArrayList<HashMap<String,String>>();
		ExcelComplete excel = new ExcelComplete();
		arrHashMap = excel.readExcel("./Files/AmazonCompleteDataSheet.xlsx","Login");
		List<Object[]> dataArr = new ArrayList<Object[]>();
		for(HashMap data:arrHashMap) {
			dataArr.add(new Object[] {
					data
			});
		}
		return dataArr.iterator();
	}
}
