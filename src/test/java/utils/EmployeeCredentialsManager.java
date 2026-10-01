package utils;

import java.util.HashMap;
import java.util.Map;

public final class EmployeeCredentialsManager {

    private static final Map<String, String> employeeCredentials = new HashMap<>();

    private EmployeeCredentialsManager() {
    }

    public static void storeEmployeeCredentials(String email, String password) {
        employeeCredentials.put(email, password);
    }

    public static String getEmployeePassword(String email) {
        return employeeCredentials.get(email);
    }

    public static Map<String, String> getEmployeeCredentials() {
        return employeeCredentials;
    }
}
