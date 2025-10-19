package com.cryptography;

public interface AsymmetricEncryptionManager extends EncryptionManager {
    byte[] createDigitalSign(byte[] plaintext) throws Exception;
    byte[] decryptDigitalSign(byte[] ciphertext) throws Exception;
}
