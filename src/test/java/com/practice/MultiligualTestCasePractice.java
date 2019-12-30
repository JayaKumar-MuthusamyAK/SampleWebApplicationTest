package com.practice;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;

import com.excelreader.ExcelReader;
import com.keywords.KeywordConfig;

public class MultiligualTestCasePractice {

	static Properties prop;
	static FileInputStream inputfile;
	
	static WebDriver driver;

	ExcelReader excelformultilingual;
	
	public static void main(String[] args) throws MalformedURLException, IOException {

		ExcelReader inputexcel = new ExcelReader(System.getProperty("user.dir")+"\\InputFiles\\MultiligualTestData.xlsx");
		
		System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");
		
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		driver.get("https://www.facebook.com/");

		selectTheLanguage("English (UK)");
		
		checkSelectedPageTextValue("English (UK)",inputexcel);

		checkElementBackGroundColorValue("English (UK)",inputexcel);
		
		checkElementSizeValue("English (UK)",inputexcel);
		
		
	}
	
	private static void checkElementSizeValue(String string, ExcelReader inputexcel) {
		
		System.out.println("*******************************ELEMENT SIZE VERIFY METHOD************************************");

		for (int h = 2; h <= inputexcel.getRowCount("loginpage"); h++) {

			String elementTypeFromExcel = inputexcel.getCellData("loginpage", "Element_Type", h);
			String elementSizeFromExcel = inputexcel.getCellData("loginpage", "Element_Size", h);

			
			WebElement element = driver.findElement(By.xpath(inputexcel.getCellData("loginpage", "Locator", h)));

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

		}
	}

	private static String convertElementSizeBoldTextIntoNumber1(String elementSizeFromExcel) {
		
		if(elementSizeFromExcel.split("\\|")[1].equalsIgnoreCase("bold !important")||elementSizeFromExcel.split("\\|")[1].equalsIgnoreCase("bold"))
			return "700";
		else if(elementSizeFromExcel.split("\\|")[1].equalsIgnoreCase("normal"))
			return "400";
		else
			return elementSizeFromExcel.split("\\|")[1];
	
	}

	private static void checkElementBackGroundColorValue(String string, ExcelReader inputexcel) {
		
		System.out.println("*******************************ELEMENT COLOR VERIFY METHOD************************************");
		
		for(int h=2; h<=inputexcel.getRowCount("loginpage");h++) {
			
			String elementTypeFromExcel = inputexcel.getCellData("loginpage", "Element_Type", h);
			String elementColorFromExcel = inputexcel.getCellData("loginpage", "Element_Color", h);
			
			WebElement element = driver.findElement(By.xpath(inputexcel.getCellData("loginpage", "Locator", h)));
			
			if(elementTypeFromExcel.equalsIgnoreCase("button")||elementTypeFromExcel.equalsIgnoreCase("buttonlabel")) {
				
				if(Color.fromString(element.getCssValue("background-color")).asHex().trim().equalsIgnoreCase(elementColorFromExcel.trim()))
					System.out.println("PASS => VERIFIED ELEMENT COLOR "+Color.fromString(element.getCssValue("background-color")).asHex());
				else
					System.out.println("Fail => NOT VERIFIED "+element.getText());
			}
			else if(elementTypeFromExcel.equalsIgnoreCase("label")||elementTypeFromExcel.equalsIgnoreCase("header_text")
					||elementTypeFromExcel.equalsIgnoreCase("drop_down_list")||elementTypeFromExcel.equalsIgnoreCase("paragraph")){
				
				if(Color.fromString(element.getCssValue("color")).asHex().trim().equalsIgnoreCase(elementColorFromExcel.trim()))
					System.out.println("PASS => VERIFIED ELEMENT TEXT COLOR "+Color.fromString(element.getCssValue("color")).asHex());
				else
					System.out.println("Fail => NOT VERIFIED "+Color.fromString(element.getCssValue("color")).asHex());
			}
			
		}
		
	}

