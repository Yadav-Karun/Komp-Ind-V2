package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import models.Data;

public class EducationalInfoDataGenerator extends RandomDataGenerator {

    public static void generateEducationalInformation(Data employeeData) {

        // Education
        String educationLevel = faker.options().option(
                "PHD",
                "Masters",
                "Bachelor",
                "12th",
                "10th"
        );

        String institutionName = faker.options().option(
                "ABC Institute of Technology",
                "Delhi Institute of Technology",
                "National Institute of Engineering",
                "Global Institute of Technology",
                "Modern College of Engineering"
        );

        String fieldOfStudy = faker.options().option(
                "Computer Science",
                "Information Technology",
                "Computer Engineering",
                "Electronics and Communication",
                "Data Science",
                "Software Engineering"
        );

        String universityName = faker.options().option(
                "University of Delhi",
                "Amity University",
                "Anna University",
                "Maharshi Dayanand University",
                "Dr. A.P.J. Abdul Kalam Technical University"
        );

        String modeOfEducation = faker.options().option(
                "Open Learning",
                "Part-Time",
                "Regular"
        );

        String passingYear = generatePassingYear();

        // Certificate
        String certificateName = faker.options().option(
                "Java Programming Certificate",
                "Selenium Automation Certificate",
                "Python Programming Certificate",
                "SQL Certification",
                "AWS Cloud Practitioner",
                "Data Analytics Certificate"
        );

        String certificateInstitutionName = faker.options().option(
                "Udemy",
                "Coursera",
                "Great Learning",
                "Simplilearn",
                "LinkedIn Learning"
        );

        String certificateIssueDate = generateCertificateIssueDate();

        // Store Education Information
        employeeData.setEducationLevel(educationLevel);
        employeeData.setEducationInstitutionName(institutionName);
        employeeData.setFieldOfStudy(fieldOfStudy);
        employeeData.setUniversityName(universityName);
        employeeData.setModeOfEducation(modeOfEducation);
        employeeData.setPassingYear(passingYear);

        // Store Certificate Information
        employeeData.setCertificateName(certificateName);
        employeeData.setCertificateInstitutionName(certificateInstitutionName);
        employeeData.setCertificateIssueDate(certificateIssueDate);
    }

    private static String generatePassingYear() {
        int year = ThreadLocalRandom.current().nextInt(2015, 2026);
        return String.valueOf(year);
    }

    private static String generateCertificateIssueDate() {
        LocalDate startDate = LocalDate.of(2018, 1, 1);
        LocalDate endDate = LocalDate.now();

        long days = java.time.temporal.ChronoUnit.DAYS.between(
                startDate,
                endDate
        );

        LocalDate issueDate = startDate.plusDays(
                ThreadLocalRandom.current().nextLong(days + 1)
        );

        return issueDate.format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy")
        );
    }
}