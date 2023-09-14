public class PasswordValidator {

    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    public static boolean validatePassword(String password) {
        if (password.length() < 6) {
            return false;
        }

        boolean hasUpperCase = false;
        boolean hasSpecialChar = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (isSpecialChar(c)) {
                hasSpecialChar = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }

        return hasUpperCase && hasSpecialChar && hasDigit;
    }

    private static boolean isSpecialChar(char c) {
        return SPECIAL_CHARACTERS.contains(String.valueOf(c));
    }
}
