package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private String URL = "https://www.webstaurantstore.com/";
    private By logo = By.cssSelector(".new-header [data-testid='banner']");
    private By submit = By.cssSelector("[value*='Search']");
    private By searchBox = By.cssSelector("#searchval");

    public HomePage(WebDriver driver) {
        super(driver);
        initializeURL(URL);
    }

    public void searchForItem(String username) {
        driver.findElement(searchBox).sendKeys(username);
        driver.findElement(submit).click();
    }

    public boolean isLogoDisplayed() {
        return driver.findElement(logo).isDisplayed();
    }
}