package com.lampenwelt.pages;

import java.util.List;
import java.util.Random;

import org.apache.commons.math3.util.Precision;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.google.common.base.Throwables;
import com.lampenwelt.wrappers.TestBase;

public class ShoppingCartPage extends TestBase {

	WebDriver driver;
	public static double grandTotal, initialCost;
	public static int random_qty, randomProduct;

	@FindBy(className = "page-title")
	public WebElement cartPageTitle;

	@FindBy(css = "tr.total-subtotal")
	public WebElement subTotalSection;

	@FindBy(css = "tr.total-shipping")
	public WebElement shippingCostSection;

	@FindBy(css = "span.value")
	public WebElement shippingCost;

	@FindBy(xpath = "//tr[contains(@class,'total-bulky-goods-fee-net')]//span")
	public List<WebElement> bulkySurcharge;

	@FindBy(xpath = "//tr[@class='total-subtotal']/td[2]/span")
	public WebElement subTotal;

	@FindBy(css = "tr.total-grand_total")
	public WebElement totalGrandCostSection;

	@FindBy(id = "country")
	public WebElement shippingCountry;

	@FindBy(xpath = "//div[@class='product-row__total']/span/div[@class='price-box']")
	public List<WebElement> productCosts;

	@FindBy(xpath = "//select[@class='qty js-qty-select option-chosen']")
	public List<WebElement> productQuantityDropDown;

	@FindBy(xpath = "//a[@title='Artikel entfernen']")
	public List<WebElement> removeProductLink;

	public static double deletedItemCost;

	public ShoppingCartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public ShoppingCartPage verifyShoppingCartPage() {
		waitforPageToload();
		if (verifyDisplayed(cartPageTitle)) {
			log.info("Cart Page is displayed");
			test.log(Status.PASS, "Cart Page is displayed");
		} else {
			log.info("Cart Page is not displayed");
			test.log(Status.FAIL, "Cart Page is not displayed");
		}

		return this;
	}

	public ShoppingCartPage verifysubTotalCost(double cost) {
		String sum = Double.toString(cost).replace(".", ",");
//				String sum=text.substring(0,text.indexOf(",")+2);
		verifyPartialText(subTotalSection, sum);
		return this;

	}

	public ShoppingCartPage calculateGrandTotal() {
		double subtotal = 0, shippingcost = 0, surcharge = 0;
		String price_text = subTotal.getText().replace(',', '.').substring(0, subTotal.getText().lastIndexOf(' '));
		subtotal = Double.parseDouble(price_text);
		price_text = shippingCost.getText().replace(',', '.').substring(0, shippingCost.getText().lastIndexOf(' '));
		shippingcost = Double.parseDouble(price_text);
		grandTotal = subtotal + shippingcost;
		if (bulkySurcharge.size() != 0) {
			test.log(Status.INFO, bulkySurcharge.get(0).getText());
			price_text = bulkySurcharge.get(0).getText().replace(',', '.').substring(0,
					bulkySurcharge.get(0).getText().lastIndexOf(' '));
			surcharge = Double.parseDouble(price_text);
			grandTotal = Precision.round(grandTotal + surcharge, 2);
//			grandTotal=grandTotal+surcharge;
		}
		return this;
	}

	public ShoppingCartPage verifyTotalGrandCost(double cost) {
		String sum = Double.toString(cost).replace(".", ",");
		verifyPartialText(totalGrandCostSection, sum);
		return this;

	}

	public ShoppingCartPage selectShippingCountry(String country) {
		try {
			selectDropDownUsingText(shippingCountry, country);
		} catch (Exception e) {
			test.log(Status.FAIL, "Unable to select the shopping " + country);
			log.info(Throwables.getStackTraceAsString(e));
		}
		return this;
	}

	public ShoppingCartPage validateShippingCost() {
		waitforPageToload();
		if (!shippingCost.getText().contains("0,00")) {
			test.log(Status.PASS, "Shipping cost is updated and the value is " + shippingCost.getText());
		} else
			test.log(Status.FAIL, "Shipping cost is not updated ");
		return this;
	}

