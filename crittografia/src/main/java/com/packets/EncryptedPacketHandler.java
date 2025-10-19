package com.packets;

import com.utils.HashingFunction;
import com.utils.ObjectByteConverter;
import com.cryptography.SymmetricEncryption;
import com.cryptography.AsymmetricEncryption;

import java.util.Arrays;

public class EncryptedPacketHandler {
    private final AsymmetricEncryption asymmetricEncryption;
    private final SymmetricEncryption symmetricEncryption;

    public EncryptedPacketHandler(AsymmetricEncryption asymmetricEncryption, SymmetricEncryption symmetricEncryption) {
        this.asymmetricEncryption = asymmetricEncryption;
        this.symmetricEncryption = symmetricEncryption;
    }

    /**
     * Crea un pacchetto cifrato contenente il messaggio crittografato e la firma digitale. <br> <br>
     *
     * Il processo di cifratura segue questi passaggi: <br>
     * 1. Genera un hash SHA-256 del messaggio originale <br>
     * 2. Crea la firma digitale cifrando l'hash con la chiave privata del mittente <br>
     * 3. Cifra il messaggio originale con la chiave pubblica del destinatario <br>
     * 4. Restituisce un pacchetto contenente sia il messaggio cifrato che la firma digitale <br>
     *
     * @param handshakePacket il pacchetto da cifrare e firmare
     * @return un EncryptedPacket contenente il messaggio cifrato e la firma digitale
     * @throws Exception se si verifica un errore durante la cifratura o la firma
     */
    public EncryptedHandshakePacket createEncryptedHandshakePacket(HandshakePacket handshakePacket) throws Exception {
        byte[] data = ObjectByteConverter.objectToByte(handshakePacket);
        byte[] hashedData = HashingFunction.hashData(data);
        byte[] digitalSign = asymmetricEncryption.createDigitalSign(hashedData);
        byte[] encryptedData = asymmetricEncryption.encryptMsg(data);

        return new EncryptedHandshakePacket(
                encryptedData,
                digitalSign
        );
    }

    /**
     * Decifra un pacchetto e verifica che non sia stato alterato. <br> <br>
     *
     * 1. Decifra la firma con la chiave pubblica del mittente <br>
     * 2. Decifra il messaggio con la chiave privata  <br>
     * 3. Confronta gli hash per verificare l'integrità <br>
     *
     * @param encryptedHandshakePacket il pacchetto da decifrare
     * @return il messaggio originale
     * @throws SecurityException se la firma non è valida
     * @throws Exception se si verifica un errore nella decifratura
     */
    public HandshakePacket readEncryptedHandshakePacket(EncryptedHandshakePacket encryptedHandshakePacket) throws Exception {
        byte[] hashFromSignature = asymmetricEncryption.decryptDigitalSign(encryptedHandshakePacket.getDigitalSign());

        byte[] decryptedData = asymmetricEncryption.decryptMsg(encryptedHandshakePacket.getEncryptedMsg());
        byte[] hashFromData = HashingFunction.hashData(decryptedData);

        if(!Arrays.equals(hashFromSignature, hashFromData)){
            throw new SecurityException(
                    "Verifica della firma digitale fallita: " +
                            "il messaggio potrebbe essere stato alterato o la firma è falsa. " +
                            "Impossibile garantire l'autenticità e l'integrità del messaggio."
            );
        }

        Packet pck = ObjectByteConverter.byteToObject(decryptedData);
        return (pck instanceof HandshakePacket) ? (HandshakePacket) pck : null;
    }

    public EncryptedDataPacket createEncryptedDataPacket(DataPacket dataPacket) throws Exception {
        byte[] data = ObjectByteConverter.objectToByte(dataPacket);
        byte[] encryptedData = symmetricEncryption.encryptMsg(data);

        return new EncryptedDataPacket(encryptedData);
    }
    public DataPacket readEncryptedDataPacket(EncryptedDataPacket encryptedDataPacket) throws Exception {
        byte[] decryptedData = symmetricEncryption.decryptMsg(encryptedDataPacket.getEncryptedMsg());

        Packet pck = ObjectByteConverter.byteToObject(decryptedData);
        return (pck instanceof DataPacket) ? (DataPacket) pck : null;
    }
}
