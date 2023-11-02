package com.subaxpress.lib.shared;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class UtilFormatos {
    public static final String FORMATO_FECHA = "dd/MM/yyyy";
    public static final String FORMATO_FECHA_HORA = "dd/MM/yyyy HH:mm:ss";
    public static String formatearFecha(LocalDate fecha, String formato) {
        // Definir el patrón del formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);

        // Formatear la fecha utilizando el patrón
        String fechaFormateada = fecha.format(formatter);

        return fechaFormateada;
    }
    public static String formatearFecha(LocalDateTime fechaHora, String formato) {
        // Definir el patrón del formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);

        // Formatear la fecha utilizando el patrón
        String fechaFormateada = fechaHora.format(formatter);

        return fechaFormateada;
    }
    public static String formatearCedulaParaguaya(String documento) {
        // 1. Remover espacios en blanco u otros caracteres no deseados (opcional).
        documento = documento.replaceAll("\\s+", "");

        // 2. Darle formato utilizando una expresión regular.
        String regex = "(\\d{1,3})(\\d{3})(\\d{3})";
        String replacement = "$1.$2.$3";
        String numeroFormateado = documento.replaceAll(regex, replacement);

        return numeroFormateado;
    }

    public static String formatNumber(double number) {
        // Crear un formateador de números con el patrón deseado
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());

        // Cambiar el patrón de formato
        formatter.applyPattern("###,###.##");

        // Cambiar el separador de miles y la coma decimal
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("es", "ES"));
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);

        // Formatear el número y devolverlo como una cadena
        return formatter.format(number);
    }
}
