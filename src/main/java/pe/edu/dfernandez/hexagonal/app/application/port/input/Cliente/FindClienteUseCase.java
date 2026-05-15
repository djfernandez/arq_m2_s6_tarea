package pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente;

import java.util.List;

import pe.edu.dfernandez.hexagonal.app.domain.model.Cliente;

public interface FindClienteUseCase {
    Cliente findClienteById(Long id);
    List<Cliente> findAllClientes();
    List<Cliente> findClientesByNombre(String nombre);
    Cliente findClienteByNombreIgnoreCase(String nombre);
}
