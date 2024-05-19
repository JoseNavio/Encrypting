package symmetric;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Scanner;

public class Symmetric {

    private static String FILE_NAME = "fichero.cifrado";

    public static void main(String[] args) {

        System.out.println("Symmetric");
        System.out.print("Enter a message: ");
        String message = new Scanner(System.in).nextLine();
        System.out.println("1. AES");
        System.out.println("2. DES");
        System.out.println("3. 3DES");
        System.out.print("Choose a cypher:");
        int choice = new Scanner(System.in).nextInt();

        switch (choice) {
            case 1:
                cypherAES(message);
                readAndDecryptAES();
                break;
            case 2:
                cypherDES(message);
                readAndDecryptDES();
                break;
            case 3:
                cypher3DES(message);
                readAndDecrypt3DES();
                break;
            default:
                System.out.println("Retry...");
                break;
        }
    }

    //Cyphers
    private static void cypherDES(String message) {
        try {
            // Create key
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            SecretKey secretKey = keyGen.generateKey();

            // Save the key
            saveKey(secretKey, "secret.key");

            // Encrypt
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(message.getBytes());

            // Save to file
            saveFile(encrypted);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cypherAES(String message) {
        try {
            // Create key
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();

            // Save the key
            saveKey(secretKey, "secret.key");

            // Encrypt
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(message.getBytes());

            // Save to file
            saveFile(encrypted);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cypher3DES(String message) {
        try {
            // Create key
            KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
            SecretKey secretKey = keyGen.generateKey();

            // Save the key
            saveKey(secretKey, "secret.key");

            // Encrypt
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(message.getBytes());

            // Save to file
            saveFile(encrypted);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //Decrypt
    private static void readAndDecrypt3DES() {
        try {
            // Load the key
            SecretKey secretKey = (SecretKey) loadKey("secret.key", "DESede");

            // Read file
            byte[] encrypted = readFile();

            // Decrypt
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(encrypted);

            System.out.println(new String(decrypted));


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void readAndDecryptAES() {
        try {
            // Load the key
            SecretKey secretKey = (SecretKey) loadKey("secret.key", "AES");

            // Read file
            byte[] encrypted = readFile();

            // Decrypt
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(encrypted);

            System.out.println(new String(decrypted));


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void readAndDecryptDES() {
        try {
            // Load the key
            SecretKey secretKey = (SecretKey) loadKey("secret.key", "DES");

            // Read file
            byte[] encrypted = readFile();

            // Decrypt
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(encrypted);

            System.out.println(new String(decrypted));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //Files
    private static byte[] readFile() throws Exception {
        // Read file
        File file = new File(FILE_NAME);
        FileInputStream fis = new FileInputStream(file);
        byte[] encrypted = new byte[(int) file.length()];
        fis.read(encrypted);
        fis.close();
        return encrypted;
    }
    private static void saveFile(byte[] encrypted) throws Exception {
        // Save to file
        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        fos.write(encrypted);
        fos.close();
    }

    //Keys
    private static void saveKey(Key key, String fileName) throws Exception {
        byte[] keyBytes = key.getEncoded();
        Files.write(Paths.get(fileName), keyBytes);
    }

    private static Key loadKey(String fileName, String algorithm) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(fileName));
        return new SecretKeySpec(keyBytes, algorithm);
    }
}