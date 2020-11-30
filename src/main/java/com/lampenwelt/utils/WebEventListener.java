package com.lampenwelt.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.lampenwelt.wrappers.SeleniumBase;

public class WebEventListener extends com.lampenwelt.wrappers.TestBase implements WebDriverEventListener {
	public void beforeNavigateTo(String url, WebDriver driver) {
		log.info("Before navigating to: '" + url + "'");
	}

	public void afterNavigateTo(String url, WebDriver driver) {
		log.info("Navigated to:'" + url + "'");
	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
		log.info("Value of the:" + element.toString() + " before any changes made");
	}

	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		log.info("Element Value Changed to: " + element.toString());
	}

	public void beforeClickOn(WebElement element, WebDriver driver) {
		log.info("Trying to Click On: " + element.toString());
	}

	public void afterClickOn(WebElement element, WebDriver driver) {
		log.info("Clicked On: " + element.toString());
	}

	public void beforeNavigateBack(WebDriver driver) {
		log.info("Navigating Back to Previous Page");
	}

	public void afterNavigateBack(WebDriver driver) {
		log.info("Navigated Back to Previous Page");
	}

	public void beforeNavigateForward(WebDriver driver) {
		log.info("Navigating Forward to Next Page");
	}

	public void afterNavigateForward(WebDriver driver) {
		log.info("Navigated Forward to Next Page");
	}

	public void onException(Throwable error, WebDriver driver) {
		log.info("Exception Occured: " + error);

	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		log.info("Trying to Find Element By : " + by.toString());
	}

	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		log.info("Found Element By : " + by.toString());
	}

	// Non Overridden Methods of WebListener Class
	public void beforeScript(String script, WebDriver driver) {
		log.info("Executing beforeScript method");

	}

	public void afterScript(String script, WebDriver driver) {
		log.info("Executing afterScript method");

	}

	public void beforeAlertAccept(WebDriver driver) {
		log.info("Before Alert accept");
	}

	public void afterAlertAccept(WebDriver driver) {
		log.info("Alert is accepted");
	}

	public void afterAlertDismiss(WebDriver driver) {
		log.info("Alert is Dismissed");
	}

	public void beforeAlertDismiss(WebDriver driver) {
		log.info("Before Alert Dismiss");
	}

	public void beforeNavigateRefresh(WebDriver driver) {
		log.info("Before Navigate Refresh");
	}

	public void afterNavigateRefresh(WebDriver driver) {
		log.info("after Navigate referesh");

	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		log.info("Before changing value of : " + element.toString());
	}

	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		log.info("After changing value of : " + element.toString());
	}

	public <X> void afterGetScreenshotAs(OutputType<X> arg0, X arg1) {
		log.info("Screenshot is captured");
	}

	public void afterGetText(WebElement arg0, WebDriver arg1, String arg2) {
		log.info("Get text method is called");
	}

	public void afterSwitchToWindow(String arg0, WebDriver arg1) {
		log.info("Switched to window");
	}

	public <X> void beforeGetScreenshotAs(OutputType<X> arg0) {
		log.info("Before get Screenshot");
	}

	public void beforeGetText(WebElement arg0, WebDriver arg1) {
		log.info("Before get text");
	}

	public void beforeSwitchToWindow(String arg0, WebDriver arg1) {
		log.info("Going to Switch window");
	}
}
