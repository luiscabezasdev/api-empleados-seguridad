package com.empleados.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration: clase con configuración de Spring.
// @Bean: el objeto que devuelve el método queda disponible para inyectarlo en cualquier clase.
@Configuration
public class PasswordConfig {

    // BCrypt convierte "miClave" en un hash irreversible distinto cada vez.
    // Lo usaremos para guardar contraseñas y, en la fase 5, para validar el login.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
