package com.packets;

import javax.crypto.SecretKey;

public class HandshakePacket extends Packet {
    private final SecretKey symmetricKey;

    public HandshakePacket(SecretKey symmetricKey) {
        this.symmetricKey = symmetricKey;
    }

    public SecretKey getSymmetricKey() {
        return symmetricKey;
    }
}
