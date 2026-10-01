package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

import actions.CommonActions;
import utils.ExcelRandomDataGenerator;
import models.Data;

public class EOR_RegisterCandidate extends CommonActions {

    public EOR_RegisterCandidate(WebDriver driver) throws Exception {
        super(driver);
    }

    By clickEmployee = By.xpath(
        "//span[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'employees')]"
    );

    By clickEOR = By.xpath(
        "//a[@href='/employees']//span[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'eor')]"
    );

    By clickBulkUpload = By.xpath(
        "//button[normalize-space()='Bulk Upload']"
    );

    By browseFile = By.xpath(
        "//input[@type='file']"
    );

    By clickUploadFile = By.xpath(
        "//button[normalize-space()='Upload']"
    );

    By uploadSuccessMessage = By.xpath(
        "//span[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'file uploaded and processed successfully.')]"
    );

    By closeButton = By.xpath(
        "//button[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'close')]"
    );

    public void registerEORCandidate() {
        // 1. Click on Employee
        // 2. Click on EOR
        // 3. Click on Add Employee
    }

    public void registerAORCandidate() {
        // 1. Click on Employee
        // 2. Click on EOR
        // 3. Click on Add Employee
    }

    public void bulkUploadCandidates(Data input) throws Exception {
        click(clickEmployee, 120);
        click(clickEOR, 120);
        click(clickBulkUpload, 120);

        String filePath = properties.getProperty("employeeBulkUploadExcelPath");
        Data generatedEmployee = ExcelRandomDataGenerator.populateEmployeeOnboardingExcel(filePath);
        input.copyEmployeeDataFrom(generatedEmployee);
        uploadFile(browseFile, filePath);

        click(clickUploadFile, 120);
        waitForElementToAppear(uploadSuccessMessage, 120);
        click(closeButton, 120);
    }
}
