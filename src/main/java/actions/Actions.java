package actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;

import java.util.List;

/**
 * Actions class for all objects that perform actions on the page
 */
public class Actions extends BasePage {

    private final Logger logger = LoggerFactory.getLogger(Actions.class.getName());
    public Actions(WebDriver driver) {
        super(driver);
    }

    /**
     * Loop through all the pages and check for a term in product title and return true
     * if condition is true on all pages
     * @param nextPageButton
     * @param pagesElement
     * @param productElement
     * @param searchText
     * @return
     */
    public boolean iterateThroughPagesAndLookForTitle(By nextPageButton, By pagesElement, By productElement, String searchText) {
        List<WebElement> pageLinks = driver.findElements(pagesElement);
        int totalPages= Integer.parseInt(pageLinks.get(pageLinks.size()-2).getText())-1;
        // Traverse through each page
        logger.info("Total number of pages to traverse: " + totalPages);
        int count = 0;
        String lookupWord = searchText.toLowerCase();
        for (int i=0; i< totalPages; i++) {
            // Perform actions on the current page
            if (checkAllProductsContainsText(productElement, lookupWord)) {
                count++;
                logger.info("Verified that all titles have '" + searchText + "' on page " + count);
            }
            // Click on the next page link
            scrollToElementAndClick(nextPageButton);
        }
        return count==pageLinks.size();
    }

    /**
     * Check if a page contains all elements have searchtext in their text
     * @param searchElement
     * @param searchText
     * @return
     */
    public boolean checkAllProductsContainsText(By searchElement, String searchText) {
        List<WebElement> elements = driver.findElements(searchElement);
        int counter=0;
        // Check each element's title for the specified text
        if(!elements.isEmpty()) {
            for (WebElement element : elements) {
                String title = element.getText().toLowerCase();
                if (title.contains(searchText)) {
                    counter++;
                } else {
                    logger.info(searchText + " not found in product " + title);
                }
            }
        }
        return counter==elements.size();
    }
}