	private static void checkSelectedPageTextValue(String string, ExcelReader inputexcel) throws MalformedURLException, IOException {
		
		System.out.println("*******************************ELEMENT VALUE VERIFY METHOD************************************");
		
		for(int h=2; h<=inputexcel.getRowCount("loginpage");h++) {
			
			String elementTypeFromExcel = inputexcel.getCellData("loginpage", "Element_Type", h);
			
			WebElement element = driver.findElement(By.xpath(inputexcel.getCellData("loginpage", "Locator", h)));
			
			//System.out.println(element.getText());
			
			if(elementTypeFromExcel.equalsIgnoreCase("label")) {
				
				if(element.getText().trim().equalsIgnoreCase(inputexcel.getCellData("loginpage", string, h).trim()))
					System.out.println("Pass => VERIFIED "+element.getText());
				else
					System.out.println("Fail => NOT VERIFIED "+element.getText());
			}
			else if(elementTypeFromExcel.equalsIgnoreCase("button")) {
				
				if(element.getText().equalsIgnoreCase(inputexcel.getCellData("loginpage", string, h).trim())) 
					
					System.out.println("Pass => VERIFIED "+element.getText());
				
				else
					System.out.println("Fail => NOT VERIFIED "+element.getText());
			}
			else if(elementTypeFromExcel.equalsIgnoreCase("buttonlabel")) {
				
				if(element.getAttribute("aria-label").equalsIgnoreCase(inputexcel.getCellData("loginpage", string, h).trim())) 
					
					System.out.println("Pass => VERIFIED "+element.getAttribute("aria-label"));
				
				else
					System.out.println("Fail => NOT VERIFIED "+element.getAttribute("aria-label"));
			}
			else if(elementTypeFromExcel.equalsIgnoreCase("header_text")) {
				
				if(element.getText().equalsIgnoreCase(inputexcel.getCellData("loginpage", string, h).trim()))
					System.out.println("Pass => VERIFIED "+element.getText());
				else
					System.out.println("Fail => NOT VERIFIED "+element.getText());
			}
			
			else if(elementTypeFromExcel.equalsIgnoreCase("text_box")) {
				
				if(element.getAttribute("aria-label").equalsIgnoreCase(inputexcel.getCellData("loginpage", string, h).trim())) {
				
					System.out.println("Pass => VERIFIED "+element.getAttribute("aria-label"));}
				else
					System.out.println("Fail => NOT VERIFIED "+element.getAttribute("aria-label"));
			}
			else if(elementTypeFromExcel.equalsIgnoreCase("paragraph")) {
				
				if(element.getText().equalsIgnoreCase(inputexcel.getCellData("loginpage", string, h).trim()))
					System.out.println("Pass => VERIFIED "+element.getText());
				else
					System.out.println("Fail => NOT VERIFIED "+element.getText());
			}
			else if(elementTypeFromExcel.equalsIgnoreCase("drop_down_list")) {
				
				Select select = new Select(element);
				
				List<WebElement> drop_down_list = select.getOptions();
				
				String string_options = inputexcel.getCellData("loginpage", string, h);
				
				String[] split_options = string_options.split("\\|");
				
				for(int opt=0; opt<split_options.length;opt++) {
					
					if(split_options[opt].equalsIgnoreCase(drop_down_list.get(opt).getText())) {
						
						System.out.println("Pass => VERIFIED "+drop_down_list.get(opt).getText());
					}
					else {
						
						System.out.println("Fail => NOT PRESENT "+drop_down_list.get(opt).getText());
					}
				}
			}
			else if(elementTypeFromExcel.equalsIgnoreCase("img")) {
				
				System.out.println("Image is downloading....");
				BufferedImage bufferImage = ImageIO.read(new URL(element.getAttribute("src")));
				ImageIO.write(bufferImage, "png", new File(System.getProperty("user.home")+"\\Downloads\\hey.png"));
				String filepathfordownloaded = System.getProperty("user.home")+"\\Downloads\\hey.png";
				String comparingfilepath = inputexcel.getCellData("loginpage", string, h);
				if(compareImages(filepathfordownloaded,comparingfilepath)==true)
					System.out.println("BOTH IMAGES IS MATCHED : PASS");
				else
					System.out.println("BOTH IMAGES IS NOT MATCHED : FAIL");
			}
			
		}
	}


