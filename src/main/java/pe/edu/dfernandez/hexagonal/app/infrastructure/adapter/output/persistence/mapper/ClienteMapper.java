package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import pe.edu.dfernandez.hexagonal.app.domain.model.Cliente;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.ClienteRequest;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.ClienteResponse;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.entity.ClienteEntity;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "documento", source = "documento")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "fecha_creacion", source = "fechaCreacion")
    @Mapping(target = "fecha_actualizacion", source = "fechaActualizacion")
    Cliente toDomain(ClienteEntity entity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "documento", source = "documento")
    Cliente toDomain(ClienteRequest request);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "documento", source = "documento")
    // @Mapping(target = "estado", source = "estado")
    // @Mapping(target = "fechaCreacion", source = "fecha_creacion")
    // @Mapping(target = "fechaActualizacion", source = "fecha_actualizacion")
    ClienteEntity toEntity(Cliente domain);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "documento", source = "documento")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "fechaCreacion", source = "fecha_creacion")
    @Mapping(target = "fechaActualizacion", source = "fecha_actualizacion")
    ClienteResponse toResponse(Cliente domain);

    List<Cliente> toDomain(List<ClienteEntity> entities);

    List<ClienteResponse> toResponse(List<Cliente> domains);
}
