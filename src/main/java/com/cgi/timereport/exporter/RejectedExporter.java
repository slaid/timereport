package com.cgi.timereport.exporter;

import com.cgi.timereport.util.ExporterUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RejectedExporter {

    public static void main(String[] args) {

        try (XSSFWorkbook workbook = ExporterUtil.readFile("C:\\Users\\michael.carvalho\\Documents\\Angela\\Väylätieto_Time_Reports_PT_Team_2017M06_v1.xlsx")) {

            if (workbook != null) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                XSSFRow row;
                XSSFCell cell;
                XSSFCell cell1;
                Pattern datePattern = Pattern.compile("\\d2-\\d2-\\d4");
                Pattern colZeroPattern = Pattern.compile("[1-53]");
                Pattern colOnePattern = Pattern.compile("");
                Pattern colTwoPattern = Pattern.compile("");
                Pattern colThreePattern = Pattern.compile("");
                Pattern colFourPattern = Pattern.compile("");
                Pattern colFivePattern = Pattern.compile("");
                Pattern colSixPattern = Pattern.compile("");
                Matcher matcher;



                if (sheet != null) {

                    try (XSSFWorkbook newWorkbook = new XSSFWorkbook()) {
                        XSSFSheet newSheet = newWorkbook.createSheet();
                        XSSFRow newRow;
                        XSSFCell newCell;
                        XSSFCellStyle newCellType = newWorkbook.createCellStyle();
                        XSSFDataFormat newDataFormat = newWorkbook.createDataFormat();
                        int numberOfRows = sheet.getPhysicalNumberOfRows();
                        long initialTime = System.currentTimeMillis();
                        int newRowId = 1;

                        if (numberOfRows != 0) {

                            row = sheet.getRow(0);
                            newRow = newSheet.createRow(0);

                            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                                cell = row.getCell(i);
                                newCell = newRow.createCell(i);
                                newSheet.setColumnWidth(i, sheet.getColumnWidth(i));
                                newCellType.cloneStyleFrom(cell.getCellStyle());
                                newCell.setCellStyle(newCellType);

                                if (i == 0 || i == 1 || i == 2)
                                    newCellType.setAlignment(HorizontalAlignment.CENTER_SELECTION);

                                if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                                    newSheet.setAutoFilter(CellRangeAddress.valueOf("A1:H1"));
                                    newCell.setCellValue(cell.getStringCellValue());
                                }
                            }

                            // Loop over the rows
                            for (int r = 1; r < numberOfRows; r++) {
                                row = sheet.getRow(r);
                                newRow = newSheet.createRow(newRowId);

                                if (row == null) {
                                    // If its empty its not worth doing nothing
                                    continue;
                                }

                                // Loop over the columns
                                for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
                                    cell = row.getCell(c);
                                    cell1 = row.getCell(c+1);

                                   if ((!cell.getCellTypeEnum().equals(CellType.BLANK) && cell.getColumnIndex() == 7) ||
                                            (cell.getCellTypeEnum().equals(CellType.BLANK) && cell.getColumnIndex() == 4) ||
                                            ((!cell.getCellTypeEnum().equals(CellType.BLANK) && cell.getColumnIndex() ==4) &&
                                               (cell1.getCellTypeEnum().equals(CellType.BLANK) && cell1.getColumnIndex() == 5)) ||
                                           (cell.getCellTypeEnum().equals(CellType.BLANK) && c==1) || (cell.getCellTypeEnum().equals(CellType.BLANK) && c==2) ||
                                           (cell.getCellTypeEnum().equals(CellType.BLANK) && c==3)) {

                                        // Loop the Columns and insert the data into the bew Sheet
                                        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                                            cell = row.getCell(i);
                                            newCell = newRow.createCell(i);
                                            newCellType = newWorkbook.createCellStyle();
                                            newCellType.cloneStyleFrom(cell.getCellStyle());

                                            switch (cell.getCellTypeEnum()) {
                                                case BLANK:
                                                    newCell.setCellValue("");
                                                    break;
                                                case STRING:
                                                    newCell.setCellValue(cell.getStringCellValue());
                                                    break;
                                                case FORMULA:
                                                    newCell.setCellFormula(cell.getCellFormula());
                                                    break;
                                                case BOOLEAN:
                                                    newCell.setCellValue(cell.getBooleanCellValue());
                                                    break;
                                                case NUMERIC:
                                                    switch (i) {
                                                        case 0:
                                                            newCell.setCellValue((int) cell.getNumericCellValue());
                                                            break;
                                                        case 1:
                                                            // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                                            // String date = sdf.format(cell.getDateCellValue());
                                                            // System.out.println(date);
                                                            newCell.setCellValue(cell.getDateCellValue());
                                                            break;
                                                        case 2:
                                                            newCell.setCellValue(cell.getNumericCellValue());
                                                            break;
                                                    }
                                                    break;
                                            }
                                            newCell.setCellStyle(newCellType);
                                        }
                                        newRowId++;
                                        break;
                                   }
                                   String tmp;
                                   switch (c) {
                                       case 0:
                                           tmp = cell.getRawValue();
                                           System.out.println(tmp);
                                           break;
                                       case 1:
                                           Date tmpDate =DateUtil.getJavaDate(cell.getNumericCellValue());
                                           System.out.println(tmpDate.toString());
                                           break;
                                       case 2:

                                           break;
                                       case 3:

                                           break;
                                       case 4:

                                           break;
                                       case 5:

                                           break;
                                       case 6:

                                           break;
                                       case 7:

                                           break;

                                   }




                                   /*else {
                                       System.out.println("Entro aqui?");
                                       for (int i=0; i<row.getPhysicalNumberOfCells(); i++) {
                                           cell =row.getCell(i);
                                           newCell = newRow.createCell(i);
                                           newCellType.cloneStyleFrom(cell.getCellStyle());

                                           switch (i) {
                                               case 0:
                                                   if (!cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                                                       System.out.println("- - - - - - NOT A NUMBER - - - - - ");
                                                       newCell.setCellValue(cell.getRawValue());
                                                   }
                                                   break;
                                               case 1:

                                                   break;
                                               case 2:

                                                   break;
                                               case 3:

                                                   break;
                                               case 4:

                                                   break;
                                               case 5:

                                                   break;
                                               case 6:

                                                   break;
                                               case 7:

                                                   break;
                                           }
                                       }
                                   }
                                   */

                                }
                            }
                            newRowId = 1;
                            long finalTime = System.currentTimeMillis();
                            long totalTime = (finalTime - initialTime) / 1000;
                            ExporterUtil.extractSystem(newWorkbook, "System");
                            System.out.println("Total time for this operation was: " + totalTime + "ms");
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Something went with opening the workbook");
        }
    }
}
