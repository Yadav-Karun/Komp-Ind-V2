package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import actions.CommonActions;

public class CandidateSelfInfo extends CommonActions {
	public CandidateSelfInfo(WebDriver driver) throws Exception {
		super(driver);
	}

    // Personal Information Locators
    By startNowButton = By.xpath("//button[starts-with(normalize-space(.), 'Start Now')]");
    By fatherNameField = By.xpath("//input[@name='father_name']");
    By motherNameField = By.xpath("//input[@name='mother_name']");
    By maritalStatusDropdown = By.xpath("(//button[@role='combobox'])[1]");
    By marriedOption = By.xpath("//select/option[normalize-space()='Married']");
    By unmarriedOption = By.xpath("//select/option[normalize-space()='Unmarried']");
    By ifMaritalStatusIsMarriedSpouseNameField = By.xpath("//input[@name='spouse_name']");
    By nationalityDropdown = By.xpath("//span[@class='truncate text-muted-foreground']");
    By nationalitySearchField = By.xpath("//input[@id='radix-_r_1e_']");
    By nationalityCountryOption = By.xpath("//div[@data-value='India']");
    By bloodGroupDropdown = By.xpath("(//button[@role='combobox'])[3]");
    By dateOfBirthField = By.xpath("//input[@name='emp_dob']");
    By physicallyHandicappedDropdown = By.xpath("(//button[@role='combobox'])[4]");
    By saveAndContinueButton = By.xpath("//button[normalize-space()='Save & Continue']");

    //Contact Information Locators
    By alternateMobileNumberField = By.xpath("//input[@name='alternate_mobile_no']");
    By emergencyContactNameField = By.xpath("//input[@name='emergency_name']");
    By emergencyContactRelationshipDropdown = By.xpath("//button[@role='combobox']");
    By emergencyContactNumberField = By.xpath("//input[@name='emergency_contact_no']");
    By currentAddressCountryDropdown = By.xpath("(//button[@type='button'])[11]");
    By currentAddressStateDropdown = By.xpath("(//button[@type='button'])[12]");
    By currentAddressCityField = By.xpath("(//button[@type='button'])[13]");
    By currentAddressPostalCodeField = By.xpath("//input[@name='current.pincode']");
    By currentAddressField = By.xpath("//input[@name='current.address']");
    By sameAsCurrentAddressCheckbox = By.xpath("//button[@id='same-as-current']");

    // Banking Information Locators
    By accountHolderNameField =By.xpath("//input[@name='account_holder_name']");
    By bankNameField = By.xpath("//input[@name='bank_name']");
    By accountNumberField = By.xpath("//input[@name='account_no']");
    By confirmAccountNumberField = By.xpath("//input[@name='confirm_account_no']");
    By ifscCodeField = By.xpath("//input[@name='ifsc_code']");
    By accountTypeDropdown = By.xpath("//button[@role='combobox']");
    By branchNameField = By.xpath("//input[@name='branch_name']");
    By bankAddressField = By.xpath("//input[@name='bank_address']");
    By uploadCancelledChequeButton = By.xpath("//input[@name='cancelled_cheque']");

    // Professional Information Locators
    By UAN = By.xpath("//input[@name='uan_no']");
    By ESIC = By.xpath("//input[@name='esi_acc_no']");
    By primary = By.xpath("//input[@placeholder='Comma-separated, e.g. React, Node.js']");
    By secondary = By.xpath("//input[@placeholder='Comma-separated']");
    By languageName = By.xpath("//input[@name='languages.0.Language_known']");
    By proficiencyDropdown = By.xpath("//label[text()='Proficiency']/following-sibling::button[@role='combobox']");

    // Education Information Locators
    By addEducationButton = By.xpath(
        "//section[.//h3[normalize-space()='Education']]//button[normalize-space()='Add']");
    By addCertificateButton = By.xpath(
        "//section[.//h3[normalize-space()='Certificates']]//button[normalize-space()='Add']");

    By educationLevelDropdown = By.xpath(
        "//label[normalize-space()='Education Level']/following-sibling::button[@type='button']");

    By educationInstitutionNameField = By.xpath(
            "//label[normalize-space()='Institution Name']/parent::*//input");

    By fieldOfStudyField = By.xpath(
            "//label[normalize-space()='Field of Study']/following::input[1]");

