package com.lampenwelt.wrappers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.lampenwelt.utils.ExcelReader;






public class TestBase extends SeleniumBase
{
	
	public static Properties property;
	public static ChromeOptions chromeOptions;
	public static EventFiringWebDriver e_driver;
    public String dataSheetName;
    ExcelReader reader;
    
		
   	
	@DataProvider(name = "fetchData")
	public Object[][] fetchData() throws IOException {
		return ExcelReader.readExcelData(dataSheetName);
	}		


	@BeforeMethod
	 public void browser()
    {
    	
    	 browserInitialization();
    }
	
	
	@AfterMethod(alwaysRun=true)
	public void tearDown() throws Exception
	{
		test.addScreenCaptureFromPath(getScreenshot());
		test.info("Test execution ended");
		quit();
		log.info("Browser Terminated");
		log.info("-----------------------------------------------");
	}
}

