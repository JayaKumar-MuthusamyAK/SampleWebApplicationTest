package com.testcases;

import org.testng.annotations.Test;

import com.excelreader.ExcelReader;
import com.keywords.Keywords;

public class TC_002_MultiligualTestCases {
	
	
ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\InputFiles\\TestCases.xlsx");
	
	@Test
	public void testcaseGSM02() {
		
		Keywords keywords = new Keywords();
		keywords.execute(excel, "TC_002_MultiligualTestCases");
		
	}
	

}
