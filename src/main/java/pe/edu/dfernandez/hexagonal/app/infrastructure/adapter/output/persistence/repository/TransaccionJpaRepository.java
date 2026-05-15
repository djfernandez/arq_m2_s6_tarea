package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.entity.TransaccionEntity;

public interface TransaccionJpaRepository extends JpaRepository<TransaccionEntity, Long> {

    @Query("SELECT t FROM TransaccionEntity t WHERE t.cuenta_origen_id = :cuentaOrigenId")
    List<TransaccionEntity> findByCuentaOrigenId(@Param("cuentaOrigenId") Long cuentaOrigenId);

    @Query("SELECT t FROM TransaccionEntity t WHERE t.cuenta_destino_id = :cuentaDestinoId")
    List<TransaccionEntity> findByCuentaDestinoId(@Param("cuentaDestinoId") Long cuentaDestinoId);

    @Query("SELECT COUNT(t) > 0 FROM TransaccionEntity t WHERE t.cuenta_origen_id = :cuentaOrigenId")
    boolean existsByCuentaOrigenId(@Param("cuentaOrigenId") Long cuentaOrigenId);

    @Query("SELECT COUNT(t) > 0 FROM TransaccionEntity t WHERE t.cuenta_destino_id = :cuentaDestinoId")
    boolean existsByCuentaDestinoId(@Param("cuentaDestinoId") Long cuentaDestinoId);

    @Query("SELECT COALESCE(SUM(t.monto), 0) FROM TransaccionEntity t WHERE t.cuenta_origen_id = :cuentaOrigen AND t.tipo in ('DEPOSITO', 'TRANSFERENCIA') AND t.estado = 'COMPLETADA'")
    BigDecimal totalIngresoByCuentaOrigen(@Param("cuentaOrigen") String cuentaOrigen);

    @Query("SELECT COALESCE(SUM(t.monto), 0) FROM TransaccionEntity t WHERE t.cuenta_origen_id = :cuentaOrigen AND t.tipo in ('RETIRO') AND t.estado = 'COMPLETADA'")
    BigDecimal totalRetiroByCuentaOrigen(@Param("cuentaOrigen") String cuentaOrigen);
}
