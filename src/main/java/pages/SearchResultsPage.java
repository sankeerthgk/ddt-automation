package pages;

import actions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage extends BasePage {
    private String URL = "/search";
    public Actions actions = new Actions(driver);
    private By search_results = By.cssSelector(".search__wrap");
    private By itemDescription = By.cssSelector("[data-testid='itemDescription']");
    private By dropdown = By.cssSelector("[data-testid='itemAddCartGrouping']");
    private By lastPage = By.cssSelector("#paging [aria-label*='last page']");
    private By nextButton = By.cssSelector("#paging li [class*='rounded-r-md']");
    private By addToCartButtons = By.cssSelector(".add-to-cart");
    private By cartButton = By.cssSelector("[data-testid='itemAddCart']");
    private By viewCartOnOverlayButton = By.cssSelector(".notification__action a:nth-child(1)");
    private By pages = By.cssSelector("#paging li a");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
        initializeURL(URL);
    }

    public boolean isSearchResultsDisplayed() {
        return driver.findElement(search_results).isDisplayed();
    }

    public boolean checkIfAllProductsContainsTitle(String title) {
        return actions.iterateThroughPagesAndLookForTitle(nextButton, pages, itemDescription, title);
    }

    public void navigateToLastPage() {
        scrollToElementAndClick(lastPage);
    }

    public void addLastProductOnPageToCart() {
        addLastProductToBag(dropdown, addToCartButtons, cartButton);
    }

    public void clickOnViewCartOnOverlay() {
        waitForSeconds(5);
        scrollToElementAndClick(viewCartOnOverlayButton);
    }
}