package pe.edu.dfernandez.hexagonal.app.application.usecase.Cliente;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.CreateClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.output.ClienteRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.domain.exception.InvalidClienteDataException;
import pe.edu.dfernandez.hexagonal.app.domain.model.Cliente;

@RequiredArgsConstructor
@Transactional
@Slf4j
public class CreateClienteUseCaseImpl implements CreateClienteUseCase {

    private final ClienteRepositoryPort clienteRepository;

    @Override
    public Cliente execute(Cliente cliente) {

        if (cliente == null) {
            log.error("El cliente no puede ser nulo");
            throw new InvalidClienteDataException("El cliente no puede ser nulo");
        }

        cliente.validateClienteInput();

        // Validar que el email no exista
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            log.error("El email {} ya está registrado", cliente.getEmail());
            throw new InvalidClienteDataException("El email ya está registrado");
        }

        // Validar que el documento no exista
        if (clienteRepository.existsByDocumento(cliente.getDocumento())) {
            log.error("El documento {} ya está registrado", cliente.getDocumento());
            throw new InvalidClienteDataException("El documento ya está registrado");
        }

        // Guardar el cliente
        return clienteRepository.save(cliente);
    }
    
}
