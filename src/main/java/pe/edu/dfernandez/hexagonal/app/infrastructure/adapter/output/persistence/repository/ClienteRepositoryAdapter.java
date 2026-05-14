package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.output.ClienteRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.domain.model.Cliente;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.mapper.ClienteMapper;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {

    private final ClienteJpaRepository clienteJpaRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public Cliente save(Cliente cliente) {
        log.info("Guardando cliente: {}", cliente);
        var clienteEntity = clienteMapper.toEntity(cliente);
        var savedEntity = clienteJpaRepository.save(clienteEntity);
        log.info("Cliente guardado con ID: {}", savedEntity.getId());
        return clienteMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        log.info("Buscando cliente por ID: {}", id);
        return clienteJpaRepository.findById(id)
                .map(clienteMapper::toDomain);
        // if (clienteEntityOpt.isPresent()) {
        //     log.info("Cliente encontrado: {}", clienteEntityOpt.get());
        //     return Optional.of(clienteMapper.toDomain(clienteEntityOpt.get()));
        // } else {
        //     log.warn("Cliente con ID {} no encontrado", id);
        //     return Optional.empty();
        // }
    }

    @Override
    public List<Cliente> findAll() {
        log.info("Buscando todos los clientes");
        var clienteEntities = clienteJpaRepository.findAll();
        return clienteMapper.toDomain(clienteEntities);
    }

    @Override
    public List<Cliente> findByNombreContaining(String nombre) {
        log.info("Buscando clientes por nombre: {}", nombre);
        var clienteEntities = clienteJpaRepository.findByNombreContainingIgnoreCase(nombre);
        return clienteMapper.toDomain(clienteEntities);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Eliminando cliente por ID: {}", id);
        clienteJpaRepository.deleteById(id);
        log.info("Cliente con ID {} eliminado", id);
    }

    @Override
    public boolean existsById(Long id) {
        log.info("Verificando existencia de cliente por ID: {}", id);
        return clienteJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        log.info("Verificando existencia de cliente por email: {}", email);
        return clienteJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByDocumento(String documento) {
        log.info("Verificando existencia de cliente por documento: {}", documento);
        return clienteJpaRepository.existsByDocumento(documento);
    }
    
}
