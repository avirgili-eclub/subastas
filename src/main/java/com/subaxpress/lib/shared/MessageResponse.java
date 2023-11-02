package com.subaxpress.lib.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Estructura base de respuesta para todos los endpoints de la aplicación.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {

    /**
     * Código de respuesta.
     */
    private int code;

    /**
     * Mensaje de la respuesta.
     */
    private String message;

    /**
     * Datos devueltos si hubiere.
     */
    private Object data;
}
