package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.output.CuentaRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.domain.model.Cuenta;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.mapper.CuentaMapper;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CuentaRepositoryAdapter implements CuentaRepositoryPort {

    private final CuentaJpaRepository cuentaJpaRepository;
    private final CuentaMapper cuentaMapper;

    @Override
    public Cuenta save(Cuenta cuenta) {
        var cuentaEntity = cuentaMapper.toEntity(cuenta);
        var savedEntity = cuentaJpaRepository.save(cuentaEntity);
        return cuentaMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Cuenta> findById(Long id) {
        return cuentaJpaRepository.findById(id)
                .map(cuentaMapper::toDomain);
    }

    @Override
    public List<Cuenta> findAll() {
        var cuentaEntities = cuentaJpaRepository.findAll();
        return cuentaMapper.toDomain(cuentaEntities);
    }

    @Override
    public List<Cuenta> findByClienteId(Long clienteId) {
        var cuentaEntities = cuentaJpaRepository.findByClienteId(clienteId);
        return cuentaMapper.toDomain(cuentaEntities);
    }

    @Override
    public Optional<Cuenta> findByNumeroCuenta(String numeroCuenta) {
        return cuentaJpaRepository.findByNumeroCuenta(numeroCuenta)
                .map(cuentaMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        cuentaJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return cuentaJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByNumeroCuenta(String numeroCuenta) {
        return cuentaJpaRepository.existsByNumeroCuenta(numeroCuenta);
    }
    
    @Override
    public Long maxId() {
        return cuentaJpaRepository.maxId();
    }
}