    By universityNameField = By.xpath(
            "//label[normalize-space()='University Name']/following::input[1]");

    By modeOfEducationField = By.xpath(
            "//label[normalize-space()='Mode of Education']/following-sibling::button[@type='button']");

    By passingYearField = By.xpath(
            "//label[normalize-space()='Passing Year']/following::input[1]");

    By certificateNameField = By.xpath(
            "//section[.//h3[normalize-space()='Certificates']]//label[normalize-space()='Certificate Name']/following::input[1]");

    By certificateInstitutionNameField = By.xpath(
            "//section[.//h3[normalize-space()='Certificates']]//label[normalize-space()='Institution Name']/following::input[1]");

    By certificateIssueDateField = By.xpath(
            "//section[.//h3[normalize-space()='Certificates']]//label[normalize-space()='Issue Date']/following::input[1]");
    
    By uploadCertificate = By.xpath("//input[@name='certificates.0.upload_certifications']");
    By closeToastButton = By.xpath("//button[@aria-label='Close toast' and @data-close-button='true']");

    // Identification Information Locators
    By aadhaarNumberField = By.xpath("//input[@name='aadhar_no']");
    By panNumberField = By.xpath("//input[@name='pan_no']");
    By drivingLicenceNumberField = By.xpath("//input[@name='licence_no']");
    By passportNumberField = By.xpath("//input[@name='passport_no']");

    By uploadAadhaarButton = By.xpath("//input[@name='upload_aadhar_front']");
    By uploadPanButton = By.xpath("//input[@name='upload_pancard']");
    By uploadDrivingLicenceButton = By.xpath("//input[@name='upload_driving_licence']");
    By uploadPassportButton = By.xpath("//input[@name='upload_passport']");

    By antiFinancialFraudCheckbox = By.xpath("//label[contains(normalize-space(.),'Anti-Financial Fraud Declaration')]//input");
    By poshCheckbox = By.xpath("//label[contains(normalize-space(.),'Prevention of Sexual Harassment (POSH) Policy')]//input");
    By employeeDeclarationCheckbox = By.xpath("//label[normalize-space()='Employee Declaration']//input");

    By antiFinancialFraudViewButton = By.xpath(
        "//div[contains(@class,'rounded-[9px]')][.//div[normalize-space()='Anti-Financial Fraud Declaration']]//button[normalize-space()='View & Acknowledge']");

    By poshViewButton = By.xpath(
        "//div[contains(@class,'rounded-[9px]')][.//div[normalize-space()='Prevention of Sexual Harassment (POSH) Policy']]//button[normalize-space()='View & Acknowledge']");

    By employeeDeclarationViewButton = By.xpath(
        "//div[contains(@class,'rounded-[9px]')][.//div[normalize-space()='Employee Declaration']]//button[normalize-space()='View & Acknowledge']");

    By iAcknowledge = By.xpath("//button[normalize-space()='I Acknowledge']");
    By doneLogout = By.xpath("//button[normalize-space()='Done']");

    public void clickStartNowButton() throws Exception {
        click(startNowButton, 10, "Start Onboarding");
    }  

    public void personalInformation(String fatherName, String motherName, String maritalStatus, String spouseName, String nationality, String bloodGroup, String dateOfBirth, String physicallyHandicapped) throws Exception {
        type(fatherNameField, fatherName);
        type(motherNameField, motherName);

        if (maritalStatus.equalsIgnoreCase("Married")) {
            selectOption(maritalStatusDropdown, "Married");
            type(ifMaritalStatusIsMarriedSpouseNameField, spouseName);
        }
        
        else {
            selectOption(maritalStatusDropdown, "Unmarried");
        }

        selectOption(nationalityDropdown, nationalityCountryOption);

        selectOption(bloodGroupDropdown, bloodGroup);

        setDate(dateOfBirthField, dateOfBirth);

        selectOption(physicallyHandicappedDropdown, physicallyHandicapped);

        click(saveAndContinueButton, 10, "Personal Information Page");
    }

