package utils;

import java.util.concurrent.ThreadLocalRandom;

import models.Data;

public class ContactInfoDataGenerator extends RandomDataGenerator {

    public static void generateContactInformation(Data employeeData) {
    String alternateMobileNumber = generatePhoneNumber();

    String relationship;

        if ("Married".equalsIgnoreCase(employeeData.getMaritalStatus())) {
        relationship = faker.options().option(
                "Father",
                "Mother",
                "Spouse",
                "Brother",
                "Sister"
        );
        } else {
        relationship = faker.options().option(
                "Father",
                "Mother",
                "Sister"
        );
        }

    String emergencyContactName;

    switch (relationship) {
        case "Father":
            emergencyContactName = employeeData.getFatherName();
            break;
        case "Mother":
            emergencyContactName = employeeData.getMotherName();
            break;
        case "Spouse":
                emergencyContactName = employeeData.getSpouseName();
                break;
        default:
            emergencyContactName = generateFirstName(
                    faker.options().option("Male", "Female")
            ) + " " + employeeData.getLastName();
            break;
    }

    String emergencyContactNumber = generatePhoneNumber();

    String country = "India";
    String state = generateState();
    String city = generateWorkingLocation(state);
    String postalCode = String.valueOf(
            ThreadLocalRandom.current().nextInt(100000, 1000000)
    );
    String address = faker.address().streetAddress();

    employeeData.setAlternateMobileNumber(alternateMobileNumber);
    employeeData.setEmergencyContactName(emergencyContactName);
    employeeData.setEmergencyContactRelationship(relationship);
    employeeData.setEmergencyContactNumber(emergencyContactNumber);
    employeeData.setCurrentAddressCountry(country);
    employeeData.setCurrentAddressState(state);
    employeeData.setCurrentAddressCity(city);
    employeeData.setCurrentAddressPostalCode(postalCode);
    employeeData.setCurrentAddress(address);
}
}