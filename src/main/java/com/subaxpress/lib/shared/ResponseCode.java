package com.subaxpress.lib.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Tipos de errores tipificados.
 */
@AllArgsConstructor
public enum ResponseCode {

    EXITO(0),

    ERROR(99);

    @Getter
    private final int codigo;
}
