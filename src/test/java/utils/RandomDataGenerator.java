package utils;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import models.Data;

public class RandomDataGenerator {

    protected static final Faker faker = new Faker(new Locale("en", "US"));

    protected static final String[] STATES = {
        "Gujarat",
        "Maharashtra",
        "Rajasthan",
        "Delhi",
        "Karnataka",
        "Tamil Nadu",
        "Telangana",
        "Uttar Pradesh",
        "Madhya Pradesh",
        "West Bengal",
        "Andhra Pradesh",
        "Haryana",
        "Punjab",
        "Kerala",
        "Bihar",
        "Odisha"
    };

    public static String generateGender() {
        return ThreadLocalRandom.current().nextBoolean() ? "Male" : "Female";
    }

public static String generateFirstName(String gender) {
    if ("Male".equalsIgnoreCase(gender)) {
        return faker.name().firstName();
    }

    if ("Female".equalsIgnoreCase(gender)) {
        return faker.name().firstName();
    }

    throw new IllegalArgumentException("Invalid gender: " + gender);
}

    public static String generateMiddleName() {
        return ThreadLocalRandom.current().nextInt(100) < 20
                ? faker.name().firstName()
                : "";
    }

    public static String generateLastName() {
        return faker.name().lastName();
    }

    public static String generateEmail(String firstName, String middleName, String lastName, String gender) {

        String genderCode = gender.equalsIgnoreCase("Male") ? "M" : "F";

        return (firstName + lastName)
                .replaceAll("[^a-zA-Z]", "")
                .toLowerCase()
                + "." + genderCode
                + "@yopmail.com";
    }

    public static String generatePhoneNumber() {
        int firstDigit = ThreadLocalRandom.current().nextInt(6, 10);
        String remainingDigits = faker.number().digits(9);

        return firstDigit + remainingDigits;
    }

    public static String generateExperience() {
        return ThreadLocalRandom.current().nextBoolean()
                ? "Fresher"
                : "Experienced";
    }

    public static String generateState() {
        return STATES[ThreadLocalRandom.current().nextInt(STATES.length)];
    }

    public static String generateWorkingLocation(String state) {

        switch (state) {

            case "Gujarat":
                return faker.options().option(
                        "Ahmedabad"
                );

            case "Maharashtra":
                return faker.options().option(
                        "Mumbai"
                );

            case "Rajasthan":
                return faker.options().option(
                        "Jaipur"
                );

            case "Delhi":
                return faker.options().option(
                        "New Delhi"
                );

            case "Karnataka":
                return faker.options().option(
                        "Ramanagara"
                );

            case "Tamil Nadu":
                return faker.options().option(
                        "Chennai"
                );

            case "Telangana":
                return faker.options().option(
                        "Hyderabad"
                );

            case "Uttar Pradesh":
                return faker.options().option(
                        "Lucknow"
                );

            case "Madhya Pradesh":
                return faker.options().option(
                        "Bhopal"
                );

            case "West Bengal":
                return faker.options().option(
                        "Kolkata"
                );

            case "Andhra Pradesh":
                return faker.options().option(
                        "Visakhapatnam"
                );

            case "Haryana":
                return faker.options().option(
                        "Karnal"
                );

            case "Punjab":
                return faker.options().option(
                        "Amritsar"
                );

            case "Kerala":
                return faker.options().option(
                        "Tiruvalla"
                );

            case "Bihar":
                return faker.options().option(
                        "Patna"
                );

            case "Odisha":
                return faker.options().option(
                        "Bhubaneswar"
                );

            default:
                return "Ahmedabad";
        }
    }

    public static String generateClient() {
        return faker.company().name()
                .replaceAll("[^a-zA-Z ]", "")
                .trim();
    }

    public static String generateDesignation() {

        return faker.options().option(
                "Software Engineer",
                "Senior Software Engineer",
                "Java Developer",
                "Automation Test Engineer",
                "QA Engineer",
                "Data Engineer",
                "Frontend Developer",
                "Backend Developer",
                "Full Stack Developer",
                "DevOps Engineer",
                "Software Developer",
                "Test Automation Engineer",
                "Python Developer",
                "Java Full Stack Developer",
                "Cloud Engineer"
        );
    }

    public static String generateJobDescription() {

        return faker.options().option(
                "Develop and maintain Java based applications.",
                "Design and execute automated test cases for web applications.",
                "Develop backend services and REST APIs.",
                "Build and maintain automated testing frameworks.",
                "Develop scalable data pipelines and data processing applications.",
                "Design and develop responsive web applications.",
                "Develop and maintain full stack enterprise applications.",
                "Deploy and maintain applications on cloud infrastructure.",
                "Develop automation scripts for application testing.",
                "Analyze application requirements and develop software solutions.",
                "Perform functional and regression testing of enterprise applications.",
                "Develop APIs and integrate third party services."
        );
    }

    public static Data generateEmployeeData() {

        String gender = generateGender();

        String firstName = generateFirstName(gender);
        String middleName = generateMiddleName();
        String lastName = generateLastName();

        String email = generateEmail(
                firstName,
                middleName,
                lastName,
                gender
        );

        String phone = generatePhoneNumber();
        String experience = generateExperience();

        String state = generateState();
        String location = generateWorkingLocation(state);

        String client = generateClient();
        String designation = generateDesignation();
        String jobDescription = generateJobDescription();

        System.out.println("======================================");
        System.out.println("Generated Employee Data");
        System.out.println("======================================");
        System.out.println("First Name       : " + firstName);
        System.out.println("Middle Name      : " + middleName);
        System.out.println("Last Name        : " + lastName);
        System.out.println("Gender           : " + gender);
        System.out.println("Email            : " + email);
        System.out.println("Phone            : " + phone);
        System.out.println("Experience       : " + experience);
        System.out.println("State            : " + state);
        System.out.println("Working Location : " + location);
        System.out.println("Client           : " + client);
        System.out.println("Designation      : " + designation);
        System.out.println("Job Description  : " + jobDescription);
        System.out.println("======================================");

        Data employeeData = new Data();
        employeeData.setFirstName(firstName);
        employeeData.setMiddleName(middleName);
        employeeData.setLastName(lastName);
        employeeData.setGender(gender);
        employeeData.setEmail(email);
        employeeData.setPhone(phone);
        employeeData.setExperience(experience);
        employeeData.setState(state);
        employeeData.setWorkingLocation(location);
        employeeData.setClient(client);
        employeeData.setDesignation(designation);
        employeeData.setJobDescription(jobDescription);

        // ADDED - Personal Information
        PersonalInfoDataGenerator.generatePersonalInformation(employeeData);

        // ADDED - Contact Information
        ContactInfoDataGenerator.generateContactInformation(employeeData);

        // ADDED - Banking Information
        BankingInfoDataGenerator.generateBankingInformation(employeeData);
        
        // ADDED - Professional Information
        ProfessionalInfoDataGenerator.generateProfessionalInformation(employeeData);

        // Educational Information
        EducationalInfoDataGenerator.generateEducationalInformation(employeeData);

        IdentificationInfoDataGenerator.generateIdentificationInformation(employeeData);
        return employeeData;
    }
}
