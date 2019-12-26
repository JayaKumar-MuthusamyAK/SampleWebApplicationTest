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
import org.testng.SkipException;

import com.excelreader.ExcelReader;
import com.testbase.TestBase;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Keywords extends TestBase {

	ExcelReader excelformultilingual = new ExcelReader(System.getProperty("user.dir")+"\\InputFiles\\MultiligualTestData.xlsx");
	
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
					case "checkMultilanguageTextChecker":
						checkMultilanguageTextChecker(data.split("\\|")[0], data.split("\\|")[1]);
						break;
					default:
						break;
					}
				
			
			}

		}

	}


	private void checkMultilanguageTextChecker(String languageName, String selectedPage) {
		

		
		for(int j=2; j<=excelformultilingual.getRowCount(selectedPage);j++) {
			
			String elementTypeFromExcel = excelformultilingual.getCellData(selectedPage, "Element_Type", j);
			
			String languageSelected = excelformultilingual.getCellData(selectedPage, languageName, j);
			
			String elementColorFromExcel = excelformultilingual.getCellData(selectedPage, "Element_Color", j);
			
			String elementSizeFromExcel = excelformultilingual.getCellData(selectedPage, "Element_Size", j);
			
			WebElement element = getWebElement(excelformultilingual.getCellData(selectedPage, "Locator", j));
			
			switch (elementTypeFromExcel) {
			case "label":
			case "button":
			case "header_text":
			case "paragraph":
			case "buttonlabel":
			case "text_box":
			case "drop_down_list":
			case "menu":
				checkSelectedPageTextValue(element, elementTypeFromExcel, languageSelected);
				checkElementBackGroundColorValue(element, elementColorFromExcel, elementTypeFromExcel);
				checkElementSize(element, elementSizeFromExcel, elementTypeFromExcel);
				break;
			case "img":
				compareTwoImages(element, languageSelected);
				break;

			default:
				break;
			}
		}
	
		
	}


	private void checkElementSize(WebElement element, String elementSizeFromExcel, String elementTypeFromExcel) {
		
		//System.out.println("*******************************ELEMENT SIZE VERIFY METHOD************************************");
		
		
		if (elementTypeFromExcel.equalsIgnoreCase("button")|| elementTypeFromExcel.equalsIgnoreCase("buttonlabel") ||
				elementTypeFromExcel.equalsIgnoreCase("label") || elementTypeFromExcel.equalsIgnoreCase("header_text")){
			
			if (elementSizeFromExcel.split("\\|")[0].equalsIgnoreCase(element.getCssValue("font-size"))&&
					convertElementSizeBoldTextIntoNumber(elementSizeFromExcel).equalsIgnoreCase(element.getCssValue("font-weight")))
				
				System.out.println("PASS VERIFIED Element Size is : " + element.getCssValue("font-size") +" Element Weight is :"+element.getCssValue("font-weight"));
			else
				System.out.println("Fail => NOT VERIFIED " + element.getCssValue("font-weight"));

		} else if (elementTypeFromExcel.equalsIgnoreCase("text_box")) {

			if(elementSizeFromExcel.split("\\|")[0].equalsIgnoreCase(String.valueOf(element.getSize().height))&&
					elementSizeFromExcel.split("\\|")[1].equalsIgnoreCase(String.valueOf(element.getSize().width)))
				
				System.out.println("Text box height and width verified : "+element.getSize().height+ " - "+element.getSize().width);
			else 
				System.out.println("Text box height and width value not verfied ");
		}
		else if(elementTypeFromExcel.equalsIgnoreCase("drop_down_list")) {
			
			if(elementSizeFromExcel.equalsIgnoreCase(element.getCssValue("font-size")))
				System.out.println("PASS => VERIFIED Drop down list element is "+element.getCssValue("font-size"));
			else
				System.out.println("Fail => NOT VERIFIED " + element.getCssValue("font-size"));
		}else if(elementTypeFromExcel.equalsIgnoreCase("paragraph")) {
			
			if(elementSizeFromExcel.equalsIgnoreCase(element.getCssValue("font-size")))
				System.out.println("PASS => VERIFIED Drop down list element is "+element.getCssValue("font-size"));
			else
				System.out.println("Fail => NOT VERIFIED " + element.getCssValue("font-size"));
		}
		else if (elementTypeFromExcel.equalsIgnoreCase("menu")) {

			if(elementSizeFromExcel.split("\\|")[0].equalsIgnoreCase(String.valueOf(element.getSize().height))&&
					elementSizeFromExcel.split("\\|")[1].equalsIgnoreCase(String.valueOf(element.getSize().width)))
				
				System.out.println("MENU height and width verified : "+element.getSize().height+ " - "+element.getSize().width);
			else 
				System.out.println("MENU height and width value not verfied "+element.getSize().height+ " - "+element.getSize().width);
		}
		
	}

	private static String convertElementSizeBoldTextIntoNumber(String elementSizeFromExcel) {
		
		if(elementSizeFromExcel.split("\\|")[1].equalsIgnoreCase("bold !important")||elementSizeFromExcel.split("\\|")[1].equalsIgnoreCase("bold"))
			return "700";
		else if(elementSizeFromExcel.split("\\|")[1].equalsIgnoreCase("normal"))
			return "400";
		else
			return elementSizeFromExcel.split("\\|")[1];
	
	}

	private void checkElementBackGroundColorValue(WebElement element, String elementColorFromExcel, String elementTypeFromExcel) {
		
		//System.out.println("*******************************ELEMENT COLOR VERIFY METHOD************************************");
		
		if(elementTypeFromExcel.equalsIgnoreCase("button")||elementTypeFromExcel.equalsIgnoreCase("buttonlabel")) {
			
			if(Color.fromString(element.getCssValue("background-color")).asHex().trim().equalsIgnoreCase(elementColorFromExcel.trim()))
				System.out.println("PASS => VERIFIED ELEMENT COLOR "+Color.fromString(element.getCssValue("background-color")).asHex());
			else
				System.out.println("Fail => NOT VERIFIED "+element.getText());
		}
		else if(elementTypeFromExcel.equalsIgnoreCase("label")||elementTypeFromExcel.equalsIgnoreCase("header_text")
				||elementTypeFromExcel.equalsIgnoreCase("drop_down_list")||elementTypeFromExcel.equalsIgnoreCase("paragraph")||elementTypeFromExcel.equalsIgnoreCase("menu")){
			
			if(Color.fromString(element.getCssValue("color")).asHex().trim().equalsIgnoreCase(elementColorFromExcel.trim()))
				System.out.println("PASS => VERIFIED ELEMENT TEXT COLOR "+Color.fromString(element.getCssValue("color")).asHex());
			else
				System.out.println("Fail => NOT VERIFIED "+Color.fromString(element.getCssValue("color")).asHex());
		}
		
	}


	private void compareTwoImages(WebElement element, String languageSelected) {
		
		System.out.println("Image is downloading....");
		BufferedImage bufferImage;
		try {
			bufferImage = ImageIO.read(new URL(element.getAttribute("src")));
			ImageIO.write(bufferImage, "png", new File(System.getProperty("user.home")+"\\Downloads\\hey.png"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String filepathfordownloaded = System.getProperty("user.home")+"\\Downloads\\hey.png";
		try {
			if(compareImages(filepathfordownloaded,languageSelected)==true)
				System.out.println("BOTH IMAGES IS MATCHED : PASS");
			else
				System.out.println("BOTH IMAGES IS NOT MATCHED : FAIL");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static boolean compareImages(String filepathfordownloaded, String comparingfilepath) throws MalformedURLException, IOException {
		
		BufferedImage bufdownimage = ImageIO.read(new File(filepathfordownloaded));
		BufferedImage bufcomimage = ImageIO.read(new File(comparingfilepath));
		
		if(bufdownimage.getHeight()==bufcomimage.getHeight()&&bufdownimage.getWidth()==bufcomimage.getWidth())
				return true;
		else
				return false;
		
	}


	private void checkSelectedPageTextValue(WebElement element, String elementTypeFromExcel,String languageSelected) {
		
		//System.out.println("*******************************ELEMENT MULTIPLE LANGUAGE VALUE VERIFY METHOD************************************");
		
		if(elementTypeFromExcel.equalsIgnoreCase("label")) {
			
			if(element.getText().trim().equalsIgnoreCase(languageSelected.trim()))
				System.out.println("Pass => VERIFIED "+element.getText());
			else
				System.out.println("Fail => NOT VERIFIED "+element.getText());
		}
		else if(elementTypeFromExcel.equalsIgnoreCase("button")) {
			
			if(element.getText().equalsIgnoreCase(languageSelected.trim())) 
				
				System.out.println("Pass => VERIFIED "+element.getText());
			
			else
				System.out.println("Fail => NOT VERIFIED "+element.getText());
		}
		else if(elementTypeFromExcel.equalsIgnoreCase("buttonlabel")) {
			
			if(element.getAttribute(KeywordConfig.attributeName).equalsIgnoreCase(languageSelected.trim())) 
				
				System.out.println("Pass => VERIFIED "+element.getAttribute("aria-label"));
			
			else
				System.out.println("Fail => NOT VERIFIED "+element.getAttribute("aria-label"));
		}
		else if(elementTypeFromExcel.equalsIgnoreCase("header_text")) {
			
			if(element.getText().trim().equalsIgnoreCase(languageSelected.trim()))
				System.out.println("PASS => VERIFIED "+element.getText());
			else
				System.out.println("FAIL => NOT VERIFIED "+languageSelected);
		}
		
		else if(elementTypeFromExcel.equalsIgnoreCase("text_box")) {
			
			if(element.getAttribute(KeywordConfig.attributeName).equalsIgnoreCase(languageSelected.trim())) {
			
				System.out.println("Pass => VERIFIED "+element.getAttribute("aria-label"));}
			else
				System.out.println("Fail => NOT VERIFIED "+element.getAttribute("aria-label"));
		}
		else if(elementTypeFromExcel.equalsIgnoreCase("paragraph")) {
			
			if(element.getText().equalsIgnoreCase(languageSelected.trim()))
				System.out.println("Pass => VERIFIED "+element.getText());
			else
				System.out.println("Fail => NOT VERIFIED "+element.getText());
		}
		else if(elementTypeFromExcel.equalsIgnoreCase("drop_down_list")) {
			
			Select select = new Select(element);

			List<WebElement> drop_down_list = select.getOptions();

			String[] split_options = languageSelected.split("\\|");

			for (int opt = 0; opt < split_options.length; opt++) {

				if (split_options[opt].equalsIgnoreCase(drop_down_list.get(opt).getText())) {

					System.out.println("Pass => VERIFIED " + drop_down_list.get(opt).getText());
				} else {

					System.out.println("Fail => NOT PRESENT " + drop_down_list.get(opt).getText());
				}
			}
		}
		
		
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
