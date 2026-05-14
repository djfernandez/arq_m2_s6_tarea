package pe.edu.dfernandez.hexagonal.app.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.CreateClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.DeleteClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.FindClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.UpdateClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.output.ClienteRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.application.usecase.Cliente.CreateClienteUseCaseImpl;
import pe.edu.dfernandez.hexagonal.app.application.usecase.Cliente.DeleteClienteUseCaseImpl;
import pe.edu.dfernandez.hexagonal.app.application.usecase.Cliente.FindClienteUseCaseImpl;
import pe.edu.dfernandez.hexagonal.app.application.usecase.Cliente.UpdateClienteUseCaseImpl;

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

}
