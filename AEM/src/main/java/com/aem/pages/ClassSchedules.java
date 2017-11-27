package com.aem.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aem.utilities.CommonLib;

public class ClassSchedules extends CommonLib {
	
		WebDriverWait wait = new WebDriverWait(driver, 60);		

		public WebElement LnkStudioCycle()
		{
			try
			{					
				String strStudioCycle = ObjIdentifier("LnkStudioCycle");			
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(strStudioCycle)));
				WebElement LnkStudioCycle = driver.findElement(By.xpath(strStudioCycle));
				return LnkStudioCycle;
			}
			catch(Exception e)
			{
				return null;
			}
			
		}

}
