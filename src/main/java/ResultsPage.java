package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultsPage {
    private WebDriver driver;

    private By eleSearchBox = By.cssSelector("input[name='q']");
    private By btnSearch = By.cssSelector("input[value='SEARCH']");
    private By eleBrandsContainer = By.id("brand");
    private By imgLoadingSpinner = By.cssSelector("div#browse div.loadingWrapper img[alt=Loading]");
    private By imgLoadingMoreSpinner = By.cssSelector("div#load-more-results img");
    private By cbxAvailability = By.cssSelector("div.body ul#availability:not(.scroll) input");
    private By eleShowMore = By.cssSelector("div#show-more-results[style]");
    private By colProducts = By.cssSelector("div.product-unit");
    
    public ResultsPage(WebDriver driver) throws Exception{
	this.driver = driver;
	if(!this.driver.getCurrentUrl().contains("search?q=")){
	    throw new Exception("Wrong page. Page url '"+this.driver.getCurrentUrl()+"' doesn't contain search?q=");
	}
//	System.out.println("Driver set in results page");
    }
    
    public ResultsPage enterSearch(String searchString){
	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(eleSearchBox));
	WebElement searchBox = driver.findElement(eleSearchBox);
	searchBox.sendKeys(searchString);
	
	return this;
    }
    
    public ResultsPage setBrandFilter(String brandName) throws Exception {
	WebElement brandsContainer = driver.findElement(eleBrandsContainer);
	WebElement brand = brandsContainer.findElement(By.cssSelector("li[title="+brandName+"]"));
	if(brand != null){
	    WebElement checkbox = brand.findElement(By.cssSelector("a>input"));
	    checkbox.click();
	    waitForSearchResultsToAppear();
	}
	else{
	    throw new Exception("Cannot find the specified brand '"+brandName+"' in the list of brands");
	}
	
	return this;
    }

    private void waitForSearchResultsToAppear() {
	(new WebDriverWait(driver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(imgLoadingSpinner));
    }
    
    public ResultsPage setExcludeOutOfStock() {
	WebElement checkbox = driver.findElement(cbxAvailability);
	checkbox.click();
	waitForSearchResultsToAppear();
	return this;
    }
    
    public List<SearchResults> getVisibleProducts() {
	List<WebElement> products = driver.findElements(colProducts);
	List<SearchResults> results = new ArrayList<>();
	
	for(WebElement product:products){
	    WebElement link = product.findElement(By.cssSelector("div.pu-title a"));
	    String name = link.getText().trim();
	    String url = link.getAttribute("href");
	    String price = product.findElement(By.cssSelector("div.product-unit div.pu-final span")).getText().trim().replaceAll("Rs\\. ", "").replaceAll(",","");
	    long longPrice = Long.parseLong(price);
	    results.add(new SearchResults(longPrice, name, url));
	}
	
	return results;
    }
    
    public ResultsPage getMoreProducts() {
	new Actions(driver).sendKeys(Keys.PAGE_DOWN).sendKeys(Keys.PAGE_DOWN).sendKeys(Keys.PAGE_DOWN).build().perform();
	(new WebDriverWait(driver, 30)).until(ExpectedConditions.invisibilityOfElementLocated(imgLoadingMoreSpinner));
	return this;
    }
    
    
    
    
    
    
    
    

}
 