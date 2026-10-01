package utils;

import java.util.concurrent.ThreadLocalRandom;
import models.Data;

public class BankingInfoDataGenerator extends RandomDataGenerator {

    public static void generateBankingInformation(Data employeeData) {

        // Account Holder Name - same as employee name
        String accountHolderName = employeeData.getFirstName();

        if (employeeData.getMiddleName() != null && !employeeData.getMiddleName().isEmpty()) {
            accountHolderName += " " + employeeData.getMiddleName();
        }

        accountHolderName += " " + employeeData.getLastName();

        // Bank Name
        String bankName = faker.options().option(
                "HDFC Bank",
                "ICICI Bank",
                "State Bank of India",
                "Axis Bank",
                "Kotak Mahindra Bank",
                "IndusInd Bank",
                "Punjab National Bank",
                "Bank of Baroda"
        );

        // Account Number
        String accountNumber = generateAccountNumber();

        // Confirm Account Number
        String confirmAccountNumber = accountNumber;

        // IFSC Code
        String ifscCode = generateIFSCCode();

        // Account Type
        String accountType = faker.options().option(
                "Saving",
                "Current",
                "Salaried"
        );

        // Use the SAME city/location generated for Excel
        String bankCity = employeeData.getWorkingLocation();

        // Use the SAME state generated for Excel
        String bankState = employeeData.getState();

        // Branch Name based on the SAME city
        String branchName = bankCity + " Branch";

        // Bank Address using the SAME city and state
        String bankAddress = faker.address().streetAddress()
                + ", " + bankCity
                + ", " + bankState
                + ", India";

        // Store Banking Information
        employeeData.setAccountHolderName(accountHolderName);
        employeeData.setBankName(bankName);
        employeeData.setAccountNumber(accountNumber);
        employeeData.setConfirmAccountNumber(confirmAccountNumber);
        employeeData.setIfscCode(ifscCode);
        employeeData.setAccountType(accountType);
        employeeData.setBranchName(branchName);
        employeeData.setBankAddress(bankAddress);
    }

    private static String generateAccountNumber() {
        return String.valueOf(
                ThreadLocalRandom.current().nextLong(1000000000L, 1000000000000000L)
        );
    }

    private static String generateIFSCCode() {
        String bankCode = faker.options().option(
                "HDFC",
                "ICIC",
                "SBIN",
                "AXIS",
                "KKBK"
        );

        String numbers = String.format(
                "%06d",
                ThreadLocalRandom.current().nextInt(0, 1000000)
        );

        String lastCharacter = String.valueOf(
                (char) ThreadLocalRandom.current().nextInt('A', 'Z' + 1)
        );

        return bankCode + numbers + lastCharacter;
    }
}