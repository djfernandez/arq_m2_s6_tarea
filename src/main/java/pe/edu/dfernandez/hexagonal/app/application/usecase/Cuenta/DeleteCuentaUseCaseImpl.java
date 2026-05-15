package pe.edu.dfernandez.hexagonal.app.application.usecase.Cuenta;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta.DeleteCuentaUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.output.CuentaRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.domain.exception.ClienteNotFoundException;

@RequiredArgsConstructor
@Transactional
@Slf4j
public class DeleteCuentaUseCaseImpl implements DeleteCuentaUseCase {

    private final CuentaRepositoryPort cuentaRepository;

    @Override
    public void execute(Long Id) {
        if (Id == null) {
            log.error("El ID de la cuenta no puede ser nulo");
            throw new IllegalArgumentException("El ID de la cuenta no puede ser nulo");
        }

        if (!cuentaRepository.existsById(Id)) {
            log.error("La cuenta con ID {} no existe", Id);
            throw new ClienteNotFoundException(Id);
        }

        cuentaRepository.deleteById(Id);
        log.info("La cuenta con ID {} ha sido eliminada", Id);
        
    }
    
}
