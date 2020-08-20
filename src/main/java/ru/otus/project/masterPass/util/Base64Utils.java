package ru.otus.project.masterPass.util;

import java.util.Base64;

public class Base64Utils {

    public static String encodeToString(byte[] byteArray){
        return Base64.getEncoder().encodeToString(byteArray);
    }

    public static byte[] decodeToByteArray(String stringValue){
        return Base64.getDecoder().decode(stringValue);
    }

}
