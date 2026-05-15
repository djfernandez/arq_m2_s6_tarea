package pe.edu.dfernandez.hexagonal.app.application.port.input.Transaccion;

import pe.edu.dfernandez.hexagonal.app.domain.model.Transaccion;

public interface UpdateTransaccionUseCase {
    Transaccion execute(Long id, Transaccion transaccion);
}
