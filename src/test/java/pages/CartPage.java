package pages;

import org.openqa.selenium.*;
import utils.Helper;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
    private WebDriver driver;
    Helper helper = new Helper(driver);

    @FindBy(css = ".a-row.sc-your-amazon-cart-is-empty")
    WebElement emptyCartTitle;

    @FindBy(css = ".a-dropdown-label")
    WebElement dropDown;

    @FindBy(css = "#dropdown1_0")
    WebElement firstItemInDropDown;

    public CartPage (WebDriver driver){
        this.driver=driver;

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }
    public void openDropDown(){
        helper.waitAndClick(dropDown);
    }
    public String getTextFromEmptyTitle(){
        String text = emptyCartTitle.getText();
        return text;
    }
    public void selectItemFromDropDown(){
        helper.waitAndClick(firstItemInDropDown);
    }
}
