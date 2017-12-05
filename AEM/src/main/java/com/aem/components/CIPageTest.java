package com.aem.components;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.junit.*;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
//import org.junit.experimental.categories.Category;
import org.openqa.selenium.WebDriver;
//import org.testng.annotations.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.qas.qtest.api.services.execution.model.AutomationTestStepLog;

import com.aem.utilities.CommonLib;
import com.aem.pages.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.runner.Description; 
import org.junit.runner.Result; 
import org.junit.runner.notification.Failure; 
import org.junit.runner.notification.RunListener; 

	
	public class CIPageTest extends CommonLib {
		
		WebDriver driver;
		HomePage HomePage;
		//ExtentReports extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/STMExtentReport.html", true);
		//ExtentTest Logger = extent.startTest("passTest");
		//Result result;
		//List<AutomationTestStepLog> log = new ArrayList<AutomationTestStepLog>();
		static ExtentReports report = new ExtentReports("C:\\Report\\Test.html");
		ExtentTest logger; 
		
		public static JSONObject config;
		public static String testrunID;
		
		@BeforeClass
		public static void Setup() throws ParseException, IOException
		{	
			
			config = getConfiguration();	
			System.out.println("testrunID" +config);
			testrunID = (String) config.get("Id");	
			System.out.println("testrunID" +testrunID);
		}
		
		
		public void OpenBrowser()  {					
			
			driver = getDriver("chrome", logger);			
		
		}
		
		
		
		@Test
		public void SampleTest1() throws SQLException, InterruptedException, IOException, ParseException {
			//extent.startTest("TC01.1","This test is a positive login test for ParaBank");
			try
			{
				logger=report.startTest("TestCase1");//			
				OpenBrowser();	
				Assert.assertEquals(1, 1);
				HomePage objHP = new HomePage();
				ClassSchedules objCS = new ClassSchedules();
				//List<Employee> emp = retriveValueFromDataBase();	
				/*Assert.assertTrue(ElementExist(objHP.LnkClass(),logger));	
				Assert.assertTrue(ElementClick(objHP.LnkClasses(),logger));		
				Assert.assertTrue(ElementClick(objHP.LnkAllClasses(), logger));
				Assert.assertTrue(ElementClick(objCS.LnkStudioCycle(), logger));*/
				Assert.assertTrue(ElementExist(objHP.LnkClass(),config));	
				
				CloseBrowser();	
			}
			catch(Exception e)
			{
				report.endTest(logger);		
			}
					
		}
		
		/*@Test
		public void SampleTest2() throws SQLException, InterruptedException {
			//extent.startTest("TC01.1","This test is a positive login test for ParaBank");
		try
		{
			logger=report.startTest("Testcase2");
			OpenBrowser();		
			Assert.assertEquals(1, 1);
			HomePage objHP = new HomePage();
			//List<Employee> emp = retriveValueFromDataBase();	
			Assert.assertTrue(ElementClick(objHP.LnkClasses(), logger));		
			Assert.assertTrue(ElementClick(objHP.LnkAllClasses(), logger));		
			CloseBrowser();	
		}
		catch(Exception e)
		{
			report.endTest(logger);		
		}
			
		}*/

		@After
		public void CloseBrowser() {				
			//driver.get("C:\\Report\\LearnAutomation.html");
			logger.log(LogStatus.PASS, "verify Browserclose" ,"Browser is closed successfully");
			report.endTest(logger);		
			driver.quit();

			
		}
		
		
		
		@AfterClass
		public static void SaveReport() {				
			//driver.get("C:\\Report\\LearnAutomation.html");	
			/*try {
				//uploadReport();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			report.flush();
			report.close();
			System.out.println("text");
		}

	}

	
