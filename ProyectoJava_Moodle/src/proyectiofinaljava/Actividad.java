/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectiofinaljava;

/**
 *
 * @author juan
 */
import java.time.LocalDate;

public class Actividad {

    private String descripcion;
    private LocalDate fecha;

    public Actividad(String descripcion, LocalDate fecha) {
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
