
package proyectiofinaljava;

/**
 *
 * @author juan
 */
import javax.swing.*;
import java.awt.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import java.awt.Font;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class Visualizar extends JFrame {

    private JComboBox<String> comboBoxSemanas;
    private JTable table;

    public Visualizar() {
        setTitle("Visualizar");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1480, 613);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        String[] semanas = new String[16];
        for (int i = 0; i < 16; i++) {
            semanas[i] = "Semana " + (i + 1);
        }
        comboBoxSemanas = new JComboBox<>(semanas);
        comboBoxSemanas.setMaximumSize(new Dimension(200, 30));
        panel.add(Box.createHorizontalStrut(10));
        panel.add(comboBoxSemanas);

        add(panel, BorderLayout.NORTH);

        JButton regresarButton = new JButton("Regresar al Menú");
        panel.add(Box.createHorizontalStrut(10));
        panel.add(regresarButton);
        add(panel, BorderLayout.NORTH);

        table = new JTable();
        table.setFont(new Font("Courier New", Font.PLAIN, 12));
        table.setEnabled(false);
        table.setDefaultRenderer(Object.class, new MultiLineTableCellRenderer());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Ajustar el alto de las filas
        table.setRowHeight(105);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);

        comboBoxSemanas.addActionListener(e -> {
            int semanaSeleccionada = comboBoxSemanas.getSelectedIndex() + 1;
            Object[][] celdas = obtenerCeldasSemana(semanaSeleccionada);
            actualizarTabla(celdas);
        });
        // Acción del botón "Regresar al Menú"
        regresarButton.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }

    private Object[][] obtenerCeldasSemana(int semana) {
        if (semana >= 1 && semana <= 16) {
            String hoja;
            int primeraFila, ultimaFila;
            String primeraColumna = "A";
            String ultimaColumna = "I";

            if (semana == 1) {
                hoja = "Semana 1";
                primeraFila = 2;
                ultimaFila = 6;
            } else if (semana >= 2 && semana <= 16) {
                hoja = "Semana " + semana;
                primeraFila = 2;
                ultimaFila = 6;
            } else {
                return new Object[0][0];
            }

            String cellRange = primeraColumna + primeraFila + ":" + ultimaColumna + ultimaFila;

            try {
                String filePath = "src/archivo/Actividades.xlsx";
                return leerCeldas(filePath, hoja, cellRange);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Object[0][0];
    }

    private Object[][] leerCeldas(String filePath, String sheetName, String cellRange) throws Exception {
        try (FileInputStream fileInputStream = new FileInputStream(filePath); Workbook workbook = WorkbookFactory.create(fileInputStream)) {
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                return new Object[0][0];
            }

            CellRangeAddress range = CellRangeAddress.valueOf(cellRange);
            int rows = range.getLastRow() - range.getFirstRow() + 1;
            int cols = range.getLastColumn() - range.getFirstColumn() + 1;

            Object[][] data = new Object[rows][cols];

            for (int i = 0; i < rows; i++) {
                Row row = sheet.getRow(range.getFirstRow() + i);
                if (row != null) {
                    for (int j = 0; j < cols; j++) {
                        Cell cell = row.getCell(range.getFirstColumn() + j);
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case STRING:
                                    data[i][j] = cell.getStringCellValue();
                                    break;
                                case NUMERIC:
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        data[i][j] = cell.getDateCellValue();
                                    } else {
                                        data[i][j] = cell.getNumericCellValue();
                                    }
                                    break;
                                case BOOLEAN:
                                    data[i][j] = cell.getBooleanCellValue();
                                    break;
                                default:
                                    data[i][j] = "";
                            }
                        } else {
                            data[i][j] = "";
                        }
                    }
                }
            }

            return data;
        } catch (Exception e) {
            throw new Exception("Error al leer el archivo Excel.", e);
        }
    }

    private void actualizarTabla(Object[][] data) {
        String[] columnNames = {"SEMANA", "DÍA", "HORAS PRESENCIALES", "UNIDAD. DESCRIPCION", "CONTENIDOS",
            "ACTIVIDADES DE APRENDIZAJE", "TIEMPO", "EVALUACION", "FECHA"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };

        table.setModel(model);

        // Definir el ancho de las columnas
        int[] columnWidths = {100, 80, 150, 200, 450, 200, 80, 100, 100};

        // Ajustar el ancho de cada columna
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
    }

// Clase para el renderizador personalizado de celdas para texto con múltiples líneas
    class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {

        public MultiLineTableCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
            setMargin(new Insets(16, 17, 7, 7));
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }

            if (value instanceof Number) {
                NumberFormat formatter = new DecimalFormat("#.###");
                String formattedValue = formatter.format(value);
                setText(formattedValue);
            } else {
                setText((value == null) ? "" : value.toString());
            }

            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Visualizar());
    }
}
