package com.subaxpress.lib.shared;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio base.
 *
 * @param <T>  entidad
 * @param <ID> tipo de dato del ID de la entidad
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * Retorna la entidad dado su UUID
     *
     * @param uuid UUID de la entidad a buscar
     * @return entidad dado su UUID
     */
    Optional<T> findByUuid(UUID uuid);
}
