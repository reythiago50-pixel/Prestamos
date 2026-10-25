package com.ejemplo.prestamos.service;

import com.ejemplo.prestamos.dto.PrestamoRequest;
import com.ejemplo.prestamos.exception.RecursoNoEncontradoException;
import com.ejemplo.prestamos.exception.ReglaNegocioException;
import com.ejemplo.prestamos.model.Herramienta;
import com.ejemplo.prestamos.model.Prestamo;
import com.ejemplo.prestamos.repository.HerramientaRepository;
import com.ejemplo.prestamos.repository.PrestamoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final HerramientaRepository herramientaRepository;

    public PrestamoService(PrestamoRepository prestamoRepository,
                           HerramientaRepository herramientaRepository) {
        this.prestamoRepository = prestamoRepository;
        this.herramientaRepository = herramientaRepository;
    }

    /**
     * Registra el préstamo y descuenta el inventario en UNA sola transacción:
     * si algo falla, no se guarda el préstamo ni se modifica el stock.
     */
    @Transactional
    public Prestamo registrar(PrestamoRequest request) {
        Herramienta herramienta = herramientaRepository.findByIdForUpdate(request.getHerramientaId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe la herramienta con id " + request.getHerramientaId()));

        int cantidad = request.getCantidad();
        if (cantidad <= 0) {
            throw new ReglaNegocioException("La cantidad a prestar debe ser mayor que cero");
        }
        if (cantidad > herramienta.getCantidad()) {
            throw new ReglaNegocioException("Unidades insuficientes: solicitadas " + cantidad
                    + ", disponibles " + herramienta.getCantidad());
        }

        herramienta.setCantidad(herramienta.getCantidad() - cantidad);
        herramientaRepository.save(herramienta);

        Prestamo prestamo = new Prestamo();
        prestamo.setResponsable(request.getResponsable());
        prestamo.setFechaPrestamo(request.getFechaPrestamo());
        prestamo.setCantidad(cantidad);
        prestamo.setHerramienta(herramienta);
        return prestamoRepository.save(prestamo);
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listar() {
        return prestamoRepository.findAllByOrderByFechaPrestamoDescIdDesc();
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listarPorHerramienta(Long herramientaId) {
        if (!herramientaRepository.existsById(herramientaId)) {
            throw new RecursoNoEncontradoException("No existe la herramienta con id " + herramientaId);
        }
        return prestamoRepository.findByHerramientaIdOrderByFechaPrestamoDescIdDesc(herramientaId);
    }
}
