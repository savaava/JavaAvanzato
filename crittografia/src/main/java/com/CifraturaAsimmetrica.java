package com;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class CifraturaAsimmetrica {
    private static final String ASYM_ALGORITHM 	= "RSA";
    private static final Integer ASYM_KEY_SIZE 	= 2048;
    private static final String CIPHER_TRANSFORMATION = ASYM_ALGORITHM+"/ECB/PKCS1Padding";

    private final KeyPairGenerator generator;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private PublicKey interlocutorPublicKey;


    public CifraturaAsimmetrica() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        generator = KeyPairGenerator.getInstance(ASYM_ALGORITHM);
        generator.initialize(ASYM_KEY_SIZE);

        setPrivateAndPublicKey(generator.generateKeyPair());
    }

    private void setPrivateAndPublicKey(KeyPair keyPair) throws InvalidKeyException {
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }
    public void generateNewAsymmetricKey() throws InvalidKeyException {
        setPrivateAndPublicKey(generator.generateKeyPair());
    }

    public PrivateKey getPrivateKey(){
        return this.privateKey;
    }
    public PublicKey getPublicKey(){
        return this.publicKey;
    }

    public PublicKey getInterlocutorPublicKey(){
        return this.interlocutorPublicKey;
    }
    public void setInterlocutorPublicKey(PublicKey interlocutorPublicKey){
        if (interlocutorPublicKey == null) {
            throw new IllegalArgumentException("La chiave pubblica dell'interlocutore non può essere null");
        }

        if(!interlocutorPublicKey.getAlgorithm().equals(ASYM_ALGORITHM)) {
            throw new IllegalArgumentException("La chiave deve essere di tipo "+ASYM_ALGORITHM);
        }

        this.interlocutorPublicKey = interlocutorPublicKey;
    }

    public byte[] encrypt(byte[] plaintext) throws Exception {
        Cipher cipherEncryption = Cipher.getInstance(CIPHER_TRANSFORMATION);

        if(interlocutorPublicKey == null)
            throw new IllegalStateException("Nessun interlocutore impostato. Usare setInterlocutorPublicKey() prima di cifrare.");

        cipherEncryption.init(Cipher.ENCRYPT_MODE, interlocutorPublicKey);
        return cipherEncryption.doFinal(plaintext);
    }
    public byte[] decrypt(byte[] ciphertext) throws Exception {
        Cipher cipherDecryption = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipherDecryption.init(Cipher.DECRYPT_MODE, privateKey);
        return cipherDecryption.doFinal(ciphertext);
    }
}
