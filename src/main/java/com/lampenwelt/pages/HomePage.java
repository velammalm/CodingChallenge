
package com.lampenwelt.pages;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.Status;
import com.google.common.base.Throwables;
import com.lampenwelt.wrappers.TestBase;

public class HomePage extends TestBase {

	WebDriver driver;

	@FindBy(id = "imgHeaderLogo")
	public WebElement headerLogo;

	@FindBy(id = "search")
	public WebElement searchBar;

	@FindBy(xpath = "//nav[@id='nav']/ol/li/a")
	public List<WebElement> headerMenus;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public SearchResultPage searchProduct(String product) {
		try {
			sendKeys(searchBar, product);
			sendKeys(searchBar, Keys.ENTER);
			test.log(Status.PASS, product + " is entered in the search bar");

		} catch (Exception e) {
			test.log(Status.FAIL, product + " couldn't be entered in the Search Bar");
			log.info(Throwables.getStackTraceAsString(e));
		}
		return new SearchResultPage(driver);
	}

}
