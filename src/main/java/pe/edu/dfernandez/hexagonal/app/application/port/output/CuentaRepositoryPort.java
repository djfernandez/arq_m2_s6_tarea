package pe.edu.dfernandez.hexagonal.app.application.port.output;

import java.util.List;
import java.util.Optional;

import pe.edu.dfernandez.hexagonal.app.domain.model.Cuenta;

public interface CuentaRepositoryPort {
    Cuenta save(Cuenta cuenta);

    Optional<Cuenta> findById(Long id);

    List<Cuenta> findAll();
    
    List<Cuenta> findByClienteId(Long clienteId);

    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByNumeroCuenta(String numeroCuenta);

    Long maxId();
}
