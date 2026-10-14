package proyectiofinaljava;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Guarda y carga las notas pendientes en un archivo de texto, una nota por línea.
 *
 * Está separada de la ventana para poder probarla sin JavaFX.
 *
 * @author juan
 */
public final class NotasPendientes {

    private NotasPendientes() {
    }

    /** Devuelve las notas guardadas, o una lista vacía si el archivo no existe o no se puede leer. */
    public static List<String> cargar(Path archivo) {
        List<String> notas = new ArrayList<>();
        if (!Files.exists(archivo)) {
            return notas;
        }
        try {
            for (String linea : Files.readAllLines(archivo, StandardCharsets.UTF_8)) {
                if (!linea.isBlank()) {
                    notas.add(linea);
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudieron leer las notas de " + archivo + ": " + e.getMessage());
        }
        return notas;
    }

    /** Escribe las notas, una por línea; crea la carpeta si no existe. Devuelve false si no se pudo guardar. */
    public static boolean guardar(Path archivo, List<String> notas) {
        List<String> lineas = new ArrayList<>();
        for (String nota : notas) {
            // Un salto de línea dentro de una nota la partiría en dos al volver a cargarla
            lineas.add(nota.replace("\r", " ").replace("\n", " "));
        }
        try {
            Path carpeta = archivo.toAbsolutePath().getParent();
            if (carpeta != null) {
                Files.createDirectories(carpeta);
            }
            Files.write(archivo, lineas, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            System.err.println("No se pudieron guardar las notas en " + archivo + ": " + e.getMessage());
            return false;
        }
    }
}
