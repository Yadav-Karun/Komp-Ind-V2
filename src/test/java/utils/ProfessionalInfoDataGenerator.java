package utils;

import java.util.concurrent.ThreadLocalRandom;
import models.Data;

public class ProfessionalInfoDataGenerator extends RandomDataGenerator {

    public static void generateProfessionalInformation(Data employeeData) {

        // UAN Number - 60% occurrence
        String uanNumber = generateWith60PercentOccurrence(
                ProfessionalInfoDataGenerator::generateUAN
        );

        // ESIC Account Number - 60% occurrence
        String esicAccountNumber = generateWith60PercentOccurrence(
                ProfessionalInfoDataGenerator::generateESIC
        );

        // Primary Skills - Required
        String primarySkills = generatePrimarySkills(
                employeeData.getDesignation()
        );

        // Secondary Skills - Required
        String secondarySkills = generateSecondarySkills();

        // Language - Required
        String language = faker.options().option(
                "English",
                "Hindi",
                "English, Hindi"
        );

        // Proficiency - Required
        String proficiency = faker.options().option(
                "Beginner",
                "Intermediate",
                "Expert"
        );

        // Store Professional Information
        employeeData.setUanNumber(uanNumber);
        employeeData.setEsicAccountNumber(esicAccountNumber);
        employeeData.setPrimarySkills(primarySkills);
        employeeData.setSecondarySkills(secondarySkills);
        employeeData.setLanguage(language);
        employeeData.setProficiency(proficiency);
    }

    private static String generateUAN() {
        return String.format(
                "%012d",
                ThreadLocalRandom.current().nextLong(
                        100000000000L,
                        1000000000000L
                )
        );
    }

    private static String generateESIC() {
        return String.format(
                "%017d",
                ThreadLocalRandom.current().nextLong(
                        10000000000000000L,
                        99999999999999999L
                )
        );
    }

    private static String generateWith60PercentOccurrence(
            java.util.function.Supplier<String> generator) {

        return ThreadLocalRandom.current().nextInt(100) < 99.99999
                ? generator.get()
                : "";
    }

    private static String generatePrimarySkills(String designation) {

        if (designation.toLowerCase().contains("java")) {
            return "Java, Spring Boot, SQL";
        }

        if (designation.toLowerCase().contains("qa")
                || designation.toLowerCase().contains("test")
                || designation.toLowerCase().contains("automation")) {
            return "Selenium, Java, TestNG";
        }

        if (designation.toLowerCase().contains("data")) {
            return "Python, SQL, PostgreSQL";
        }

        if (designation.toLowerCase().contains("python")) {
            return "Python, SQL, REST API";
        }

        if (designation.toLowerCase().contains("devops")
                || designation.toLowerCase().contains("cloud")) {
            return "AWS, Docker, Jenkins";
        }

        return "Java, SQL, Git";
    }

    private static String generateSecondarySkills() {
        return faker.options().option(
                "Git, Maven, Jenkins",
                "REST API, Git, Postman",
                "Docker, Git, Linux",
                "SQL, Git, Postman",
                "Jenkins, Maven, Git",
                "Linux, Git, Docker"
        );
    }
}