package com.cryptography;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;
import javax.security.auth.DestroyFailedException;

/**
 * Implementazione di cifratura simmetrica utilizzando AES-GCM.
 * <p>
 * Questa classe gestisce due chiavi simmetriche separate:
 * <ul>
 *  <li>Chiave simmetrica del mittente per cifrare i messaggi in uscita</li>
 *  <li>Chiave simmetrica dell'interlocutore per decifrare i messaggi in entrata</li>
 * </ul>
 * <p>
 * Garanzie di sicurezza fornite:
 * <ul>
 *  <li><strong>Confidenzialità</strong>: AES</li>
 *  <li><strong>Integrità dei dati</strong>: Modalità GCM (Galois/Counter Mode)</li>
 *  <li><strong>Autenticazione del messaggio</strong>: GCM</li>
 * </ul>
 */
public class SymmetricEncryption implements EncryptionManager {
    private static final String SYM_ALGORITHM = "AES";
    private static final Integer SYM_KEY_SIZE = 256;
    private static final String CIPHER_TRANSFORMATION = SYM_ALGORITHM+"/GCM/NoPadding";

    private final KeyGenerator keyGenerator;
    private SecretKey senderSymmetricKey;
    private SecretKey interlocutorSymmetricKey;


    public SymmetricEncryption() throws Exception  {
        keyGenerator = KeyGenerator.getInstance(SYM_ALGORITHM);
        keyGenerator.init(SYM_KEY_SIZE);
    }

    public SecretKey getSenderKey() {
        return this.senderSymmetricKey;
    }
    public void generateNewSecretKey() {
        this.senderSymmetricKey = keyGenerator.generateKey();
    }
    public void destroySecretKey() throws DestroyFailedException {
        this.senderSymmetricKey.destroy();
    }

    public SecretKey getInterlocutorKey(){
        return this.interlocutorSymmetricKey;
    }
    public void setInterlocutorKey(SecretKey interlocutorSymmetricKey) {
        if (interlocutorSymmetricKey == null) {
            throw new IllegalArgumentException("La chiave simmetrica dell'interlocutore non può essere null");
        }

        if(!interlocutorSymmetricKey.getAlgorithm().equals(SYM_ALGORITHM)) {
            throw new IllegalArgumentException("La chiave deve essere di tipo "+SYM_ALGORITHM);
        }

        this.interlocutorSymmetricKey = interlocutorSymmetricKey;
    }

    @Override
    public byte[] encryptMsg(byte[] plaintext) throws Exception {
        if(senderSymmetricKey == null)
            throw new IllegalStateException("senderSymmetricKey non impostata");

        Cipher cipherEncryption = Cipher.getInstance(CIPHER_TRANSFORMATION);

        // GCM richiede IV di 12 byte
        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);

        cipherEncryption.init(Cipher.ENCRYPT_MODE, senderSymmetricKey, gcmSpec);
        byte[] ciphertext = cipherEncryption.doFinal(plaintext);

        // concatena IV + ciphertext per poterlo recuperare in decryption
        byte[] result = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(ciphertext, 0, result, iv.length, ciphertext.length);

        return result;
    }
    @Override
    public byte[] decryptMsg(byte[] ciphertext) throws Exception {
        if(interlocutorSymmetricKey == null)
            throw new IllegalStateException("Nessun interlocutore impostato. Usare setInterlocutorKey() prima di cifrare il msg.");

        // Estrai IV dai primi 12 byte
        byte[] iv = new byte[12];
        System.arraycopy(ciphertext, 0, iv, 0, 12);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);

        // Il vero ciphertext (con tag di autenticazione) parte dal byte 12
        byte[] actualCiphertext = new byte[ciphertext.length - 12];
        System.arraycopy(ciphertext, 12, actualCiphertext, 0, actualCiphertext.length);

        Cipher cipherDecryption = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipherDecryption.init(Cipher.DECRYPT_MODE, interlocutorSymmetricKey, gcmSpec);

        return cipherDecryption.doFinal(actualCiphertext);
    }


    @Override
    public String toString(){
        return "SymmetricEncryption instance"+"\n"+
                "- SYM_ALGORITHM: "+SYM_ALGORITHM+"\n"+
                "- SYM_KEY_SIZE: "+SYM_KEY_SIZE+"\n"+
                "- CIPHER_TRANSFORMATION: "+CIPHER_TRANSFORMATION+"\n"+
                "- senderSymmetricKey: "+senderSymmetricKey.getFormat()+"\n"+
                "- senderKey: "+(senderSymmetricKey != null ? senderSymmetricKey.getFormat() : "null")+"\n"+
                "- "+(interlocutorSymmetricKey==null ? "NO interlocutorSymmetricKey" : "interlocutorSymmetricKey: "+interlocutorSymmetricKey.getFormat())+"\n";
    }
}