package com.empleados.repository;

import com.empleados.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<Empleado, Long> ya trae: findAll, findById, save, deleteById, etc.
// Spring crea la implementación automáticamente; no escribimos SQL.
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    // Spring deduce la consulta por el nombre del método: "WHERE documento = ?"
    boolean existsByDocumento(String documento);
}
