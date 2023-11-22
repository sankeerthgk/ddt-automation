package com.tests;

import base.Base;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.SearchResultsPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestClass extends Base {

    private Logger logger = LoggerFactory.getLogger(TestClass.class.getName());

    @Test(dataProvider = "dataProvider")
    public void testSearchAddAndRemoveProduct(String searchItem, String titleLookup) {
        //1. Go to https://www.webstaurantstore.com/
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isLogoDisplayed(), "Logo is not displayed on Home page");
        logger.info("Verified that logo is displayed on Home page");

        //2. Search for "stainless work table".
        homePage.searchForItem(searchItem);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        Assert.assertTrue(searchResultsPage.isSearchResultsDisplayed(), "Search results are not displayed");

        //3. Check the search result ensuring every product has the word 'Table' in its title.
        Assert.assertTrue(searchResultsPage.checkIfAllProductsContainsTitle(titleLookup), "Not all products have Table in their title");
        logger.info("All products have the word Table in the title");

        //4. Add the last of found item on last page to Cart.
        searchResultsPage.addLastProductOnPageToCart();
        searchResultsPage.clickOnViewCartOnOverlay();

        //5. Empty Cart.
        CartPage cartPage = new CartPage(driver);
        cartPage.emptyCartRemoveAllProducts();
        Assert.assertTrue(cartPage.isEmptyCartMessageDisplayed(), "Cart is not empty");
        logger.info("Test Passed");
    }

    @DataProvider(name = "dataProvider")
    public Iterator<Object[]> testData() throws IOException {
        // Specify the path to your text file
        String txtFilePath = properties.getProperty("testDataPath");

        // Read test data from the text file
        List<Object[]> testDataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(txtFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                testDataList.add(new Object[]{data[0], data[1]});
            }
        }

        // Return iterator for the test data
        return testDataList.iterator();
    }
}
