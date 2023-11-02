package com.subaxpress.lib.shared;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.UUID;


@MappedSuperclass
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BaseEntity {

    /**
     * ID primario de la entidad para uso interno.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("ID primario de la entidad que utilizará internamente.")
    private Long id;

    /**
     * ID alternativo de la entidad que será expuesto.
     */
    @Column(name = "uuid", updatable = false, nullable = false)
    @Comment("ID alternativo de la entidad que será expuesto.")
    private UUID uuid = UUID.randomUUID();

    /**
     * Estado de la entidad.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    @ColumnDefault("'ACTIVO'")
    @Comment("Estado de la entidad.")
    private Estado estado = Estado.ACTIVO;

    /**
     * Versión que indica las veces que ha sido modificada la entidad.
     */
    @Version
    @Comment("Versión que indica las veces que ha sido modificada la entidad.")
    private Long version;

    /**
     * Marca para borrado lógico.
     */
    @Column(name = "eliminado")
    @Where(clause = "eliminado=false")
    @Comment("Marca para borrado lógico.")
    private boolean eliminado = Boolean.FALSE;

    /**
     * Fecha de creación de la entidad.
     */
    @CreationTimestamp
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "creado_el", updatable = false)
    @Comment("Fecha de creación de la entidad.")
    private LocalDateTime creadoEl;

    /**
     * Usuario que ha creado la entidad.
     */
    @Column(name = "creado_por", updatable = false)
    @Comment("Usuario que ha creado la entidad.")
    private String creadoPor;

    /**
     * Fecha de última actualización de la entidad.
     */
    @UpdateTimestamp
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "actualizado_el", insertable = false)
    @Comment("Fecha de última actualización de la entidad.")
    private LocalDateTime actualizadoEl;

    /**
     * Usuario que ha actualizado por última vez la entidad.
     */
    @Column(name = "actualizado_por")
    @Comment("Usuario que ha actualizado por última vez la entidad.")
    private String actualizadoPor;

    /**
     * Usuario que ha marcado la entidad como eliminada.
     */
    @Column(name = "eliminado_el")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Comment("Usuario que ha marcado la entidad como eliminada.")
    private LocalDateTime eliminadoEl;

    /**
     * Fecha de eliminación de la entidad.
     */
    @Column(name = "eliminado_por")
    @Comment("Fecha de eliminación de la entidad.")
    private String eliminadoPor;

    //TODO: verificar que la implementacion de equals y hascode sea correcta
//    @Override
//    public final boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null) return false;
//        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
//        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
//        if (thisEffectiveClass != oEffectiveClass) return false;
//        BaseEntity that = (BaseEntity) o;
//        return getId() != null && Objects.equals(getId(), that.getId());
//    }
//
//    @Override
//    public final int hashCode() {
//        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
//    }

}
