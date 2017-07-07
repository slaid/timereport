package com.cgi.timereport.exporter;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

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
                XSSFRow row;

                // Declare a Cell Object Reference
                XSSFCell cell;

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

                // Create Sheet to Write into the document
                XSSFSheet sheet;

                if (workbook != null)
                    sheet = workbook.getSheetAt(0);
                else {
                    System.out.println("There isn't any Sheet Available.");
                    System.out.println("Aborted!");
                    return;
                }







                String sheetName = workbook.getSheetName(0);
                tmpWorkbook.setSheetName(0, sheetName);

                // Get number of Rows from Sheet
                int numOfRows = sheet.getPhysicalNumberOfRows();

                System.out.println("Number of Rows are: " + numOfRows);


                for (int r = 0; r < numOfRows; r++) {
                    row = sheet.getRow(r);

                    XSSFRow tmpRow = tmpSheet.createRow(r);
                    XSSFCell tmpCell;



                    tmpSheet.setColumnWidth(r,  sheet.getColumnWidth(r));


                    System.out.println("% % % % % % % - - ROW Number " + r + " - - % % % % % % %");
                    if (row != null) {
                        for (short c = 0; c < row.getPhysicalNumberOfCells(); c++) {
                            cell = row.getCell(c);
                            if (cell != null) {
                                XSSFCellStyle tmpCellStyle = tmpWorkbook.createCellStyle();
                                // Creates the new Cell
                                tmpCell = tmpRow.createCell(c);
                                // tmpCellStyle.setWrapText(cell.getCellStyle().getWrapText());
                                tmpCellStyle.cloneStyleFrom(cell.getCellStyle());
                                // Set the Cell type
                                tmpCell.setCellStyle(tmpCellStyle);

                                // tmpCellStyle.cloneStyleFrom(cell.getCellStyle());
                                // cell.setCellStyle(tmpCellStyle);

                                // Checks the type of the Cell
                                switch (cell.getCellTypeEnum()) {
                                    case BLANK:
                                        System.out.print("- - -  EMPTY - - -");
                                        tmpCell.setCellValue("");
                                        break;
                                    case BOOLEAN:
                                        System.out.print(cell.getBooleanCellValue());
                                        tmpCell.setCellValue(cell.getBooleanCellValue());
                                        break;
                                    case STRING:
                                        System.out.print(cell.getStringCellValue());
                                        tmpCell.setCellValue(cell.getStringCellValue());
                                        break;
                                    case NUMERIC:
                                        System.out.print(cell.getNumericCellValue());
                                        tmpCell.setCellValue(cell.getNumericCellValue());
                                        break;
                                    case FORMULA:
                                        System.out.print(cell.getCellFormula());
                                        tmpCell.setCellFormula(cell.getCellFormula());
                                        break;
                                }
                                System.out.print("  |  ");
                            }
                        }
                    }
                    System.out.println();
                    System.out.println("# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #");
                }


            try (FileOutputStream outputStream = new FileOutputStream("workbook.xlsx")) {
                tmpWorkbook.write(outputStream);
            }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Filename is: " + fileName);
        System.out.println("copy-" + fileName);
    }
}
