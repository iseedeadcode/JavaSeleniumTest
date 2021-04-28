import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.*;
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
import java.util.concurrent.TimeUnit;
import java.io.ByteArrayInputStream;


import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchData {
    final String url;
    final int price;

    SearchData(String url, int price) {
        this.url = url;
        this.price = price;
    }
}

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
    public static void waitAndClick(String selector){
        WebElement element;
        boolean elementDisplayed = driver.findElement(By.cssSelector(selector)).isDisplayed();
        if(elementDisplayed){
            element = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
            element.click();
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Allure.addAttachment(selector, new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

    }
    public static void navigateToCart(){

        if(driver.findElement(By.cssSelector("#hlb-view-cart-announce")).isDisplayed()){

            waitAndClick("#hlb-view-cart-announce");
        }
        else{
            waitAndClick("#attach-view-cart-button-form");

        }
    }
    public static void login(String email, String password){
        driver.findElement(By.cssSelector(".nav-line-1-container")).click();
        driver.findElement(By.cssSelector("#ap_email")).click();
        driver.findElement(By.cssSelector("#ap_email")).sendKeys(email + Keys.ENTER);
        driver.findElement(By.cssSelector("#ap_password")).sendKeys(password + Keys.ENTER);
    }
    public static void search(String searchtext){
        driver.findElement(By.xpath("//*[@id=\"twotabsearchtextbox\"]")).click();
        driver.findElement(By.cssSelector(".nav-search-field")).click();
        driver.findElement(By.xpath("//*[@id=\"twotabsearchtextbox\"]")).sendKeys(searchtext + Keys.ENTER);
    }
    public static void searchLowestPriceAndOpen(String model, String gb){
        List<WebElement> Href  = driver.findElements(By.cssSelector(".s-include-content-margin.s-border-bottom.s-latency-cf-section"));
        ArrayList<SearchData> resultMap = new ArrayList<>();
        for (WebElement temp : Href) {

            Pattern name = Pattern.compile(model, Pattern.CASE_INSENSITIVE);
            Pattern gigabytes = Pattern.compile(gb, Pattern.CASE_INSENSITIVE);
            Pattern price = Pattern.compile("[£][\\d]+", Pattern.CASE_INSENSITIVE);
            Matcher matcherGB = gigabytes.matcher(temp.getText());
            Matcher matchName = name.matcher(temp.getText());
            Matcher matchPrice = price.matcher(temp.getText());
            boolean matchFoundGB = matcherGB.find();
            boolean matchFoundName = matchName.find();

            if(matchFoundGB && matchFoundName) {

                List<String> listMatches = new ArrayList<String>();
                while(matchPrice.find())
                {
                    listMatches.add(matchPrice.group(0));
                }

                for(String s : listMatches)
                {
                    String newOne = s.replaceAll("[^\\d\\.\\,\\s]+","");
                    int tst = Integer.parseInt(newOne);
                    WebElement searchBox = temp.findElement(By.cssSelector(".a-link-normal.a-text-normal"));
                    resultMap.add(new SearchData(searchBox.getAttribute("href"), tst));
                }

            }

        }
        resultMap.sort(Comparator.comparingInt(searchData -> searchData.price));

        SearchData lowestPriceUrl = resultMap.get(0);
        driver.get(lowestPriceUrl.url);
    }
    @Test()
    @DisplayName("Amazon Search Test")
    public void amazonSearchTest() throws ParseException {
        driver.get("https://amazon.co.uk");
//        login("test", "testPass");
        search("iphone 11 256Gb black");
        searchLowestPriceAndOpen("iphone 11", "256gb");
        waitAndClick("#add-to-cart-button");
        waitAndClick("#attachSiNoCoverage");
        navigateToCart();
        waitAndClick(".a-dropdown-label");
        waitAndClick("#dropdown1_0");
        String emptyString = driver.findElement(By.cssSelector(".a-row.sc-your-amazon-cart-is-empty")).getText();
        assertTrue(emptyString.contains("Your Amazon basket is empty"));


    }
}
