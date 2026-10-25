package com.ejemplo.prestamos.repository;

import com.ejemplo.prestamos.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findAllByOrderByFechaPrestamoDescIdDesc();

    List<Prestamo> findByHerramientaIdOrderByFechaPrestamoDescIdDesc(Long herramientaId);
}
