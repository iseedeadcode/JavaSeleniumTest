package pages;
import org.openqa.selenium.*;


import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage {
    private WebDriver driver;
    private static String PAGE_URL="https://www.amazon.co.uk";

    @FindBy(xpath = "//*[@id=\"twotabsearchtextbox\"]")
    WebElement searchField;

    @FindBy(css = ".nav-search-field")
    WebElement searchFieldInput;

    public HomePage (WebDriver driver){
        this.driver=driver;
        driver.get(PAGE_URL);
        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public void performSearch(String searchInput) {
        searchField.click();
        searchFieldInput.click();
        searchField.sendKeys(searchInput + Keys.ENTER);
    }
}