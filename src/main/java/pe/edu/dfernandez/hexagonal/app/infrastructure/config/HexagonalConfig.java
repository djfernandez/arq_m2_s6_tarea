package pe.edu.dfernandez.hexagonal.app.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.CreateClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.DeleteClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.FindClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.UpdateClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta.CreateCuentaUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta.DeleteCuentaUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta.FindCuentaUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta.UpdateCuentaUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Transaccion.CreateTransaccionUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Transaccion.DeleteTransaccionUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Transaccion.FindTransaccionUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Transaccion.UpdateTransaccionUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.output.ClienteRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.application.port.output.CuentaRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.application.port.output.TransaccionRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.application.usecase.Cliente.CreateClienteUseCaseImpl;
import pe.edu.dfernandez.hexagonal.app.application.usecase.Cliente.DeleteClienteUseCaseImpl;
import pe.edu.dfernandez.hexagonal.app.application.usecase.Cliente.FindClienteUseCaseImpl;
import pe.edu.dfernandez.hexagonal.app.application.usecase.Cliente.UpdateClienteUseCaseImpl;
import pe.edu.dfernandez.hexagonal.app.application.usecase.Cuenta.CreateCuentaUseCaseImpl;
import pe.edu.dfernandez.hexagonal.app.application.usecase.Cuenta.DeleteCuentaUseCaseImpl;
import pe.edu.dfernandez.hexagonal.app.application.usecase.Cuenta.FindCuentaUseCaseImpl;
import pe.edu.dfernandez.hexagonal.app.application.usecase.Cuenta.UpdateCuentaUseCaseImpl;
import pe.edu.dfernandez.hexagonal.app.application.usecase.Transaccion.CreateTransaccionUseCaseImpl;
import pe.edu.dfernandez.hexagonal.app.application.usecase.Transaccion.DeleteTransaccionUseCaseImpl;
import pe.edu.dfernandez.hexagonal.app.application.usecase.Transaccion.FindTransaccionUseCaseImpl;
import pe.edu.dfernandez.hexagonal.app.application.usecase.Transaccion.UpdateTransaccionUseCaseImpl;

@Configuration
public class HexagonalConfig {

    @Bean
    CreateClienteUseCase createClienteUseCase(ClienteRepositoryPort clienteRepository) {
        return new CreateClienteUseCaseImpl(clienteRepository);
    }

    @Bean
    FindClienteUseCase findClienteUseCase(ClienteRepositoryPort clienteRepository) {
        return new FindClienteUseCaseImpl(clienteRepository);
    }

    @Bean
    UpdateClienteUseCase updateClienteUseCase(ClienteRepositoryPort clienteRepository) {
        return new UpdateClienteUseCaseImpl(clienteRepository);
    }

    @Bean
    DeleteClienteUseCase deleteClienteUseCase(ClienteRepositoryPort clienteRepository) {
        return new DeleteClienteUseCaseImpl(clienteRepository);
    }

    @Bean
    CreateCuentaUseCase createCuentaUseCase(CuentaRepositoryPort cuentaRepository) {
        return new CreateCuentaUseCaseImpl(cuentaRepository);
    }

    @Bean
    FindCuentaUseCase findCuentaUseCase(CuentaRepositoryPort cuentaRepository) {
        return new FindCuentaUseCaseImpl(cuentaRepository);
    }

    @Bean
    UpdateCuentaUseCase updateCuentaUseCase(CuentaRepositoryPort cuentaRepository) {
        return new UpdateCuentaUseCaseImpl(cuentaRepository);
    }

    @Bean
    DeleteCuentaUseCase deleteCuentaUseCase(CuentaRepositoryPort cuentaRepository) {
        return new DeleteCuentaUseCaseImpl(cuentaRepository);
    }

    @Bean
    CreateTransaccionUseCase createTransaccionUseCase(TransaccionRepositoryPort transaccionRepository) {
        return new CreateTransaccionUseCaseImpl(transaccionRepository);
    }

    @Bean
    FindTransaccionUseCase findTransaccionUseCase(TransaccionRepositoryPort transaccionRepository) {
        return new FindTransaccionUseCaseImpl(transaccionRepository);
    }

    @Bean
    UpdateTransaccionUseCase updateTransaccionUseCase(TransaccionRepositoryPort transaccionRepository) {
        return new UpdateTransaccionUseCaseImpl(transaccionRepository);
    }

    @Bean
    DeleteTransaccionUseCase deleteTransaccionUseCase(TransaccionRepositoryPort transaccionRepository) {
        return new DeleteTransaccionUseCaseImpl(transaccionRepository);
    }
}
