package pe.edu.dfernandez.hexagonal.app.application.port.input.Transaccion;

import pe.edu.dfernandez.hexagonal.app.domain.model.Transaccion;

public interface CreateTransaccionUseCase {
    Transaccion execute(Transaccion transaccion);
}
