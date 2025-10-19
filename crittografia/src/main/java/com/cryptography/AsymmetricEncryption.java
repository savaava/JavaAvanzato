package com.cryptography;

import javax.crypto.*;
import java.security.*;

public class AsymmetricEncryption implements AsymmetricEncryptionManager {
    private static final String ASYM_ALGORITHM = "RSA";
    private static final Integer ASYM_KEY_SIZE = 4096;
    private static final String CIPHER_TRANSFORMATION = ASYM_ALGORITHM+"/ECB/PKCS1Padding";

    private final KeyPairGenerator keyGenerator;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private PublicKey interlocutorPublicKey;


    public AsymmetricEncryption() throws NoSuchAlgorithmException {
        keyGenerator = KeyPairGenerator.getInstance(ASYM_ALGORITHM);
        keyGenerator.initialize(ASYM_KEY_SIZE);

        setPrivateAndPublicKey(keyGenerator.generateKeyPair());
    }

    private void setPrivateAndPublicKey(KeyPair keyPair) {
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }
    public void generateNewAsymmetricKey() {
        setPrivateAndPublicKey(keyGenerator.generateKeyPair());
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

    @Override
    public byte[] encryptMsg(byte[] plaintext) throws Exception {
        if(interlocutorPublicKey == null)
            throw new IllegalStateException("Nessun interlocutore impostato. Usare setInterlocutorPublicKey() prima di cifrare il msg.");

        Cipher cipherEncryption = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipherEncryption.init(Cipher.ENCRYPT_MODE, interlocutorPublicKey);
        return cipherEncryption.doFinal(plaintext);
    }
    @Override
    public byte[] decryptMsg(byte[] ciphertext) throws Exception {
        Cipher cipherDecryption = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipherDecryption.init(Cipher.DECRYPT_MODE, privateKey);
        return cipherDecryption.doFinal(ciphertext);
    }

    @Override
    public byte[] createDigitalSign(byte[] plaintext) throws Exception {
        Cipher cipherEncryption = Cipher.getInstance(CIPHER_TRANSFORMATION);

        cipherEncryption.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipherEncryption.doFinal(plaintext);
    }
    @Override
    public byte[] decryptDigitalSign(byte[] ciphertext) throws Exception {
        if(interlocutorPublicKey == null)
            throw new IllegalStateException("Nessun interlocutore impostato. Usare setInterlocutorPublicKey() prima di decifrare la firma.");

        Cipher cipherDecryption = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipherDecryption.init(Cipher.DECRYPT_MODE, interlocutorPublicKey);
        return cipherDecryption.doFinal(ciphertext);
    }


    @Override
    public String toString(){
        return "AsymmetricEncryption instance"+"\n"+
                "- ASYM_ALGORITHM: "+ASYM_ALGORITHM+"\n"+
                "- ASYM_KEY_SIZE: "+ASYM_KEY_SIZE+"\n"+
                "- CIPHER_TRANSFORMATION: "+CIPHER_TRANSFORMATION+"\n"+
                "- privateKey: "+privateKey.getFormat()+"\n"+
                "- publicKey: "+publicKey.getFormat()+"\n"+
                "- "+(interlocutorPublicKey==null ? "NO interlocutorPublicKey" : "interlocutorPublicKey: "+interlocutorPublicKey.getFormat())+"\n";
    }
}
