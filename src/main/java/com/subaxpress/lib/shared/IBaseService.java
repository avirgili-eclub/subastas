package com.subaxpress.lib.shared;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * ServicioRequest base.
 *
 * @param <T>  entidad sobre la que se trabajará
 * @param <R>  DTO de tipo clase o record
 * @param <ID> tipo de dato del ID de la entidad
 */
public interface IBaseService<T extends BaseEntity, R, ID> {

    List<R> getAll();

    Optional<R> getById(ID id);

    Optional<R> getByUuid(UUID uuid);

    Page<T> findAll(Specification<T> spec, Pageable pageable);
    List<T> findAll();

    Optional<T> findById(ID id);

    Optional<T> findByUuid(UUID uuid);

    T create(T entity) throws ValidationException;

    R createFromDto(R record) throws ValidationException;

    T update(T entity) throws ValidationException;

    R updateFromDto(UUID id, R record) throws ValidationException;

    void delete(ID id) throws ValidationException;

    void deleteByUuid(UUID uuid) throws ValidationException;

    void remove(ID id) throws ValidationException;

    void removeByUuid(UUID uuid) throws ValidationException;

    String getLoggedInUser();
}
