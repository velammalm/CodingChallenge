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
import com.lampenwelt.wrappers.TestBase;

public class TC001_SearchAndAddProducts extends TestBase {

	HomePage homePage;
	SearchResultPage searchResultPage;
	ProductPage productPage;

	@BeforeTest
	public void setValues() {
		testCaseName = "Search and Add Products to Cart";
		testDescription = "validation of product search in homepage and addition of products to the cart";
		authors = "lampenwelt";
		category = "Regression";
		dataSheetName = "TestData_TC001";
	}

	@Test(dataProvider = "fetchData", invocationCount = 1)
	public void validateProductSearch(String product_1, String product_2, String product_3)
			throws InterruptedException {
		try {
			homePage = new HomePage(driver);
			productPage = new ProductPage(driver);
			ArrayList<String> products = new ArrayList<String>(Arrays.asList(product_1, product_2, product_3));
			test.log(Status.INFO, "Search result obtained");
			for (int i = 0; i < products.size(); i++) {
				homePage.searchProduct(products.get(i))
				.searchResultIsDisplayed()
				.selectProduct()
				.verifyProductPage()
//			.getProductName()
//			.getProductPrice()
						.clickAddToCartButton();
				test.log(Status.PASS, "Products added to cart successfully");
			}
			productPage.clickGoToCartButton();
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed due to Unexpected exception");
			System.out.println(e.getStackTrace());
			log.error(e.getMessage());
			Assert.fail("Exception occurred");

		}

	}

}
