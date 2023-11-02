package com.subaxpress.lib.shared;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Es una clase que contiene una lista de errores.
 */
@Data
@Builder
public class ErrorMessage {

    private int code;

    private List<Map<String, String>> messages;

    /**
     * Toma una lista de errores de campo y devuelve una lista de mapas con
     * el nombre del campo como clave y el mensaje de error como valor.
     *
     * @param code   El código de error, que se utiliza para determinar el tipo de error.
     * @param result El resultado de la validación.
     * @return Una lista de errores
     */
    public static ErrorMessage formatMessages(int code, List<FieldError> result) {
        List<Map<String, String>> errors = result.stream().map(e -> {
            Map<String, String> error = new HashMap<>();
            error.put(e.getField(), e.getDefaultMessage());
            return error;
        }).collect(Collectors.toList());

        return ErrorMessage.builder()
            .code(code)
            .messages(errors)
            .build();
    }
}
