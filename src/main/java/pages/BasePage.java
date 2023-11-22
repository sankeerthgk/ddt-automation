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

public class BasePage {
    protected WebDriver driver;
    private Logger logger = LoggerFactory.getLogger(BasePage.class.getName());

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void initializeURL(String URL) {
        if (!driver.getCurrentUrl().contains(URL)) {
            driver.get(URL);
        }
    }

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

    private static List<WebElement> hasChildElement(WebElement parentElement, By childElementLocator) {
        return parentElement.findElements(childElementLocator);
    }

    public void scrollToElementAndClick(By elementLocator) {
        WebElement element = getElement(elementLocator);
        scrollToElement(element);
        element.click();
    }

    public void scrollToElementAndClick(WebElement element) {
        scrollToElement(element);
        element.click();
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void waitForElementToBeDisplayed(By locator, int timeoutInSeconds) {
        WebElement element = getElement(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.of(timeoutInSeconds, ChronoUnit.SECONDS));
        if(element == null) {
            logger.error("Element not present");
        } else {
            wait.until(ExpectedConditions.visibilityOf(element));
        }
    }

    public WebElement getElement(By locator) {
        try {
            return driver.findElement(locator);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            // Element not found
            logger.error(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public void waitForSeconds(int seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public void selectFirstValueFromDropDown(WebElement dropdownElement) {
        Select dropdown = new Select(dropdownElement);
        // Select the first element
        dropdown.selectByIndex(1);
    }


}