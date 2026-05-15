package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.CreateClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.DeleteClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.FindClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.UpdateClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.domain.exception.ClienteNotFoundException;
import pe.edu.dfernandez.hexagonal.app.domain.exception.InvalidClienteDataException;
import pe.edu.dfernandez.hexagonal.app.domain.model.Cliente;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.ClienteRequest;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.ClienteResponse;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.mapper.ClienteMapper;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final CreateClienteUseCase createClienteUseCase;
    private final FindClienteUseCase findClienteUseCase;
    private final UpdateClienteUseCase updateClienteUseCase;
    private final DeleteClienteUseCase deleteClienteUseCase;

    private final ClienteMapper clienteMapper;

    @PostMapping
    public ResponseEntity<ClienteResponse> createCliente(@RequestBody ClienteRequest request) {
        log.info("Recibida solicitud para crear cliente: {}", request);
        try {
            Cliente clienteDomain = clienteMapper.toDomain(request);
            Cliente createdCliente = createClienteUseCase.execute(clienteDomain);

            if (createdCliente == null) {
                log.error("Error al crear cliente: Cliente creado es null");
                return ResponseEntity.badRequest().build();
            }

            log.info("Cliente creado exitosamente: {}", createdCliente.getId());

            var response = clienteMapper.toResponse(createdCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (InvalidClienteDataException e) {
            log.error("Datos de cliente inválidos: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            log.error("Error al crear cliente", e);
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getCliente(@PathVariable Long id) {
        log.info("Recibida solicitud para obtener cliente con ID: {}", id);
        try {
            Cliente cliente = findClienteUseCase.findClienteById(id);
            if (cliente == null) {
                log.warn("Cliente con ID {} no encontrado", id);
                return ResponseEntity.notFound().build();
            }
            log.info("Cliente encontrado: {}", cliente);
            ClienteResponse response = clienteMapper.toResponse(cliente);
            return ResponseEntity.ok(response);
        } catch (InvalidClienteDataException e) {
            log.error("Datos de cliente inválidos: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            log.error("Error al obtener cliente", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> getAllClientes() {
        log.info("Recibida solicitud para obtener todos los clientes");
        try {            
            List<Cliente> clientes = findClienteUseCase.findAllClientes();
            log.info(clientes.toString());
            List<ClienteResponse> response = this.clienteMapper.toResponse(clientes);
            log.info("Clientes encontrados: {}", clientes.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al obtener clientes", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClienteResponse>> searchClientesByNombre(@RequestParam String nombre) {
        log.info("Recibida solicitud para buscar clientes por nombre: {}", nombre);
        try {                
            List<Cliente> clientes = findClienteUseCase.findClientesByNombre(nombre);
            List<ClienteResponse> response = clienteMapper.toResponse(clientes);
            log.info("Clientes encontrados con nombre '{}': {}", nombre, clientes.size());
            return ResponseEntity.ok(response);
        } catch (InvalidClienteDataException e) {
            log.error("Datos de cliente inválidos: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            log.error("Error al buscar clientes por nombre", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> updateCliente(@PathVariable Long id, @RequestBody ClienteRequest request) {
        log.info("Recibida solicitud para actualizar cliente con ID: {}", id);
        try {            
            Cliente clienteDomain = clienteMapper.toDomain(request);
            Cliente updatedCliente = updateClienteUseCase.execute(id, clienteDomain);

            if (updatedCliente == null) {
                log.error("Error al actualizar cliente: Cliente actualizado es null");
                return ResponseEntity.badRequest().build();
            }

            log.info("Cliente actualizado exitosamente: {}", updatedCliente.getId());

            var response = clienteMapper.toResponse(updatedCliente);
            return ResponseEntity.ok(response);

        } catch (InvalidClienteDataException e) {
            log.error("Datos de cliente inválidos: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (ClienteNotFoundException e) {
            log.error("Cliente no encontrado: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error al actualizar cliente", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        log.info("Recibida solicitud para eliminar cliente con ID: {}", id);
        try {            
            deleteClienteUseCase.execute(id);
            log.info("Cliente con ID {} eliminado exitosamente", id);
            return ResponseEntity.noContent().build();
        } catch (InvalidClienteDataException e) {
            log.error("Datos de cliente inválidos: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (ClienteNotFoundException e) {
            log.error("Cliente no encontrado: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error al eliminar cliente", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
