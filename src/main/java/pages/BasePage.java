package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Base Page to initialize objects for a page
 */
public class BasePage {
    protected WebDriver driver;
    private final Logger logger = LoggerFactory.getLogger(BasePage.class.getName());

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Initialize URL on any page
     * @param URL
     */
    public void initializeURL(String URL) {
        if (!driver.getCurrentUrl().contains(URL)) {
            driver.get(URL);
        }
    }

    /**
     * Adds last product on a page to bag
     * @param dropdown
     * @param productElements
     * @param addToCartButton
     */
    public void addLastProductToBag(By dropdown, By productElements, By addToCartButton) {
        List<WebElement> products = driver.findElements(productElements);
        WebElement lastProduct = products.get(products.size()-1);
        scrollToElement(lastProduct);
        // Check if last product has dropdown
        List<WebElement> productDropdowns = lastProduct.findElements(dropdown);
        if (!productDropdowns.isEmpty()) {
            // Select first value from dropdown if it exists
            selectFirstValueFromDropDown(productDropdowns.get(0));
        }
        lastProduct.findElement(addToCartButton).click();

    }

    /**
     * Scroll to an element and click
     * @param elementLocator
     */
    public void scrollToElementAndClick(By elementLocator) {
        WebElement element = getElement(elementLocator);
        scrollToElement(element);
        element.click();
    }

    /**
     * Scroll to element and click
     * @param element
     */
    public void scrollToElementAndClick(WebElement element) {
        scrollToElement(element);
        element.click();
    }

    /**
     * Scroll to element
     * @param element
     */
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Wait for an element to be displayed on page
     * @param locator
     * @param timeoutInSeconds
     */
    public void waitForElementToBeDisplayed(By locator, int timeoutInSeconds) {
        WebElement element = getElement(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(timeoutInSeconds, ChronoUnit.SECONDS));
        if(element == null) {
            logger.error("Element not present");
        } else {
            wait.until(ExpectedConditions.visibilityOf(element));
        }
    }

    /**
     * Get webelement from locator
     * @param locator
     * @return
     */
    public WebElement getElement(By locator) {
        try {
            return driver.findElement(locator);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            // Element not found
            logger.error(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    /**
     * Implicitly wait for seconds
     * @param seconds
     */
    public void waitForSeconds(int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    /**
     * Select the first value from a dropdown
     * @param dropdownElement
     */
    public void selectFirstValueFromDropDown(WebElement dropdownElement) {
        Select dropdown = new Select(dropdownElement);
        // Select the first element
        dropdown.selectByIndex(1);
    }


}