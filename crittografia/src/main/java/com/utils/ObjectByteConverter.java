package com.utils;

import com.packets.DataPacket;
import com.packets.Packet;

import java.io.*;

public class ObjectByteConverter {
    /**
     * Converte un oggetto serializzabile in array di byte
     */
    public static byte[] objectToByte(Serializable object) throws IOException {
        if (object == null) {
            throw new IllegalArgumentException("L'oggetto non può essere null");
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            oos.writeObject(object);
            return baos.toByteArray();
        }
    }

    /**
     * Converte un array di byte in oggetto
     */
    public static Packet byteToObject(byte[] data) throws IOException, ClassNotFoundException {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("I dati non possono essere null o vuoti");
        }

        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {

            Object packetObj = ois.readObject();

            Class<Packet> expectedClass = Packet.class;
            if(!expectedClass.isInstance(packetObj)) {
                throw new ClassCastException("L'oggetto deserializzato non è del tipo atteso: " + expectedClass.getName());
            }

            return expectedClass.cast(packetObj);
        }
    }
}
