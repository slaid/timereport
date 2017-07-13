package com.cgi.timereport.exporter;

import com.cgi.timereport.util.ExporterUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RejectedExporter {

    String fileName;

    public RejectedExporter(String fileName) {
        this.fileName = fileName;
    }

    public void execute() {
        try (XSSFWorkbook workbook = ExporterUtil.readFile(fileName)) {

            if (workbook != null) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                XSSFRow row;
                XSSFCell cell;
                XSSFCell cell1;
                Pattern datePattern = Pattern.compile("\\d2-\\d2-\\d4");
                Pattern colZeroPattern = Pattern.compile("[22]");
                Pattern colOnePattern = Pattern.compile("[0-3][0-9]-[0-1][0-9]-20[0-9][0-9]");
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
                        boolean formatError = false;
                        boolean blank = false;

                        if (numberOfRows != 0) {

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

                                    newCell = newRow.createCell(c);
                                    newCellType = newWorkbook.createCellStyle();
                                    newCellType.cloneStyleFrom(cell.getCellStyle());
                                    // newCell.setCellStyle(newCellType);

                                    switch (c) {
                                        case 0:
                                            if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                                newCellType.setFillForegroundColor((short) 0x5);
                                                newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                newCell.setCellValue("");
                                                if (!formatError)
                                                    formatError = true;
                                            } else if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                                                Integer tmp = (int) cell.getNumericCellValue();
                                                matcher = colZeroPattern.matcher(String.valueOf(tmp));
                                                if (!matcher.find()) {
                                                    newCellType.setFillForegroundColor((short) 0x5);
                                                    newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                    newCell.setCellValue(tmp);
                                                    if (!formatError)
                                                        formatError = true;
                                                }
                                                else {
                                                    newCell.setCellValue((int) cell.getNumericCellValue());
                                                }
                                            }
                                            break;

                                        case 1:
                                            if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                                newCellType.setFillForegroundColor((short) 0x5);
                                                newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                newCell.setCellValue("");
                                                if (!formatError)
                                                    formatError = true;
                                            } else if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                                String date = sdf.format(cell.getDateCellValue());
                                                // Falta fazer o Matcher para o tipo de data
                                                // System.out.println(date);
                                                newCell.setCellValue(date);
                                            }
                                            break;

                                        case 2:
                                            if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                                newCellType.setFillForegroundColor((short) 0x5);
                                                newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                newCell.setCellValue("");
                                                if (!formatError)
                                                    formatError = true;
                                            } else if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                                                newCell.setCellValue((int) cell.getNumericCellValue());
                                            }
                                            break;

                                        case 3:
                                            if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                                newCellType.setFillForegroundColor((short) 0x5);
                                                newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                newCell.setCellValue("");
                                            } else if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                                                newCell.setCellValue(cell.getStringCellValue());
                                            }
                                            break;

                                        case 4:
                                        case 5:
                                            if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                                newCellType.setFillForegroundColor((short) 0x5);
                                                newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                newCell.setCellValue("");
                                                if (!formatError)
                                                    formatError = true;
                                            } else if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                                                newCell.setCellValue(cell.getStringCellValue());
                                            }
                                            break;

                                        case 6:
                                            if (cell.getCellTypeEnum().equals(CellType.BLANK))
                                                newCell.setCellValue("");
                                            else if (cell.getCellTypeEnum().equals(CellType.STRING))
                                                newCell.setCellValue(cell.getStringCellValue());
                                            break;

                                        case 7:
                                            if (!cell.getCellTypeEnum().equals(CellType.BLANK) && cell.getCellTypeEnum().equals(CellType.STRING)) {
                                                newCellType.setFillForegroundColor((short) 0x5);
                                                newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                newCell.setCellValue(cell.getStringCellValue());
                                            }
                                            break;
                                    }

                                    newCell.setCellStyle(newCellType);



                                    /*

                                    // Checks if the the required columns are BLANK
                                    // Verifies if the Absences column is filled
                                    // Verifies if the System column is filled
                                    // Verifies if the System column is filled AND if the Task column is filled
                                    // Verifies if, at least, one of the columns is filled (Date column, Hours column, Name column
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
                                                    if (i == 1|| i == 2 || i == 3) {
                                                        // newCellType.setFillBackgroundColor((short) 0xb);
                                                        newCellType.setFillForegroundColor((short) 0x5);
                                                        newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                    }
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
                                   } else {

                                       switch (c) {
                                           // For the column A verify if its a valid number
                                           case 0:
                                               if (cell.getCellTypeEnum().equals(CellType.NUMERIC) || cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                                   Integer tmp = (int) cell.getNumericCellValue();
                                                   matcher = colZeroPattern.matcher(String.valueOf(tmp));
                                                   if (!matcher.find()) {
                                                       System.out.println("Entro aqui alguma vez?");
                                                       System.out.println(tmp);
                                                       newCell = newRow.createCell(c);
                                                       newCellType = newWorkbook.createCellStyle();
                                                       newCellType.cloneStyleFrom(cell.getCellStyle());
                                                       newCellType.setFillForegroundColor((short) 0x5);
                                                       newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                       newCell.setCellValue(cell.getNumericCellValue());
                                                       newCell.setCellStyle(newCellType);
                                                   }
                                               }
                                               break;
                                       }
                                   }
                                   */



                                }

                                if (formatError) {
                                    newRowId++;
                                    formatError = false;
                                }


                            }
                            newRowId = 1;
                            long finalTime = System.currentTimeMillis();
                            long totalTime = (finalTime - initialTime) / 1000;
                            ExporterUtil.extractSystem(newWorkbook, "System" + "_" + "REJECTED");
                            System.out.println("Total time for this operation was: " + totalTime + "ms");
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Something went with opening the workbook");
        }
    }

    public static void main(String[] args) {

        try (XSSFWorkbook workbook = ExporterUtil.readFile("C:\\Users\\michael.carvalho\\Documents\\Angela\\Väylätieto_Time_Reports_PT_Team_2017M06_v1.xlsx")) {

            if (workbook != null) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                XSSFRow row;
                XSSFCell cell;
                XSSFCell cell1;
                Pattern datePattern = Pattern.compile("\\d2-\\d2-\\d4");
                Pattern colZeroPattern = Pattern.compile("[22]");
                Pattern colOnePattern = Pattern.compile("[0-3][0-9]-[0-1][0-9]-20[0-9][0-9]");
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
                        boolean formatError = false;
                        boolean blank = false;

                        if (numberOfRows != 0) {

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

                                    newCell = newRow.createCell(c);
                                    newCellType = newWorkbook.createCellStyle();
                                    newCellType.cloneStyleFrom(cell.getCellStyle());
                                    // newCell.setCellStyle(newCellType);

                                    switch (c) {
                                        case 0:
                                            if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                                newCellType.setFillForegroundColor((short) 0x5);
                                                newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                newCell.setCellValue("");
                                                if (!formatError)
                                                    formatError = true;
                                            } else if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                                                Integer tmp = (int) cell.getNumericCellValue();
                                                matcher = colZeroPattern.matcher(String.valueOf(tmp));
                                                if (!matcher.find()) {
                                                    newCellType.setFillForegroundColor((short) 0x5);
                                                    newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                    newCell.setCellValue(tmp);
                                                    if (!formatError)
                                                        formatError = true;
                                                }
                                                else {
                                                    newCell.setCellValue((int) cell.getNumericCellValue());
                                                }
                                            }
                                            break;

                                        case 1:
                                            if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                                newCellType.setFillForegroundColor((short) 0x5);
                                                newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                newCell.setCellValue("");
                                                if (!formatError)
                                                    formatError = true;
                                            } else if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                                String date = sdf.format(cell.getDateCellValue());
                                                // Falta fazer o Matcher para o tipo de data
                                                // System.out.println(date);
                                                newCell.setCellValue(date);
                                            }
                                            break;

                                        case 2:
                                            if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                                newCellType.setFillForegroundColor((short) 0x5);
                                                newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                newCell.setCellValue("");
                                                if (!formatError)
                                                    formatError = true;
                                            } else if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                                                newCell.setCellValue((int) cell.getNumericCellValue());
                                            }
                                            break;

                                        case 3:
                                            if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                                newCellType.setFillForegroundColor((short) 0x5);
                                                newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                newCell.setCellValue("");
                                            } else if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                                                newCell.setCellValue(cell.getStringCellValue());
                                            }
                                            break;

                                        case 4:
                                        case 5:
                                            if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                                newCellType.setFillForegroundColor((short) 0x5);
                                                newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                newCell.setCellValue("");
                                                if (!formatError)
                                                    formatError = true;
                                            } else if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                                                newCell.setCellValue(cell.getStringCellValue());
                                            }
                                            break;

                                        case 6:
                                            if (cell.getCellTypeEnum().equals(CellType.BLANK))
                                                newCell.setCellValue("");
                                            else if (cell.getCellTypeEnum().equals(CellType.STRING))
                                                newCell.setCellValue(cell.getStringCellValue());
                                            break;

                                        case 7:
                                            if (!cell.getCellTypeEnum().equals(CellType.BLANK) && cell.getCellTypeEnum().equals(CellType.STRING)) {
                                                newCellType.setFillForegroundColor((short) 0x5);
                                                newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                newCell.setCellValue(cell.getStringCellValue());
                                            }
                                            break;
                                    }

                                    newCell.setCellStyle(newCellType);



                                    /*

                                    // Checks if the the required columns are BLANK
                                    // Verifies if the Absences column is filled
                                    // Verifies if the System column is filled
                                    // Verifies if the System column is filled AND if the Task column is filled
                                    // Verifies if, at least, one of the columns is filled (Date column, Hours column, Name column
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
                                                    if (i == 1|| i == 2 || i == 3) {
                                                        // newCellType.setFillBackgroundColor((short) 0xb);
                                                        newCellType.setFillForegroundColor((short) 0x5);
                                                        newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                    }
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
                                   } else {

                                       switch (c) {
                                           // For the column A verify if its a valid number
                                           case 0:
                                               if (cell.getCellTypeEnum().equals(CellType.NUMERIC) || cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                                   Integer tmp = (int) cell.getNumericCellValue();
                                                   matcher = colZeroPattern.matcher(String.valueOf(tmp));
                                                   if (!matcher.find()) {
                                                       System.out.println("Entro aqui alguma vez?");
                                                       System.out.println(tmp);
                                                       newCell = newRow.createCell(c);
                                                       newCellType = newWorkbook.createCellStyle();
                                                       newCellType.cloneStyleFrom(cell.getCellStyle());
                                                       newCellType.setFillForegroundColor((short) 0x5);
                                                       newCellType.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                       newCell.setCellValue(cell.getNumericCellValue());
                                                       newCell.setCellStyle(newCellType);
                                                   }
                                               }
                                               break;
                                       }
                                   }
                                   */



                                }

                                if (formatError) {
                                    newRowId++;
                                    formatError = false;
                                }


                            }
                            newRowId = 1;
                            long finalTime = System.currentTimeMillis();
                            long totalTime = (finalTime - initialTime) / 1000;
                            ExporterUtil.extractSystem(newWorkbook, "System" + "_" + "REJECTED");
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
