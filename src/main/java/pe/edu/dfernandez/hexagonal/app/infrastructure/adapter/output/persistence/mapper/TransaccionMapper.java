package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import pe.edu.dfernandez.hexagonal.app.domain.model.Transaccion;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.TransaccionRequest;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.TransaccionResponse;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.entity.TransaccionEntity;

public interface TransaccionMapper {
 
    TransaccionMapper INSTANCE = Mappers.getMapper(TransaccionMapper.class);

    @BeanMapping(ignoreByDefault = true)
    Transaccion toDomain(TransaccionEntity entity);

    @Mapping(target = "cuenta_origen_id", source = "cuenta_origen_id")
    @Mapping(target = "cuenta_destino_id", source = "cuenta_destino_id")
    @Mapping(target = "monto", source = "monto")
    @Mapping(target = "tipo", source = "tipo")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "estado", source = "estado")
    Transaccion toEntity(TransaccionRequest domain);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cuenta_origen_id", source = "cuenta_origen_id")
    @Mapping(target = "cuenta_destino_id", source = "cuenta_destino_id")
    @Mapping(target = "monto", source = "monto")
    @Mapping(target = "tipo", source = "tipo")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "fecha_creacion", source = "fecha_creacion")
    @Mapping(target = "fecha_actualizacion", source = "fecha_actualizacion")
    TransaccionEntity toEntity(Transaccion domain);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cuenta_origen_id", source = "cuenta_origen_id")
    @Mapping(target = "cuenta_destino_id", source = "cuenta_destino_id")
    @Mapping(target = "monto", source = "monto")
    @Mapping(target = "tipo", source = "tipo")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "fecha_creacion", source = "fecha_creacion")
    @Mapping(target = "fecha_actualizacion", source = "fecha_actualizacion")
    TransaccionResponse toResponse(Transaccion domain);

    List<Transaccion> toDomain(List<TransaccionEntity> entities);

    List<TransaccionResponse> toResponse(List<Transaccion> domains);
}
