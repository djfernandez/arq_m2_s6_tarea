package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import pe.edu.dfernandez.hexagonal.app.domain.model.Cuenta;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.CuentaRequest;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.CuentaResponse;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.entity.CuentaEntity;

@Mapper(componentModel = "spring")
public interface CuentaMapper {
    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

    @BeanMapping(ignoreByDefault = true)
    Cuenta toDomain(CuentaEntity entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "cliente_id", source = "cliente_id")
    @Mapping(target = "numeroCuenta", source = "numeroCuenta")
    @Mapping(target = "saldo", source = "saldo")
    @Mapping(target = "estado", source = "estado")                                                            // automáticamente
    Cuenta toDomain(CuentaRequest request);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "numeroCuenta", source = "numeroCuenta")
    @Mapping(target = "saldo", source = "saldo")
    @Mapping(target = "estado", source = "estado")
    CuentaEntity toEntity(Cuenta domain);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "cliente_id", source = "cliente_id")
    @Mapping(target = "numeroCuenta", source = "numeroCuenta")
    @Mapping(target = "saldo", source = "saldo")
    @Mapping(target = "estado", source = "estado")
    CuentaResponse toResponse(Cuenta domain);

    List<Cuenta> toDomain(List<CuentaEntity> entities);

    List<CuentaResponse> toResponse(List<Cuenta> domains);
}
