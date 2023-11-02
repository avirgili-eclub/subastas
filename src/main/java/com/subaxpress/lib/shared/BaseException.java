package com.subaxpress.lib.shared;

/**
 * Creación de tipos de excepciones.
 */
public abstract class BaseException extends Exception {

    /**
     * Mensaje de la excepción.
     */
    private final String message;

    /**
     * BaseExcepcion
     *
     * @param message mensaje de la excepción
     */
    public BaseException(String message) {
        this.message = message;
    }

    /**
     * Obtener mensaje de la excepción.
     *
     * @return mensaje de la excepción
     */
    @Override
    public String getMessage() {
        return message;
    }
}
