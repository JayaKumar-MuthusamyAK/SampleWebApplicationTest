package com.testbase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.SkipException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	
	
	public WebDriver drObj;
	
	public Properties prop;
	
	public FileInputStream filein;
	
	public void openBrowser(String browsername) {
		
		if (browsername.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			drObj = new ChromeDriver();
		}
		else if (browsername.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			drObj = new FirefoxDriver();
		}
		else {
			throw new SkipException("BROWSER IS NOT MATCHING");
		}
	}
	
	public void navigateURL(String URL) {
		
		drObj.get(URL);
	}
	
	public void closeBrowser() {
		
		drObj.close();
	}
	
	public void loadProperties() {
		
		prop = new Properties();
		try {
			filein = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\com\\locators\\loginpage.properties");
			prop.load(filein);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public WebElement getWebElement(String locatorstring) {
		
		String locator = prop.getProperty(locatorstring);
		
		if(locator.split(":")[0].contains("id")) {
			return drObj.findElement(By.id(locator.split(":")[1]));
		}
		else if(locator.split(":")[0].contains("tagname")) {
			return drObj.findElement(By.tagName(locator.split(":")[1]));
		}
		else if(locator.split(":")[0].contains("classname")) {
			return drObj.findElement(By.className(locator.split(":")[1]));
		}
		else if(locator.split(":")[0].contains("cssselector")) {
			return drObj.findElement(By.cssSelector(locator.split(":")[1]));
		}
		else if(locator.split(":")[0].contains("name")) {
			return drObj.findElement(By.name(locator.split(":")[1]));
		}
		else if(locator.split(":")[0].contains("linktext")) {
			return drObj.findElement(By.linkText(locator.split(":")[1]));
		}
		else if(locator.split(":")[0].contains("partiallinktext")) {
			return drObj.findElement(By.partialLinkText(locator.split(":")[1]));
		}
		else if(locator.split(":")[0].contains("xpath")) {
			return drObj.findElement(By.xpath(locator.split(":")[1]));
		}
		else
			throw new Error("ELEMENT VALUE IS NOT MATCHED");
	
	}
	
	public List<WebElement> getListOfWebElement(String locatorstring) {
		
		String locator = prop.getProperty(locatorstring);
		
		if(locator.split(":")[0].contains("id")) {
			return drObj.findElements(By.id(locator.split(":")[1]));
		}
		else if(locator.split(":")[0].contains("tagname")) {
			return drObj.findElements(By.tagName(locator.split(":")[1]));
		}
		else if(locator.split(":")[0].contains("classname")) {
			return drObj.findElements(By.className(locator.split(":")[1]));
		}
		else if(locator.split(":")[0].contains("cssselector")) {
			return drObj.findElements(By.cssSelector(locator.split(":")[1]));
		}
		else if(locator.split(":")[0].contains("name")) {
			return drObj.findElements(By.name(locator.split(":")[1]));
		}
		else if(locator.split(":")[0].contains("linktext")) {
			return drObj.findElements(By.linkText(locator.split(":")[1]));
		}
		else if(locator.split(":")[0].contains("partiallinktext")) {
			return drObj.findElements(By.partialLinkText(locator.split(":")[1]));
		}
		else if(locator.split(":")[0].contains("xpath")) {
			return drObj.findElements(By.xpath(locator.split(":")[1]));
		}
		else
			throw new Error("LIST OF ELEMENT VALUE IS NOT MATCHED");
	
	}
	
	

}
