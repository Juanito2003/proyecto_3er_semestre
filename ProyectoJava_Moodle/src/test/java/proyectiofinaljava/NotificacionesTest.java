package proyectiofinaljava;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.function.Consumer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class NotificacionesTest {

    private static final int COLUMNA_FECHA = 8;

    @TempDir
    Path carpeta;

    /** Crea un Excel con una hoja, la cabecera y una fila; {@code rellenar} decide qué lleva la fila. */
    private File crearExcel(Consumer<Row> rellenar) throws IOException {
        File archivo = carpeta.resolve("actividades.xlsx").toFile();
        try (XSSFWorkbook libro = new XSSFWorkbook(); FileOutputStream salida = new FileOutputStream(archivo)) {
            Sheet hoja = libro.createSheet("Semana 1");
            hoja.createRow(0).createCell(COLUMNA_FECHA).setCellValue("FECHA");
            rellenar.accept(hoja.createRow(1));
            libro.write(salida);
        }
        return archivo;
    }

    private static String comoTexto(LocalDate fecha) {
        Date date = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return new SimpleDateFormat("d 'de' MMMM yyyy", new Locale("es", "ES")).format(date);
    }

    @Test
    void fechaDeHoyEscritaComoTexto() throws IOException {
        String hoy = comoTexto(LocalDate.now());
        File archivo = crearExcel(fila -> fila.createCell(COLUMNA_FECHA).setCellValue(hoy));
        assertTrue(new Notificaciones(archivo).hayActividadHoy(), "texto: " + hoy);
    }

    @Test
    void fechaDeHoyConFormatoDeFechaDeExcel() throws IOException {
        File archivo = crearExcel(fila -> {
            Cell celda = fila.createCell(COLUMNA_FECHA);
            celda.setCellValue(LocalDate.now());
            CellStyle estilo = fila.getSheet().getWorkbook().createCellStyle();
            estilo.setDataFormat((short) 14); // formato de fecha integrado de Excel (d/m/aaaa)
            celda.setCellStyle(estilo);
        });
        assertTrue(new Notificaciones(archivo).hayActividadHoy());
    }

    @Test
    void fechaDeOtroDia() throws IOException {
        String manana = comoTexto(LocalDate.now().plusDays(1));
        File archivo = crearExcel(fila -> fila.createCell(COLUMNA_FECHA).setCellValue(manana));
        assertFalse(new Notificaciones(archivo).hayActividadHoy());
    }

    @Test
    void celdaVaciaNoLanzaExcepcion() throws IOException {
        File archivo = crearExcel(fila -> fila.createCell(0).setCellValue("Semana 1"));
        assertFalse(new Notificaciones(archivo).hayActividadHoy());
    }

    @Test
    void textoQueNoEsUnaFechaNoLanzaExcepcion() throws IOException {
        File archivo = crearExcel(fila -> fila.createCell(COLUMNA_FECHA).setCellValue("por decidir"));
        assertFalse(new Notificaciones(archivo).hayActividadHoy());
    }

    @Test
    void archivoQueNoExisteNoLanzaExcepcion() {
        File archivo = carpeta.resolve("no-existe.xlsx").toFile();
        assertFalse(new Notificaciones(archivo).hayActividadHoy());
    }

    @Test
    void leerElExcelNoLoModifica() throws IOException {
        String hoy = comoTexto(LocalDate.now());
        File archivo = crearExcel(fila -> fila.createCell(COLUMNA_FECHA).setCellValue(hoy));
        byte[] antes = Files.readAllBytes(archivo.toPath());
        new Notificaciones(archivo);
        assertTrue(java.util.Arrays.equals(antes, Files.readAllBytes(archivo.toPath())),
                "Notificaciones no debe reescribir el Excel al leerlo");
    }
}
