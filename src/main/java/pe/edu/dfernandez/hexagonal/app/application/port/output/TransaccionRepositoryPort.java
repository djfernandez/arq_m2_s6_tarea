package pe.edu.dfernandez.hexagonal.app.application.port.output;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import pe.edu.dfernandez.hexagonal.app.domain.model.Transaccion;

public interface TransaccionRepositoryPort {
    Transaccion save(Transaccion transaccion);

    Optional<Transaccion> findById(Long id);

    List<Transaccion> findAll();

    List<Transaccion> findByCuentaOrigenId(Long cuentaOrigenId);

    List<Transaccion> findByCuentaDestinoId(Long cuentaDestinoId);

    BigDecimal totalIngresoByCuentaOrigen(String cuentaOrigen);

    BigDecimal totalRetiroByCuentaOrigen(String cuentaOrigen);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

    boolean existsByCuentaOrigenId(Long cuentaOrigenId);

    boolean existsByCuentaDestinoId(Long cuentaDestinoId);
}
