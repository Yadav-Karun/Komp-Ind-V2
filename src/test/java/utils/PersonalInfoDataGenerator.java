package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

import models.Data;

public class PersonalInfoDataGenerator extends RandomDataGenerator {

    public static void generatePersonalInformation(Data employeeData) {

        // ADDED - Father Name - Male
        String fatherName = generateFirstName("Male") + " " + employeeData.getLastName();

        // ADDED - Mother Name - Female
        String motherName = generateFirstName("Female") + " " + employeeData.getLastName();

        String maritalStatus = ThreadLocalRandom.current().nextBoolean()
                ? "Married"
                : "Unmarried";

        String spouseName = "";

        if ("Married".equalsIgnoreCase(maritalStatus)) {
        spouseName = generateFirstName("Female") + " " + employeeData.getLastName();
        }

        // ADDED - Nationality
        String nationality = "India";

        // ADDED - Blood Group
        String bloodGroup = faker.options().option(
                "A+",
                "A-",
                "B+",
                "B-",
                "AB+",
                "AB-",
                "O+",
                "O-"
        );

        int year = ThreadLocalRandom.current().nextInt(1997, 2008);
        int month = ThreadLocalRandom.current().nextInt(1, 13);
        int day = ThreadLocalRandom.current().nextInt(
                1,
                LocalDate.of(year, month, 1).lengthOfMonth() + 1
        );

        LocalDate dateOfBirth = LocalDate.of(year, month, day);

        String dateOfBirthFormatted = dateOfBirth.format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy")
        );

        // ADDED - Physically Handicapped - 50% Yes / 50% No
        String physicallyHandicapped = ThreadLocalRandom.current().nextBoolean()
                ? "Yes"
                : "No";

        // ADDED - Store Personal Information in Data object
        employeeData.setFatherName(fatherName);
        employeeData.setMotherName(motherName);
        employeeData.setMaritalStatus(maritalStatus);
        employeeData.setSpouseName(spouseName);
        employeeData.setNationality(nationality);
        employeeData.setBloodGroup(bloodGroup);
        employeeData.setDateOfBirth(dateOfBirthFormatted);
        employeeData.setPhysicallyHandicapped(physicallyHandicapped);
    }
}