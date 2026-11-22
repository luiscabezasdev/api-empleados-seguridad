package com.empleados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Punto de entrada de la aplicación.
// @SpringBootApplication le dice a Spring: "escanea este paquete (com.empleados)
// y sus subpaquetes para encontrar controladores, servicios, repositorios, etc.".
@SpringBootApplication
public class CrudEmpleadosApplication {

    public static void main(String[] args) {
        // Arranca el servidor web embebido (Tomcat) y conecta con la base de datos.
        SpringApplication.run(CrudEmpleadosApplication.class, args);
    }
}
