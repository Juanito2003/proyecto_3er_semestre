package proyectiofinaljava;

/**
 * Rutas de los archivos de datos de la aplicación.
 *
 * La planificación se guarda en un Excel que la aplicación también
 * modifica, así que se lee del disco (no del classpath). La ruta es
 * relativa a la carpeta del proyecto, que es el directorio de trabajo
 * al ejecutar desde NetBeans.
 *
 * @author juan
 */
public final class Rutas {

    public static final String EXCEL_ACTIVIDADES = "src/archivo/Actividades.xlsx";

    private Rutas() {
    }
}
