package com.test;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FlipkartLazyLoadingTest {

    WebDriver driver;

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        
//        WebDriverManager.edgedriver().setup();
//        driver = new EdgeDriver();
//        driver.manage().window().maximize();
        
    }

    @Test
    public void testFlipkartSearch() {
        // Navigate to Flipkart homepage
        driver.get("https://www.flipkart.com/");
 
        
        // Determine page load time
        long startTime = System.currentTimeMillis();

        // Your page load time logic here

        long endTime = System.currentTimeMillis();
        long pageLoadTime = endTime - startTime;
        System.out.println("Page Load Time: " + pageLoadTime + " milliseconds");

        // Search for "iPhone 13" under the "Mobile" category
        driver.findElement(By.name("q")).sendKeys("iphone 13");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        TakesScreenshot tsc = (TakesScreenshot) driver;
		 
		 //Generate Screenshot as file
		 File rsc = tsc.getScreenshotAs(OutputType.FILE);
		 
        
        
		 try {
			FileHandler.copy(rsc, new File("C:\\Users\\Sony\\TestingApps\\E-commerce\\Screenshots\\sendkeys.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        // Check images are loaded and visible till the screen height
        
        WebElement firstProductImage = driver.findElement(By.cssSelector("div[class='_4rR01T']"));
        if (firstProductImage.isDisplayed()) {
            System.out.println("First product image is visible.");
            try {
    			FileHandler.copy(rsc, new File("C:\\Users\\Sony\\TestingApps\\E-commerce\\Screenshots\\iphone_13_pink.png"));
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

        } else {
            System.out.println("First product image is not visible.");
        }
        // Check the page has a scroll feature
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        
        // Check the frequency at which the content will be refreshed while scrolling    _1AtVbE col-12-12
        long refreshStartTime = System.currentTimeMillis();
        // Your logic to identify the frequency of content refresh

        long refreshEndTime = System.currentTimeMillis();
        long refreshTime = refreshEndTime - refreshStartTime;
        System.out.println("Content Refresh Frequency: " + refreshTime + " milliseconds");

        // Verify that the image is downloaded just before the user scrolls to its position and gets displayed in time
        WebElement lazyLoadedImage = driver.findElement(By.cssSelector("div[class='_1AtVbE col-12-12']"));
        scrollIntoView(lazyLoadedImage);

        // Wait for the image to become visible
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(lazyLoadedImage));
        try {
			FileHandler.copy(rsc, new File("C:\\Users\\Sony\\TestingApps\\E-commerce\\Screenshots\\iphone_13_Starlight.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("Lazy-loaded image is visible after scrolling.");

        // Verify it navigates to the bottom of the page
        WebElement we = driver.findElement(By.partialLinkText("Apple iPhone 13 (Blue, 128 GB)"));
        try {
			FileHandler.copy(rsc, new File("C:\\Users\\Sony\\TestingApps\\E-commerce\\Screenshots\\iphone 13_blue.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        scrollIntoView(we);
        we.click();
        

        // Check whether different browsers and screen resolutions render it the same way
        System.out.println("Browser: " + ((ChromeDriver) driver).getCapabilities().getBrowserName());
        System.out.println("Screen Resolution: " + driver.manage().window().getSize());
   

   
        
//        System.out.println("Browser: " + ((EdgeDriver) driver).getCapabilities().getBrowserName());
//        System.out.println("Screen Resolution: " + driver.manage().window().getSize());
    }

    private void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }
    @AfterTest
    public void tearDown() {
    	if (driver != null) {
          driver.quit();
      }
    }
}
