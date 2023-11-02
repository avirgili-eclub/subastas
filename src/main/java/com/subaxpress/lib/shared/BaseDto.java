package com.subaxpress.lib.shared;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
@ToString
@EqualsAndHashCode
public class BaseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 2759266338256846539L;

    /**
     * ID de cuenta del cliente.
     */
    @JsonProperty("id")
    private UUID uuid;

    /**
     * Estado de la entidad.
     */
    @Enumerated(EnumType.STRING)
    private Estado estado;

    /**
     * Versión que indica las veces que ha sido modificada la entidad.
     */
    private Long version;

    /**
     * Marca para borrado lógico.
     */
    private boolean eliminado;

    /**
     * Fecha de creación de la entidad.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creadoEl;

    /**
     * Usuario que ha creado la entidad.
     */
    private String creadoPor;

    /**
     * Fecha de última actualización de la entidad.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actualizadoEl;

    /**
     * Usuario que ha actualizado por última vez la entidad.
     */
    private String actualizadoPor;

    /**
     * Usuario que ha marcado la entidad como eliminada.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eliminadoEl;

    /**
     * Fecha de eliminación de la entidad.
     */
    private String eliminadoPor;
}