	private static void selectTheLanguage(String string) {
		
		if(string.equalsIgnoreCase("Kannada")) {
			
			driver.findElement(By.xpath("//a[@title='Kannada']")).click();
		}
		else if(string.equalsIgnoreCase("Tamil")) {
			
			driver.findElement(By.xpath("//a[@title='Tamil']")).click();
		}
		else if(string.equalsIgnoreCase("Hindi")) {
			
			driver.findElement(By.xpath("//a[@title='Hindi']")).click();
		}
		else if(string.equalsIgnoreCase("Malayalam")) {
			
			driver.findElement(By.xpath("//a[@title='Malayalam']")).click();
		}
		else if(string.equalsIgnoreCase("Telugu")) {
			
			driver.findElement(By.xpath("//a[@title='Telugu']")).click();
		}
		else if(string.equalsIgnoreCase("English")) {
			
			driver.findElement(By.xpath("//a[@href='https://en-gb.facebook.com/']")).click();
		}
		else if(string.equalsIgnoreCase("Deutsch")) {
			
			driver.findElement(By.xpath("//a[@data-tooltip-content='German']")).click();
		}
		
	}
	private static boolean compareImages1(String filepathfordownloaded, String comparingfilepath) throws MalformedURLException, IOException {
		
		BufferedImage bufdownimage = ImageIO.read(new File(filepathfordownloaded));
		BufferedImage bufcomimage = ImageIO.read(new File(comparingfilepath));
		
		if(bufdownimage.getHeight()==bufcomimage.getHeight()&&bufdownimage.getWidth()==bufcomimage.getWidth())
				return true;
		else
				return false;
		
	}
	
	private void checkMultilanguageTextChecker(String languageName, String selectedPage,String excelfilepath) {
		
	
		excelformultilingual = new ExcelReader(System.getProperty("user.dir")+excelfilepath);
		
		for(int j=1; j<=excelformultilingual.getRowCount(selectedPage);j++) {
			
			String elementTypeFromExcel = excelformultilingual.getCellData(selectedPage, "Element_Type", j);
			
			String languageSelected = excelformultilingual.getCellData(selectedPage, languageName, j);
			
			String elementColorFromExcel = excelformultilingual.getCellData(selectedPage, "Element_Color", j);
			
			String elementSizeFromExcel = excelformultilingual.getCellData(selectedPage, "Element_Size", j);
			
			String locatorValue = excelformultilingual.getCellData(selectedPage, "Locator", j);
			
			
			switch (elementTypeFromExcel) {
			case "label":
			case "button":
			case "header_text":
			case "paragraph":
			case "buttonlabel":
			case "text_box":
			case "drop_down_list":
			case "menu":
			case "icon":
			case "labelWithOutWeight":
				checkElementSize(locatorValue, elementSizeFromExcel, elementTypeFromExcel);
				checkElementBackGroundColorValue(locatorValue, elementColorFromExcel, elementTypeFromExcel);
				checkSelectedPageTextValue(locatorValue, elementTypeFromExcel, languageSelected);
				break;
			case "img":
				compareTwoImages(locatorValue, languageSelected);
				break;

			default:
				break;
			}
		}
	
		
	}
	
	private void checkElementSize(String locVal, String elementSizeFromExcel, String elementTypeFromExcel) {
		
		//System.out.println("*******************************ELEMENT SIZE VERIFY METHOD************************************");
		
		WebElement element = getWebElement(locVal);

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
				System.out.println("Text box height and width value not verfied "+element.getSize().height+ " - "+element.getSize().width);
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
		else if (elementTypeFromExcel.equalsIgnoreCase("link")) {
			
			if(elementSizeFromExcel.equalsIgnoreCase(element.getCssValue("font-size")))
				System.out.println("PASS => VERIFIED "+element.getCssValue("font-size"));
			else 
				System.out.println("FAIL => NOT VERIFIED "+element.getCssValue("font-size"));
			
		}
		else if (elementTypeFromExcel.equalsIgnoreCase("icon")) {
			
			if(elementSizeFromExcel.equalsIgnoreCase(element.getCssValue("font-size")))
				System.out.println("PASS => VERIFIED "+element.getCssValue("font-size"));
			else 
				System.out.println("FAIL => NOT VERIFIED "+element.getCssValue("font-size"));
			
		}
		else if (elementTypeFromExcel.equalsIgnoreCase("labelWithOutWeight")) {
			
			if(elementSizeFromExcel.equalsIgnoreCase(element.getCssValue("font-size")))
				System.out.println("PASS => VERIFIED "+element.getCssValue("font-size"));
			else 
				System.out.println("FAIL => NOT VERIFIED"+element.getText());
			
		}
		
	}

