package security.utils;

import security.data.User;

public class Validate {

    public static boolean validateUser(User user) {

        if (validateName(user.getNick()) && validateEmail(user.getEmail()) && validatePassword(user.getPassword())) {
            return true;
        }
        return false;
    }

    public static boolean validateName(String name) {
        if (!name.matches("^[a-z]{6,}.*.{2}$")) {
            System.out.println("Invalid name.");
            return false;
        }
        return true;
    }

    public static boolean validateFile(String name) {
        if (!name.matches("^.{1,8}\\..{3}$")) {
            System.out.println("Invalid file name.");
            return false;
        }
        return true;
    }

    private static boolean validateEmail(String email) {
        if (!email.matches("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$")) {
            System.out.println("Invalid email.");
            return false;
        }
        return true;
    }

    private static boolean validatePassword(String password) {
        if (password.length() < 8 || !password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
            System.out.println("Invalid password.");
            return false;
        }
        return true;
    }

}
