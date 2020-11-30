package com.lampenwelt.wrappers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.lampenwelt.utils.Reporter;
import com.lampenwelt.utils.WebEventListener;
import com.mongodb.diagnostics.logging.Logger;

public class SeleniumBase extends Reporter {

	public static WebDriver driver; 
	public WebDriverWait wait;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	
	
	public void browserInitialization()
    {
		try {
			Properties property = new Properties();
			FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/Configuration.properties");
			property.load(inputStream);
			 String broswerName = property.getProperty("Browser");
			 if(broswerName.equalsIgnoreCase("chrome")) {
			 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			driver=new ChromeDriver();
			 }
			 else if(broswerName.equalsIgnoreCase("firefox")) {
				 System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver.exe");
					driver = new FirefoxDriver();
				} else if (broswerName.equalsIgnoreCase("ie")) {
					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") +"/drivers/IEDriverServer.exe");
					driver = new InternetExplorerDriver();
				}
            e_driver = new EventFiringWebDriver(driver);	
			eventListener = new WebEventListener();
			e_driver.register(eventListener);
			driver = e_driver;
			driver.manage().window().maximize();
			driver.get(property.getProperty("url"));
			log.info("Browser Launched");
			test.log(Status.PASS, "Application is launched");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
	
	public void click(WebElement ele) {
		String text = "";
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			text = ele.getText();
			ele.click();
		} catch (StaleElementReferenceException e) {
			throw new RuntimeException();
			
		}
	}

	public  void clickElementByJavaScript(WebElement element) 
	{
		try {
			JavascriptExecutor javaScript = ((JavascriptExecutor) driver);
			javaScript.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			test.log(Status.FAIL, "Unable to click the element");
			log.info("JavaScript click failed");
		}
	}
	public void clickWithNoSnap(WebElement ele) {
		String text = "";
		try {
			text = ele.getText();
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			ele.click();
			reportStep("The Element with text: " + text + " clicked", "pass", false);
		} catch (StaleElementReferenceException e) {
			reportStep("The Element " + ele + " could not be clicked", "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			System.err.println(e);
		}

	}
	public static String getScreenshot() throws Exception {
		String dateName = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
                //after execution, you could see a folder "FailedTestsScreenshots" under src folder
		String destination = System.getProperty("user.dir") + "/Screenshots/"+"Screenshot_"+dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public void sendKeys(WebElement element, String data) {
		try {
			element.sendKeys(data);
			System.out.println(data+" is entered in element ");
		} catch (Exception e) {
			System.err.println(e);
//			log.info("exception occurent while entering"+data+ "to element");
		}
	}

	public void sendKeys(WebElement element, Keys key) {
		element.sendKeys(key);
		
	}
	
	public  void waitForElementToBeVisible(WebElement element)
	{
		new WebDriverWait(driver, 30)
		.until(ExpectedConditions.visibilityOf(element));
	}
	public  void waitForElementToBeClickable(WebElement element)
	{
		new WebDriverWait(driver, 30)
		.until(ExpectedConditions.elementToBeClickable(element));
	}
	public void waitforPageToload() {
	new WebDriverWait(driver, 60).until(
		      webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	}
	
	public void moveToElement(WebElement element) 
	{
		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
		log.info("moved to element "+element);
	}

	public void clear(WebElement ele) {
		try {
			ele.clear();
			reportStep("The field is cleared Successfully", "pass");
		} catch (ElementNotInteractableException e) {
			reportStep("The field is not Interactable", "fail");
			throw new RuntimeException();
		}
	}

	public void clearAndType(WebElement ele, String data) {
		try {
			ele.clear();
			ele.sendKeys(data);
			reportStep("The Data :" + data + " entered Successfully", "pass");
		} catch (ElementNotInteractableException e) {
			reportStep("The Element " + ele + " is not Interactable", "fail");
			throw new RuntimeException();
		}

	}

	public String getElementText(WebElement ele) {
		String text = ele.getText();
		return text;
	}

	public String getBackgroundColor(WebElement ele) {
		String cssValue = ele.getCssValue("color");
		return cssValue;
	}

	public String getTypedText(WebElement ele) {
		String attributeValue = ele.getAttribute("value");
		return attributeValue;
	}

	public void selectDropDownUsingText(WebElement ele, String value) {

		new Select(ele).selectByVisibleText(value);
	}

	public void selectDropDownUsingIndex(WebElement ele, int index) {
		new Select(ele).selectByIndex(index);
	}

	public void selectDropDownUsingValue(WebElement ele, String value) {
		new Select(ele).selectByValue(value);
	}

	public boolean verifyExactText(WebElement ele, String expectedText) {
		try {
			if (ele.getText().equals(expectedText)) {
				reportStep("The expected text contains the actual " + expectedText, "pass");
				return true;
			} else {
				reportStep("The expected text doesn't contain the actual " + expectedText, "fail");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Text");
		}

		return false;
	}

	public boolean verifyPartialText(WebElement ele, String expectedText) {
		try {
			if (ele.getText().contains(expectedText)) {
				reportStep("The expected text contains the actual " + expectedText, "pass");
				return true;
			} else {
				reportStep("The expected text doesn't contain the actual " + expectedText, "fail");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Text");
		}

		return false;
	}

	public boolean verifyExactAttribute(WebElement ele, String attribute, String value) {
		try {
			if (ele.getAttribute(attribute).equals(value)) {
				reportStep("The expected attribute :" + attribute + " value contains the actual " + value, "pass");
				return true;
			} else {
				reportStep("The expected attribute :" + attribute + " value does not contains the actual " + value,
						"fail");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Attribute Text");
		}
		return false;
	}

	public void verifyPartialAttribute(WebElement ele, String attribute, String value) {
		try {
			if (ele.getAttribute(attribute).contains(value)) {
				reportStep("The expected attribute :" + attribute + " value contains the actual " + value, "pass");
			} else {
				reportStep("The expected attribute :" + attribute + " value does not contains the actual " + value,
						"fail");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Attribute Text");
		}

	}

	public boolean verifyDisplayed(WebElement ele) {
		try {
			waitForElementToBeVisible(ele);
			if (ele.isDisplayed()) {
				return true;
			} else {
				return false;
			}
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}
		return false;

	}

	public boolean verifyDisappeared(WebElement ele) {
		return false;

	}

	public boolean verifyEnabled(WebElement ele) {
		try {
			if (ele.isEnabled()) {
				reportStep("The element " + ele + " is Enabled", "pass");
				return true;
			} else {
				reportStep("The element " + ele + " is not Enabled", "fail");
			}
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}
		return false;
	}

	public void verifySelected(WebElement ele) {
		try {
			if (ele.isSelected()) {
				reportStep("The element " + ele + " is selected", "pass");
				// return true;
			} else {
				reportStep("The element " + ele + " is not selected", "fail");
			}
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}
		// return false;

	}

//	public RemoteWebDriver startApp(String url) {
//		return startApp("chrome", url);
//	}

	/*public RemoteWebDriver startApp(String browser, String url) {
		try {
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
				driver = new ChromeDriver();
			} else if (browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
				driver = new FirefoxDriver();
			} else if (browser.equalsIgnoreCase("ie")) {
				System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
			driver.navigate().to(url);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch (Exception e) {
			reportStep("The Browser Could not be Launched. Hence Failed", "fail");
			throw new RuntimeException();
		}
		return driver;

	}*/

//	public WebElement locateElement(String locatorType, String value) {
//		try {
//			switch (locatorType.toLowerCase()) {
//			case "id":
//				return driver.findElementById(value);
//			case "name":
//				return driver.findElementByName(value);
//			case "class":
//				return driver.findElementByClassName(value);
//			case "link":
//				return driver.findElementByLinkText(value);
//			case "xpath":
//				return driver.findElementByXPath(value);
//			}
//		} catch (NoSuchElementException e) {
//			reportStep("The Element with locator:" + locatorType + " Not Found with value: " + value, "fail");
//			throw new RuntimeException();
//		} catch (Exception e) {
//			reportStep("The Element with locator:" + locatorType + " Not Found with value: " + value, "fail");
//		}
//		return null;
//	}

//	public WebElement locateElement(String value) {
//		WebElement findElementById = driver.findElementById(value);
//		return findElementById;
//	}
//
//	public List<WebElement> locateElements(String type, String value) {
//		try {
//			switch (type.toLowerCase()) {
//			case "id":
//				return driver.findElementsById(value);
//			case "name":
//				return driver.findElementsByName(value);
//			case "class":
//				return driver.findElementsByClassName(value);
//			case "link":
//				return driver.findElementsByLinkText(value);
//			case "xpath":
//				return driver.findElementsByXPath(value);
//			}
//		} catch (NoSuchElementException e) {
//			System.err.println("The Element with locator:" + type + " Not Found with value: " + value);
//			throw new RuntimeException();
//		}
//		return null;
//	}

	public void switchToAlert() {
		driver.switchTo().alert();
	}

	public void acceptAlert() {
		String text = "";
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			alert.accept();
			reportStep("The alert " + text + " is accepted.", "pass");
		} catch (NoAlertPresentException e) {
			reportStep("There is no alert present.", "fail");
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}

	}

	public void dismissAlert() {
		String text = "";
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			alert.dismiss();
			System.out.println("The alert " + text + " is accepted.");
		} catch (NoAlertPresentException e) {
			System.out.println("There is no alert present.");
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}

	}

	public String getAlertText() {
		String text = "";
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
		} catch (NoAlertPresentException e) {
			System.out.println("There is no alert present.");
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}
		return text;
	}

	public void typeAlert(String data) {
		driver.switchTo().alert().sendKeys(data);

	}

	public void switchToWindow(int index) {
		try {
			Set<String> allWindows = driver.getWindowHandles();
			List<String> allhandles = new ArrayList<String>(allWindows);
			String exWindow = allhandles.get(index);
			driver.switchTo().window(exWindow);
			System.out.println("The Window With index: " + index + " switched successfully");
		} catch (NoSuchWindowException e) {
			System.err.println("The Window With index: " + index + " not found");
		}
	}

	public void switchToWindow(String title) {
		try {
			Set<String> allWindows = driver.getWindowHandles();
			for (String eachWindow : allWindows) {
				driver.switchTo().window(eachWindow);
				if (driver.getTitle().equals(title)) {
					break;
				}
			}
			System.out.println("The Window With Title: " + title + "is switched ");
		} catch (NoSuchWindowException e) {
			System.err.println("The Window With Title: " + title + " not found");
		} finally {
			takeSnap();
		}
	}

	public void switchToFrame(int index) {
		driver.switchTo().frame(index);

	}

	public void switchToFrame(WebElement ele) {
		driver.switchTo().frame(ele);

	}

	public void switchToFrame(String idOrName) {
		driver.switchTo().frame(idOrName);

	}

	public void defaultContent() {
		driver.switchTo().defaultContent();

	}

	public boolean verifyUrl(String url) {
		if (driver.getCurrentUrl().equals(url)) {
			System.out.println("The url: " + url + " matched successfully");
			return true;
		} else {
			System.out.println("The url: " + url + " not matched");
		}
		return false;
	}

	public boolean verifyTitle(String title) {
		if (driver.getTitle().equals(title)) {
			System.out.println("Page title: " + title + " matched successfully");
			return true;
		} else {
			System.out.println("Page url: " + title + " not matched");
		}
		return false;
	}

	public static long takeSnap() {
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
		try {
			FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
					new File(System.getProperty("user.dir") + "/Screenshots/"+"Screenshot_" + number + ".jpg"));
		} catch (WebDriverException e) {
			log.info("The browser has been closed.");
		} catch (IOException e) {
			log.info("Unable to take Screenshot");
		}
		return number;
	}

	public void close() {
		driver.close();

	}

	public void quit() {
		driver.quit();

	}

}
