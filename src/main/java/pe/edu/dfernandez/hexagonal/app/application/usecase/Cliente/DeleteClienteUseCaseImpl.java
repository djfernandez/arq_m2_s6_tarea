package pe.edu.dfernandez.hexagonal.app.application.usecase.Cliente;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.DeleteClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.output.ClienteRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.domain.exception.ClienteNotFoundException;

@RequiredArgsConstructor
@Transactional
@Slf4j
public class DeleteClienteUseCaseImpl implements DeleteClienteUseCase {
    
    private final ClienteRepositoryPort clienteRepository;
    
    @Override
    public void execute(Long Id) {
        
        if (Id == null) {
            log.error("El ID del cliente no puede ser nulo");
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo");
        }

        if (!clienteRepository.existsById(Id)) {
            log.error("El cliente con ID {} no existe", Id);
            throw new ClienteNotFoundException(Id);
        }

        clienteRepository.deleteById(Id);
        log.info("El cliente con ID {} ha sido eliminado", Id);

    }
    
}
