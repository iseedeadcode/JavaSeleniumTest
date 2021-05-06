package pages;
import org.openqa.selenium.*;


import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.ArrayList;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.*;



public class SearchPage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"twotabsearchtextbox\"]")
    WebElement searchField;

    @FindBy(css = ".nav-search-field")
    WebElement searchFieldInput;

    By searchResult = By.cssSelector(".s-include-content-margin.s-border-bottom.s-latency-cf-section");

    public SearchPage (WebDriver driver){
        this.driver=driver;

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }
    class SearchData {
        final String url;
        final int price;

        SearchData(String url, int price) {
            this.url = url;
            this.price = price;
        }
    }
    public void searchLowestPriceAndOpen(String model, String gb){
        List<WebElement> Href  = driver.findElements(searchResult);
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
}