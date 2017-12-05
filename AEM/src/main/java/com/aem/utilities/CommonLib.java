package com.aem.utilities;

import junit.framework.Assert;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.qas.api.ClientConfiguration;
import org.qas.qtest.api.auth.QTestCredentials;
import org.qas.qtest.api.services.attachment.model.Attachment;
import org.qas.qtest.api.services.design.TestDesignService;
import org.qas.qtest.api.services.design.TestDesignServiceClient;
import org.qas.qtest.api.services.design.model.ListTestCaseRequest;
import org.qas.qtest.api.services.design.model.TestCase;
import org.qas.qtest.api.services.execution.TestExecutionService;
import org.qas.qtest.api.services.execution.TestExecutionServiceClient;
import org.qas.qtest.api.services.execution.model.AutomationTestLog;
import org.qas.qtest.api.services.execution.model.AutomationTestLogRequest;
import org.qas.qtest.api.services.execution.model.AutomationTestStepLog;
import org.qas.qtest.api.services.execution.model.GetTestRunRequest;
import org.qas.qtest.api.services.execution.model.ListTestRunRequest;
import org.qas.qtest.api.services.execution.model.TestLog;
import org.qas.qtest.api.services.execution.model.TestRun;
import org.qas.qtest.api.services.project.ProjectService;
import org.qas.qtest.api.services.project.ProjectServiceClient;
import org.qas.qtest.api.services.project.model.ListProjectRequest;
import org.qas.qtest.api.services.project.model.Project;
//import org.qas.qtest.api.auth.PropertiesQTestCredentials;
//import org.qas.qtest.api.auth.QTestCredentials;
//import org.qas.qtest.api.services.design.TestDesignServiceClient;
//import org.qas.qtest.api.services.design.model.CreateTestCaseRequest;
//import org.qas.qtest.api.services.design.model.TestCase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



//import com.relevantcodes.extentreports.ExtentReports;
//import com.relevantcodes.extentreports.ExtentTest;
//import com.relevantcodes.extentreports.LogStatus;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.*;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CommonLib{
	public static WebDriver driver;
	static WebDriverWait wait;
	static File f1 = new File("./resources/Configuration/Configuration.json");
	static String objfile = "./resources/objRepo/AEM.xml";
	
	private static XSSFSheet xlWSheet;	 
	private static XSSFWorkbook xlWBook;
	private static XSSFCell cell;
	private static XSSFRow row;
	protected static List<AutomationTestStepLog> testStepLogs = new ArrayList<AutomationTestStepLog>();
	
