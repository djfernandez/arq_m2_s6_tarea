package pe.edu.dfernandez.hexagonal.app.application.usecase.Cliente;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.FindClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.output.ClienteRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.domain.exception.ClienteNotFoundException;
import pe.edu.dfernandez.hexagonal.app.domain.exception.InvalidClienteDataException;
import pe.edu.dfernandez.hexagonal.app.domain.model.Cliente;

@RequiredArgsConstructor
@Transactional
@Slf4j
public class FindClienteUseCaseImpl implements FindClienteUseCase {
    
    private final ClienteRepositoryPort clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public Cliente findClienteById(Long Id) {
        if (Id == null) {
            log.error("El ID del cliente no puede ser nulo");
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo");
        }

        return clienteRepository.findById(Id)
                .orElseThrow(() -> {
                    log.error("El cliente con ID {} no existe", Id);
                    return new ClienteNotFoundException(Id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAllClientes() {
        log.info("Buscando todos los clientes");
        List<Cliente> clientes = clienteRepository.findAll();
        log.info("Se encontraron {} clientes", clientes.size());
        return clientes;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findClientesByNombre(String nombre) {
        log.info("Buscando clientes por nombre: {}", nombre);

        if (nombre == null || nombre.isEmpty()) {
            log.error("El nombre del cliente no puede ser nulo o vacío");
            throw new InvalidClienteDataException("El nombre del cliente no puede ser nulo o vacío");
        }

        List<Cliente> clientes = clienteRepository.findByNombreContaining(nombre);
        log.info("Se encontraron {} clientes con el nombre {}", clientes.size(), nombre);
        return clientes;
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findClienteByNombreIgnoreCase(String nombre) {
        log.info("Buscando cliente por nombre (ignorar mayúsculas): {}", nombre);

        if (nombre == null || nombre.isEmpty()) {
            log.error("El nombre del cliente no puede ser nulo o vacío");
            throw new InvalidClienteDataException("El nombre del cliente no puede ser nulo o vacío");
        }
        return clienteRepository.findByNombreIgnoreCase(nombre);                
    }
    
}
