package com.packets;

public class EncryptedHandshakePacket extends EncryptedPacket {
    private final byte[] digitalSign;

    public EncryptedHandshakePacket(byte[] encryptedMsg, byte[] digitalSign) {
        super(encryptedMsg);
        this.digitalSign = digitalSign;
    }

    public byte[] getDigitalSign() {
        return digitalSign;
    }
}
