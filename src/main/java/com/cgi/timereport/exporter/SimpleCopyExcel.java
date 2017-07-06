package com.cgi.timereport.exporter;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SimpleCopyExcel {

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

    /**
     * Creates an {@link XSSFWorkbook} with the specified filename
     * @param filename The file name
     * @return Returns a {@link XSSFWorkbook} from the given filename
     */
    private static XSSFWorkbook readFile1(String filename) {
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

        try (XSSFWorkbook workbook = readFile1("C:\\Users\\michael.carvalho\\Documents\\Angela\\OTH_201706_v2.xlsx")) {
            try (XSSFWorkbook tmpWorkbook = new XSSFWorkbook()) {
                // Create a Sheet
                XSSFSheet tmpSheet = tmpWorkbook.createSheet();


                // Declare a Row Object Reference
                Row row;

                // Declare a Cell Object Reference
                Cell cell;

                CellStyle cellStyle = tmpWorkbook.createCellStyle();

                Font font = tmpWorkbook.createFont();
                font.setFontHeightInPoints((short) 12);
                font.setColor((short) 0xC);
                font.setStrikeout(true);
                font.setFontName("Verdana");
                font.setBold(true);

                cellStyle.setFont(font);
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setFillForegroundColor((short) 0xA);

                tmpWorkbook.setSheetName(0, "First Sheet");

                XSSFSheet sheet = workbook.getSheetAt(0);
                int numOfRows = sheet.getPhysicalNumberOfRows();

                System.out.println("Number of Rows are: " + numOfRows);

                for (int r=0; r < 25; r++) {
                    row = sheet.getRow(r);
                    for (short c=0; c < 2; c++) {
                        cell = row.getCell(c);
                        System.out.println(cell.toString());
                    }
                    System.out.println("- - - - - - - -");
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Filename is: " + fileName);
        System.out.println("copy-" + fileName);
       //  System.out.println(workbook.getNameAt(0));
    }

}
