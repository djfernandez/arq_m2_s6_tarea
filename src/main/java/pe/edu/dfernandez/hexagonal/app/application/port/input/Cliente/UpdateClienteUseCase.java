package pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente;

import pe.edu.dfernandez.hexagonal.app.domain.model.Cliente;

public interface UpdateClienteUseCase {
    Cliente execute(Long Id, Cliente cliente);
}
