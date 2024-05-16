
package security;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Auth {

    private final Scanner sc;

    public Auth() {
        this.sc = new Scanner(System.in);
    }

    public void registrarUsuario(String username, String password, String email, String nombreFichero) {
        if (!validarNombreUsuario(username) || !validarPassword(password) || !validarEmail(email)) {
            return;
        }

        String passwordEncriptada = encriptarPassword(password);

        File fichero = new File("src/ejercicio1/" + nombreFichero + ".txt");
        if (fichero.exists()) {
            System.out.println("El fichero ya existe");
            return;
        }

        try (FileWriter fileWriter = new FileWriter(fichero)) {
            fileWriter.write("Nombre de usuario: " + username + "\n");
            fileWriter.write("Contraseña encriptada: " + passwordEncriptada + "\n");
            fileWriter.write("Email: " + email + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en el fichero: " + e.getMessage());
        }

    }

    public void login(String username, String password, String nombreFichero, String email) {
        // Definimos la ruta completa del archivo
        String rutaCompleta = "src/ejercicio1/" + nombreFichero + ".txt";
        File fichero = new File(rutaCompleta);
        if (!fichero.exists()) {
            System.out.println("El fichero no existe");
            return;
        }

        // Variables temporales para almacenar los datos del usuario mientras esté en sesión
        String usernameEncontrado = "";
        String emailEncontrado = "";

        // leemos el fichero
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fichero));
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                String[] partes = linea.split(":");
                if (partes[0].trim().equals("Nombre de usuario") && partes[1].trim().equals(username)) {
                    linea = bufferedReader.readLine();
                    partes = linea.split(":");
                    if (partes[0].trim().equals("Contraseña encriptada") && partes[1].trim().equals(encriptarPassword(password))) {
                        linea = bufferedReader.readLine();
                        partes = linea.split(":");
                        if (partes[0].trim().equals("Email") && partes[1].trim().equals(email)) {
                            // Almacenamos los datos del usuario
                            usernameEncontrado = username;
                            emailEncontrado = email;
                            System.out.println("Inicio de sesión exitoso");
                            break; // Terminamos el bucle una vez que se ha encontrado una coincidencia exitosa
                        }
                    }
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Mostramos los datos del usuario si se encontró una coincidencia exitosa
        if (!usernameEncontrado.isEmpty() && !emailEncontrado.isEmpty()) {
            System.out.println("========= Datos del usuario : ================");
            System.out.println("Nombre de usuario: " + usernameEncontrado);
            System.out.println("Email: " + emailEncontrado);
        } else {
            System.out.println("No se encontraron coincidencias para el inicio de sesión");
        }
    }



    private boolean validarNombreUsuario(String username) {
        if (username.length() < 8 || !username.matches("^(?=.*[a-z]{6})[a-z0-9]+[!@#$%^&*()_+|~-]{2}$")) {
            System.out.println("El nombre de usuario no es válido. Debe tener 8 caracteres como mínimo y tener 6 letras minúsculas seguidas de 2 caracteres especiales.");
            return false;
        }
        return true;
    }

    private boolean validarPassword(String password) {
        if (password.length() < 8 || !password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
            System.out.println("La contraseña no es válida. Debe tener 8 caracteres como mínimo y contener al menos una mayúscula, una minúscula y un dígito.");
            return false;
        }
        return true;
    }

    private boolean validarEmail(String email) {
        if (!email.matches("^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$")) {
            System.out.println("El email no es válido");
            return false;
        }
        return true;
    }

    private String encriptarPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = password.getBytes();
            byte[] encriptacion = messageDigest.digest(bytes);
            StringBuilder hexString = new StringBuilder();
            for (byte b : encriptacion) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error al encriptar la contraseña: " + e.getMessage());
            return null;
        }
    }

}
