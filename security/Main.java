package security;

import security.data.User;
import security.utils.Validate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Save auth = new Save();

        int choice;

        System.out.println("Security");
        System.out.println();
        System.out.println("1. Sign up");
        System.out.println("2. Exit");

        System.out.print("Pick a choice: ");
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            //Log in
            case 1:

                User userInput = logInData();

                //todo Only for testing purposes...
                User validMockUser = new User("Juan", "el_juan@gem.es","juanitat", "juanit3T");

                if (Validate.validateUser(userInput)) {

                    System.out.println("File name: ");
                    String fileName = scanner.nextLine();

                    if(Validate.validateFile(fileName)){
                        File generatedFile = auth.saveUser(validMockUser, fileName);
                        readFile(generatedFile);
                    }

                }
                break;
            //Exit
            case 2:
                System.exit(0);
                break;
            default:
                System.out.println("Retry...");
                break;
        }
    }

    //Reads a file
 private static void readFile(File file) {
    try (Scanner fileScanner = new Scanner(file)) {
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            System.out.println(line);
        }
    } catch (FileNotFoundException e) {
        System.out.println("An error occurred while reading the file.");
        e.printStackTrace();
    }
}

    private static User logInData() {

        System.out.println("Nick: ");
        String nick = scanner.nextLine();

        System.out.println("Password: ");
        String password = scanner.nextLine();

        System.out.println("Introduce user complete name: ");
        String name = scanner.nextLine();

        System.out.println("Email: ");
        String email = scanner.nextLine();

        return new User(name, email, nick, password);
    }

}