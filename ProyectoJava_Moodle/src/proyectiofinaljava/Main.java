package proyectiofinaljava;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author juan
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Mostrar el diálogo de autenticación
        String nombreUsuario = autenticarUsuario();
        if (nombreUsuario != null) {
            // El usuario se autenticó correctamente, crear y mostrar la ventana principal
            VtnContenedora ventana = new VtnContenedora();
            ventana.setNombreUsuario(nombreUsuario);
            ventana.start(primaryStage);
        } else {
            // Cerrar la aplicación si el usuario no se autenticó correctamente
            primaryStage.close();
        }
    }

    // Función para autenticar al usuario
    private String autenticarUsuario() {
        // Crear los controles para el diálogo de autenticación
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        // Crear un VBox para organizar los controles en el diálogo
        VBox vbox = new VBox();
        vbox.getChildren().addAll(
                new Label("Usuario:"),
                usernameField,
                new Label("Contraseña:"),
                passwordField
        );

        // Crear el diálogo de autenticación
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Autenticación");
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Configurar el resultado del diálogo
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                // Comprobar si el usuario y la contraseña son correctos
                String username = usernameField.getText();
                String password = passwordField.getText();

                // Autenticación básica, contraseña "1234"
                if (username.equals("usuario") && password.equals("1234")) {
                    return username;
                } else {
                    // Mostrar mensaje de error si las credenciales son incorrectas
                    mostrarMensajeError("Usuario o contraseña incorrectos.");
                    return null;
                }
            }
            return null;
        });

        // Mostrar el diálogo de autenticación y esperar a que el usuario ingrese las credenciales
        dialog.showAndWait();

        // Obtener el resultado del diálogo (nombre de usuario autenticado)
        return dialog.getResult();
    }

    private void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de autenticación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}



