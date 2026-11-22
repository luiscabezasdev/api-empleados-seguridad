package com.empleados.service;

import com.empleados.dto.EmpleadoRequest;
import com.empleados.dto.EmpleadoResponse;
import com.empleados.model.Empleado;
import com.empleados.repository.EmpleadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// @Service: clase con la lógica de negocio. El controlador le pide cosas y ella usa el repositorio.
@Service
public class EmpleadoService {

    private final EmpleadoRepository repository;

    // Inyección por constructor: Spring nos pasa el repositorio automáticamente.
    public EmpleadoService(EmpleadoRepository repository) {
        this.repository = repository;
    }

    public List<EmpleadoResponse> listar() {
        return repository.findAll().stream()
                .map(EmpleadoResponse::desde)
                .toList();
    }

    public EmpleadoResponse buscarPorId(Long id) {
        return EmpleadoResponse.desde(obtenerOExcepcion(id));
    }

    // @Transactional: todo el método se ejecuta como una sola operación con la base de datos.
    @Transactional
    public EmpleadoResponse crear(EmpleadoRequest request) {
        Empleado empleado = new Empleado(
                request.nombre(),
                request.apellido(),
                request.documento(),
                request.cargo(),
                request.salario(),
                request.fechaIngreso()
        );
        return EmpleadoResponse.desde(repository.save(empleado));
    }

    @Transactional
    public EmpleadoResponse actualizar(Long id, EmpleadoRequest request) {
        Empleado empleado = obtenerOExcepcion(id);
        // Reemplazo completo: se cambian todos los campos con lo que llega en la petición.
        empleado.setNombre(request.nombre());
        empleado.setApellido(request.apellido());
        empleado.setDocumento(request.documento());
        empleado.setCargo(request.cargo());
        empleado.setSalario(request.salario());
        empleado.setFechaIngreso(request.fechaIngreso());
        return EmpleadoResponse.desde(repository.save(empleado));
    }

    @Transactional
    public void eliminar(Long id) {
        obtenerOExcepcion(id); // Si no existe, lanza 404 antes de intentar borrar.
        repository.deleteById(id);
    }

    // Busca el empleado; si no existe, responde 404 Not Found.
    private Empleado obtenerOExcepcion(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Empleado no encontrado"));
    }
}
