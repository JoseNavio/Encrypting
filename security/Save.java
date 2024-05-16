
package security;

import security.data.User;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Save {


    public Save() {

    }

    public File saveUser(User user, String fileName) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String dateTimeString = LocalDateTime.now().format(formatter);

        File file = new File(fileName + "_" + dateTimeString);

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write("- Name: " + user.getName() + "\n");
            fileWriter.write("- Email: " + user.getEmail() + "\n");
            fileWriter.write("- Password: " + encrypt(user.getPassword()) + "\n");

            return file;

        } catch (IOException e) {
            throw new RuntimeException("Error writing file." + e.getMessage());
        }
    }


    private String encrypt(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = password.getBytes();
            byte[] encrypt = messageDigest.digest(bytes);
            StringBuilder hexString = new StringBuilder();
            for (byte buffer : encrypt) {
                String hex = Integer.toHexString(0xff & buffer);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error encrypting... " + e.getMessage());
            return null;
        }
    }

}