//	static ExtentTest Logger = extent.startTest("passTest");
	public static WebDriver getDriver(String config, ExtentTest logger)  {
		JSONObject jsonObject = JSONReadFromFile();
		//String browser = (String) jsonObject.get("browser");
		//String[] arrconfig = config.split("+",1);
		String browser = config.trim();
		File f = new File("./resources/drivers");
		System.out.println("browser" +config);
		if (browser.toLowerCase().equals("chrome")) {			
			System.setProperty("webdriver.chrome.driver", f.getAbsolutePath() + "/chromedriver.exe");
			driver = new ChromeDriver();

		} else if (browser.toLowerCase().equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", f.getAbsolutePath() + "/geckodriver.exe");
			driver = new FirefoxDriver();

		} else if (browser.toLowerCase().equals("ie")) {
			System.setProperty("webdriver.ie.driver", f.getAbsolutePath() + "/IEDriverServer.exe");
			driver = new InternetExplorerDriver();

		}else if (browser.toLowerCase().equals("windows 7 chrome"))
		{
			
			DesiredCapabilities capability = new DesiredCapabilities();
            capability.setCapability("browser", "Chrome");
            capability.setCapability("browser_version", "34.0");
            capability.setCapability("os", "windows");
            capability.setCapability("os_version", "7");
            capability.setCapability("build", "qtestPOC");
            capability.setCapability("browserstack.local", "true"); 
            capability.setCapability("browserstack.debug", "true"); 
            capability.setCapability("browserstack.user", "rekhabhupatiraju1");// add username
            capability.setCapability("browserstack.key", "5dnGrykYjvZU5BqpKE9q"); //add automate-key

            try {
				driver = new RemoteWebDriver(new URL("http://hub-cloud.browserstack.com/wd/hub/"), capability);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				
			}
		}
			
			
		
		try
		{
			driver.manage().window().maximize();
			driver.get(getdata("URL"));
			logger.log(LogStatus.PASS, "Open Browser" , config + " browser is opened successfully with " +getdata("URL"));
		}catch(Exception e)
		{
				logger.log(LogStatus.FAIL, "Open Browser",  "Browser is not opened");	
		}
		return driver;
	}
	
	
	public void printFailures(Result result) throws Exception { 
		
        List<Failure> failures = result.getFailures(); 
        if (failures.size() == 0) { 
            return; 
        } 
        if (failures.size() > 1) { 
        	getScreenhot(driver, "StepFailure");
        } 
        
        	        
    } 
	public static String getScreenhot(WebDriver driver, String screenshotName) throws Exception {
		
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
                //after execution, you could see a folder "FailedTestsScreenshots" under src folder
		String destination = "c:/Report/"+screenshotName+dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}
	
	public static String getdata(String Param ) throws IOException
	{
		//InputStream xlFiletoRead = new FileInputStream("C:/eclipse-java-oxygen-R-win32-x86_64/eclipse/Lifetime/AEM/resources/TestData/AEMTestData.xls");
		String value = "";
		try
		{
			File xlfile = new File("./resources/TestData/AEMTestData.xlsx");
			FileInputStream inputStream = new FileInputStream(xlfile);
			xlWBook = new XSSFWorkbook(inputStream);
			xlWSheet = xlWBook.getSheet("TestData");
			
		
		
			int rowCount = xlWSheet.getLastRowNum()-xlWSheet.getFirstRowNum();
			/*List<String> ParamName =new LinkedList<String>();//creating linkedlist
			List<String> ParamVal =new LinkedList<String>();//creating linkedlist 
			ArrayList<String> obj = new ArrayList<String>();*/
			int col_num = -1;
			for (int i = 0; i < rowCount+1; i++)
			{
				row = xlWSheet.getRow(i);				
				for (int j = 0; j < 1; j++)
				{					
					if(row.getCell(j).getStringCellValue().equalsIgnoreCase(Param))
						
					value = row.getCell(j+1).getStringCellValue();
					break;
				}
			}
			
		}catch(Exception e)
		{
			
		}
		/*Iterator rows = xlWSheet.rowIterator();
		while (rows.hasNext())		{
			Row=(XSSFRow) rows.next();
			Iterator cells = Row.cellIterator();			
			while (cells.hasNext())
			{
				Cell=(XSSFCell) cells.next();				
				System.out.print(Cell.getStringCellValue()+" ");
				
			}		
		}*/
		return value;
		
	}
	public static JSONObject getConfiguration() throws IOException, ParseException
	{
		String config = null;
		JSONObject tr = null;
		Map<String, String> env = System.getenv();
		for (String envName : env.keySet()) {
			System.out.format("%s=%s%n", envName, env.get("QTE_SCHEDULED_TX_DATA"));
		}
		String url = env.get("QTE_SCHEDULED_TX_DATA");
		//String url = "http://localhost:6789/job-detail/161";
		System.out.println("url : " + url);
		URL obj;
		try {
			obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");		
			con.setDoOutput(true);			
			try {
				InputStream response = null;		    	
				response = con.getInputStream();
				System.out.println("response" +response);
				// print resulting stream
				BufferedReader buffer = new BufferedReader(new InputStreamReader(response));
				String inputLine;
				//while ((inputLine = buffer.readLine()) != null)
				inputLine = buffer.readLine();
				System.out.println("inputLine" +inputLine);
				Object Obj = new JSONParser().parse(inputLine);	
					
			     JSONObject jso = (JSONObject) Obj;	
			     JSONObject qte = (JSONObject)jso.get("QTE");
			     JSONArray  testrun = (JSONArray)qte.get("testRuns");
			     tr = (JSONObject)testrun.get(0);
			    // config = (String) tr.get("Browser Configuration");		
			     if((String) tr.get("Browserstack")!=null)
			     {
			    	 config =(String) tr.get("Browserstack");
			    	 System.out.println("browserstack" +config);
			     }else if((String) tr.get("Browser Configuration")!=null)
			     {
			    	 config =(String) tr.get("Browser Configuration");
			     }			   
			     System.out.println("Browserstack" +config);
			     
				buffer.close();
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return config;
		return tr;
	}
	
	public static String uploadReport() throws IOException, ParseException
	{
		String config = null;
		Map<String, String> env = System.getenv();
		for (String envName : env.keySet()) {
			System.out.format("%s=%s%n", envName, env.get("QTE_SCHEDULED_TX_DATA"));
		}
		String url = env.get("QTE_SCHEDULED_TX_DATA");
		//String url = "http://localhost:6789/job-detail/161";
		System.out.println("url : " + url);
		URL obj;
		try {
			obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");		
			con.setDoOutput(true);			
			try {
				InputStream response = null;		    	
				response = con.getInputStream();
				System.out.println("response" +response);
				// print resulting stream
				BufferedReader buffer = new BufferedReader(new InputStreamReader(response));
				String inputLine;
				//while ((inputLine = buffer.readLine()) != null)
				inputLine = buffer.readLine();
				System.out.println("inputLine" +inputLine);
				Object Obj = new JSONParser().parse(inputLine);	
					
			     JSONObject jso = (JSONObject) Obj;	
			     JSONObject qte = (JSONObject)jso.get("QTE");
			     JSONArray  testrun = (JSONArray)qte.get("testRuns");
			     JSONObject tr = (JSONObject)testrun.get(0);
			    config = (String) tr.get("Id");	
			    Long testrunID = Long.parseLong(config);
			    
			    ClientConfiguration configuration = SdkSampleUtils.createConfiguration();
				   
			    System.out.println("configuration " +configuration.getProperties());
			    QTestCredentials credentials = SdkSampleUtils.getCredentials();
			     // list project.
			     ProjectService projectService = new ProjectServiceClient(credentials, configuration);
			     List<Project> projects = projectService.listProject(new ListProjectRequest());
			   
			   /*  System.out.println("Listing all project on your site: " + projects);
			     if (projects == null || projects.isEmpty()) {
			       System.out.println("No project found.");
			     } else {
			       for (Project project : projects) {
			         System.out.println("Project:");
			         System.out.println(project);
			       }
			     }*/
			     String project = SdkSampleUtils.getProjectId();
			     Long projectId = Long.parseLong(project);
			     TestExecutionService testExecutionService = new TestExecutionServiceClient(credentials, configuration);
			     System.out.println("BEGIN list all test-runs in project ...");
			     try {
			       List<TestRun> testRuns = testExecutionService.listTestRun(new ListTestRunRequest().withProjectId(projectId));
			       System.out.println("Listing all test run in project: " + projectId + ". We only display first 10 of test-runs" +testRuns.size());
			       if (testRuns == null || testRuns.isEmpty()) {
			         System.out.println("No test run in project");
			       } else {
			         int count = 0;
			         for (TestRun testRun : testRuns) {
			           System.out.println("TestRun: ===========================");
			           System.out.println(testRun);
			           count++;
			           if (count == 10) break;
			         }
			       }
			     } finally {
			       System.out.println("END list all test-runs in project ...");
			     }
			     List<TestRun> testRuns = testExecutionService.listTestRun(new ListTestRunRequest().withProjectId(projectId));
			     TestRun testRun = testExecutionService.getTestRun(
			    	        new GetTestRunRequest()
			    	          .withProjectId(projectId)
			    	          .withTestRunId(testrunID)
			    	          .withExpandTestCase(true)
			    	      );
			     
			     System.out.println("testRun " + testRun);
			     System.out.println("testRuns.get(0).getId() " + testRuns.get(0).getId());
			     System.out.println("BEGIN submit automation test log ...");
			     List<AutomationTestStepLog> testStepLogs = new ArrayList<AutomationTestStepLog>();
			     
			     testStepLogs.add(
			    	        new AutomationTestStepLog()
			    	          .withDescription("test step 1")
			    	          .withExpected("result 1")
			    	          .withActualResult("result 5")
			    	          .withStatus("FAIL")
			    	      );

			    	      testStepLogs.add(
			    	        new AutomationTestStepLog()
			    	          .withDescription("test step 2")
			    	          .withExpected("result 2")
			    	          /*.withAttachments(Collections.singletonList(
			    	            new Attachment()
			    	              .withName("test_step_2.txt")
			    	              .withContentType("text/plain")
			    	              .withData(new ByteArrayInputStream("Test Step step attachment content".getBytes()))
			    	          ))*/
			    	          .withStatus("PASS")
			    	      );
			     try {
			    	 TestLog testLog = testExecutionService.submitAutomationTestLog(
			    		        new AutomationTestLogRequest()
			    		          .withProjectId(projectId)
			    		          .withTestRunId(testrunID)
			    		          .withSuitePerDay(false)
			    		          .withAutomationTestLog(
			    		            new AutomationTestLog()
			    		              .withExecutionStartDate(new Date())
			    		              .withExecutionEndDate(new Date())
			    		              //.withName("AutomationTestLog")
			    		              //.withAutomationContent("<test>AutomationContent</test>")
			    		              .withStatus("PASS")
			    		              //.withSystemName("TestNG")
			    		              .withTestStepLogs(testStepLogs)
			    		          )
			    		      );
			             
			       

			       System.out.println("TESTLOG result ========================");
			       System.out.println(testLog);
			     } catch (Exception ex) {
			       ex.printStackTrace();
			     } finally {
			       System.out.println("END submit automation test log ...");
			     } 
			     	
			     // view all test run.
			    
			     
				buffer.close();
			//submitAutomationTestLogWithTestSteps(testExecutionService, projectId,false);
			   
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	     // get project id.
	    // Long projectId = SdkSampleUtils.getProjectId();
	   //  Long projectId = Long.parseLong(project);
	     // create automation test log and list all test runs.
	     /*TestDesignService testDesignService = new TestDesignServiceClient(credentials,configuration);	 
	    
	     List<TestCase> testCases = testDesignService.listTestCase(new ListTestCaseRequest().withProjectId(projectId));
	     System.out.println("testCases"+testCases.size());*/
	    // TestExecutionService testExecutionService = new TestExecutionServiceClient(credentials, configuration);

	     // view all test run.
	     
	     
	
		return config;
	
	}
	
	
	
	protected static void submitAutomationTestLogWithTestSteps(Long  testRunID,List<AutomationTestStepLog> testStepLogs) {
		System.out.println("BEGIN submit automation test log with test steps...");
		try {
			// create input stream.
			// create list of test step log.
			
					
			  ClientConfiguration configuration = SdkSampleUtils.createConfiguration();
			   
			    System.out.println("configuration " +configuration.getProperties());
			    QTestCredentials credentials = SdkSampleUtils.getCredentials();
			     // list project.
			   
			     String project = SdkSampleUtils.getProjectId();
			     Long projectId = Long.parseLong(project);
			     TestExecutionService testExecutionService = new TestExecutionServiceClient(credentials, configuration);			   
			     System.out.println("testStepLogs submit start ");   
			    	 TestLog testLog = testExecutionService.submitAutomationTestLog(
			    		        new AutomationTestLogRequest()
			    		          .withProjectId(projectId)
			    		          .withTestRunId(testRunID)
			    		          .withSuitePerDay(false)
			    		          .withAutomationTestLog(
			    		            new AutomationTestLog()
			    		              .withExecutionStartDate(new Date())
			    		              .withExecutionEndDate(new Date())
			    		              //.withName("AutomationTestLog")
			    		              //.withAutomationContent("<test>AutomationContent</test>")
			    		              .withStatus("PASS")
			    		              //.withSystemName("TestNG")
			    		              .withTestStepLogs(testStepLogs)
			    		          )
			    		      );
			    	 System.out.println("testStepLogs is submitted ");    

			
		} catch (Exception ex) {
			//ex.printStackTrace();
			System.out.println("testStepLogs is not submitted ");
		} finally {
			//System.out.println("END submit automation test log with test steps ...");
		}
	}

	
	public static void addautolog(JSONObject config,String Status, String Desc, String ExpRes, String ActRes)
	{
		 
		Long testrunID = (Long) config.get("Id");	
	     testStepLogs.add(
	    	        new AutomationTestStepLog()
	    	          .withDescription(Desc)
	    	          .withExpected(ExpRes)
	    	          .withActualResult(ActRes)
	    	          .withStatus(Status)
	    	      );
	     System.out.println("testStepLogs is added " + testStepLogs.size());
	     
	     submitAutomationTestLogWithTestSteps(testrunID,testStepLogs );
	    	     
	}
	public static boolean ElementVisible(WebElement element, int time, ExtentTest logger) {
		boolean flag = false;
		try {
			if(element != null)
			{				
				flag = true;
				logger.log(LogStatus.PASS, "Verify Element Visible", element.getText() + " is visible");
			}
		} catch (Exception e) {			
			logger.log(LogStatus.FAIL, "Verify Element Visible", "Element is not visible");
		}
		return flag;
	}
	
	public static boolean ElementNotVisible(WebElement element, int time, ExtentTest logger) {
		boolean flag = false;
		try {
			wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.visibilityOf(element));
			flag = true;
			logger.log(LogStatus.FAIL, "Verify Element Not Visible", "Element is visible");
		} catch (Exception e) {
			logger.log(LogStatus.PASS, "Verify Element Not Visible", element.getText() + " is not visible");
		}
		return flag;
	}

/*	public static boolean alertIsPresent(WebDriver driver, int time) {
		boolean flag = false;
		try {
			wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.alertIsPresent());
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}*/
	
	
	/*public boolean  ElementExist(WebElement element, ExtentTest logger) {
		boolean flag = false;
		
		try {			
				flag = element.isDisplayed();
				logger.log(LogStatus.PASS, "Verify Element Exist", element.getText() + " Exist in the " + driver.getTitle() + " Page");
			
		} catch (Exception e) {
				//e.printStackTrace();
				logger.log(LogStatus.FAIL, "Verify Element Exist", "Element does not Exist" + driver.getTitle() + " Page");
				try {
					
					String screenshotPath = getScreenhot(driver, "StepFailure");
					//To add it in the extent report 
					logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
		}		
			
		return flag;

	}*/
	
	public boolean  ElementExist(WebElement element, JSONObject config) {
		boolean flag = false;
		
		try {			
				flag = element.isDisplayed();
				System.out.println("element is displayed");
				addautolog(config,"PASS","Verify Element Exist",element.getText() + " should be displayed in the " + driver.getTitle() + " Page",element.getText() + " is displayed in the " + driver.getTitle() + " Page");
				//logger.log(LogStatus.PASS, "Verify Element Exist", element.getText() + " Exist in the " + driver.getTitle() + " Page");
			
		} catch (Exception e) {
				//e.printStackTrace();
				//logger.log(LogStatus.FAIL, "Verify Element Exist", "Element does not Exist" + driver.getTitle() + " Page");
				try {
					
					String screenshotPath = getScreenhot(driver, "StepFailure");
					//To add it in the extent report 
				//	logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
		}		
			
		return flag;

	}

	public boolean ElementClick(WebElement element,ExtentTest logger) {
		boolean flag = false;
		try
		{
			//ElementExist(element, logger);
			String Eletext = element.getText();
			element.click();
			flag = true;
			logger.log(LogStatus.PASS, "Element Click", Eletext + " link is clicked successfully");
			
			
		}catch(Exception e)
		{
			logger.log(LogStatus.FAIL, "Element Click", element.getText() + " link is not clicked");
			try {
				getScreenhot(driver, "StepFailure");
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}	
		return flag;
	}
	


	public static void EnterText(WebElement element, String name, int time, ExtentTest logger) {
		try
		{
			if(element != null)
			{
				if(element.isDisplayed()) {
					element.clear();
					element.sendKeys(name);			
				}
			}
			else
			{
				logger.log(LogStatus.FAIL, "Element Click", "link is not displayed");
			}
		}catch(Exception e)
		{
			logger.log(LogStatus.FAIL, "Element Click", element.getText() + " link is not clicked");
		}
	}

//	public static String getText(WebElement element) {
//		String name = null;
//		if (elementFound(element)) {
//			name = element.getAttribute("value");
//		}
//		return name;
//
//	}

	

	public static JSONObject JSONReadFromFile() {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {

			Object obj = parser.parse(new FileReader(f1.getAbsoluteFile()));

			jsonObject = (JSONObject) obj;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	
	
	public static String ObjIdentifier(String objname) {
		String ObjIdentifier = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();			
			Document doc = builder.parse(new File(objfile));			
			Element rootElement = doc.getDocumentElement();	        
	        NodeList list = rootElement.getElementsByTagName(objname);	 
	        for (int i = 0; i < list.getLength(); i++) {	          
	            Node childNode = list.item(i);	            
	            System.out.println("Object xpath : " + childNode.getTextContent());
	            ObjIdentifier = childNode.getTextContent();
	            return ObjIdentifier;
	        }

			} catch (Exception e) {
				e.printStackTrace();
			}
		return ObjIdentifier;		
		
		
	}

	/*public static void getScreenShot(String screenShotFileName) {
		File screenShotLocation = new File("./screenshot/" + screenShotFileName
				+ ".png");
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File file = screenshot.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(file, screenShotLocation);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	

}
