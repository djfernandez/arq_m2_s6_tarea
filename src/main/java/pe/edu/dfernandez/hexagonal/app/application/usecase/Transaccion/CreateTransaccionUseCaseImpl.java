package pe.edu.dfernandez.hexagonal.app.application.usecase.Transaccion;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Transaccion.CreateTransaccionUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.output.TransaccionRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.domain.exception.InvalidClienteDataException;
import pe.edu.dfernandez.hexagonal.app.domain.model.Transaccion;

@RequiredArgsConstructor
@Transactional
@Slf4j
public class CreateTransaccionUseCaseImpl implements CreateTransaccionUseCase {

    private final TransaccionRepositoryPort transaccionRepository;

    @Override
    public Transaccion execute(Transaccion transaccion) {
      if (transaccion == null) {
            log.error("La transacción no puede ser nula");
            throw new InvalidClienteDataException("La transacción no puede ser nula");
        }

        transaccion.validateTransaccionInput();

        // Guardar la transacción
        return transaccionRepository.save(transaccion);
    }
    
}
