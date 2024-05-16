package security;

import data.User;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Auth autenticacion = new Auth();

        String username;
        String password;
        String email;
        String name;
        int choice;

        System.out.println("Security");
        System.out.println();
        System.out.println("1. Sign up");
        System.out.println("2. Exit");

        System.out.print("Pick a choice: ");
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                User userInput = logInData(scanner);
                if (choice == 1) {
                    autenticacion.registrarUsuario(userInput.username, userInput.password, userInput.email, userInput.nombreCompleto);
                } else {
                    autenticacion.login(userInput.username, userInput.password, userInput.nombreCompleto, userInput.email);
                }
                break;
            case 2:
                System.exit(0);
                break;
            default:
                System.out.println("Retry...");
                break;
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