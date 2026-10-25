package com.ejemplo.prestamos.controller;

import com.ejemplo.prestamos.model.Herramienta;
import com.ejemplo.prestamos.service.HerramientaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/herramientas")
public class HerramientaController {

    private final HerramientaService herramientaService;

    public HerramientaController(HerramientaService herramientaService) {
        this.herramientaService = herramientaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Herramienta registrar(@Valid @RequestBody Herramienta herramienta) {
        return herramientaService.registrar(herramienta);
    }

    @GetMapping
    public List<Herramienta> listar() {
        return herramientaService.listar();
    }
}
