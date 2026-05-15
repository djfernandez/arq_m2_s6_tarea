package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.output.TransaccionRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.domain.model.Transaccion;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.mapper.TransaccionMapper;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TransaccionRepositoryAdapter implements TransaccionRepositoryPort {

    private final TransaccionJpaRepository transaccionJpaRepository;
    private final TransaccionMapper transaccionMapper;

    @Override
    public Transaccion save(Transaccion transaccion) {
        var entity = transaccionMapper.toEntity(transaccion);
        var savedEntity = transaccionJpaRepository.save(entity);
        return transaccionMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Transaccion> findById(Long id) {
        return transaccionJpaRepository.findById(id)
                .map(transaccionMapper::toDomain);
    }

    @Override
    public List<Transaccion> findAll() {
        return transaccionMapper.toDomain(transaccionJpaRepository.findAll());
    }

    @Override
    public List<Transaccion> findByCuentaOrigenId(Long cuentaOrigenId) {
        return transaccionMapper.toDomain(transaccionJpaRepository.findByCuentaOrigenId(cuentaOrigenId));
    }

    @Override
    public List<Transaccion> findByCuentaDestinoId(Long cuentaDestinoId) {
        return transaccionMapper.toDomain(transaccionJpaRepository.findByCuentaDestinoId(cuentaDestinoId));
    }

    @Override
    public void deleteById(Long id) {
        transaccionJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return transaccionJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean existsByCuentaOrigenId(Long cuentaOrigenId) {
        return transaccionJpaRepository.existsByCuentaOrigenId(cuentaOrigenId);
    }

    @Override
    public boolean existsByCuentaDestinoId(Long cuentaDestinoId) {
        return transaccionJpaRepository.existsByCuentaDestinoId(cuentaDestinoId);
    }

    @Override
    public BigDecimal totalIngresoByCuentaOrigen(String cuentaOrigen) {
        return transaccionJpaRepository.totalIngresoByCuentaOrigen(cuentaOrigen);
    }

    @Override
    public BigDecimal totalRetiroByCuentaOrigen(String cuentaOrigen) {
        return transaccionJpaRepository.totalRetiroByCuentaOrigen(cuentaOrigen);
    }
}
