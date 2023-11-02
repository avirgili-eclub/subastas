package com.subaxpress.lib.shared;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador base.
 *
 * @param <T> entidad sobre la que se trabajará
 * @param <R> DTO de tipo clase o record
 */
@Log4j2
public abstract class BaseRestController<T extends BaseEntity, R> {

    /**
     * ServicioRequest principal asociado al controlador.
     */
    protected final IBaseService<T, R, Long> service;

    /**
     * Se realiza la inyección de dependencias.
     *
     * @param service servicio principal asociado al controlador
     */
    public BaseRestController(IBaseService<T, R, Long> service) {
        this.service = service;
    }

    /**
     * Buscar listado de entidades creadas.
     *
     * @return listado de entidades encontradas
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    public ResponseEntity<List<R>> getAll() {
        List<R> result = service.getAll();
        return ResponseEntity.ok(result);
    }

    /**
     * Buscar entidad dado su ID.
     *
     * @param id ID de la entidad a buscar
     * @return entidad encontrada
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseEntity<R> getById(@PathVariable("id") UUID id) {
        R result = service.getByUuid(id).orElse(null);
        return result != null
                ? ResponseEntity.ok(result)
                : ResponseEntity.notFound().build();
    }

    /**
     * Crear una nueva entidad.
     *
     * @param body   datos de la entidad a crear
     * @param result captura de error y validaciones lanzadas
     * @return entidad creada
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody R body,
                                    BindingResult result) {
        if (result.hasErrors()) {
            log.error("Error de validación {}", result.getFieldErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ErrorMessage.formatMessages(ResponseCode.ERROR.getCodigo(),
                            result.getFieldErrors()));
        }

        try {
            R resultCreate = service.createFromDto(body);
            return ResponseEntity.ok(new MessageResponse(
                    ResponseCode.EXITO.getCodigo(),
                    "Registro creado con éxito", resultCreate));
        } catch (ValidationException e) {
            log.error("Error al crear {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id,
                                    @Valid @RequestBody R body,
                                    BindingResult result) {
        if (result.hasErrors()) {
            log.error("Error de validación {}", result.getFieldErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ErrorMessage.formatMessages(ResponseCode.ERROR.getCodigo(),
                            result.getFieldErrors()));
        }

        try {
            R resultUpdate = service.updateFromDto(id, body);
            return ResponseEntity.ok(new MessageResponse(
                    ResponseCode.EXITO.getCodigo(),
                    "Registro actualizado con éxito", resultUpdate));
        } catch (ValidationException e) {
            log.error("Error al actualizar {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        try {
            service.deleteByUuid(id);
            return ResponseEntity.ok(new MessageResponse(
                    ResponseCode.EXITO.getCodigo(),
                    "Registro eliminado con éxito", id));
        } catch (ValidationException e) {
            log.error("Error al eliminar {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
