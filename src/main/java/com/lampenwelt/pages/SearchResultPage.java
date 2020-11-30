package com.lampenwelt.pages;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.common.base.Throwables;
import com.lampenwelt.wrappers.TestBase;

public class SearchResultPage extends TestBase {

	WebDriver driver;

	@FindBy(css = "div.category-products")
	public WebElement searchResults;

	@FindBy(xpath = "//div[@class='product-image']/a")
	public List<WebElement> items;

	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public SearchResultPage searchResultIsDisplayed() throws InterruptedException {
		waitforPageToload();
		if (verifyDisplayed(searchResults)) {
			log.info("Search Result is displayed");
			test.log(Status.PASS, "Search Result is displayed");
		} else {
			test.log(Status.FAIL, "Search Result is not displayed");
		}
		return this;
	}

	public ProductPage selectProduct() {
		try {
			int max_limit;
			System.out.println("entered into select product");
			waitForElementToBeVisible(items.get(0));
			max_limit = items.size();
			if (max_limit > 5)
				max_limit = 5;
			int int_random = new Random().nextInt(max_limit);
			log.info("Item to be selected is item# " + int_random);
			waitForElementToBeClickable(items.get(int_random));
			clickElementByJavaScript(items.get(int_random));
			test.log(Status.PASS, "Product is selected");
		} catch (Exception e) {
			test.log(Status.FAIL, "Unable to select Product");
			log.info("Failed due to following exception: " + Throwables.getStackTraceAsString(e));
			Assert.fail("Failed due to Unexpected exception");
		}
		return new ProductPage(driver);

	}
}
