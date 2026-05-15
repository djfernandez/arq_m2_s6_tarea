package pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta;

import java.util.List;

import pe.edu.dfernandez.hexagonal.app.domain.model.Cuenta;

public interface FindCuentaUseCase {
    Cuenta findCuentaById(Long id);

    List<Cuenta> findAllCuentas();

    Cuenta findCuentasByNumeroCuenta(String numeroCuenta);

    String generarNumeroCuenta();

    List<Cuenta> findCuentasByClienteId(Long clienteId);

}
