package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.entity.CuentaEntity;

public interface CuentaJpaRepository extends JpaRepository<CuentaEntity, Long> {

    @Query("SELECT c FROM CuentaEntity c WHERE c.numeroCuenta = :numeroCuenta")
    Optional<CuentaEntity> findByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);

    @Query("SELECT c FROM CuentaEntity c WHERE c.cliente_id = :clienteId")
    List<CuentaEntity> findAllByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT COUNT(c) > 0 FROM CuentaEntity c WHERE c.cliente_id = :clienteId")
    boolean existsByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT COUNT(c) > 0 FROM CuentaEntity c WHERE c.numeroCuenta = :numeroCuenta")
    boolean existsByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);

    @Query("SELECT MAX(c.id) FROM CuentaEntity c")
    Long maxId();

    @Query("SELECT c FROM CuentaEntity c WHERE c.cliente_id = :clienteId")
    List<CuentaEntity> findByClienteId(@Param("clienteId") Long clienteId);
}
