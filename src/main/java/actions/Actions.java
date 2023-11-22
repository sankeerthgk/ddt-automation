package actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.List;

public class Actions extends BasePage {

    public Actions(WebDriver driver) {
        super(driver);
    }

    public boolean iterateThroughPagesAndLookForTitle(By nextPageButton, By pagesElement, By productElement, String searchText) {
        List<WebElement> pageLinks = driver.findElements(pagesElement);
        int totalPages= Integer.parseInt(pageLinks.get(pageLinks.size()-2).getText())-1;
        // Traverse through each page
        int count = 0;
        for (int i=0; i< totalPages; i++) {
            // Perform actions on the current page
            if (checkAllProductsContainsText(productElement, searchText))
                count++;
            // Click on the next page link
            scrollToElementAndClick(nextPageButton);
        }
        return count==pageLinks.size();
    }

    public boolean checkAllProductsContainsText(By searchElement, String searchText) {
        List<WebElement> elements = driver.findElements(searchElement);
        int counter=0;
        // Check each element's title for the specified text
        for (WebElement element : elements) {
            String title = element.getText();
            if (title != null && title.contains(searchText)) {
                counter++;
            }
        }
        return counter==elements.size();
    }
}