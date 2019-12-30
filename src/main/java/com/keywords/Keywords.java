package com.keywords;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.SkipException;

import com.excelreader.ExcelReader;
import com.testbase.TestBase;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Keywords extends TestBase {

	static ExcelReader excelformultilingual;
	
	public void execute(ExcelReader excel, String testCaseName) {

		loadProperties();
		
		for (int h = 1; h < excel.getRowCount("TestSteps")+1; h++) {

			System.out.println(excel.getCellData("TestSteps", "TestCase_Name", h));

			if (excel.getCellData("TestSteps", "TestCase_Name", h).equalsIgnoreCase(testCaseName)) {

				String keywords = excel.getCellData("TestSteps", "Keywords", h);
				String locators = excel.getCellData("TestSteps", "Locators", h);
				String data = excel.getCellData("TestSteps", "Data", h);
				
				System.out.println(data);
				
					switch (keywords) {
					case "openBrowser":
						openBrowser(data);
						break;
					case "navigateURL":
						navigateURL(data);
						break;
					case "click":
						click(locators);
						break;
					case "chooseOption":
						chooseOption(locators,data);
						break;
					case "enterText":
						enterText(locators,data);
						break;
					case "VerifyElementSize":
						VerifyElementSize(locators,data);
						break;
					case "VerifyElementColor":
						VerifyElementColor(locators,data);
						break;
					case "CheckElementPresent":
						CheckElementPresent(locators);
						break;
					case "VerifyElementTextName":
						VerifyElementTextName(locators,data);
						break;
					case "VerifyElementTextBoxSize":
						VerifyElementTextBoxSize(locators,data);
						break;
					case "VerifyIconColor":
						VerifyIconColor(locators,data);
						break;
					default:
						break;
					}
				
			
			}

		}

	}


	private void VerifyIconColor(String locators, String data) {
		
		if(Color.fromString(getWebElement(locators).getCssValue("fill")).asHex().trim().equalsIgnoreCase(data))
			Assert.assertEquals(Color.fromString(getWebElement(locators).getCssValue("fill")).asHex().trim(), data);
		else
			Assert.assertEquals(Color.fromString(getWebElement(locators).getCssValue("fill")).asHex().trim(), data);
		
	}


	private void VerifyElementTextBoxSize(String locators, String data) {
		
		if(String.valueOf(getWebElement(locators).getSize().height).equalsIgnoreCase(data.split("\\|")[0]))
			Assert.assertEquals(String.valueOf(getWebElement(locators).getSize().height), data.split("\\|")[0]);
		else 
			Assert.assertEquals(String.valueOf(getWebElement(locators).getSize().height), data.split("\\|")[1]);
		
	}


	private void VerifyElementTextName(String locators, String data) {
		
		if(getWebElement(locators).getText().equalsIgnoreCase(data))
			Assert.assertEquals(getWebElement(locators).getText(), data);
	}


	private void CheckElementPresent(String locators) {
	
		if(getListOfWebElement(locators).size()!=0)
			Assert.assertTrue(true);
		else
			Assert.assertTrue(false);
	}


	private void VerifyElementColor(String locators, String data) {
		
		
		if(Color.fromString(getWebElement(locators).getCssValue("color")).asHex().trim().equalsIgnoreCase(data))
			Assert.assertEquals(Color.fromString(getWebElement(locators).getCssValue("color")).asHex().trim(), data);
		else
			Assert.assertEquals(Color.fromString(getWebElement(locators).getCssValue("color")).asHex().trim(), data);
		
	}
	
	


	private void VerifyElementSize(String locators, String data) {
		
			if(getWebElement(locators).getCssValue("font-size").equalsIgnoreCase(data))
				Assert.assertEquals(getWebElement(locators).getCssValue("font-size"), data);
			else
				Assert.assertEquals(getWebElement(locators).getCssValue("font-size"), data);
		
		
	}






	private void enterText(String locators, String data) {
		
		getWebElement(locators).sendKeys(data);
	}


	public void openBrowser(String browsername) {

		if (browsername.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			drObj = new ChromeDriver();
		} else if (browsername.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			drObj = new FirefoxDriver();
		} else {
			throw new SkipException("BROWSER IS NOT MATCHING");
		}
	}

	public void navigateURL(String URL) {

		drObj.get(URL);
	}
	
	private void click(String locators) {
	
		getWebElement(locators).click();
	}
	
	private void chooseOption(String locators, String data) {
		
		for(int i=0; i<getListOfWebElement(locators).size();i++) {
			
			if(getListOfWebElement(locators).get(i).getText().equalsIgnoreCase(data)) {
				getListOfWebElement(locators).get(i).click();
				break;
			}
		}
	}
}
