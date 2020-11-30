
package com.lampenwelt.testcases;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.util.Precision;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.google.common.base.Throwables;
import com.lampenwelt.pages.HomePage;
import com.lampenwelt.pages.ProductPage;
import com.lampenwelt.pages.SearchResultPage;
import com.lampenwelt.pages.ShoppingCartPage;
import com.lampenwelt.wrappers.TestBase;

public class TC002_AddProductFromCartPage extends TestBase {
	HomePage homePage;
	SearchResultPage searchResultPage;
	ProductPage productPage;
	ShoppingCartPage shoppingCartPage;
	static double addedCost;
	static double newItemCost;

	@BeforeTest
	public void setValues() {
		testCaseName = "Add new product from Cart Page";
		testDescription = "To verify whether user is able to add new Product from Cart Page";
		authors = "Lampenwelt";
		category = "Regression";
		dataSheetName = "TestData_TC002";
	}

	@Test(dataProvider = "fetchData", invocationCount = 1)
	public void addNewProductFromCartPage(String product_1, String product_2, String product_3, String newItem)
			throws InterruptedException {
		try {
			homePage = new HomePage(driver);
			productPage = new ProductPage(driver);
			shoppingCartPage = new ShoppingCartPage(driver);
			ArrayList<String> products = new ArrayList<String>(Arrays.asList(product_1, product_2, product_3));
			for (int i = 0; i < products.size(); i++) {
				homePage.searchProduct(products.get(i))
				.searchResultIsDisplayed()
				.selectProduct()
				.verifyProductPage()
				.getProductName()
				.getProductPrice()
				.addProductPrice(i);
				productPage.clickAddToCartButton();
				test.log(Status.INFO, "All the Products are added to cart successfully");
			}
			addedCost = productPage.sum;
			System.out.println(addedCost);
			productPage.clickGoToCartButton()
			.verifyShoppingCartPage()
			.verifysubTotalCost(addedCost)
			.calculateGrandTotal()
			.verifyTotalGrandCost(shoppingCartPage.grandTotal);
			homePage.searchProduct(newItem)
			.searchResultIsDisplayed()
			.selectProduct()
			.verifyProductPage()
			.getProductName()
			.getProductPrice();
			newItemCost = productPage.productCost;
			System.out.println("newItemCost is" + newItemCost);
			productPage.clickAddToCartButton()
			.clickGoToCartButton()
			.verifyShoppingCartPage()
			.verifysubTotalCost(Precision.round(addedCost + newItemCost, 2))
			.calculateGrandTotal()
			.verifyTotalGrandCost(shoppingCartPage.grandTotal);

		} catch (Exception e) {
			test.log(Status.FAIL, "Failed due to Unexpected exception");
			log.error(Throwables.getStackTraceAsString(e));
			Assert.fail("Exception occurred");

		}

	}

}
