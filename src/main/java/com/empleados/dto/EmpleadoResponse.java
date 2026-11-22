package com.empleados.dto;

import com.empleados.model.Empleado;

import java.math.BigDecimal;
import java.time.LocalDate;

// DTO de salida: lo que la API devuelve al cliente.
// Nunca devolvemos la entidad directamente; así controlamos exactamente qué campos se ven.
public record EmpleadoResponse(
        Long id,
        String nombre,
        String apellido,
        String documento,
        String cargo,
        BigDecimal salario,
        LocalDate fechaIngreso
) {

    // Método de conveniencia: convierte una entidad (de la base de datos) en respuesta JSON.
    public static EmpleadoResponse desde(Empleado e) {
        return new EmpleadoResponse(
                e.getId(),
                e.getNombre(),
                e.getApellido(),
                e.getDocumento(),
                e.getCargo(),
                e.getSalario(),
                e.getFechaIngreso()
        );
    }
}
