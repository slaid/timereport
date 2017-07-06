package com.cgi.timereport.exporter;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class WritingExcel {
    public static void main(String[] args) {
        short rowNum;

        try {
            // Create a New File
            FileOutputStream out = new FileOutputStream("workbook.xls");

            // Create a New Workbook
            Workbook workbook = new HSSFWorkbook();

            // Create a new Sheet
            Sheet sheet = workbook.createSheet();

            // Declare a Row Object Reference
            Row row;

            // Declare a Cell Object Reference
            Cell cell;

            // Create 3 cell styles
            CellStyle cellStyle = workbook.createCellStyle();
            CellStyle cellStyle1 = workbook.createCellStyle();
            CellStyle cellStyle2 = workbook.createCellStyle();
            DataFormat dataFormat = workbook.createDataFormat();

            // Create 2 fonts objects
            Font font = workbook.createFont();
            Font font1 = workbook.createFont();

            // Set font 1 to 12 point type
            font.setFontHeightInPoints((short) 12);
            // Make it blue
            font.setColor((short) 0xC);
            // Make it bold
            // Arial is the default font
            // font.setBoldweight(Font.BOLDWEIGHT_BOLD);

            // Set font1 to 10 point type
            font1.setFontHeightInPoints((short) 10);
            // Make it red
            font1.setColor(Font.COLOR_RED);
            // Make it Bold
            // font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font1.setStrikeout(true);

            // Set cell type
            cellStyle.setFont(font);
            // Set the cell format
            cellStyle.setDataFormat(dataFormat.getFormat("#,##0.0"));

            // Set thin Border
            cellStyle1.setBorderBottom(BorderStyle.THIN);
            // Fill w foreground fill color
            cellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // Set the cell format to text see DataFormat for a full list
            cellStyle1.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

            // Set the Font for the CellStyle1
            cellStyle1.setFont(font1);

            // Set the sheet name in Unicode
            workbook.setSheetName(0, "\u0422\u0435\u0441\u0442\u043E\u0432\u0430\u044F " +
                    "\u0421\u0442\u0440\u0430\u043D\u0438\u0447\u043A\u0430");
            // In case of plain ASCII
            // wb.setSheetName(0, "HSSF Test");

            // Create a sheet with 30 rows (0-29)
            for (rowNum = 0; rowNum < 30; rowNum++) {
                // Create a Row
                row = sheet.createRow(rowNum);
                // On Every other row
                if ((rowNum % 2) == 0) {
                    // Make the row height higher (in twips - 1/20 of a point)
                    row.setHeight((short) 0x249);
                }

                // row.setRowNum((short) rowNum);
                // Create 10 cells (0-9) (the += 2 becomes apparent later
                for (short cellNum = (short) 0; cellNum < 10; cellNum += 2) {
                    // Create a Numeric Cell
                    cell = row.createCell(cellNum);
                    // Do some goofy math to demonstrate decimals
                    cell.setCellValue(rowNum * 10000 + cellNum + (((double) rowNum / 1000)
                            + ((double) cellNum / 10000)));
                    String cellValue;

                    // Create a String cell (see why += 2 in the)
                    cell = row.createCell((short) (cellNum + 1));

                    // On Every other row
                    if ((rowNum % 2) == 0) {
                        // Set this cell to the first cell style we defined
                        cell.setCellStyle(cellStyle);
                        // Set the cell's String value to "Test"
                        cell.setCellValue("Test");
                    } else {
                        cell.setCellStyle(cellStyle1);
                        // Set the cell's String value to "\u0422\u0435\u0441\u0442"
                        cell.setCellValue("\u0422\u0435\u0441\u0442");
                    }

                    // Make this column a bit wider
                    sheet.setColumnWidth((cellNum + 1), (int) ((50 * 8) / ((double) 1 / 20)));
                }
            }

            // Draw a thick black border on the row at the bottom using BLANKS
            // advance 2 rows
            rowNum++;
            rowNum++;

            row = sheet.createRow(rowNum);

            // Define the third style to be the default
            // Except with a thick black border at the bottom
            cellStyle2.setBorderBottom(BorderStyle.THICK);

            // Create 50 cells
            for (short cellNum = (short) 0; cellNum < 50; cellNum++) {
                // Create a blank type cell (no value)
                cell = row.createCell(cellNum);
                // Set it to the thick black border style
                cell.setCellStyle(cellStyle2);
            }

            // End Draw thick black border

            // Demonstrate adding/naming and deleting a sheet
            // Create a sheet, set its title then delete it
            // sheet = workbook.createSheet();
            workbook.setSheetName(1, "DeletedSheet");
            workbook.removeSheetAt(1);
            // End Deleted Sheet

            // Write the workbook to the output stream
            // Close our file (don't blow out our file handles)
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
