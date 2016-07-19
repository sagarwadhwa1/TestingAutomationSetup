package test.java;

import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import main.java.HomePage;
import main.java.SearchResults;

public class TestClass {
    private WebDriver driver;
    List<SearchResults> results = null;

    
    @BeforeTest
    public void goToHomePage(){
	System.setProperty("webdriver.chrome.driver", "" + System.getProperty("user.dir") +  "\\lib\\chromedriver.exe");
	DesiredCapabilities capabilities = DesiredCapabilities.chrome();
	ChromeOptions co = new ChromeOptions();
	co.addArguments(new String[] { "--disable-extensions" });
	capabilities.setCapability(ChromeOptions.CAPABILITY, co);
	driver = new ChromeDriver(capabilities);
	driver.manage().window().maximize();
	driver.get("https://www.flipkart.com");
//	System.out.println("Home Page loaded");
	
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void enterSearch() throws Exception {
	results =  new HomePage(driver).enterSearch("iPhone 6").clickSearchButton().setBrandFilter("Apple").setExcludeOutOfStock().getMoreProducts().getVisibleProducts();
	Collections.sort(results);
    }
    
    @AfterTest 
    public void printList() {
	for(SearchResults result : results){
	    System.out.println(result.toString());
	}
	driver.close();
	driver.quit();
    }
    
}
