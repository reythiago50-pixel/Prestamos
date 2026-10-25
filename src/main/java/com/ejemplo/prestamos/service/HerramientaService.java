package com.ejemplo.prestamos.service;

import com.ejemplo.prestamos.exception.ReglaNegocioException;
import com.ejemplo.prestamos.model.Herramienta;
import com.ejemplo.prestamos.repository.HerramientaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HerramientaService {

    private final HerramientaRepository herramientaRepository;

    public HerramientaService(HerramientaRepository herramientaRepository) {
        this.herramientaRepository = herramientaRepository;
    }

    @Transactional
    public Herramienta registrar(Herramienta herramienta) {
        if (herramienta.getCantidad() == null || herramienta.getCantidad() < 0) {
            throw new ReglaNegocioException("La cantidad no puede ser negativa");
        }
        herramienta.setId(null); // siempre se crea una nueva
        return herramientaRepository.save(herramienta);
    }

    @Transactional(readOnly = true)
    public List<Herramienta> listar() {
        return herramientaRepository.findAll();
    }
}
