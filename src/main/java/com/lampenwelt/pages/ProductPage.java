package com.lampenwelt.pages;

import org.apache.commons.math3.util.Precision;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.google.common.base.Throwables;
import com.lampenwelt.wrappers.TestBase;

public class ProductPage extends TestBase {

	WebDriver driver;
	public static double productCost;
	public static String price_name;
	public static double sum;

	@FindBy(id = "product_addtocart_form")
	public WebElement productView;

	@FindBy(id = "btnAddToCart")
	public WebElement addToCartButton;

	@FindBy(xpath = "(//div[contains(@class,\"cart-dropdown\")])[1]")
	public WebElement myCartIcon;

	@FindBy(id = "btnGoToCart")
	public WebElement goToCartButton;

	@FindBy(css = "div.product-name")
	public WebElement productName;

	@FindBy(xpath = "(//span[contains(@id,'product-price')]/span)[2]")
	public WebElement productPrice;

	public ProductPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public ProductPage verifyProductPage() {
		waitforPageToload();
		if (verifyDisplayed(productView)) {
			test.log(Status.PASS, "Product information Page is displayed");
			log.info("Product information Page is displayed");
		} else {
			test.log(Status.FAIL, "Product information Page is not displayed");
			log.info("Product information Page is not displayed");
		}

		return this;
	}

	public ProductPage getProductName() {

		try {
			price_name = productName.getText();
			log.info("Selected ProductName is " + price_name);
			test.log(Status.PASS, "Selected ProductName is: " + price_name);
		} catch (Exception e) {
			test.log(Status.FAIL, "Unable to get the product name");
			log.info(Throwables.getStackTraceAsString(e));
		}

		return this;
	}

	public ProductPage getProductPrice() {

		try {
			String price_text = productPrice.getText().replace(',', '.');
			productCost = Double.parseDouble(price_text);
			log.info("Price of the product is " + productCost);
			test.log(Status.PASS, "Price of the product is EUR " + productCost);
		} catch (NumberFormatException e) {
			test.log(Status.FAIL, "Unable to get the product price");
			log.info(Throwables.getStackTraceAsString(e));
		}
		return this;
	}

	public ProductPage addProductPrice(int i) {
		try {
			if (i == 0)
				sum = 0;
			sum = sum + productCost;
			sum = Precision.round(sum, 2);
			test.log(Status.PASS, "Total cost of the selected products are " + sum);
			log.info("Total cost of the product is " + sum);
		} catch (Exception e) {
			log.info(Throwables.getStackTraceAsString(e));
		}
		return this;
	}

	public ProductPage clickAddToCartButton() {
		try {
			waitForElementToBeVisible(addToCartButton);
			clickElementByJavaScript(addToCartButton);
			test.log(Status.PASS, "Add to Cart Button is clicked ");
			log.info("Add to Cart Button is clicked ");
		} catch (Exception e) {
			log.info(Throwables.getStackTraceAsString(e));
		}

		return this;
	}

	public ShoppingCartPage clickGoToCartButton() {
		try {
//			moveToElement(myCartIcon);
//			waitForElementToBeVisible(goToCartButton);
			click(myCartIcon);
			test.log(Status.PASS, "Clicked my cart icon");
			log.info("Clicked my cart icon");
		} catch (Exception e) {
			test.log(Status.FAIL, "Unable to click my cart icon");
			log.info(Throwables.getStackTraceAsString(e));
		}
		return new ShoppingCartPage(driver);
	}
}