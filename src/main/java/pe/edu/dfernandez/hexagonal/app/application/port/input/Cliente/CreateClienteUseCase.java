package pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente;

import pe.edu.dfernandez.hexagonal.app.domain.model.Cliente;

public interface CreateClienteUseCase {
    Cliente execute(Cliente cliente);
}
