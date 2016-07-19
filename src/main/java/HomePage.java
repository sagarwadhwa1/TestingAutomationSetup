package main.java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;

    private By eleSearchBox = By.cssSelector("input[name='q']");
    private By btnSearch = By.cssSelector("input[value='SEARCH']");
    
    public HomePage(WebDriver driver){
	this.driver = driver;
//	System.out.println("Driver set in home page");
    }
    
    public HomePage enterSearch(String searchString){
	(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(eleSearchBox));
	WebElement searchBox = driver.findElement(eleSearchBox);
	searchBox.sendKeys(searchString);
	
	return this;
    }
    
    public ResultsPage clickSearchButton() throws Exception{
	WebElement searchButton = (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(btnSearch));
	searchButton.click();
	return new ResultsPage(driver);
    }
    
    
    
    

}
