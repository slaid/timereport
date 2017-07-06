package com.cgi.timereport.exporter;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class HSSFReadWrite {

    /**
     * Creates an {@link HSSFWorkbook} with the specified OS filename
     * @param fileName The File Name
     * @return Returns a {@link HSSFWorkbook} from the given filename
     * @throws IOException In case something goes wrong reading the file
     */
    private static HSSFWorkbook readFile(String fileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        try {
            return new HSSFWorkbook(fileInputStream);
        } finally {
            fileInputStream.close();
        }
    }

    /**
     * Given a filename this outputs a sample sheet with just a set of rows/cells.
     * @param outputFilename Corresponds the output of the filename
     * @throws IOException Returns an Exception in case something goes Wrong
     */
    private static void testCreateSampleSheet(String outputFilename) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            HSSFSheet sheet = workbook.createSheet();
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            HSSFCellStyle cellStyle1 = workbook.createCellStyle();
            HSSFCellStyle cellStyle2 = workbook.createCellStyle();
            HSSFFont font1 = workbook.createFont();
            HSSFFont font2 = workbook.createFont();

            font1.setFontHeightInPoints((short) 12);
            font1.setColor((short) 0xA);
            font1.setBold(true);
            font2.setFontHeightInPoints((short) 10);
            font2.setColor((short) 0xF);
            font2.setBold(true);

            cellStyle.setFont(font1);
            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("($#,##0_);[Red]($#,##0)"));
            cellStyle1.setBorderBottom(BorderStyle.THIN);
            cellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle1.setFillForegroundColor((short) 0xA);
            cellStyle1.setFont(font2);

            workbook.setSheetName(0, "HSSF Test");
            int rowNum;
            for (rowNum = 0; rowNum < 300; rowNum++) {
                HSSFRow row = sheet.createRow(rowNum);
                if ((rowNum % 2) == 0) {
                    row.setHeight((short) 0x249);
                }

                for (int cellNum = 0; cellNum < 50; cellNum += 2) {
                    HSSFCell cell = row.createCell(cellNum);
                    cell.setCellValue(rowNum * 10000 + cellNum + (((double) rowNum / 1000) + ((double) cellNum / 10000)));
                    if ((rowNum % 2) == 0)
                        cell.setCellStyle(cellStyle);
                    cell = row.createCell(cellNum + 1);
                    cell.setCellValue(new HSSFRichTextString("Test"));
                    // 50 characters divided by 1/20th of a point
                    sheet.setColumnWidth(cellNum + 1, (int) (50 * 8 / 0.05));
                    if ((rowNum % 2) == 0)
                        cell.setCellStyle(cellStyle2);
                }
            }

            // Draw a thick black border on the row at the bottom using BLANKS
            rowNum++;
            rowNum++;
            HSSFRow row = sheet.createRow(rowNum);

            cellStyle2.setBorderBottom(BorderStyle.THICK);
            for (int cellNum = 0; cellNum < 50; cellNum++) {
                HSSFCell cell = row.createCell(cellNum);
                cell.setCellStyle(cellStyle2);
            }

            sheet.addMergedRegion(new CellRangeAddress(0, 3, 0, 3));
            sheet.addMergedRegion(new CellRangeAddress(100, 110, 100, 110));

            // End Draw think black border
            // Create a sheet, set its title then delete it
            workbook.createSheet();
            workbook.setSheetName(1, "DeletedSheet");
            workbook.removeSheetAt(1);

            // End Delete Sheet
            try (FileOutputStream out = new FileOutputStream(outputFilename)) {
                workbook.write(out);
            }


        } finally {
            workbook.close();
        }
    }

    /**
     * Method main
     *
     * Given 1 argument takes that as the filename, inputs it and dumps the
     * cell values/types out to sys.out.<br/>
     *
     * Given 2 arguments where the second argument is the word "write" and the
     * first is the filename - writes out a sample (test) spreadsheet
     * see {@link HSSFReadWrite#testCreateSampleSheet(String)}.<br/>
     *
     * Given 2 arguments where the first is an input filename and the second
     * an output filename (not write), attempts to fully read in the
     * spreadsheet and fully write it out.<br/>
     *
     * Given 3 arguments where the first is an input filename and the second an
     * output filename (not write) and the third is "modify1", attempts to read in the
     * spreadsheet, deletes rows 0-24. 74-99. Changes cell at row 39, col 3 to
     * "MODIFIED CELL" then writes it out. Hence this is "modify test 1". If you take
     * the output from the write test, you'll have a valid scenario.
     *
     * @param args
     */

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("At least one argument expected");
            return;
        }

        String fileName = args[0];
        try {
            if (args.length < 2) {
                try (HSSFWorkbook workbook = HSSFReadWrite.readFile(fileName)) {
                    for (int k = 0; k < workbook.getNumberOfSheets(); k++) {
                        HSSFSheet sheet = workbook.getSheetAt(k);
                        int rows = sheet.getPhysicalNumberOfRows();
                        System.out.println("Sheet " + k + " \"" + workbook.getSheetName(k) + "\" has " + rows + " row(s).");
                        for (int r = 0; r < rows; r++) {
                            HSSFRow row = sheet.getRow(r);
                            if (row == null)
                                continue;
                            System.out.println("\nROW " + row.getRowNum() + " has " + row.getPhysicalNumberOfCells() + " cell(s).");
                            for (int c = 0; c < row.getLastCellNum(); c++) {
                                HSSFCell cell = row.getCell(c);
                                String value;

                                if (cell != null) {
                                    switch (cell.getCellTypeEnum()) {
                                        case FORMULA:
                                            value = "FORMULA value=" + cell.getCellFormula();
                                            break;
                                        case NUMERIC:
                                            value = "NUMERIC value=" + cell.getNumericCellValue();
                                            break;
                                        case STRING:
                                            value = "STRING value=" + cell.getStringCellValue();
                                            break;
                                        case BLANK:
                                            value = "<BLANK>";
                                            break;
                                        case BOOLEAN:
                                            value = "BOOLEAN value=" + cell.getBooleanCellValue();
                                            break;
                                        case ERROR:
                                            value = "ERROR value=" + cell.getErrorCellValue();
                                            break;
                                        default:
                                            value = "UNKNOWN value of type " + cell.getCellTypeEnum();
                                    }
                                    System.out.println("CELL col=" + cell.getColumnIndex() + " VALUE=" + value);
                                }
                            }
                        }
                    }
                }
            } else if (args.length ==2) {
                if (args[1].toLowerCase(Locale.ROOT).equals("write")) {
                    System.out.println("Write mode");
                    long time = System.currentTimeMillis();
                    HSSFReadWrite.testCreateSampleSheet(fileName);
                    System.out.println("" + (System.currentTimeMillis() - time) + " ms generation time");
                } else {
                    System.out.println("Readwrite test");
                    try (HSSFWorkbook workbook = HSSFReadWrite.readFile(fileName)) {
                        try (FileOutputStream stream = new FileOutputStream(args[1])) {
                            workbook.write(stream);
                        }
                    }
                }
            } else if (args.length == 3 && args[2].equalsIgnoreCase("modify1")) {
                // Delete row 0-24, row 74-99 && change cell 3 on row 39 to string "MODIFIED CELL!!"

                try (HSSFWorkbook workbook = HSSFReadWrite.readFile(fileName)){
                    HSSFSheet sheet = workbook.getSheetAt(0);

                    for (int k=0; k<25; k++) {
                        HSSFRow row = sheet.getRow(k);
                        System.out.println("Row Number " + row.getRowNum() + " was deleted!!!");
                        sheet.removeRow(row);
                    }

                    for (int k=74; k<100; k++) {
                        HSSFRow row = sheet.getRow(k);
                        System.out.println("Row Number " + row.getRowNum() + " was deletd!!!");
                        sheet.removeRow(row);
                    }
                    HSSFRow row = sheet.getRow(39);
                    HSSFCell cell = row.getCell(3);
                    cell.setCellValue("MODIFIED CELL!!!");

                    try (FileOutputStream fileOutputStream = new FileOutputStream(args[1])) {
                        workbook.write(fileOutputStream);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
