package com.subaxpress.lib.shared;

/**
 * Excepciones lanzadas cuando ocurren problemas a nivel de base de datos.
 */
public class DBExeption {

    /**
     * Error de ejecución SQL.
     */
    public static class BadExecution extends BaseException {

        private static final long serialVersionUID = 3555714415375055302L;

        public BadExecution(String msg) {
            super(msg);
        }
    }

    /**
     * No existen datos donde esperamos al menos una fila.
     */
    public static class NoData extends BaseException {

        private static final long serialVersionUID = 8777415230393628334L;

        public NoData(String msg) {
            super(msg);
        }
    }

    /**
     * Existen varias filas donde esperamos solo una fila.
     */
    public static class MoreData extends BaseException {

        private static final long serialVersionUID = -3987707665150073980L;

        public MoreData(String msg) {
            super(msg);
        }
    }

    /**
     * Error de parámetros no válidos.
     */
    public static class InvalidParam extends BaseException {

        private static final long serialVersionUID = 4235225697094262603L;

        public InvalidParam(String msg) {
            super(msg);
        }
    }
}
