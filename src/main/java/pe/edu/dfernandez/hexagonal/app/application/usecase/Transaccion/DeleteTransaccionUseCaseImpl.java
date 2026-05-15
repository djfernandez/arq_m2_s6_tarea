package pe.edu.dfernandez.hexagonal.app.application.usecase.Transaccion;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Transaccion.DeleteTransaccionUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.output.TransaccionRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.domain.exception.ClienteNotFoundException;

@RequiredArgsConstructor
@Transactional
@Slf4j
public class DeleteTransaccionUseCaseImpl implements DeleteTransaccionUseCase {

    private final TransaccionRepositoryPort transaccionRepository;

    @Override
    public void execute(Long id) {
        if (id == null) {
            log.error("El ID de la transacción no puede ser nulo");
            throw new IllegalArgumentException("El ID de la transacción no puede ser nulo");
        }

        if (!transaccionRepository.existsById(id)) {
            log.error("La transacción con ID {} no existe", id);
            throw new ClienteNotFoundException(id);
        }

        transaccionRepository.deleteById(id);
        log.info("La transacción con ID {} ha sido eliminada", id);
        
    }
    
}
