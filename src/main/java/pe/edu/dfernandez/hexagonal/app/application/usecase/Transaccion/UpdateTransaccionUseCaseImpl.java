package pe.edu.dfernandez.hexagonal.app.application.usecase.Transaccion;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Transaccion.UpdateTransaccionUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.output.TransaccionRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.domain.exception.ClienteNotFoundException;
import pe.edu.dfernandez.hexagonal.app.domain.exception.InvalidClienteDataException;
import pe.edu.dfernandez.hexagonal.app.domain.model.Transaccion;

@RequiredArgsConstructor
@Transactional
@Slf4j
public class UpdateTransaccionUseCaseImpl implements UpdateTransaccionUseCase {

    private final TransaccionRepositoryPort transaccionRepository;

    @Override
    public Transaccion execute(Long id, Transaccion transaccion) {
        if (id == null || id <= 0) {
            log.error("El ID de la cuenta no puede ser nulo o menor o igual a cero");
            throw new IllegalArgumentException("El ID de la cuenta no puede ser nulo o menor o igual a cero");
        }

        if (transaccion == null) {
            log.error("La transacción no puede ser nula");
            throw new InvalidClienteDataException("La transacción no puede ser nula");
        }

        transaccion.validateTransaccionInput();

        Transaccion existingTransaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("La transacción con ID {} no existe", id);
                    return new ClienteNotFoundException(id);
                });

        // log.info(existingTransaccion.toString());
        if (!existingTransaccion.getCuenta_origen_id().equals(transaccion.getCuenta_origen_id())
                && transaccionRepository.existsByCuentaOrigenId(transaccion.getCuenta_origen_id())) {
            log.error("El número de cuenta {} ya está registrado", transaccion.getCuenta_origen_id());
            throw new InvalidClienteDataException("El número de cuenta ya está registrado");
        }

        existingTransaccion.updateDetails(
                transaccion.getCuenta_origen_id(), transaccion.getCuenta_destino_id(),
                transaccion.getMonto(), transaccion.getComision(),
                transaccion.getTipo(), transaccion.getDescripcion());

        return transaccionRepository.save(existingTransaccion);
    }

}
