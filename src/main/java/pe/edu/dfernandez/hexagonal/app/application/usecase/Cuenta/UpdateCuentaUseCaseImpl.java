package pe.edu.dfernandez.hexagonal.app.application.usecase.Cuenta;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta.UpdateCuentaUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.output.CuentaRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.domain.exception.ClienteNotFoundException;
import pe.edu.dfernandez.hexagonal.app.domain.exception.InvalidClienteDataException;
import pe.edu.dfernandez.hexagonal.app.domain.model.Cuenta;

@RequiredArgsConstructor
@Transactional
@Slf4j
public class UpdateCuentaUseCaseImpl implements UpdateCuentaUseCase {

    private final CuentaRepositoryPort cuentaRepository;

    @Override
    public Cuenta execute(Long id, Cuenta cuenta) {
        if (id == null || id <= 0) {
            log.error("El ID de la cuenta no puede ser nulo o menor o igual a cero");
            throw new IllegalArgumentException("El ID de la cuenta no puede ser nulo o menor o igual a cero");
        }

        if (cuenta == null) {
            log.error("La cuenta no puede ser nula");
            throw new InvalidClienteDataException("La cuenta no puede ser nula");
        }

        cuenta.validateCuentaInput();

        Cuenta existingCuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("La cuenta con ID {} no existe", id);
                    return new ClienteNotFoundException(id);
                });

        // log.info(existingCuenta.toString());
        if (!existingCuenta.getNumeroCuenta().equals(cuenta.getNumeroCuenta())
                && cuentaRepository.existsByNumeroCuenta(cuenta.getNumeroCuenta())) {
            log.error("El número de cuenta {} ya está registrado", cuenta.getNumeroCuenta());
            throw new InvalidClienteDataException("El número de cuenta ya está registrado");
        }

        existingCuenta.updateDetails(cuenta.getCliente_id(), cuenta.getNumeroCuenta(), cuenta.getSaldo());

        return cuentaRepository.save(existingCuenta);
    }

}
