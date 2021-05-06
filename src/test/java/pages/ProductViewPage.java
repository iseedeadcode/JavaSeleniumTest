package pages;
import org.openqa.selenium.*;


import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Helper;


public class ProductViewPage {
    private WebDriver driver;
    Helper helper = new Helper(driver);

    @FindBy(css = "#add-to-cart-button")
    WebElement addToCart;

    @FindBy(css = "#attachSiNoCoverage")
    WebElement declineCoverage;

    @FindBy(css = "#hlb-view-cart-announce")
    WebElement openCartv1;

    @FindBy(css = "#attach-view-cart-button-form")
    WebElement openCartv2;

    public ProductViewPage (WebDriver driver){
        this.driver=driver;

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }
    public void addToCart(){
        helper.waitAndClick(addToCart);
    }

    public void declineCoverage(){
        helper.waitAndClick(declineCoverage);
    }

    public void navigateToCart(){

        if(openCartv1.isDisplayed()){

            helper.waitAndClick(openCartv1);
        }
        else{
            helper.waitAndClick(openCartv2);

        }
    }
}
