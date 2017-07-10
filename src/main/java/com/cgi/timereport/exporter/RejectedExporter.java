package com.cgi.timereport.exporter;

import com.cgi.timereport.util.ExporterUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class RejectedExporter {

    public static void main(String[] args) {

        try (XSSFWorkbook workbook = ExporterUtil.readFile("C:\\Users\\michael.carvalho\\Documents\\Angela\\Väylätieto_Time_Reports_PT_Team_2017M06_v1.xlsx")) {
            if (workbook != null) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                XSSFRow row;
                XSSFCell cell;
                if (sheet != null) {
                    try (XSSFWorkbook newWorkbook = new XSSFWorkbook()) {
                        XSSFSheet newSheet = newWorkbook.createSheet();
                        
                        int numberOfRows = sheet.getPhysicalNumberOfRows();

                        for (int r=0; r<numberOfRows; r++) {
                            row = sheet.getRow(r);


                            for (int c=0; c<row.getPhysicalNumberOfCells(); c++) {


                            }

                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Something went with opening the workbook");
        }
    }
}
