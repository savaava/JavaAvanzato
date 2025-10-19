package com;

import com.cryptography.AsymmetricEncryption;
import com.cryptography.SymmetricEncryption;
import com.packets.*;

public class Main {
    public static void main(String[] args) throws Exception {
        AsymmetricEncryption savaAsymmetric = new AsymmetricEncryption();
        SymmetricEncryption savaSymmetric = new SymmetricEncryption();
        EncryptedPacketHandler sava = new EncryptedPacketHandler(
                savaAsymmetric,
                savaSymmetric
        );

        AsymmetricEncryption gisoAsymmetric = new AsymmetricEncryption();
        SymmetricEncryption gisoSymmetric = new SymmetricEncryption();
        EncryptedPacketHandler giso = new EncryptedPacketHandler(
                gisoAsymmetric,
                gisoSymmetric
        );

        gisoAsymmetric.setInterlocutorPublicKey(savaAsymmetric.getPublicKey());
        savaAsymmetric.setInterlocutorPublicKey(gisoAsymmetric.getPublicKey());

        /* sava invia a giso la chiave per la cifratura simmetrica usando la cifratura asimmetrica */
        savaSymmetric.generateNewSecretKey();
        EncryptedHandshakePacket savaPck = sava.createEncryptedHandshakePacket(
                new HandshakePacket(savaSymmetric.getSenderKey())
        );

        gisoSymmetric.generateNewSecretKey();
        EncryptedHandshakePacket gisoPck = giso.createEncryptedHandshakePacket(
                new HandshakePacket(gisoSymmetric.getSenderKey())
        );

        /* Invio degli encryptedHandshakePacket tramite Networking */
        gisoSymmetric.setInterlocutorKey(
                giso.readEncryptedHandshakePacket(savaPck).getSymmetricKey()
        );

        savaSymmetric.setInterlocutorKey(
                sava.readEncryptedHandshakePacket(gisoPck).getSymmetricKey()
        );

        /* Scambio di un msg sava -> giso */
        DataPacket savaDataPacket = new DataPacket("Sono sava ciao!", "Devi sapere che questo è il contenuto del msg");
        EncryptedDataPacket savaEncryptedDataPacket = sava.createEncryptedDataPacket(savaDataPacket);

        /* Invio del msg a giso tramite Networking */
        DataPacket pckToGiso = giso.readEncryptedDataPacket(savaEncryptedDataPacket);
        System.out.println(pckToGiso);
    }
}