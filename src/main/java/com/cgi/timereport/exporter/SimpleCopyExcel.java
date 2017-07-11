package com.cgi.timereport.exporter;

import com.cgi.timereport.util.ExporterUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SimpleCopyExcel {

    /**
     * Generates a list of the available systems given by the {@link XSSFSheet}
     * @param sheet {@link XSSFSheet} to extract the content
     * @return Returns a new list
     */
    private static List<String> getSystems(XSSFSheet sheet) {
        List<String> systemList = new ArrayList<>();
        long initialTime = System.currentTimeMillis();
        // Declare a Row Object Reference
        XSSFRow row;
        // Declare a Cell Object Reference
        XSSFCell cell;
        for (int r=0; r<sheet.getPhysicalNumberOfRows(); r++) {
            row = sheet.getRow(r);
            if (row != null) {
                for (int c=0; c<row.getPhysicalNumberOfCells(); c++) {
                    cell = row.getCell(c);
                    if (c == 4 && cell != null && cell.getCellTypeEnum().equals(CellType.STRING)) {
                        if (!systemList.contains(cell.getStringCellValue())) {
                            systemList.add(cell.getStringCellValue());
                        }
                    }
                }
            }
        }
        long finalTime = System.currentTimeMillis();
        long totalTime = (finalTime - initialTime);
        System.out.println("Total time was: " + totalTime + "ms");
        return systemList;
    }



    /**
     * Creates a new .xlsx file
     * @param workbook {@link XSSFWorkbook} object to create the new Workbook
     * @param system The name for the new System
     */
    private static void extractSystem(XSSFWorkbook workbook, String system) {
        try (FileOutputStream outputStream = new FileOutputStream(system + ".xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            System.err.println("Something went wrong writing the new file");
        }
    }



    /**
     * Creates an {@link HSSFWorkbook} with the specified filename
     * @param filename The file name
     * @return Returns a {@link HSSFWorkbook} from the given filename
     */
    private static HSSFWorkbook readFile(String filename) {
        try (FileInputStream inputStream = new FileInputStream(filename)) {
            return new HSSFWorkbook(inputStream);
        } catch (FileNotFoundException e) {
            System.err.println("File doesn't exist!!!");
            return null;
        } catch (IOException e1) {
            System.err.println("There was something that went wrong reading the file");
            return null;
        }
    }



    private static void copyFile(HSSFWorkbook workbook) {

        try (HSSFWorkbook newWorkBook = new HSSFWorkbook()) {
            HSSFSheet tmpSheet = newWorkBook.createSheet();

            String outputFilename = "";
            try (FileOutputStream out = new FileOutputStream(outputFilename)) {
                // This will be removed
                out.close();
            }
        } catch (IOException e) {
            System.err.println("Error on creating the new WorkBook");
        }
    }

    /**
     * Method main
     *
     * Given 1 argument takes that as the filename, inputs it and dumps the
     * cell values/types out to sys.out.<br/>
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("At least one argument expected");
            return;
        }
        String fileName = args[0];

        try (XSSFWorkbook workbook = ExporterUtil.readFile("C:\\Users\\michael.carvalho\\Documents\\Angela\\Väylätieto_Time_Reports_PT_Team_2017M06_v1.xlsx")) {

            if (workbook != null) {
                // Extract the Sheet from the current document
                XSSFSheet sheet = workbook.getSheetAt(0);
                // Verify if sheet exists
                if (sheet != null) {
                    // Declare a Row Object Reference
                    XSSFRow row;
                    // Declare a Cell Object Reference
                    XSSFCell cell;

                    // Create the system list
                    List<String> systemList = getSystems(sheet);

                    List<String> filesCreated = new ArrayList<>();

                    // Print the systems
                    for (String system: systemList)
                        System.out.println(system);

                    // Return number of Rows
                    int numRows = sheet.getPhysicalNumberOfRows();

                    int newRowId = 0;

                    if (systemList.size() != 0 && numRows != 0) {

                        // Loop over the available systems
                        for (String system: systemList) {
                                try (XSSFWorkbook newWorkbook = new XSSFWorkbook()) {
                                // Create a new Sheet
                                XSSFSheet newSheet = newWorkbook.createSheet();
                                // Create a temporary/new cell
                                XSSFRow newRow;
                                // Create temporary/new cell
                                XSSFCell newCell;

                                XSSFCellStyle newCellStyle = newWorkbook.createCellStyle();

                                long initialTime = System.currentTimeMillis();

                                // Loop row by row
                                for (int r=0; r<numRows; r++) {

                                    row = sheet.getRow(r);
                                    boolean added = false;
                                    // Create temporary/new row
                                    newRow = newSheet.createRow(newRowId);

                                    if (row.getPhysicalNumberOfCells() != 0 && row.getCell(4) != null &&
                                            row.getCell(4).getCellTypeEnum().equals(CellType.STRING) &&
                                            !row.getCell(4).getStringCellValue().isEmpty()) {

                                        String tmpString = row.getCell(4).getStringCellValue();
                                        if (!tmpString.equals("System") || !tmpString.equals("")) {

                                            for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {

                                                cell = row.getCell(c);

                                                if (cell != null) {
                                                    newSheet.setColumnWidth(c, sheet.getColumnWidth(c));
                                                    // Verify if the system is the same
                                                    if (system.equals(tmpString) && !filesCreated.contains(system)) {
                                                        // Create temporary/new cell
                                                        newCell = newRow.createCell(c);
                                                        // Clone the properties from the cell being read
                                                        newCellStyle.cloneStyleFrom(cell.getCellStyle());
                                                        // Set the new Style into the new cell created
                                                        newCell.setCellStyle(newCellStyle);

                                                        switch (cell.getCellTypeEnum()) {
                                                            case BLANK:
                                                                newCell.setCellValue("");
                                                                break;
                                                            case STRING:
                                                                newCell.setCellValue(cell.getStringCellValue());
                                                                break;
                                                            case BOOLEAN:
                                                                newCell.setCellValue(cell.getBooleanCellValue());
                                                                break;
                                                            case NUMERIC:
                                                                newCell.setCellValue(cell.getNumericCellValue());
                                                                break;
                                                            case FORMULA:
                                                                newCell.setCellFormula(cell.getCellFormula());
                                                                break;
                                                        }
                                                        added = true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (added)
                                        newRowId++;
                                }
                                // Write the new file
                                extractSystem(newWorkbook, system);
                                filesCreated.add(system);
                                newRowId = 0;
                                long finalTime = System.currentTimeMillis();
                                long totalTime = (finalTime - initialTime);
                                System.out.println("And the total time was: " + totalTime + "ms.");
                            }
                        }
                    }
                }
            }
            else {
                System.out.println("There isn't any Sheet Available.");
                System.out.println("Aborted!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
