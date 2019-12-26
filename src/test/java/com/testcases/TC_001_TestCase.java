package com.testcases;

import org.testng.annotations.Test;

import com.excelreader.ExcelReader;
import com.keywords.Keywords;
import com.testbase.TestBase;

public class TC_001_TestCase extends TestBase{
	
	ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\InputFiles\\TestCases.xlsx");
	
	@Test
	public void testcaseGSM01() {
		
		Keywords keywords = new Keywords();
		keywords.execute(excel, "TC_001_TestCase");
		
	}

}
