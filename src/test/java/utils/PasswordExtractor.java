package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PasswordExtractor {

    private PasswordExtractor() {
    }

    public static String extractClientPassword(String mailBody) {
        return extractPassword(mailBody, "Temporary\\s+password");
    }

    public static String extractEmployeePassword(String mailBody) {
        return extractPassword(mailBody, "Password");
    }

    public static String extractDocumentVerificationPassword(String mailBody) {
        return extractPassword(mailBody, "(?:temp\\.?|temporary)\\s*password");
    }

    private static String extractPassword(String mailBody, String labelPattern) {
        Pattern pattern = Pattern.compile(
                "(?im)" + labelPattern + "\\s*:\\s*([^\\s]+)",
                Pattern.CASE_INSENSITIVE
        );
        Matcher matcher = pattern.matcher(mailBody);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        throw new IllegalStateException("Password not found in email body.");
    }
}
