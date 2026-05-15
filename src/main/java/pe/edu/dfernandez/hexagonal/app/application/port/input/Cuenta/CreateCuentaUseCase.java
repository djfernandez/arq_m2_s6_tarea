package pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta;

import pe.edu.dfernandez.hexagonal.app.domain.model.Cuenta;

public interface CreateCuentaUseCase {
    Cuenta execute(Cuenta cuenta);
}
