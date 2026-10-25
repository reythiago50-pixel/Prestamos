package com.ejemplo.prestamos.repository;

import com.ejemplo.prestamos.model.Herramienta;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HerramientaRepository extends JpaRepository<Herramienta, Long> {

    /**
     * Bloquea la fila durante la transacción para que dos préstamos simultáneos
     * no descuenten el mismo stock (SELECT ... FOR UPDATE).
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT h FROM Herramienta h WHERE h.id = :id")
    Optional<Herramienta> findByIdForUpdate(@Param("id") Long id);
}
