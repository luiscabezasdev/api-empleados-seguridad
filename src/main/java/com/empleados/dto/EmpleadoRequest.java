package com.empleados.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

// DTO de entrada: los datos que el cliente (Postman) envía en POST y PUT.
// Es un "record": una clase compacta de Java 17 que solo guarda datos.
// Las validaciones (@NotBlank, @Positive...) se añadirán en la fase 6.
public record EmpleadoRequest(
        String nombre,
        String apellido,
        String documento,
        String cargo,
        BigDecimal salario,
        LocalDate fechaIngreso
) {
}
