package proyectiofinaljava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class NotasPendientesTest {

    @TempDir
    Path carpeta;

    @Test
    void guardarYVolverACargarConservaLasNotasYSuOrden() {
        Path archivo = carpeta.resolve("notas.txt");
        List<String> notas = List.of("Entregar práctica de Estructura de Datos", "Repasar tema 3", "Año 2026: ñ y tildes");
        assertTrue(NotasPendientes.guardar(archivo, notas));
        assertEquals(notas, NotasPendientes.cargar(archivo));
    }

    @Test
    void sinArchivoDevuelveUnaListaVacia() {
        assertTrue(NotasPendientes.cargar(carpeta.resolve("no-existe.txt")).isEmpty());
    }

    @Test
    void ignoraLasLineasEnBlanco() throws IOException {
        Path archivo = carpeta.resolve("notas.txt");
        Files.write(archivo, List.of("Primera", "", "   ", "Segunda"), StandardCharsets.UTF_8);
        assertEquals(List.of("Primera", "Segunda"), NotasPendientes.cargar(archivo));
    }

    @Test
    void creaLaCarpetaSiNoExiste() {
        Path archivo = carpeta.resolve("datos").resolve("notas.txt");
        assertTrue(NotasPendientes.guardar(archivo, List.of("Nota")));
        assertEquals(List.of("Nota"), NotasPendientes.cargar(archivo));
    }

    @Test
    void unaNotaConSaltoDeLineaSigueSiendoUnaSola() {
        Path archivo = carpeta.resolve("notas.txt");
        NotasPendientes.guardar(archivo, List.of("Línea uno\nlínea dos"));
        assertEquals(List.of("Línea uno línea dos"), NotasPendientes.cargar(archivo));
    }

    @Test
    void guardarUnaListaVaciaDejaElArchivoVacio() {
        Path archivo = carpeta.resolve("notas.txt");
        NotasPendientes.guardar(archivo, List.of("Nota"));
        NotasPendientes.guardar(archivo, List.of());
        assertTrue(NotasPendientes.cargar(archivo).isEmpty());
    }
}
