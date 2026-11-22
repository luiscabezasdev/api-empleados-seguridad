package com.empleados.controller;

import com.empleados.dto.EmpleadoRequest;
import com.empleados.dto.EmpleadoResponse;
import com.empleados.service.EmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @RestController: esta clase recibe peticiones HTTP y devuelve JSON.
// @RequestMapping: todas las rutas empiezan por /api/empleados.
@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService service;

    public EmpleadoController(EmpleadoService service) {
        this.service = service;
    }

    // GET /api/empleados -> 200 con la lista
    @GetMapping
    public List<EmpleadoResponse> listar() {
        return service.listar();
    }

    // GET /api/empleados/5 -> 200, o 404 si no existe
    @GetMapping("/{id}")
    public EmpleadoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // POST /api/empleados -> 201 Created
    // @RequestBody: convierte el JSON de la petición en un EmpleadoRequest.
    @PostMapping
    public ResponseEntity<EmpleadoResponse> crear(@RequestBody EmpleadoRequest request) {
        EmpleadoResponse creado = service.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /api/empleados/5 -> 200, o 404 si no existe
    @PutMapping("/{id}")
    public EmpleadoResponse actualizar(@PathVariable Long id, @RequestBody EmpleadoRequest request) {
        return service.actualizar(id, request);
    }

    // DELETE /api/empleados/5 -> 204 No Content, o 404 si no existe
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
