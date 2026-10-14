package proyectiofinaljava;

/**
 * Rutas de los archivos de datos de la aplicación.
 *
 * La planificación se guarda en un Excel que la aplicación también
 * modifica, así que se lee del disco (no del classpath). La ruta es
 * relativa a la carpeta ProyectoJava_Moodle, que es el directorio de
 * trabajo al ejecutar con Maven (mvn javafx:run) o desde NetBeans.
 *
 * @author juan
 */
public final class Rutas {

    public static final String EXCEL_ACTIVIDADES = "datos/Actividades.xlsx";

    /** Notas pendientes del panel inferior, una por línea. */
    public static final String NOTAS_PENDIENTES = "datos/notas.txt";

    private Rutas() {
    }
}
