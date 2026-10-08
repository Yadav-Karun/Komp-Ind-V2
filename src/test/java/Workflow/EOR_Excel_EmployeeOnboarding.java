package Workflow;

import java.util.Locale;

import core.TestBase;
import models.Data;
import pages.LoginPage;
import pages.EOR_RegisterCandidate;
import pages.ForgotPassword;
import pages.Logout;
import pages.VerifyCandidate;
import pages.CandidatePage;
import pages.CandidateSelfInfo;
import pages.Yopmail;
import utils.EmployeeCredentialsManager;

public class EOR_Excel_EmployeeOnboarding {

    private LoginPage loginPage;
    private EOR_RegisterCandidate registerCandidate;
    // UPDATED ON 2026-10-06 12:19 IST: Uses one shared CandidatePage instance because candidate search and offer-letter actions run on the same page and driver.
    private CandidatePage candidatePage;
    private Yopmail yopmail;
    private Logout logout;
    private CandidateSelfInfo candidatePersonalInfo;
    private ForgotPassword forgotPasswordCandidate;
    private VerifyCandidate verifyCandidate;

    public EOR_Excel_EmployeeOnboarding() throws Exception {

        loginPage = new LoginPage(TestBase.getDriver());
        registerCandidate = new EOR_RegisterCandidate(TestBase.getDriver());
        logout = new Logout(TestBase.getDriver());
        candidatePage = new CandidatePage(TestBase.getDriver());
        yopmail = new Yopmail(TestBase.getDriver());
        candidatePersonalInfo = new CandidateSelfInfo(TestBase.getDriver());
        forgotPasswordCandidate = new ForgotPassword(TestBase.getDriver());
        verifyCandidate = new VerifyCandidate(TestBase.getDriver());
    }

    public boolean loginAdmin(Data input) throws Exception {
        loginPage.goToLoginPage();
        loginPage.enterCredentials(input);
        return loginPage.isLoginErrorVisible();
    }

    public boolean loginCandidate(Data input) throws Exception {
        loginPage.goToLoginPage();
        loginPage.enterCredentials(input);
        return loginPage.isLoginErrorVisible();
    }

    public void RegisterCandidate(Data input) throws Exception {
        registerCandidate.bulkUploadCandidates(input);
    }
    public void logoutAccount() throws Exception {
        logout.logout();
    }

    public void searchCandidate(Data input) throws Exception {
        candidatePage.searchCandidate(input.getEmail());
    }

    public void yopmailAcceptLetterOfIntent(Data input) throws Exception {
        String employeeEmail = input.getEmail().toLowerCase(Locale.ROOT);
        yopmail.goToYopmailEmployee(employeeEmail);
        yopmail.acceptEmployeeLoi();
        String employeePassword = yopmail.getEmployeeLoginPassword();
        EmployeeCredentialsManager.storeEmployeeCredentials(employeeEmail, employeePassword);
    }

    public void loginCandidateWithStoredCredentials(Data input) throws Exception {

        String employeeEmail = input.getEmail().trim().toLowerCase(Locale.ROOT);

        String employeePassword =
                EmployeeCredentialsManager.getEmployeePassword(employeeEmail);

        if (employeePassword == null) {
            throw new RuntimeException(
                    "No stored password found for email: " + employeeEmail
            );
        }

        loginPage.loginAsEmployee(employeeEmail, employeePassword);
    }

    public void CandidatePersonal(Data input) throws Exception {
        candidatePersonalInfo.clickStartNowButton();
        
        candidatePersonalInfo.personalInformation(input.getFatherName(), input.getMotherName(), input.getMaritalStatus(), input.getSpouseName(), input.getNationality(), input.getBloodGroup(), input.getDateOfBirth(), input.getPhysicallyHandicapped());

        candidatePersonalInfo.contactInformation(input.getAlternateMobileNumber(), input.getEmergencyContactName(), input.getEmergencyContactRelationship(), input.getEmergencyContactNumber(), input.getCurrentAddressCountry(), input.getCurrentAddressState(), input.getCurrentAddressCity(), input.getCurrentAddressPostalCode(), input.getCurrentAddress());

        candidatePersonalInfo.bankingInformation(input.getAccountHolderName(), input.getBankName(), input.getAccountNumber(), input.getConfirmAccountNumber(), input.getIfscCode(), input.getAccountType(), input.getBranchName(), input.getBankAddress());

        candidatePersonalInfo.ProfessionalInformation(input.getUanNumber(), input.getEsicAccountNumber(), input.getPrimarySkills(), input.getSecondarySkills(), input.getLanguage(), input.getProficiency());

        candidatePersonalInfo.educationalInformation(input.getEducationLevel(), input.getEducationInstitutionName(), input.getFieldOfStudy(), input.getUniversityName(), input.getModeOfEducation(), input.getPassingYear(), input.getCertificateName(), input.getCertificateInstitutionName(), input.getCertificateIssueDate());

        candidatePersonalInfo.identificationInformation(input.getAadhaarNumber(), input.getPanNumber(), input.getDrivingLicenceNumber(), input.getPassportNumber());
    }

    // UPDATED ON 2026-10-03 12:44 IST: Completes the reset flow, stores the Yopmail OTP, and verifies the generic password can sign in.
    public void forgotPassword(Data input) throws Exception {
        forgotPasswordCandidate.sendResetCode(input.getEmail());

        String otp = yopmail.getPasswordResetOtp();
        input.setOtp(otp);

        yopmail.switchToCandidateWindow();
        forgotPasswordCandidate.resetPassword(input.getOtp(), input.getgenericPassword());
        loginPage.loginAsEmployee(input.getEmail(), input.getgenericPassword());

        if (loginPage.isLoginErrorVisible()) {
            throw new RuntimeException("Candidate login failed after password reset.");
        }
    }

    public void verifyCandidate(Data input) throws Exception {
        verifyCandidate.searchEmployee(input.getEmail());
    }

    public void candidatePage(Data input) throws Exception {
        candidatePage.searchCandidate(input.getEmail());

        // ADDED ON 2026-10-07 11:41 IST: Sends the offer letter after the verified candidate has been searched and selected.
        candidatePage.sendOfferLetter(input.getEmail());
    }

    public void clickCandidateLoginLink() throws Exception {
        loginPage.clickCandidateLoginLink();
    }

}
    
