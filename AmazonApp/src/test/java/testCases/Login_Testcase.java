package testCases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.amazon.BaseDriver.IntiateDriver;
import com.amazon.Utilities.ExcelComplete;
import com.amazon.Utilities.Reporting;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;

public class Login_Testcase {
	
	AppiumDriver<?> driver = null;
	HashMap<String,String> data;
	IntiateDriver iDriver=null;
	ExtentTest logger= null;
	Reporting report=null;
	
	@Test(dataProvider="readerLogin")
	public void myTest(HashMap<String,String> data) throws Exception{
		iDriver = new IntiateDriver("", data.get("Platform"));
		driver = iDriver.getDriver();
		report = new Reporting(driver);
		logger = report.startTest("Login flow");
		//Login log = new Login(driver, data, logger, report);
	}
	
	@SuppressWarnings("rawtypes")
	@DataProvider(name="readerLogin")
	public Iterator<Object[]> getData()throws Exception{
		ArrayList<HashMap<String,String>> arrHashMap = new ArrayList<HashMap<String,String>>();
		ExcelComplete excel = new ExcelComplete();
		arrHashMap = excel.readExcel("./Files/RM_App_Login.xlsx","Login");
		List<Object[]> dataArr = new ArrayList<Object[]>();
		for(HashMap data:arrHashMap) {
			dataArr.add(new Object[] {
					data
			});
		}
		return dataArr.iterator();
	}
}
