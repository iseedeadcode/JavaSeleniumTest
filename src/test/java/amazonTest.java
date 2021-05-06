import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.*;
import pages.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.ArrayList;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import pages.SearchPage;
import pages.CartPage;

import java.util.concurrent.TimeUnit;
import java.io.ByteArrayInputStream;


import static org.junit.jupiter.api.Assertions.assertTrue;



public class amazonTest {
    public static WebDriver driver;

    @BeforeAll
    public static void openBrowser(){
        System.setProperty("webdriver.chrome.driver","C:\\WebDriver\\bin\\chromedriver.exe");

        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @AfterAll
    public static void closeBrowser(){
        driver.quit();
    }

//    public static void login(String email, String password){
//        driver.findElement(By.cssSelector(".nav-line-1-container")).click();
//        driver.findElement(By.cssSelector("#ap_email")).click();
//        driver.findElement(By.cssSelector("#ap_email")).sendKeys(email + Keys.ENTER);
//        driver.findElement(By.cssSelector("#ap_password")).sendKeys(password + Keys.ENTER);
//    }
    @Test()
    @DisplayName("Amazon Search Test")
    public void amazonSearchTest() throws ParseException {
//        login("test", "testPass");
        HomePage home = new HomePage(driver);
        SearchPage searchePage = new SearchPage(driver);
        ProductViewPage productViewPage = new ProductViewPage(driver);
        CartPage cartPage = new CartPage(driver);
        home.performSearch("iphone 11 256Gb black");
        searchePage.searchLowestPriceAndOpen("iphone 11", "256gb");
        productViewPage.addToCart();
        productViewPage.declineCoverage();
        productViewPage.navigateToCart();
        cartPage.openDropDown();
        cartPage.selectItemFromDropDown();
        String emptyString = cartPage.getTextFromEmptyTitle();
        assertTrue(emptyString.contains("Your Amazon basket is empty"));


    }
}
