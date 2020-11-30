package com.lampenwelt.testcases;

import java.util.ArrayList;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.lampenwelt.pages.HomePage;
import com.lampenwelt.pages.ProductPage;
import com.lampenwelt.pages.SearchResultPage;
import com.lampenwelt.pages.ShoppingCartPage;
import com.lampenwelt.wrappers.TestBase;

public class TC004_UpdateProductQuantity extends TestBase {

	HomePage homePage;
	SearchResultPage searchResultPage;
	ProductPage productPage;
	static double addedCost;
	ShoppingCartPage shoppingCartPage;

	@BeforeTest
	public void setValues() {
		testCaseName = "Update Product Quantity";
		testDescription = "Validation of product quantity updation in cart page";
		authors = "lampenwelt";
		category = "Regression";
		dataSheetName = "TestData_TC004";
	}

	@Test(dataProvider = "fetchData", invocationCount = 1)
	public void validateProductSearch(String product_1, String product_2, String product_3)
			throws InterruptedException {
		try {
			homePage = new HomePage(driver);
			productPage = new ProductPage(driver);
			shoppingCartPage = new ShoppingCartPage(driver);
			ArrayList<String> products = new ArrayList<String>(Arrays.asList(product_1, product_2, product_3));
			test.log(Status.INFO, "Search result obtained");
			for (int i = 0; i < products.size(); i++) {
				homePage.searchProduct(products.get(i))
				.searchResultIsDisplayed()
				.selectProduct()
				.verifyProductPage()
				.getProductName()
				.getProductPrice()
				.clickAddToCartButton()
				.addProductPrice(i);
				productPage.clickAddToCartButton();
				test.log(Status.PASS, "Products added to cart successfully");
			}
			addedCost = productPage.sum;
			productPage.clickGoToCartButton()
			.verifyShoppingCartPage()
			.verifysubTotalCost(addedCost)
			.calculateGrandTotal()
			.verifyTotalGrandCost(shoppingCartPage.grandTotal)
			.updateProductQuantity()
			.verifyCostAfterUpdatingQuantity(addedCost);

		} catch (Exception e) {
			test.log(Status.FAIL, "Failed due to Unexpected exception");
			System.out.println(e.getStackTrace());
			log.error(e.getMessage());
			Assert.fail("Exception occurred");

		}

	}

}
