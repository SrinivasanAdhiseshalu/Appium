package com.amazon.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author ${SrinivasanAdhiseshalu}
 *
 * ${tags}
 */

public class ExcelComplete {
	
	@SuppressWarnings({ "resource", "deprecation" })
	public ArrayList<HashMap<String,String>> readExcel(String excel, String sheet) throws Exception{
		String heading="";
		ArrayList<HashMap<String,String>> arrMap = new ArrayList<HashMap<String,String>>();
		FileInputStream fis = new FileInputStream(new File(excel));
		//XSSFWorkbook wb1 = new XSSFWorkbook(fis);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sh = wb.getSheet(sheet);
		int rowCount = sh.getLastRowNum() - sh.getFirstRowNum();
		for(int i=1;i<rowCount;i++) {
			HashMap<String,String> hm = new HashMap<String,String>();
			Row row = sh.getRow(i);
			int colCount = row.getLastCellNum() - row.getFirstCellNum();
			for(int j=0;j<colCount;j++) {
				String key="";
				heading = sh.getRow(0).getCell(j).getStringCellValue();
				Cell cel = row.getCell(j);
				switch(cel.getCellType()) {
					case Cell.CELL_TYPE_STRING :
						key = cel.getStringCellValue();
						break;
				}
				hm.put(heading, key);
			}
			arrMap.add(hm);
		}
		
		return arrMap;
	}
	
	
	
}
