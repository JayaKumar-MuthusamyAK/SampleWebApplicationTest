package com.excelreader;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Random;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public static String filename = System.getProperty("user.dir") + "\\src\\config\\testcases\\TestData.xlsx";
	public String path;
	public FileInputStream fis = null;
	public static FileOutputStream fileOut = null;
	public XSSFWorkbook workbook = null;
	public XSSFSheet sheet = null;
	public XSSFRow row = null;
	public XSSFCell cell = null;

	public ExcelReader(String path) {

		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean setCellData1(String sheetName, String colName, int rowNum, String data) {
		try {
			fis = new FileInputStream(path);
			// System.out.println(path);
			workbook = new XSSFWorkbook(fis);

			if (rowNum <= 0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return false;

			sheet = workbook.getSheetAt(index);

			row = sheet.getRow(0);

			for (int j = 0; j <= sheet.getLastRowNum(); j++) {

				if (sheet.getRow(j) != null) {
					for (int i = 0; i <= sheet.getRow(j).getLastCellNum(); i++) {
						// System.out.println(sheet.getRow(j).getCell(i).getStringCellValue());
						if (sheet.getRow(j).getCell(i) != null) {

							// System.out.println(sheet.getRow(j).getCell(i).getStringCellValue().trim());
							if (sheet.getRow(j).getCell(i).getStringCellValue().trim()
									.equalsIgnoreCase(colName.trim())) {
								col_Num = i;
								break;
							}

						}

					}

				}
			}
			if (col_Num == -1)
				return false;

			sheet.autoSizeColumn(col_Num);
			row = sheet.getRow(rowNum);
			if (row == null)
				row = sheet.createRow(rowNum);

			cell = row.getCell(col_Num);
			if (cell == null)
				cell = row.createCell(col_Num);

			cell.setCellValue(data);

			fileOut = new FileOutputStream(path);

			workbook.write(fileOut);

			//fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	

	public boolean setCoulmnNameWithColNo(String sheetName, String data, int columNo) {

		System.out.println("Method calling..");
		try {

			fis = new FileInputStream(path);
			// System.out.println(path);
			workbook = new XSSFWorkbook(fis);

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;

			if (index == -1)
				return false;

			sheet = workbook.getSheetAt(index);

			row = sheet.getRow(0);

			System.out.println(sheet.getLastRowNum());

			sheet.autoSizeColumn(columNo);
			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);

			cell = row.getCell(columNo);
			if (cell == null)
				cell = row.createCell(columNo);
			cell.setCellValue(data);

			fileOut = new FileOutputStream(path);

			workbook.write(fileOut);

			//fileOut.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	// returns number of columns in a sheet	
		public int getColumnCount(String sheetName){
			// check if sheet exists
			
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);
			
			if(row==null)
				return -1;
			
			return row.getLastCellNum();
			
			
			
		}
		
		public String getFirstRowCellData(String sheetName, int rowNo, int colNo) {
			
			try{
	
			int index = workbook.getSheetIndex(sheetName);
			
			if(index==-1)
				return "";
			
		
			sheet = workbook.getSheetAt(index);
		
			row = sheet.getRow(rowNo);
			if(row==null)
				return "";
			cell = row.getCell(colNo);
			if(cell==null)
				return "";
			
		  if(cell.getCellType()==CellType.STRING)
			  return cell.getStringCellValue();
		  else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA ){
			  
			  String cellText  = String.valueOf(cell.getNumericCellValue());
			  if (DateUtil.isCellDateFormatted(cell)) {
		           // format in form of M/D/YY
				  double d = cell.getNumericCellValue();

				  Calendar cal =Calendar.getInstance();
				  cal.setTime(DateUtil.getJavaDate(d));
		            cellText =
		             (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
		           cellText = cal.get(Calendar.MONTH)+1 + "/" +
		                      cal.get(Calendar.DAY_OF_MONTH) + "/" +
		                      cellText;
		           
		          // System.out.println(cellText);

		         }
			  return cellText;
		  }else if(cell.getCellType()==CellType.BLANK)
		      return "";
		  else 
			  return String.valueOf(cell.getBooleanCellValue());
			}
			catch(Exception e){
				
				e.printStackTrace();
				return "row "+rowNo+" or column "+colNo +" does not exist  in xls";
			}
		
		
}

	// Randomly Creating excel file:
	public static String createExcelFile(String format) {

		Random random = new Random();
		
		String pathofthefile = null;

		if (format.equalsIgnoreCase(".xlsx")) {
			
			pathofthefile = creatingexcelfile("TestDataGeneratedFile" + random.nextInt() + ".xlsx");

			return pathofthefile;
		}

		else if (format.equalsIgnoreCase(".csv")) {

			pathofthefile = creatingexcelfile("TestDataGeneratedFile" + random.nextInt() + ".csv");

			return pathofthefile;
		} 

		return "";

	}

	public static String creatingexcelfile(String excelfilename) {
		
		File file = new File(System.getProperty("user.home")+"\\Downloads\\"+excelfilename);
	
		try {
			if (!file.exists()) {
				FileOutputStream fos = new FileOutputStream(file);
				XSSFWorkbook book = new XSSFWorkbook();

				XSSFSheet sheet = book.createSheet("Sheet1");

				Row row = sheet.createRow(0);
				Cell cell0 = row.createCell(0);

				book.write(fos);
				fos.flush();
				fos.close();
				return System.getProperty("user.home")+"\\Downloads\\"+excelfilename;
			}
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return excelfilename;

	}

	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}

	}

	// returns the data from a cell
	public String getCellData(String sheetName, String colName, int rowNum) {
		DataFormatter formatter = new DataFormatter();
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			// Iterator rowItr = sheet.rowIterator();

			// while ( rowItr.hasNext() ) {
			// row = (XSSFRow) rowItr.next();

			if (row == null) {
				return "";
			}

			// System.out.println(sheet.getFirstRowNum());
			// System.out.println(sheet.getLastRowNum());
			// System.out.println(sheet.getRow(1).getLastCellNum());
			for (int j = 0; j <= sheet.getLastRowNum(); j++) {

				if (sheet.getRow(j) != null) {
					for (int i = 0; i <= sheet.getRow(j).getLastCellNum(); i++) {
						// System.out.println(sheet.getRow(j).getCell(i).getStringCellValue());
						if (sheet.getRow(j).getCell(i) != null) {

							// System.out.println(sheet.getRow(j).getCell(i).getStringCellValue().trim());
							String text = formatter.formatCellValue(sheet.getRow(j).getCell(i));
							if (text.trim().equalsIgnoreCase(colName.trim())) {
								col_Num = i;
								break;
							}

							else {
								// System.out.println();
							}
						}

					}

				}
			}

			if (col_Num == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";
			// System.out.println(cell.getCellType());
			if (cell.getCellType() == CellType.STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

					// System.out.println(cellText);

				}

				return cellText;
			} else if (cell != null || cell.getCellType() == CellType.BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		}

		catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}

	}

	public String getCellData(String sheetName,int colNum,int rowNum){
		try{
			if(rowNum <=0)
				return "";
		
		int index = workbook.getSheetIndex(sheetName);

		if(index==-1)
			return "";
		
	
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(rowNum-1);
		if(row==null)
			return "";
		cell = row.getCell(colNum);
		if(cell==null)
			return "";
		
	  if(cell.getCellType()==CellType.STRING)
		  return cell.getStringCellValue();
	  else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA ){
		  
		  String cellText  = String.valueOf(cell.getNumericCellValue());
		  if (DateUtil.isCellDateFormatted(cell)) {
	           // format in form of M/D/YY
			  double d = cell.getNumericCellValue();

			  Calendar cal =Calendar.getInstance();
			  cal.setTime(DateUtil.getJavaDate(d));
	            cellText =
	             (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
	           cellText = cal.get(Calendar.MONTH)+1 + "/" +
	                      cal.get(Calendar.DAY_OF_MONTH) + "/" +
	                      cellText;
	           
	          // System.out.println(cellText);

	         }

		  
		  
		  return cellText;
	  }else if(cell.getCellType()==CellType.BLANK)
	      return "";
	  else 
		  return String.valueOf(cell.getBooleanCellValue());
		}
		catch(Exception e){
			
			e.printStackTrace();
			return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
		}
	}
	public static void main(String[] args) {

		ExcelReader excelreader = new ExcelReader(System.getProperty("user.dir") + "\\" + createExcelFile(".xlsx"));

		excelreader.setCellData1("Sheet1", "Name", 1, "dad");

	}

}
