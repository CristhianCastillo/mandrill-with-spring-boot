package com.ptesa.demoemailmandril.util;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static String encodeFileToBase64String(String fileName) throws IOException {
        File file = new File(fileName);
        byte[] encoded = Base64.encodeBase64(org.apache.commons.io.FileUtils.readFileToByteArray(file));
        return new String(encoded, StandardCharsets.US_ASCII);
    }
}
