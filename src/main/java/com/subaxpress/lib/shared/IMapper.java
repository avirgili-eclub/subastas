package com.subaxpress.lib.shared;

import org.mapstruct.*;

/**
 * Interfase base para mapeado entre DTOs y DAOs.
 *
 * @param <T> entidad
 * @param <R> DTO
 */
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IMapper<T, R> {

    /**
     * Mapear DTO a entidad.
     *
     * @param dto DTO a mapear
     * @return entidad mapeada desde DTO
     */
    T toEntity(R dto);

    /**
     * Mapear entidad a DTO.
     *
     * @param entity entidad a mapear
     * @return DTO mapeado desde entidad
     */
    R toDto(T entity);

    /**
     * Mapear DTO a entidad ignorando campos nulos.
     *
     * @param dto    DTO a mapear
     * @param entity entidad mapeada desde DTO
     * @return entidad mapeada desde DTO
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    T partialUpdate(R dto, @MappingTarget T entity);
}
