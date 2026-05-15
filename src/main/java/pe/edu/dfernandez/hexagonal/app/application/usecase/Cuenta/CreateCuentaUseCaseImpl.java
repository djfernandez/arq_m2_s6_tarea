package pe.edu.dfernandez.hexagonal.app.application.usecase.Cuenta;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta.CreateCuentaUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.output.CuentaRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.domain.exception.InvalidClienteDataException;
import pe.edu.dfernandez.hexagonal.app.domain.model.Cuenta;

@RequiredArgsConstructor
@Transactional
@Slf4j
public class CreateCuentaUseCaseImpl implements CreateCuentaUseCase {

    private final CuentaRepositoryPort cuentaRepository;

    @Override
    public Cuenta execute(Cuenta cuenta) {
        if (cuenta == null) {
            log.error("La cuenta no puede ser nula");
            throw new InvalidClienteDataException("La cuenta no puede ser nula");
        }

        cuenta.validateCuentaInput();

        // Validar que el numero de cuenta no exista
        if (cuentaRepository.existsByNumeroCuenta(cuenta.getNumeroCuenta())) {
            log.error("El Numero de cuenta {} ya está registrado", cuenta.getNumeroCuenta());
            throw new InvalidClienteDataException("El numero de cuenta ya está registrado");
        }

        // Guardar la cuenta
        return cuentaRepository.save(cuenta);
    }
}
