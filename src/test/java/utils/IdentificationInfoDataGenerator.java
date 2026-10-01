package utils;

import java.util.concurrent.ThreadLocalRandom;
import models.Data;

public class IdentificationInfoDataGenerator extends RandomDataGenerator {

    public static void generateIdentificationInformation(Data employeeData) {
        String aadhaarNumber = generateAadhaarNumber();
        String panNumber = generatePanNumber();
        String drivingLicenceNumber = generateDrivingLicenceNumber();
        String passportNumber = generatePassportNumber();

        employeeData.setAadhaarNumber(aadhaarNumber);
        employeeData.setPanNumber(panNumber);
        employeeData.setDrivingLicenceNumber(drivingLicenceNumber);
        employeeData.setPassportNumber(passportNumber);
    }

    private static String generateAadhaarNumber() {
        return String.format(
                "%012d",
                ThreadLocalRandom.current().nextLong(100000000000L, 1000000000000L)
        );
    }

    private static String generatePanNumber() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder pan = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            pan.append(letters.charAt(
                    ThreadLocalRandom.current().nextInt(letters.length())
            ));
        }

        pan.append(ThreadLocalRandom.current().nextInt(1000, 10000));

        pan.append(letters.charAt(
                ThreadLocalRandom.current().nextInt(letters.length())
        ));

        return pan.toString();
    }

    private static String generateDrivingLicenceNumber() {
        return "DL" + ThreadLocalRandom.current().nextInt(100000000, 1000000000);
    }

    private static String generatePassportNumber() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder passport = new StringBuilder();

        passport.append(letters.charAt(
                ThreadLocalRandom.current().nextInt(letters.length())
        ));

        passport.append(String.format(
                "%07d",
                ThreadLocalRandom.current().nextInt(0, 10000000)
        ));

        return passport.toString();
    }
}