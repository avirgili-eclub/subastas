package com.subaxpress.lib.shared;

public class UtilCodigo {

    public static String generateCode(String prefix, Long lastId) {
        String code = prefix + UtilCodigo.completeWithZeros(lastId, 5);
        return code;
    }

    public static String completeWithZeros(Long number, int length) {
        String result = String.valueOf(number);
        while (result.length() < length) {
            result = "0" + result;
        }
        return result;
    }
}
