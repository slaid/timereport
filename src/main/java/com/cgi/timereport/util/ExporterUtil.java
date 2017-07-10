package com.cgi.timereport.util;


import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExporterUtil {
    /**
     * Creates an {@link XSSFWorkbook} with the specified filename
     * @param filename The file name
     * @return Returns a {@link XSSFWorkbook} from the given filename
     */
    public static XSSFWorkbook readFile(String filename) {
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
}
