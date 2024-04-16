package proyectiofinaljava;

/**
 *
 * @author mateo
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Optional;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JLabel;
import jfxtras.scene.control.CalendarPicker;

public class VtnContenedora extends Application {

    private BorderPane panelPrincipal;
    private HBox panelEncabezado;
    private VBox panelIzquierdo;
    private VBox panelCentral;
    private VBox panelInferior;
    private VBox panelNotas;
    private VBox panelNotificaciones;

    private Label etiquetaMoodle;

    private TreeView<String> arbolCursos;
    private Label etiquetaNotas;
    private Label etiquetaNotificaciones;
    private ListView<String> listaNotasPendientes;

    private String nombreUsuario;

    public VtnContenedora() {
        panelPrincipal = new BorderPane();
        panelPrincipal.setStyle("-fx-background-color: #f4f4f4;");
        panelEncabezado = new HBox();
        panelEncabezado.setStyle("-fx-background-color: #3498db;");
        panelEncabezado.setPadding(new Insets(20));
        panelEncabezado.setAlignment(Pos.CENTER);

        etiquetaMoodle = new Label("Plantain");
        etiquetaMoodle.setFont(new Font("Arial", 48));
        etiquetaMoodle.setTextFill(Color.WHITE);

        panelEncabezado.getChildren().add(etiquetaMoodle);

        panelPrincipal.setTop(panelEncabezado);

        panelIzquierdo = new VBox();
        panelIzquierdo.setStyle("-fx-background-color: #ecf0f1;");
        panelIzquierdo.setPadding(new Insets(10));
        panelIzquierdo.setSpacing(10);

        TreeItem<String> root = new TreeItem<>("Cursos");
        TreeItem<String> curso1 = new TreeItem<>("MA 3-1");
        TreeItem<String> curso2 = new TreeItem<>("MA 3-2");
        TreeItem<String> curso1Materia1 = new TreeItem<>("ESTRUCTURA DE DATOS");
        TreeItem<String> curso1Materia2 = new TreeItem<>("SISTEMAS OPERATIVOS");
        TreeItem<String> curso2Materia1 = new TreeItem<>("ESTRUCTURA DE DATOS");
        TreeItem<String> curso2Materia2 = new TreeItem<>("SISTEMAS OPERATIVOS");
        root.getChildren().addAll(curso1, curso2);
        curso1.getChildren().addAll(curso1Materia1, curso1Materia2);
        curso2.getChildren().addAll(curso2Materia1, curso2Materia2);

        arbolCursos = new TreeView<>(root);
        arbolCursos.setStyle("-fx-font-size: 16;");

        ContextMenu contextMenu = new ContextMenu();
        MenuItem cambiarNombreMenuItem = new MenuItem("Cambiar nombre");
        MenuItem agregarCursoOMateriaMenuItem = new MenuItem("Agregar nuevo curso/materia");
        MenuItem eliminarCursoOMateriaMenuItem = new MenuItem("Eliminar curso/materia");

        contextMenu.getItems().addAll(cambiarNombreMenuItem, agregarCursoOMateriaMenuItem, eliminarCursoOMateriaMenuItem);

        arbolCursos.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                TreeItem<String> selectedItem = arbolCursos.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    contextMenu.show(arbolCursos, event.getScreenX(), event.getScreenY());

                    cambiarNombreMenuItem.setOnAction(e -> {
                        TextInputDialog dialog = new TextInputDialog(selectedItem.getValue());
                        dialog.setTitle("Cambiar nombre");
                        dialog.setHeaderText("Cambiar nombre del curso/materia");
                        dialog.setContentText("Nuevo nombre:");
                        Optional<String> result = dialog.showAndWait();
                        result.ifPresent(newName -> selectedItem.setValue(newName));
                    });

                    agregarCursoOMateriaMenuItem.setOnAction(e -> {
                        TextInputDialog dialog = new TextInputDialog("Nuevo Curso/Materia");
                        dialog.setTitle("Agregar nuevo curso/materia");
                        dialog.setHeaderText("Agregar nuevo curso/materia");
                        dialog.setContentText("Nombre:");
                        Optional<String> result = dialog.showAndWait();
                        result.ifPresent(newName -> selectedItem.getChildren().add(new TreeItem<>(newName)));
                    });

                    eliminarCursoOMateriaMenuItem.setOnAction(e -> {
                        selectedItem.getParent().getChildren().remove(selectedItem);
                    });
                }
            } else {
                contextMenu.hide();
            }
        });

        panelIzquierdo.getChildren().add(arbolCursos);
        panelPrincipal.setLeft(panelIzquierdo);

        String rutaImagen = "C:\\Users\\juanm\\OneDrive\\Documentos\\NetBeansProjects\\ProyectioFinalJava_1_2_4_3_2_1_1\\src\\archivo\\Uno.jpg";

        // Cargar la imagen desde la ubicación absoluta
        Image imagen = new Image("file:" + rutaImagen);

        // Crear el ImageView con la imagen cargada
        ImageView imageView = new ImageView(imagen);

        double anchoDeseado = 550; // Cambia este valor al ancho deseado
        imageView.setFitWidth(anchoDeseado);
        imageView.setPreserveRatio(true); // Mantener la relación de aspecto

        double alturaDeseada = 500; // Cambia este valor a la altura deseada
        imageView.setFitHeight(alturaDeseada);
        imageView.setPreserveRatio(true); // Mantener la relación de aspecto

        // Crear un VBox y agregar el ImageView
        VBox vbox = new VBox(imageView);

        // Crear una escena y agregar el VBox
        Scene scene = new Scene(vbox, 400, 300);

        panelCentral = new VBox(imageView);
        panelCentral.setStyle("-fx-background-color: #f4f4f4;");
        panelCentral.setPadding(new Insets(20));
        panelCentral.setAlignment(Pos.TOP_CENTER);

        VBox panelCalendario = new VBox();
        panelCalendario.setStyle("-fx-background-color: #ecf0f1;");
        panelCalendario.setPadding(new Insets(10));
        panelCalendario.setSpacing(10);
        panelCalendario.setAlignment(Pos.TOP_CENTER);

        CalendarPicker calendario = new CalendarPicker();
        calendario.setLocale(Locale.getDefault()); // Configura el idioma del calendario
        calendario.setAllowNull(Boolean.TRUE); // Permite seleccionar "ninguna fecha"
        calendario.setShowTime(Boolean.FALSE);

        // Agregar el VBox del calendario al panel central
        panelPrincipal.setCenter(calendario);

        // Agregar el VBox del calendario al panel central
        panelPrincipal.setCenter(panelCentral);

        panelInferior = new VBox(); // Cambiado de panelDerecho a panelInferior
        panelInferior.setStyle("-fx-background-color: #ecf0f1;");
        panelInferior.setPadding(new Insets(10));
        panelInferior.setSpacing(10);
        panelInferior.setPrefHeight(120); // Establecer la altura preferida (ajusta este valor según tus necesidades)
        panelInferior.setAlignment(Pos.BOTTOM_CENTER);

        arbolCursos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.getValue().equals("ESTRUCTURA DE DATOS")) {
                // Mostrar el contenido de Estructura de Datos en el panel central
                VBox panelContenidoEstructuraDatos = new VBox();
                panelContenidoEstructuraDatos.setAlignment(Pos.CENTER);

                // Agregar el calendario al VBox
                panelContenidoEstructuraDatos.getChildren().add(calendario);

                // Agregar los botones "Editar Planificación" y "Visualizar Planificación" al VBox
                Button btnEditarPlanificacion = new Button("Editar Planificación");
                btnEditarPlanificacion.setOnAction(event -> {
                    // Abrir el frame para seleccionar la semana
                    new Editar().show();
                });
                Button btnVisualizarPlanificacion = new Button("Visualizar Planificación");
                btnVisualizarPlanificacion.setOnAction(event -> {
                    // Abrir el frame para seleccionar la semana
                    new Visualizar().show();
                });

                btnEditarPlanificacion.setStyle("-fx-font-size: 20;");
                btnVisualizarPlanificacion.setStyle("-fx-font-size: 20;");
                HBox botonesPlanificacion = new HBox(10, btnEditarPlanificacion, btnVisualizarPlanificacion);
                botonesPlanificacion.setAlignment(Pos.CENTER);

                panelContenidoEstructuraDatos.getChildren().add(botonesPlanificacion);
                VBox.setMargin(botonesPlanificacion, new Insets(200, 0, 0, 0));
                // Limpiar el panelCentral y agregar el VBox "panelContenidoEstructuraDatos"
                panelCentral.getChildren().clear();
                panelCentral.getChildren().add(panelContenidoEstructuraDatos);
            } else {
                // Limpiar el panelCentral si no se selecciona "ESTRUCTURA DE DATOS"
                panelCentral.getChildren().clear();
            }
        });

        VBox panelDerecho = new VBox();
        panelDerecho.setStyle("-fx-background-color: #ecf0f1;");
        panelDerecho.setPadding(new Insets(10));
        panelDerecho.setSpacing(10);
        panelDerecho.setAlignment(Pos.CENTER);

        panelNotificaciones = new VBox();
        panelNotificaciones.setStyle("-fx-background-color: #f4f4f4;");
        panelNotificaciones.setPadding(new Insets(10));
        panelNotificaciones.setSpacing(10);

        etiquetaNotificaciones = new Label("Linea de Tiempo");
        Notificaciones noti = new Notificaciones();
        noti.notificacion(etiquetaNotificaciones);
        etiquetaNotificaciones.setFont(new Font("Arial", 18));

        panelNotificaciones.getChildren().addAll(etiquetaNotificaciones);
        panelDerecho.getChildren().add(panelNotificaciones);

        panelNotas = new VBox();
        panelNotas.setStyle("-fx-background-color: #f4f4f4;");
        panelNotas.setPadding(new Insets(10));
        panelNotas.setSpacing(10);

        etiquetaNotas = new Label("Notas pendientes:");
        etiquetaNotas.setFont(new Font("Arial", 18));

        listaNotasPendientes = new ListView<>();
        listaNotasPendientes.setStyle("-fx-font-size: 16;");

        ContextMenu contextMenuActividades = new ContextMenu();
        MenuItem agregarActividadMenuItem = new MenuItem("Agregar nota");
        MenuItem verActividadMenuItem = new MenuItem("Ver nota");
        MenuItem modificarActividadMenuItem = new MenuItem("Modificar nota");

        contextMenuActividades.getItems().addAll(agregarActividadMenuItem, verActividadMenuItem, modificarActividadMenuItem);

        listaNotasPendientes.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenuActividades.show(listaNotasPendientes, event.getScreenX(), event.getScreenY());
            } else {
                contextMenuActividades.hide();
            }
        });

        agregarActividadMenuItem.setOnAction(e -> agregarNotaPendiente());
        verActividadMenuItem.setOnAction(e -> verNotaSeleccionada());
        modificarActividadMenuItem.setOnAction(e -> modificarActividadSeleccionada());

        panelNotas.getChildren().addAll(etiquetaNotas, listaNotasPendientes);
        panelInferior.getChildren().add(panelNotas); // Cambiado de panelDerecho a panelInferior

        panelPrincipal.setBottom(panelInferior);
        panelPrincipal.setRight(panelDerecho);
    }

    private void agregarNotaPendiente() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nota actividad");
        dialog.setHeaderText("Agregar nueva Nota pendiente");
        dialog.setContentText("Nombre de la Nota:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(activityName -> {
            listaNotasPendientes.getItems().add(activityName);
        });
    }

    private void verNotaSeleccionada() {
        String selectedActivity = listaNotasPendientes.getSelectionModel().getSelectedItem();
        if (selectedActivity != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nota Seleccionada");
            alert.setHeaderText("Detalles de la Nota");
            alert.setContentText("Detalles de la Nota:\n" + selectedActivity);
            alert.showAndWait();
        }
    }

    private void modificarActividadSeleccionada() {
        String selectedActivity = listaNotasPendientes.getSelectionModel().getSelectedItem();
        if (selectedActivity != null) {
            TextInputDialog dialog = new TextInputDialog(selectedActivity);
            dialog.setTitle("Modificar Nota");
            dialog.setHeaderText("Modificar Nota pendiente");
            dialog.setContentText("Nuevo nombre para la Nota:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newName -> {
                int selectedIndex = listaNotasPendientes.getSelectionModel().getSelectedIndex();
                listaNotasPendientes.getItems().set(selectedIndex, newName);
            });
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Image iconImage = new Image("C:\\Users\\juanm\\OneDrive\\Documentos\\NetBeansProjects\\ProyectioFinalJava_1_2_4_3_2_1_1\\src\\archivo\\Dos.jpg"); // Ruta absoluta del icono
        primaryStage.getIcons().add(iconImage);

        primaryStage.setTitle("Plantain");
        primaryStage.setScene(new Scene(panelPrincipal, 1300, 600));

        // Permitir que la tecla derecha active el cambio de nombre
        panelPrincipal.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
            }
        });

        primaryStage.show();
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
