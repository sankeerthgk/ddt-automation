package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Base {

    public WebDriver driver;
    public Properties properties;
    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeTest
    public void setUp() throws IOException {
        properties = new Config().loadConfig();
        String browserName = properties.getProperty("browser");

        if (browserName.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserName.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equals("ie")) {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        } else {
            System.out.println(browserName + " is not a valid browser");
        }
        driver.manage().window().maximize();
        initializeReport();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            // Capture a screenshot if the test fails
            captureScreenshot(result.getName());
            test = extent.createTest(result.getName(), "Test Fail");
        } else {
            test = extent.createTest(result.getName(), "Test Pass");
        }
        // Close the browser
        driver.quit();
        // Generate the report
        extent.flush();
    }

    private void captureScreenshot(String testName) {
        // Convert WebDriver object to TakesScreenshot
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;

        // Get the screenshot as File
        File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);

        // Define the destination file path
        String destFilePath = "screenshots/" + testName + ".png";

        try {
            // Copy the screenshot file to the destination
            FileUtils.copyFile(screenshot, new File(destFilePath));
            System.out.println("Screenshot captured: " + destFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeReport(){
        htmlReporter =  new ExtentHtmlReporter(System.getProperty("user.dir")+"/Reports/extentReport.html");
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("report");
        htmlReporter.config().setTheme(Theme.STANDARD);
        extent =new ExtentReports();
        extent.attachReporter(htmlReporter);
    }
}
