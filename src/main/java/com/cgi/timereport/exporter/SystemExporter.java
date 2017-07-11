package com.cgi.timereport.exporter;


import com.cgi.timereport.util.ExporterUtil;
import org.apache.poi.ss.usermodel.CellType;
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

                if (sheet.getPhysicalNumberOfRows() > 0) {
                    // This part is to add the header (row = 0)
                    row = sheet.getRow(0);
                    for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
                        cell = row.getCell(c);

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
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
