package proyectiofinaljava;

/**
 *
 * @author juan
 */
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.scene.control.Label;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Notificaciones {

    // Columna "FECHA" de cada hoja de semana (columna I)
    private static final int COLUMNA_FECHA = 8;

    private final List<LocalDate> fechas = new ArrayList<>();

    public Notificaciones() {
        this(new File(Rutas.EXCEL_ACTIVIDADES));
    }

    public Notificaciones(File archivo) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d 'de' MMMM yyyy", new Locale("es", "ES"));

        try (XSSFWorkbook libro = new XSSFWorkbook(archivo)) {
            // Recorre todas las hojas (una por semana) y salta la cabecera
            for (int m = 0; m < libro.getNumberOfSheets(); m++) {
                XSSFSheet hoja = libro.getSheetAt(m);
                for (Row fila : hoja) {
                    if (fila.getRowNum() == 0) {
                        continue;
                    }
                    LocalDate fecha = leerFecha(fila.getCell(COLUMNA_FECHA), dateFormat);
                    if (fecha != null) {
                        fechas.add(fecha);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("No se pudieron leer las actividades de " + archivo + ": " + e.getMessage());
        }
    }

    // Devuelve la fecha de la celda o null si está vacía o no es una fecha válida
    private static LocalDate leerFecha(Cell celda, SimpleDateFormat dateFormat) {
        if (celda == null) {
            return null;
        }
        if (celda.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(celda)) {
            return celda.getLocalDateTimeCellValue().toLocalDate();
        }
        if (celda.getCellType() == CellType.STRING) {
            String texto = celda.getStringCellValue().trim();
            if (texto.isEmpty()) {
                return null;
            }
            try {
                return dateFormat.parse(texto).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } catch (ParseException e) {
                System.err.println("Fecha no reconocida: " + texto);
            }
        }
        return null;
    }

    public boolean hayActividadHoy() {
        return fechas.contains(LocalDate.now());
    }

    public void notificacion(Label notificacion) {
        if (hayActividadHoy()) {
            notificacion.setText("Tiene una Actividad\n a punto de empezar");
        }
    }
}
