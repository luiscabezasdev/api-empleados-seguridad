package com.empleados.config;

import com.empleados.model.Rol;
import com.empleados.model.Usuario;
import com.empleados.repository.RolRepository;
import com.empleados.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

// CommandLineRunner: el método run() se ejecuta una vez, justo después de arrancar la aplicación.
// Aquí creamos los datos iniciales (seed) si todavía no existen.
@Component
public class DatosIniciales implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Las contraseñas vienen de variables de entorno (ver application.properties).
    // Si no están definidas, el valor es cadena vacía y la app se detiene.
    private final String adminPassword;
    private final String usuario1Password;

    // Spring "inyecta" automáticamente los objetos que pedimos en el constructor.
    public DatosIniciales(RolRepository rolRepository,
                          UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          @Value("${app.seed.admin-password}") String adminPassword,
                          @Value("${app.seed.usuario1-password}") String usuario1Password) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminPassword = adminPassword;
        this.usuario1Password = usuario1Password;
    }

    @Override
    public void run(String... args) {
        // Validamos antes de tocar la base de datos: mejor fallar con un mensaje claro.
        if (adminPassword.isBlank() || usuario1Password.isBlank()) {
            throw new IllegalStateException(
                    "Faltan variables de entorno: define ADMIN_PASSWORD y USUARIO1_PASSWORD antes de arrancar la aplicación.");
        }

        Rol rolAdmin = obtenerORCrearRol("ADMIN");
        Rol rolUsuario = obtenerORCrearRol("USUARIO_NORMAL");

        crearUsuarioSiNoExiste("admin", adminPassword, Set.of(rolAdmin));
        crearUsuarioSiNoExiste("usuario1", usuario1Password, Set.of(rolUsuario));
    }

    private Rol obtenerORCrearRol(String nombre) {
        return rolRepository.findByNombre(nombre)
                .orElseGet(() -> rolRepository.save(new Rol(nombre)));
    }

    private void crearUsuarioSiNoExiste(String username, String passwordEnTextoPlano, Set<Rol> roles) {
        if (usuarioRepository.existsByUsername(username)) {
            return; // Ya existe: no lo volvemos a crear en cada arranque.
        }
        Usuario usuario = new Usuario(username, passwordEncoder.encode(passwordEnTextoPlano));
        usuario.setRoles(roles);
        usuarioRepository.save(usuario);
    }
}
