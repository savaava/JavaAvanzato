package com.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingFunction {
    private static final String HASH_ALGORITHM = "SHA-256";

    private HashingFunction(){}

    public static byte[] hashData(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        return digest.digest(data);
    }
}
