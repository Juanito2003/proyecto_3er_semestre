# Plantain — aula virtual de escritorio

Proyecto universitario de fin de tercer semestre: una aplicación de escritorio inspirada en Moodle para organizar cursos, materias y la planificación semanal de actividades.

Hecha en **Java** con **JavaFX** para la ventana principal y **Swing** para las ventanas de planificación. Los datos se guardan en un libro de **Excel** que se lee y escribe con **Apache POI**.

## Funcionalidades

- **Inicio de sesión** con un diálogo de usuario y contraseña.
- **Árbol de cursos y materias** editable con el botón derecho: cambiar nombre, añadir y eliminar.
- **Planificación semanal** (16 semanas) de cada materia:
  - *Visualizar*: muestra la tabla de la semana elegida (día, horas, unidad, contenidos, actividades, evaluación y fecha).
  - *Editar*: permite modificar la tabla y guardar los cambios en el Excel.
- **Calendario** para consultar fechas (JFXtras `CalendarPicker`).
- **Aviso de actividad**: si hay una actividad planificada para hoy, aparece un aviso en el panel lateral.
- **Notas pendientes**: lista con menú contextual para añadir, ver y modificar notas.

## Tecnologías

| | |
|---|---|
| Lenguaje | Java 19 |
| Interfaz | JavaFX y Swing |
| Componentes extra | JFXtras (`jfxtras-controls`) |
| Datos | Excel (`.xlsx`) con Apache POI |
| Proyecto | NetBeans (Ant) |

## Estructura

```
ProyectoJava_Moodle/
├── src/proyectiofinaljava/
│   ├── Main.java            Arranque e inicio de sesión
│   ├── VtnContenedora.java  Ventana principal (cursos, calendario, notas, avisos)
│   ├── Visualizar.java      Consulta de la planificación semanal
│   ├── Editar.java          Edición y guardado de la planificación
│   ├── Notificaciones.java  Lectura de fechas y aviso de actividades del día
│   ├── Actividad.java       Modelo de actividad
│   └── Rutas.java           Rutas de los archivos de datos
└── src/archivo/
    ├── Actividades.xlsx     Planificación: una hoja por semana
    └── Uno.jpg, Dos.jpg     Imagen de portada e icono
```

## Cómo ejecutarlo

1. Abre la carpeta `ProyectoJava_Moodle` como proyecto en **NetBeans** con un JDK 19 o superior.
2. En *Tools → Libraries* define las bibliotecas que usa el proyecto: **JavaFX** (SDK de OpenJFX), **JFXtras** (`jfxtras-controls`) y **POI** / **POI-ooxml** (Apache POI 5).
3. Ejecuta el proyecto (clase principal `proyectiofinaljava.Main`).
4. Inicia sesión con el usuario de prueba **`usuario`** y la contraseña **`1234`**.

El Excel se abre con una ruta relativa a la carpeta del proyecto, así que la aplicación debe ejecutarse desde ahí (es lo que hace NetBeans por defecto).

## Limitaciones conocidas

- El inicio de sesión es de demostración: un único usuario con la contraseña en el código, sin roles de alumno y profesor.
- Las notas pendientes y los cambios en el árbol de cursos no se guardan al cerrar.
- La planificación solo está enlazada a la materia *Estructura de Datos*.
