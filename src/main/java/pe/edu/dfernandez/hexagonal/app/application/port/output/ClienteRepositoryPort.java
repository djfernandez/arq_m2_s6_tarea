package pe.edu.dfernandez.hexagonal.app.application.port.output;

import java.util.List;
import java.util.Optional;

import pe.edu.dfernandez.hexagonal.app.domain.model.Cliente;

public interface ClienteRepositoryPort {
    
    Cliente save(Cliente cliente);

    Optional<Cliente> findById(Long id);

    List<Cliente> findAll();

    List<Cliente> findByNombreContaining(String nombre);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

    boolean existsByDocumento(String documento);
}