    public void contactInformation(String alternateMobileNumber, String emergencyContactName, String emergencyContactRelationship, String emergencyContactNumber, String currentAddressCountry, String currentAddressState, String currentAddressCity, String currentAddressPostalCode, String currentAddress) throws Exception {

        type(alternateMobileNumberField, alternateMobileNumber);
        type(emergencyContactNameField, emergencyContactName);

        selectOption(emergencyContactRelationshipDropdown, emergencyContactRelationship);
        type(emergencyContactNumberField, emergencyContactNumber);

        selectOption(currentAddressCountryDropdown, currentAddressCountry);

        selectOption(currentAddressStateDropdown, currentAddressState);
        selectOption(currentAddressCityField, currentAddressCity);

        type(currentAddressPostalCodeField, currentAddressPostalCode);
        type(currentAddressField, currentAddress);

        driver.findElement(sameAsCurrentAddressCheckbox).click();

        click(saveAndContinueButton, 10, "Contact Information Page");
    }

    public void bankingInformation(String accountHolderName, String bankName, String accountNumber, String confirmAccountNumber, String ifscCode, String accountType, String branchName, String bankAddress) throws Exception {

        type(accountHolderNameField, accountHolderName);
        type(bankNameField, bankName);
        type(accountNumberField, accountNumber);
        type(confirmAccountNumberField, confirmAccountNumber);
        type(ifscCodeField, ifscCode);

        selectOption(accountTypeDropdown, accountType);

        type(branchNameField, branchName);
        type(bankAddressField, bankAddress);

        driver.findElement(uploadCancelledChequeButton).sendKeys(properties.getProperty("chequePdfPath"));
        
        click(saveAndContinueButton, 10, "Bank Information Page");
    }

    public void ProfessionalInformation(String UANNumber, String ESICNumber, String primarySkill, String secondarySkill, String language, String proficiency){

        type(UAN, UANNumber);
        type(ESIC, ESICNumber);
        type(primary, primarySkill);
        type(secondary, secondarySkill);
        type(languageName, language);

        selectOption(proficiencyDropdown, proficiency);

        click(saveAndContinueButton, 10, "Professional Information Page");
    }

    public void educationalInformation(String educationLevel, String educationInstitutionName, String fieldOfStudy, String universityName, String modeOfEducation, String passingYear, String certificateName, String certificateInstitutionName, String certificateIssueDate) throws Exception {

        scrollIntoViewAndClick(addEducationButton, 10, "Add Education");
        selectOption(educationLevelDropdown, educationLevel);
        type(educationInstitutionNameField, educationInstitutionName);
        type(fieldOfStudyField, fieldOfStudy);
        type(universityNameField, universityName);
        selectOption(modeOfEducationField, modeOfEducation);
        type(passingYearField, passingYear);

        scrollIntoViewAndClick(addCertificateButton, 10, "Add Certificate");
        type(certificateNameField, certificateName);
        type(certificateInstitutionNameField, certificateInstitutionName);
        setDate(certificateIssueDateField, certificateIssueDate);

        driver.findElement(uploadCertificate).sendKeys(properties.getProperty("certificatefPath"));

        click(saveAndContinueButton, 10, "Educational Information Page");
    }

    public void identificationInformation(String aadhaarNumber, String panNumber, String drivingLicenceNumber, String passportNumber) throws Exception {

        type(aadhaarNumberField, aadhaarNumber);
        type(panNumberField, panNumber);
        type(drivingLicenceNumberField, drivingLicenceNumber);
        type(passportNumberField, passportNumber);

        driver.findElement(uploadAadhaarButton).sendKeys(properties.getProperty("aadhaarPdfPath"));
        driver.findElement(uploadPanButton).sendKeys(properties.getProperty("panPdfPath"));
        driver.findElement(uploadDrivingLicenceButton).sendKeys(properties.getProperty("drivingPdfPath"));
        driver.findElement(uploadPassportButton).sendKeys(properties.getProperty("pasportPdfPath"));

        scrollIntoViewAndClick(antiFinancialFraudViewButton, 10, "Anti-Financial Fraud View & Acknowledge");
        click(iAcknowledge, 10);

        scrollIntoViewAndClick(poshViewButton, 10, "POSH View & Acknowledge");
        click(iAcknowledge, 10);

        scrollIntoViewAndClick(employeeDeclarationViewButton, 10, "Employee Declaration View & Acknowledge");
        click(iAcknowledge, 10);

        click(saveAndContinueButton, 10, "Identification Information Page");

        click(doneLogout, 10, "Logout");
    }
}