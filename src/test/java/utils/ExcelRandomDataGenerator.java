package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import models.Data;

public class ExcelRandomDataGenerator {

    public static Data populateEmployeeOnboardingExcel(String filePath) throws Exception {
        File excelFile = new File(filePath);

        if (!excelFile.exists()) {
            throw new RuntimeException("Excel file not found: " + filePath);
        }

        int employeeCount = 0;
        Data firstGeneratedEmployee = null;

        try (FileInputStream inputStream = new FileInputStream(excelFile);
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            int firstEmployeeRow = 1;
            int lastEmployeeRow = getLastEmployeeRow(sheet);

            for (int rowIndex = firstEmployeeRow; rowIndex <= lastEmployeeRow; rowIndex++) {

                Row row = sheet.getRow(rowIndex);

                if (row == null || !hasData(row)) {
                    continue;
                }

                employeeCount++;

                Data employeeData = RandomDataGenerator.generateEmployeeData();

                if (firstGeneratedEmployee == null) {
                    firstGeneratedEmployee = employeeData;
                }

                // E - State
                setCellValue(row, 4, employeeData.getState());

                // F - First Name
                setCellValue(row, 5, employeeData.getFirstName());

                // G - Middle Name
                setCellValue(row, 6, employeeData.getMiddleName());

                // H - Last Name
                setCellValue(row, 7, employeeData.getLastName());

                // I - Gender
                setCellValue(row, 8, employeeData.getGender());

                // J - Email
                setCellValue(row, 9, employeeData.getEmail());

                // K - Experience
                setCellValue(row, 10, employeeData.getExperience());

                // L - Phone
                setCellValue(row, 11, employeeData.getPhone());

                // N - Working Location
                setCellValue(row, 13, employeeData.getWorkingLocation());

                // Q - End Client
                setCellValue(row, 16, employeeData.getClient());

                // S - Designation
                setCellValue(row, 18, employeeData.getDesignation());

                // Y - Job Description
                setCellValue(row, 24, employeeData.getJobDescription());

                System.out.println("Random data generated for Excel row: " + (rowIndex + 1));
            }

            try (FileOutputStream outputStream = new FileOutputStream(excelFile)) {
                workbook.write(outputStream);
            }
        }

        System.out.println("======================================");
        System.out.println("Employee Excel updated successfully.");
        System.out.println("Total employees generated: " + employeeCount);
        System.out.println("File: " + filePath);
        System.out.println("======================================");

        if (firstGeneratedEmployee == null) {
            throw new IllegalStateException("No employee rows were available to generate employee data.");
        }

        return firstGeneratedEmployee;
    }

    private static int getLastEmployeeRow(Sheet sheet) {
        int lastRow = 0;

        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);

            if (row != null && hasData(row)) {
                lastRow = rowIndex;
            }
        }

        return lastRow;
    }

    private static boolean hasData(Row row) {
        for (int columnIndex = 0; columnIndex <= 24; columnIndex++) {
            if (row.getCell(columnIndex) != null
                    && !row.getCell(columnIndex).toString().trim().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    private static void setCellValue(Row row, int columnIndex, String value) {
        if (row.getCell(columnIndex) == null) {
            row.createCell(columnIndex);
        }

        row.getCell(columnIndex).setCellValue(value);
    }
}
