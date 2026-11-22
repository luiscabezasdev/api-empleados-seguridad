package com.empleados.repository;

import com.empleados.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    // Optional: puede que el rol no exista todavía; evita trabajar con null.
    Optional<Rol> findByNombre(String nombre);
}
