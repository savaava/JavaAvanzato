package com.packets;

import java.io.Serial;
import java.io.Serializable;

public abstract class Packet implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
