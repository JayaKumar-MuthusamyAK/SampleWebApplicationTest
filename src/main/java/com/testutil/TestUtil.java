package com.testutil;

import java.util.Hashtable;

import com.excelreader.ExcelReader;

public class TestUtil {
	
	
	
	// Read the test data based on the test cases form need
			public static Object[][] getData(String testcases, ExcelReader xls) {
				int testStartsRowNum = 0;

				for (int rNum = 1; rNum <= xls.getRowCount("TestData"); rNum++) {
					if (testcases.equals(xls.getCellData("TestData", 0, rNum))) {
						testStartsRowNum = rNum;
						break;
					}
				}

				System.out.println("Test cases started row number :" + testStartsRowNum);

				int columnStartNum = testStartsRowNum + 1;
				int cols = 0;

				while (!xls.getCellData("TestData", cols, columnStartNum).equals("")) {
					cols++;
				}

				System.out.println("Test cases total column count are :" + cols);

				int rowStartNum = testStartsRowNum + 2;
				int rows = 0;

				while (!xls.getCellData("TestData", 0, (rowStartNum + rows)).equals("")) {
					rows++;
				}

				System.out.println("Test cases total rows count are :" + rows);

				Object[][] data = new Object[rows][1];
				// System.out.println(data.length);
				Hashtable<String, String> table = null;

				for (int rNum = rowStartNum; rNum < (rowStartNum + rows); rNum++) {
					table = new Hashtable<String, String>();
					for (int cNum = 0; cNum < cols; cNum++) {
						table.put(xls.getCellData("TestData", cNum, columnStartNum), xls.getCellData("TestData", cNum, rNum));

						System.out.print(xls.getCellData("TestData", cNum, columnStartNum) + "--"
								+ xls.getCellData("TestData", cNum, rNum) + " --");
					}
					System.out.println();
					data[rNum - rowStartNum][0] = table;
				}
				return data;

			}

}
