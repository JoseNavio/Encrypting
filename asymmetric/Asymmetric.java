package asymmetric;


import javax.crypto.Cipher;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Scanner;

public class Asymmetric {

    private static String FILE_NAME = "fichero.cifrado";

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a message: ");
        String message = scanner.nextLine();

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        Files.write(Paths.get("private.key"), privateKey.getEncoded());

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedMessage = cipher.doFinal(message.getBytes());

        Files.write(Paths.get(FILE_NAME), encryptedMessage);

        System.out.println("Message encrypted and saved to " + FILE_NAME);

        // Read and decrypt
        readAndDecrypt();
    }

    private static void readAndDecrypt() throws Exception {
        byte[] encryptedMessage = Files.readAllBytes(Paths.get(FILE_NAME));
        byte[] privateKeyBytes = Files.readAllBytes(Paths.get("private.key"));

        PrivateKey privateKey = KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedMessage = cipher.doFinal(encryptedMessage);

        System.out.println("Decrypted message: " + new String(decryptedMessage));
    }
}