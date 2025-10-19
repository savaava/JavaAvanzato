package com.packets;

import java.io.Serial;
import java.io.Serializable;

public abstract class EncryptedPacket implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final byte[] encryptedMsg;

    public EncryptedPacket(byte[] encryptedMsg) {
        this.encryptedMsg = encryptedMsg;
    }

    public byte[] getEncryptedMsg() {
        return encryptedMsg;
    }
}
