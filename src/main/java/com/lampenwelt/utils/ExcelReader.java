package com.lampenwelt.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	XSSFWorkbook workbook;
	XSSFSheet currentSheet;
	XSSFRow dataRow;
	XSSFCell cell;
	Map<String, Integer> columns = new HashMap<String, Integer>();
	public static int size;

	public ExcelReader(String excelfileName) throws IOException {
		workbook = new XSSFWorkbook(System.getProperty("user.dir") + "/Data/" + excelfileName + ".xlsx");
	}

	public void switchToSheet(String sheetName) {

		try {
			currentSheet = workbook.getSheet(sheetName);
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object[][] readExcelData(String excelfileName) throws IOException {
		XSSFWorkbook wbook = new XSSFWorkbook(System.getProperty("user.dir") + "/Data/" + excelfileName + ".xlsx");
		XSSFSheet sheet = wbook.getSheetAt(0);
		int rowCount = sheet.getLastRowNum();
		int colCount = sheet.getRow(0).getLastCellNum();
		Object[][] data = new Object[rowCount][colCount];
		for (int i = 1; i <= rowCount; i++) {
			XSSFRow row = sheet.getRow(i);
			for (int j = 0; j < colCount; j++) {
				XSSFCell cell = row.getCell(j);
				String stringCellValue = cell.getStringCellValue();
				data[i - 1][j] = stringCellValue;
			}
		}
		wbook.close();
		return data;
	}

	public String getCellData(String column, int row) throws IOException {
		dataRow = currentSheet.getRow(row);
		currentSheet.getRow(0).forEach(cell -> {
			columns.put(cell.getStringCellValue(), cell.getColumnIndex());
		});
		return getCellDataAsString(dataRow.getCell(columns.get(column)));
	}

	public int getTotalMatchingHeadings(String name) throws IOException {
		dataRow = currentSheet.getRow(0);
		size = 0;
		for (int i = 0; i < dataRow.getLastCellNum(); i++) {
			if (dataRow.getCell(i).getStringCellValue().contains("PRODUCT")) {
				size = size + 1;
			}
		}
		workbook.close();
		return size;

	}

	public int getNoOfRows() {
		return currentSheet.getLastRowNum();
	}

	public String getCellDataAsString(XSSFCell cell) throws IOException {
		String cellType = cell.getCellType().name();
		String data = "";
		switch (cellType) {
		case "STRING": {
			data = cell.getStringCellValue();
			break;
		}
		case "NUMERIC": {
			data = String.valueOf((int) cell.getNumericCellValue());
			break;
		}
		case "BOOLEAN": {
			data = String.valueOf((boolean) cell.getBooleanCellValue());
			break;
		}
		default:
			break;
		}
		;
		workbook.close();
		return data;
	}

	public void closeWorkBook() throws IOException {
		workbook.close();
	}
}
