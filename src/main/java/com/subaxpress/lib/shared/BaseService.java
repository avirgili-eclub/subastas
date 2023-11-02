package com.subaxpress.lib.shared;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
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
@Transactional
public abstract class BaseService<T extends BaseEntity, R, ID>
        implements IBaseService<T, R, ID> {

    /**
     * Repositorio principal asociado al servicio.
     */
    protected final BaseRepository<T, ID> repository;

    /**
     * Mapeado de DTO a entidad y viceversa.
     */
    protected final IMapper<T, R> mapper;

    /**
     * Se realiza la inyección de dependencias.
     *
     * @param repository repositorio principal asociado al servicio
     * @param mapper     mapeado de DTO a entidad y viceversa
     */
    protected BaseService(BaseRepository<T, ID> repository,
                          IMapper<T, R> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Retorna una lista con todas las entidades creadas.
     *
     * @return listado de entidades creadas
     */
    public List<R> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Retorna un DTO de la entidad dado su ID.
     *
     * @param id ID de la entidad a buscar
     * @return DTO de la entidad dado su ID
     */
    public Optional<R> getById(ID id) {
        return repository.findById(id)
                .map(mapper::toDto);
    }

    /**
     * Retorna un DTO de la entidad dado su UUID
     *
     * @param uuid UUID de la entidad a buscar
     * @return DTO de la entidad dado su UUID
     */
    public Optional<R> getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .map(mapper::toDto);
    }

    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    /**
     * Retorna una entidad dado su ID.
     *
     * @param id ID de la entidad a buscar
     * @return entidad dado su ID
     */
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    /**
     * Retorna una entidad dado su UUID
     *
     * @param uuid UUID de la entidad a buscar
     * @return entidad dado su UUID
     */
    public Optional<T> findByUuid(UUID uuid) {
        return repository.findByUuid(uuid);
    }

    /**
     * Crea una nueva entidad.
     *
     * @param entity entidad con los datos utilizados para su creación
     * @return entidad creada
     * @throws ValidationException error en la validación de datos
     */
    public T create(T entity) throws ValidationException {
        entity.setEliminado(false);
        beforeCreate(entity);
        setAuditFields(entity, TipoAuditoria.CREACION);
        T result = repository.saveAndFlush(entity);
        afterCreate(result);
        return result;
    }

    /**
     * Crea una nueva entidad tomando los datos contenidos en el DTO especificado.
     *
     * @param dto DTO con los datos a crear
     * @return entidad creada
     */
    public R createFromDto(R dto) throws ValidationException {
        T entity = mapper.toEntity(dto);
        entity.setEliminado(false);
        beforeCreate(entity);
        setAuditFields(entity, TipoAuditoria.CREACION);
        T result = repository.saveAndFlush(entity);
        afterCreate(result);
        return mapper.toDto(result);
    }

    /**
     * Actualiza una entidad dado su ID.
     *
     * @param entity entidad a actualizar
     * @return entidad actualizada
     * @throws ValidationException el objeto pasado no es válido
     */
    public T update(T entity) throws ValidationException {
        beforeUpdate(entity);
        setAuditFields(entity, TipoAuditoria.ACTUALIZACION);
        T result = repository.save(entity);
        afterUpdate(result);
        return result;
    }

    /**
     * Actualiza una entidad dado su ID tomando los datos contenidos en el DTO especificado.
     *
     * @param id  ID de la entidad a actualizar
     * @param dto DTO con los datos a actualizar
     * @return entidad actualizada
     * @throws DBExeption.NoData no existe una entidad con el ID especificado
     */
    public R updateFromDto(UUID id, R dto) throws ValidationException {
        T entity = findByUuid(id).orElseThrow(() ->
                new ValidationException("No existe el registro"));
        mapValues(entity, mapper.partialUpdate(dto, entity));
        beforeUpdate(entity);
        setAuditFields(entity, TipoAuditoria.ACTUALIZACION);
        T result = repository.save(entity);
        afterUpdate(result);
        return mapper.toDto(result);
    }

    /**
     * Marca un registro como eliminado (borrado lógico).
     *
     * @param id ID de la entidad
     */
    public void delete(ID id) throws ValidationException {
        T entity = findById(id).orElseThrow(() ->
                new ValidationException("No existe el registro"));
        entity.setEliminado(true);
        beforeDelete(entity);
        setAuditFields(entity, TipoAuditoria.ELIMINACION);
        repository.save(entity);
        afterDelete(entity);
    }

    public void deleteByUuid(UUID id) throws ValidationException {
        T entity = findByUuid(id).orElseThrow(() ->
                new ValidationException("No existe el registro"));
        entity.setEliminado(true);
        beforeDelete(entity);
        setAuditFields(entity, TipoAuditoria.ELIMINACION);
        repository.save(entity);
        afterDelete(entity);
    }

    /**
     * Elimina definitivamente un registro de la base de datos.
     *
     * @param id ID de la entidad
     */
    public void remove(ID id) {
        T entity = findById(id).orElseThrow(() ->
                new ValidationException("No existe el registro"));
        beforeDelete(entity);
        repository.delete(entity);
        afterDelete(entity);
    }

    public void removeByUuid(UUID id) {
        T entity = findByUuid(id).orElseThrow(() ->
                new ValidationException("No existe el registro"));
        beforeDelete(entity);
        repository.delete(entity);
        afterDelete(entity);
    }

    /**
     * Reglas de negocio y/o validaciones a ejecutarse antes de persistir la entidad T.
     *
     * @param entity entidad a crearse
     */
    public void beforeCreate(T entity) {
    }

    /**
     * Reglas de negocio y/o validaciones a ejecutarse antes de persistir la entidad T.
     *
     * @param entity entidad a actualizarse
     */
    public void beforeUpdate(T entity) {
    }

    /**
     * Reglas de negocio y/o validaciones a ejecutarse antes de persistir la entidad T.
     *
     * @param entity entidad a eliminarse
     */
    public void beforeDelete(T entity) {
    }

    /**
     * Reglas de negocio y/o validaciones a ejecutarse cuando se ha persistido la entidad T.
     *
     * @param entity entidad creada
     */
    public void afterCreate(T entity) {
    }

    /**
     * Reglas de negocio y/o validaciones a ejecutarse cuando se ha persistido la entidad T.
     *
     * @param entity entidad actualizada
     */
    public void afterUpdate(T entity) {
    }

    /**
     * Reglas de negocio y/o validaciones a ejecutarse cuando se ha persistido la entidad T.
     *
     * @param entity entidad eliminada
     */
    public void afterDelete(T entity) {
    }

    /**
     * Mapea los valores de una entidad.
     *
     * @param live   entidad
     * @param entity entidad
     */
    protected void mapValues(T live, T entity) {
        BeanUtils.copyProperties(entity, live,
                "id",
                "uuid",
                "version",
                "creadoEl",
                "creadoPor",
                "actualizadoEl",
                "actualizadoPor",
                "eliminadoEl",
                "eliminadoPor");
    }

    protected void setAuditFields(T entity, TipoAuditoria accion) {
        switch (accion) {
            case CREACION -> {
                entity.setCreadoEl(LocalDateTime.now());
                entity.setCreadoPor(getLoggedInUser());
            }
            case ACTUALIZACION -> {
                entity.setActualizadoEl(LocalDateTime.now());
                entity.setActualizadoPor(getLoggedInUser());
            }
            case ELIMINACION -> {
                entity.setEliminadoEl(LocalDateTime.now());
                entity.setEliminadoPor(getLoggedInUser());
            }
            default -> throw new IllegalArgumentException("Acción incorrecta");
        }
    }

    public String getLoggedInUser() {
        if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null){
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDetails userDetails = null;
            if(principal instanceof UserDetails) {
                userDetails = (UserDetails) principal;
                return userDetails.getUsername();
            }
            return principal.toString();
        }else {
            return "anonymus";
        }
    }

    public Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date);
        } catch (Exception e) {
            try {
                return new SimpleDateFormat("dd/MM/yyyy").parse(date);
            } catch (Exception e1) {
                try {
                    return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(date);
                } catch (Exception e2) {
                    throw new ValidationException("Formato de fecha no válido");
                }
            }
        }
    }
}
