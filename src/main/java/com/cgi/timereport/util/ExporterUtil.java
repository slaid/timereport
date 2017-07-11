package com.cgi.timereport.util;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTAutoFilter;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFilter;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFilterColumn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExporterUtil {
    /**
     * Creates an {@link XSSFWorkbook} with the specified filename
     * @param filename The file name
     * @return Returns a {@link XSSFWorkbook} from the given filename
     */
    public static XSSFWorkbook readFile(String filename) {
        try (FileInputStream inputStream = new FileInputStream(filename)) {
            return new XSSFWorkbook(inputStream);
        } catch (FileNotFoundException e) {
            System.err.println("File doesn't exist. Please verify the extension");
            return null;
        } catch (IOException e) {
            System.err.println("There was something that went wrong reading the file");
            return null;
        }
    }


    /**
     * Creates a new .xlsx file
     * @param workbook {@link XSSFWorkbook} object to create the new Workbook
     * @param system The name for the new System
     */
    public static void extractSystem(XSSFWorkbook workbook, String system) {
        try (FileOutputStream outputStream = new FileOutputStream(system + ".xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            System.err.println("Something went wrong writing the new file");
        }
    }

    private static void setAutoFilter(XSSFSheet sheet, int column, String value) {
        sheet.setAutoFilter(CellRangeAddress.valueOf("A1:H1"));

        CTAutoFilter sheetFilter = sheet.getCTWorksheet().getAutoFilter();
        CTFilterColumn filterColumn = sheetFilter.addNewFilterColumn();
        filterColumn.setColId(column);
        CTFilter filter = filterColumn.addNewFilters().insertNewFilter(0);
        filter.setVal(value);

        // We have to apply the filter ourselves by hiding the rows:
        for (Row row: sheet) {
            for (Cell cell: row) {
                if (cell.getColumnIndex() == column && !cell.getStringCellValue().equals(value)) {
                    XSSFRow r1 = (XSSFRow) cell.getRow();
                    if (r1.getRowNum() != 0)
                        r1.getCTRow().setHidden(true);
                }
            }
        }
    }

}
