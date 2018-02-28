package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import Test.TestCaseContainer;
import io.appium.java_client.AppiumDriver;


/**
 * 
 * @author martin.wang
 *
 */
public class POIUtils {

	// Suppress default constructor for noninstantiability
	private POIUtils() {
		throw new AssertionError();
	}

	public static String getCellValue(Workbook wb, Cell cell) {

		FormulaEvaluator evaluator = wb.getCreationHelper()
				.createFormulaEvaluator();
		String cellValue = "";
		if (cell != null) {
			if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				cell = evaluator.evaluateInCell(cell);
			}
			cellValue = getCellValue(cell);
		}
		return cellValue;
	}

	public static String getCellValue(Cell cell) {
		String cellValue = "";

		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getRichStringCellValue().getString();
				break;

			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					cellValue = DateTimeUtils.dateToString(date);
				} else {
					cell.setCellType(Cell.CELL_TYPE_STRING); // treat numeric
																// cells as
																// String type
					cellValue = cell.getRichStringCellValue().getString();
				}
				break;

			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = cell.getBooleanCellValue() + "";
				break;

			case Cell.CELL_TYPE_FORMULA:
			default:
				cellValue = "";
				break;
			}
		}

		return cellValue;
	}


	/**Read a column from excel (include startIndex and endIndex)
	 * 
	 * @param sheet
	 * @param columnIndex
	 * @param rowIndex_start:include
	 * @param rowIndex_end:exclude
	 * @return
	 */
	public static ArrayList<String> getColumnFromExcel(HSSFSheet sheet,
			int columnIndex, int rowIndex_start, int rowIndex_end){
		
		ArrayList<String> columnData = new ArrayList<String>();
		for (int i = rowIndex_start; i <= rowIndex_end; i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				Cell cell = row.getCell(columnIndex);
				if (cell != null) {
					String cellValue = POIUtils.getCellValue(cell);
					if (!Validate.isBlank(cellValue)) {
						columnData.add(cellValue);
					}else{
						columnData.add("");
					}
				}else{
					columnData.add("");
				}
			}else{
				columnData.add("");
			}

		}

		return columnData;
	}
	
	public static ArrayList<String> getColumnFromExcel(HSSFSheet sheet,
			int columnIndex, int rowIndex_start) {
		
		ArrayList<String> columnData = new ArrayList<String>();
		int rows = sheet.getPhysicalNumberOfRows();
		for (int i = rowIndex_start; i < rows; i++) {
			// System.out.println(i);
			Row row = sheet.getRow(i);
			if (row != null) {
				Cell cell = row.getCell(columnIndex);
				if (cell != null) {
					String cellValue = POIUtils.getCellValue(cell);
					if (!Validate.isBlank(cellValue)) {
						columnData.add(cellValue);
					}
				}
			}

		}

		return columnData;
	}

	public static ArrayList<String> getColumnFromExcel(HSSFSheet sheet,
			int columnIndex) {
		return getColumnFromExcel(sheet, columnIndex, 0);
	}

	public static ArrayList<String> getColumnFromExcel(HSSFSheet sheet, String columnName){
		int columnIndex = -1;
		ArrayList<String> row = POIUtils.getRowFromExcel(sheet, 0);
		for(int i = 0; i < row.size(); i ++){
			String rowData = row.get(i);
			if(rowData.equals(columnName)){
				columnIndex = i;
				break;
			}
		}
		
		if(columnIndex == -1){
			return null;
		}else{
			return getColumnFromExcel(sheet, columnIndex);
		}
	}
	
	public static ArrayList<String> getRowFromExcel(HSSFSheet sheet,
			int rowIndex, int columnIndex_start) {
		ArrayList<String> rowData = new ArrayList<String>();
		Row row = sheet.getRow(rowIndex);
		Cell cell = null;
		String cellValue = "";
		for (int i = columnIndex_start; i < row.getPhysicalNumberOfCells(); i++) {
			cell = row.getCell(i);
			cellValue = POIUtils.getCellValue(cell);
			if (!Validate.isBlank(cellValue)) {
				rowData.add(cellValue);
			}
		}
		
		return rowData;
	}

	public static ArrayList<String> getRowFromExcel(HSSFSheet sheet,
			int rowIndex) {
		return getRowFromExcel(sheet, rowIndex, 0);
	}

	public static HSSFSheet getSheetByName(HSSFWorkbook wb, String strSheetName) {
		return wb.getSheet(strSheetName);
	}

	public static HashMap<String, ArrayList<String>> getColumnPairs(
			HSSFSheet sheet, int dataRowIndex_start, int key_columnIndex,
			int value_columnIndex) {
		HashMap<String, ArrayList<String>> key_values_map = new HashMap<String, ArrayList<String>>();

		ArrayList<String> values = null;

		Row row;
		Cell cell;
		String key = "", value = "";

		for (int i = dataRowIndex_start; i < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i); // for each row
			if (row != null) {
				cell = row.getCell(key_columnIndex); // for key column
				if (cell != null) {
					key = cell.getRichStringCellValue().getString();
				}

				cell = row.getCell(value_columnIndex); // for value column
				if (cell != null) {
					value = cell.getRichStringCellValue().getString();
				}
			}

			if (!Validate.isBlank(key) && !Validate.isBlank(value)) {

				if (key_values_map.containsKey(key)) {
					values = key_values_map.get(key);
				} else {
					values = new ArrayList<String>();
				}

				if (!values.contains(value)) {
					values.add(value);
				}

				key_values_map.put(key, values);
				key = "";
				value = "";
			}
		}

		return key_values_map;
	}

	public static int getRowNumByKeyWord(HSSFSheet sheet, int columnIndex,
			String search_keyword) {
		Row row;
		Cell cell;
		String value = "";

		int rowNum = -1;
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i); // for each row
			if (row != null) {
				cell = row.getCell(columnIndex); // for key column
				if (cell != null) {
					value = cell.getRichStringCellValue().getString();
					if (value.equals(search_keyword)) {
						rowNum = i;
						break;
					}
				}
			}

		}
		return rowNum;
	}

	/**
	 * This is call by Test Case instance to catch information in Testing Page and fill into the current test case
	 * @param fileName 
	 * @param sheetName
	 * @param keyword_ColumnIndex
	 * @param keyword_search
	 * @param data_ColumnIndex
	 * @param data
	 */
	public static void writeTestResultToCurrentTestCase(String fileName, String sheetName,
			int keyword_ColumnIndex, String keyword_search,
			int data_ColumnIndex, String data) {
		try {
			FileInputStream file = new FileInputStream(fileName);
			HSSFWorkbook wb = new HSSFWorkbook(file);
		
			HSSFSheet sheet= wb.getSheet(sheetName);
			if (sheet== null) {
				sheet=wb.createSheet(sheetName); 
			}
			
			int rowNum = POIUtils.getRowNumByKeyWord(sheet, keyword_ColumnIndex,
					keyword_search);
			HSSFRow row = sheet.getRow(rowNum);
			Cell cell = row.getCell(data_ColumnIndex);
			if (cell == null) {
				cell = row.createCell(data_ColumnIndex);
			}
			cell.setCellValue(data);
			file.close();

			FileOutputStream outFile = new FileOutputStream(fileName);
			wb.write(outFile);
			outFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getRowNum(HSSFSheet sheet, int columnIndex,
			String search_keyword) {
		Row row;
		Cell cell;
		String value = "";

		int rowNum = -1;
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i); // for each row
			if (row != null) {
				cell = row.getCell(columnIndex); // for key column
				if (cell != null) {
					value = cell.getRichStringCellValue().getString();
					if (value.equals(search_keyword)) {
						rowNum = i;
						break;
					}
				}
			}

		}
		return rowNum;
	}
	
	/**Martin: save row data into excel file
	 * 
	 * @param fileName
	 * @param sheetName
	 * @param data: a set of row data
	 */
	public static void saveIntoExcel(String fileName, String sheetName, ArrayList<ArrayList<String>> data){
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(sheetName);
			
			for(int i = 0; i < data.size(); i ++){
				HSSFRow row = sheet.createRow(i);
				
				ArrayList<String> columnData = data.get(i);
				for(int j = 0; j < columnData.size(); j ++){
					Cell cell = row.createCell(j);
					cell.setCellValue(columnData.get(j));
				}
			}
			
			FileOutputStream out = new FileOutputStream(new File(fileName));
			wb.write(out);
			out.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeExcelData(String fileName, String sheetName,
			int keyword_ColumnIndex, String keyword_search,
			int data_ColumnIndex, String data) {
		try {
			FileInputStream file = new FileInputStream(fileName);
			HSSFWorkbook wb = new HSSFWorkbook(file);

			HSSFSheet sheet = wb.getSheet(sheetName);

			int rowNum = POIUtils.getRowNum(sheet, keyword_ColumnIndex,
					keyword_search);
			HSSFRow row = sheet.getRow(rowNum);
			Cell cell = row.getCell(data_ColumnIndex);
			if (cell == null) {
				cell = row.createCell(data_ColumnIndex);
			}
			cell.setCellValue(data);
			file.close();

			FileOutputStream outFile = new FileOutputStream(fileName);
			wb.write(outFile);
			outFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void filterDebugMsg(Exception e, TestCaseContainer TestDataObject){
		System.out.println("Error in test case: " + TestDataObject.toString());
		System.out.println("Caused by:" + e.getCause());
		
		System.out.println("Calling stack:");
		StackTraceElement[] elems = e.getStackTrace();
		if(e instanceof InvocationTargetException){
			Throwable s = ((InvocationTargetException)e).getTargetException();
			elems = s.getStackTrace();
		}
		
		for(StackTraceElement elem: elems){
			if(elem.getClassName().startsWith("com.netdimen")){
				System.out.println(elem.toString());
			}
		}
	}
	
}
