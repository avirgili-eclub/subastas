package com.subaxpress.domain.subastas.domain.entity;

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
@Table(name = "ofertas")
public class Ofertas extends BaseEntity {
    private String compradorId;
    private Double precio;
    private String subastaId;
    private LocalDateTime fechaHoraOferta;
    @ManyToOne
    @JoinColumn(name = "subasta_id")
    private Subasta subasta;
}
