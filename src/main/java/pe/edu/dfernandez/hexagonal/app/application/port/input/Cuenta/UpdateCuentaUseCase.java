package pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta;

import pe.edu.dfernandez.hexagonal.app.domain.model.Cuenta;

public interface UpdateCuentaUseCase {
    Cuenta execute(Long id, Cuenta cuenta);
}
