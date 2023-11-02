package com.subaxpress.domain.subastas.domain.entity;

import com.subaxpress.domain.productos.model.entity.Producto;
import com.subaxpress.domain.subastas.domain.enums.EstadoSubasta;
import com.subaxpress.lib.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "subasta")
public class Subasta extends BaseEntity {
    private String vendedorId;
    @OneToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Double precioInicial;
    private Double precioActual;
    private EstadoSubasta estadoSubasta;
    private String compradorId;
    @OneToMany(mappedBy = "subasta")
    private List<Ofertas> ofertas;

}
