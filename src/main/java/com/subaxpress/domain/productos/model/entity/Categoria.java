package com.subaxpress.domain.productos.model.entity;

import com.subaxpress.lib.shared.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
public class Categoria extends BaseEntity {
    private String nombre;
    private String descripcion;
    @ManyToMany(mappedBy = "categorias")
    private List<Producto> productos;
}
