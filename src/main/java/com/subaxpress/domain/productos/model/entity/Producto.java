package com.subaxpress.domain.productos.model.entity;

import com.subaxpress.domain.subastas.domain.entity.Subasta;
import com.subaxpress.lib.shared.BaseEntity;
import com.subaxpress.domain.productos.model.enums.EstadoProducto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "producto")
public class Producto extends BaseEntity {
    private String titulo;
    private String descripcion;
    private String imagen;
    private Double precio;
    @Enumerated(EnumType.STRING)
    private EstadoProducto estadoProducto;
    @ManyToMany
    @JoinTable(
            name = "producto_categoria",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;
    @OneToOne(mappedBy = "producto")
    private Subasta subasta;
}
