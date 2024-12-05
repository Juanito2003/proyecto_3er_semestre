package proyectiofinaljava;
/**
 *
 * @author juan
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javax.swing.JLabel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Notificaciones {

    Date[] date = new Date[500];
    Date fechaActual = new Date();

    public Notificaciones() {
        File archivo = new File("src/archivo/Actividades.xlsx");
        String[] dateString = new String[500];
        SimpleDateFormat dateFormat = new SimpleDateFormat("d 'de' MMMM yyyy", new Locale("es", "ES"));

        int i = 0;
        int m;

        try {
            InputStream input = new FileInputStream(archivo);
            XSSFWorkbook libro = new XSSFWorkbook(archivo);

            Cell columna = null;

            XSSFSheet hoja;
            for (m = 0; m < 16; m++) {
                hoja = libro.getSheetAt(m);
                Iterator<Row> filas = hoja.rowIterator();

                if (filas.hasNext()) {
                    filas.next();
                }

                while (filas.hasNext()) {
                    columna = filas.next().getCell(8);
                    if (columna.getStringCellValue() != null) {
                        dateString[i] = columna.getStringCellValue();
                        System.out.println(dateString[i]);
                    }

                    try {
                        date[i] = dateFormat.parse(dateString[i]);
                        System.out.println("Date: " + date[i]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    i++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void notificacion(Label notificacion  ) {

        int o = 0;

        while (o < 60) {
            LocalDate soloFecha1 = fechaActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusDays(1);
            LocalDate soloFecha2 = date[o].toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusDays(1);

            int comparacion = soloFecha2.compareTo(soloFecha1);

            if (comparacion < 0) {
                System.out.println("La fecha 1 es anterior a la fecha 2.");
                o++;

            } else if (comparacion > 0) {
                System.out.println("La fecha 1 es posterior a la fecha 2.");
                o++;

            } else {
                System.out.println("Las fechas son iguales.");

                // notificacion.setText("Tiene una actividad a punto de empezar");
                //   notificacion.repaint();
                notificacion.setText("Tiene una Actividad\n a punto de empezar");

                break;

            }

        }
        System.out.println("ya basta");
    }
}
    
