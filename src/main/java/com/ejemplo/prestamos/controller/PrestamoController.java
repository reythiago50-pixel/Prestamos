package com.ejemplo.prestamos.controller;

import com.ejemplo.prestamos.dto.PrestamoRequest;
import com.ejemplo.prestamos.model.Prestamo;
import com.ejemplo.prestamos.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prestamo registrar(@Valid @RequestBody PrestamoRequest request) {
        return prestamoService.registrar(request);
    }

    @GetMapping
    public List<Prestamo> listar() {
        return prestamoService.listar();
    }

    @GetMapping("/herramienta/{id}")
    public List<Prestamo> listarPorHerramienta(@PathVariable Long id) {
        return prestamoService.listarPorHerramienta(id);
    }
}
