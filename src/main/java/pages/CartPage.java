package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {
    private String URL = "/cart";
    private By emptyCartButton = By.cssSelector(".emptyCartButton");
    private By emptyCartOverlayButton = By.cssSelector(".bg-gray-50 button.border-solid:nth-child(1)");
    private By emptyCartMessage = By.cssSelector(".cartEmpty .empty-cart__text .header-1");

    public CartPage(WebDriver driver) {
        super(driver);
        initializeURL(URL);
    }

    public void emptyCartRemoveAllProducts() {
        driver.findElement(emptyCartButton).click();
        driver.findElement(emptyCartOverlayButton).click();
    }

    public boolean isEmptyCartMessageDisplayed() {
        waitForSeconds(5);
        return driver.findElement(emptyCartMessage).isDisplayed();
    }
}