	private WebElement getWebElement(String locVal) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String convertElementSizeBoldTextIntoNumber(String elementSizeFromExcel) {
		
		if(elementSizeFromExcel.split("\\|")[1].equalsIgnoreCase("bold !important")||elementSizeFromExcel.split("\\|")[1].equalsIgnoreCase("bold")
				||elementSizeFromExcel.split("\\|")[1].equalsIgnoreCase("700"))
			return "700";
		else if(elementSizeFromExcel.split("\\|")[1].equalsIgnoreCase("normal")||elementSizeFromExcel.split("\\|")[1].equalsIgnoreCase("400"))
			return "400";
		else
			return elementSizeFromExcel.split("\\|")[1];
	
	}

	private void checkElementBackGroundColorValue(String locVal, String elementColorFromExcel, String elementTypeFromExcel) {
		
		//System.out.println("*******************************ELEMENT COLOR VERIFY METHOD************************************");
		
		WebElement element = getWebElement(locVal);
		
		if(elementTypeFromExcel.equalsIgnoreCase("button")||elementTypeFromExcel.equalsIgnoreCase("buttonlabel")) {
			
			if(Color.fromString(element.getCssValue("background-color")).asHex().trim().equalsIgnoreCase(elementColorFromExcel.trim()))
				System.out.println("PASS => VERIFIED ELEMENT COLOR "+Color.fromString(element.getCssValue("background-color")).asHex());
			else
				System.out.println("Fail => NOT VERIFIED "+element.getText());
		}
		else if(elementTypeFromExcel.equalsIgnoreCase("label")||elementTypeFromExcel.equalsIgnoreCase("header_text")
				||elementTypeFromExcel.equalsIgnoreCase("drop_down_list")||elementTypeFromExcel.equalsIgnoreCase("paragraph")||elementTypeFromExcel.equalsIgnoreCase("menu")
				||elementTypeFromExcel.equalsIgnoreCase("labelWithOutWeight")){

			if(Color.fromString(element.getCssValue("color")).asHex().trim().equalsIgnoreCase(elementColorFromExcel.trim()))
				System.out.println("PASS => VERIFIED ELEMENT TEXT COLOR "+Color.fromString(element.getCssValue("color")).asHex());
			else
				System.out.println("Fail => NOT VERIFIED"+Color.fromString(element.getCssValue("color")).asHex());
		}
		
	}


	private void compareTwoImages(String locVal, String languageSelected) {
		
		System.out.println("Image is downloading....");
		WebElement element = getWebElement(locVal);
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


	private void checkSelectedPageTextValue(String locValue, String elementTypeFromExcel,String languageSelected) {
		
		//System.out.println("*******************************ELEMENT MULTIPLE LANGUAGE VALUE VERIFY METHOD************************************");
		
WebElement element = getWebElement(locValue);

	if(languageSelected!=null&&!languageSelected.equalsIgnoreCase("")) {
		
		if(elementTypeFromExcel.equalsIgnoreCase("label")) {
			
			if(element.getText().trim().equalsIgnoreCase(languageSelected.trim()))
				System.out.println("Pass => VERIFIED "+element.getText());
			else
				System.out.println("Fail => NOT VERIFIED "+element.getText());
		}
		if(elementTypeFromExcel.equalsIgnoreCase("labelWithOutWeight")) {
			
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
		
	}



}


/*  
 * try { prop = new Properties(); // check website language URL; if
 * (driver.getCurrentUrl().equalsIgnoreCase("https://www.facebook.com/")) {
 * 
 * inputfile = new FileInputStream(new
 * File("./InputFiles/fb_en1.properties.txt")); prop.load(inputfile); } else if
 * (driver.getCurrentUrl().equalsIgnoreCase("https://de-de.facebook.com/")) {
 * 
 * inputfile = new FileInputStream(new
 * File("./InputFiles/fb_de.properties.txt")); prop.load(inputfile); } else if
 * (driver.getCurrentUrl().equalsIgnoreCase("https://kn-in.facebook.com/")) {
 * 
 * inputfile = new FileInputStream(new
 * File("./InputFiles/fb_kan1.properties.txt")); prop.load(inputfile);
 * 
 * }
 * 
 * } catch (IOException e) { e.printStackTrace(); }
 */