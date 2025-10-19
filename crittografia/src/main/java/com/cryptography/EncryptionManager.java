package com.cryptography;

public interface EncryptionManager {
    byte[] encryptMsg(byte[] plaintext) throws Exception;
    byte[] decryptMsg(byte[] ciphertext) throws Exception;
}
