package com.cgi.timereport.exporter;


import com.cgi.timereport.util.ExporterUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SystemExporter {

    public static void main(String[] args) {

        List<XSSFWorkbook> workbooks = new ArrayList<>();
        Set<String> systems = new HashSet<>();
        List<String> createdSystems = new ArrayList<>();

        try (XSSFWorkbook workbook = ExporterUtil.readFile("C:\\Users\\michael.carvalho\\Documents\\Angela\\Väylätieto_Time_Reports_PT_Team_2017M06_v1.xlsx")) {
            if (workbook != null) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                XSSFRow row;
                XSSFCell cell;

                int numOfRows = sheet.getPhysicalNumberOfRows();
                try (XSSFWorkbook newWorkbook = new XSSFWorkbook()) {
                    XSSFSheet newSheet = newWorkbook.createSheet();
                    XSSFRow newRow;
                    XSSFCell newCell;
                    XSSFCellStyle newCellType = newWorkbook.createCellStyle();

                    if (sheet.getPhysicalNumberOfRows() > 0) {
                        // This part is to add the header (row = 0)
                        row = sheet.getRow(0);
                        newRow = newSheet.createRow(0);

                        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                            cell = row.getCell(i);
                            newCell = newRow.createCell(i);
                            newSheet.setColumnWidth(i, sheet.getColumnWidth(i));
                            newCellType.cloneStyleFrom(cell.getCellStyle());
                            newCell.setCellStyle(newCellType);

                            if (i == 0 || i == 1 || i == 2) {
                                newCellType.setAlignment(HorizontalAlignment.CENTER_SELECTION);
                                newCell.setCellStyle(newCellType);
                            }

                            if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                                newSheet.setAutoFilter(CellRangeAddress.valueOf("A1:H1"));
                                newCell.setCellValue(cell.getStringCellValue());
                            }

                        }
                    }
                }



                // Collect Systems and put them in a SET
                for (int r = 1; r < numOfRows; r++) {
                    row = sheet.getRow(r);
                    cell = row.getCell(4);
                    if ((cell != null) && cell.getCellTypeEnum().equals(CellType.STRING))
                        systems.add(cell.getStringCellValue());
                }

                // Create the systems individually

                for (int r=1; r<numOfRows; r++) {
                    row = sheet.getRow(r);

                    cell = row.getCell(4);
                    String system = cell.getStringCellValue();
                    if (!createdSystems.contains(system)) {

                    }

                }


                for (String system: systems) {
                    System.out.println("System: " + system);
                    /*
                    try (XSSFWorkbook newWorkbook = new XSSFWorkbook()) {
                        XSSFSheet newSheet = newWorkbook.createSheet();
                        newWorkbook.setSheetName(0, system);
                        XSSFRow newRow;
                        XSSFCell newCell;
                        XSSFCellStyle newCellType;

                        for (int r=0; r<numOfRows; r++) {
                            row = sheet.getRow(r);

                        }


                    }
                    */
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
