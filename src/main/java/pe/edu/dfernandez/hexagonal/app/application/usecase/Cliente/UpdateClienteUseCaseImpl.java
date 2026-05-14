package pe.edu.dfernandez.hexagonal.app.application.usecase.Cliente;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.UpdateClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.output.ClienteRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.domain.exception.ClienteNotFoundException;
import pe.edu.dfernandez.hexagonal.app.domain.exception.InvalidClienteDataException;
import pe.edu.dfernandez.hexagonal.app.domain.model.Cliente;

@RequiredArgsConstructor
@Transactional
@Slf4j
public class UpdateClienteUseCaseImpl implements UpdateClienteUseCase {

    private final ClienteRepositoryPort clienteRepository;

    @Override
    public Cliente execute(Long Id, Cliente cliente) {

        if (Id == null || Id <= 0) {
            log.error("El ID del cliente no puede ser nulo o menor o igual a cero");
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo o menor o igual a cero");
        }

        if (cliente == null) {
            log.error("El cliente no puede ser nulo");
            throw new InvalidClienteDataException("El cliente no puede ser nulo");
        }

        cliente.validateClienteInput();

        Cliente existingCliente = clienteRepository.findById(Id)
                .orElseThrow(() -> {
                    log.error("El cliente con ID {} no existe", Id);
                    return new ClienteNotFoundException(Id);
                });

        // log.info(existingCliente.toString());
        if (!existingCliente.getEmail().equals(cliente.getEmail())
                && clienteRepository.existsByEmail(cliente.getEmail())) {
            log.error("El email {} ya está registrado", cliente.getEmail());
            throw new InvalidClienteDataException("El email ya está registrado");
        }

        existingCliente.updateDetails(cliente.getNombre(), cliente.getEmail(), cliente.getDocumento());

        return clienteRepository.save(existingCliente);

    }

}
