package com.testcases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.excelreader.ExcelReader;
import com.keywords.Keywords;
import com.testbase.TestBase;
import com.testutil.TestUtil;

public class TC_003_LoginPageAttributesValidationTestCases extends TestBase{

ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\InputFiles\\TestCases.xlsx");
	
	@Test(dataProvider = "testDataProvider")
	public void testcaseGSM02() {
		
		Keywords keywords = new Keywords();
		keywords.execute(excel, "TC_003_LoginPageAttributesValidation");
		
	}
	
	@DataProvider
	public Object[][] getData(){
		
		return TestUtil.getData("TC_003_LoginPageAttributesValidation", excel);
		
	}
}