	public ShoppingCartPage updateProductQuantity() {
		try {
			random_qty = new Random().nextInt(7) + 2;
			randomProduct = new Random().nextInt(productCosts.size());
			String price_text = productCosts.get(randomProduct).getText().replace(',', '.').substring(0,
					productCosts.get(randomProduct).getText().indexOf(' '));
			initialCost = Double.parseDouble(price_text);
			int num = randomProduct + 1;
			test.log(Status.INFO,
					"Price of the Product " + num + " before updating product quantity is " + initialCost);
			selectDropDownUsingValue(productQuantityDropDown.get(randomProduct), Integer.toString(random_qty));
			waitforPageToload();
			test.log(Status.INFO, "Quantity of the Product " + num + " id updated to " + random_qty);
		} catch (NumberFormatException e) {
			test.log(Status.FAIL, "Unable to update Product Quantity due to NumberFormatException");
			log.info(Throwables.getStackTraceAsString(e));
		} catch (Exception e) {
			test.log(Status.FAIL, "Unable to update Product Quantity");
			log.info(Throwables.getStackTraceAsString(e));
		}
		return this;
	}

	public ShoppingCartPage verifyCostAfterUpdatingQuantity(double initialSubTotalCost) {
		double updatedCost = initialCost * (random_qty - 1) + initialSubTotalCost;
		updatedCost = Precision.round(updatedCost, 2);
		String sum = Double.toString(updatedCost).replace(".", ",");
		if (subTotal.getText().contains(".")) {
			String costText = subTotal.getText().replace(".", "");
			if (costText.contains(sum)) {
				test.log(Status.PASS, "The expected cost amount " + sum + " is displayed in sub total");
			} else
				test.log(Status.FAIL, "The expected cost amount " + sum + " is not displayed in sub total");
		} else
			verifyPartialText(subTotal, sum);
		double shippingcost = 0, surcharge = 0;
		String price_text = shippingCost.getText().replace(',', '.').substring(0,
				shippingCost.getText().lastIndexOf(' '));
		shippingcost = Double.parseDouble(price_text);
		grandTotal = updatedCost + shippingcost;
		if (bulkySurcharge.size() != 0) {
			test.log(Status.INFO, bulkySurcharge.get(0).getText());
			price_text = bulkySurcharge.get(0).getText().replace(',', '.').substring(0,
					bulkySurcharge.get(0).getText().lastIndexOf(' '));
			surcharge = Double.parseDouble(price_text);
			grandTotal = Precision.round(grandTotal + surcharge, 2);

		}
		sum = Double.toString(grandTotal).replace(".", ",");
		if (totalGrandCostSection.getText().contains(".")) {
			String costText = totalGrandCostSection.getText().replace(".", "");
			if (costText.contains(sum)) {
				test.log(Status.PASS, "The expected cost amount " + sum + " is displayed in GrandTotalPrice");
			} else
				test.log(Status.FAIL, "The expected cost amount " + sum + " is not displayed in GrandTotalPrice");
		} else
			verifyPartialText(totalGrandCostSection, sum);

		return this;
	}

	public ShoppingCartPage deleteProduct() {
		int total_Products = productCosts.size();
		randomProduct = new Random().nextInt(productCosts.size());
		String price_text = productCosts.get(randomProduct).getText().replace(',', '.').substring(0,
				productCosts.get(randomProduct).getText().indexOf(' '));
		deletedItemCost = Double.parseDouble(price_text);
		int num = randomProduct + 1;
		test.log(Status.INFO, "Product to be deleted is Product " + num);
		click(removeProductLink.get(randomProduct));
		waitforPageToload();
		if (productCosts.size() == (total_Products - 1))
			test.log(Status.PASS, "Product" + num + "is deleted successfully");
		else
			test.log(Status.FAIL, "Unable to delete the product");
		return this;
	}

}
