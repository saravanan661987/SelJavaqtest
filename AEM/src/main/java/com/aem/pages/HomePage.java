package com.aem.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
//import org.qas.qtest.api.auth.PropertiesQTestCredentials;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aem.utilities.CommonLib;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
//import org.qas.qtest.api.auth.QTestCredentials;
//import org.qas.qtest.api.services.design.TestDesignServiceClient;
//import org.qas.qtest.api.services.design.model.CreateTestCaseRequest;
//import org.qas.qtest.api.services.design.model.TestCase;



public class HomePage extends CommonLib {
	
	
	WebDriverWait wait = new WebDriverWait(driver, 60);
	

	public WebElement LnkClasses()
	{
		try
		{	
			
			String strClasses = ObjIdentifier("LnkClasses");			
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(strClasses)));
			WebElement LnkClasses = driver.findElement(By.xpath(strClasses));
			return LnkClasses;
		}
		catch(Exception e)
		{
			return null;
		}
		
	}

		
	public WebElement LnkAllClasses()
	{
		try
		{
			
			String strAllClasses = ObjIdentifier("LnkAllClasses");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(strAllClasses)));
			WebElement LnkAllClasses = driver.findElement(By.xpath(strAllClasses));
			return LnkAllClasses;
		}catch(Exception e)
		{
			return null;
		}
	}
	
	public WebElement LnkClass()
	{
		try
		{
			
			String strClasses = ObjIdentifier("LnkClass");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(strClasses)));
			WebElement LnkClass = driver.findElement(By.xpath(strClasses));
			return LnkClass;
		}catch(Exception e)
		{
			return null;
		}
	}


	
	
	

	

}
