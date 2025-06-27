package com;

import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws Exception {
        CifraturaAsimmetrica cifraturaAsimmetrica1 = new CifraturaAsimmetrica();
        CifraturaAsimmetrica cifraturaAsimmetrica2 = new CifraturaAsimmetrica();

        cifraturaAsimmetrica1.setInterlocutorPublicKey(cifraturaAsimmetrica2.getPublicKey());
        byte[] bytesToSendTo2 = cifraturaAsimmetrica1.encrypt(StringByteConverter.stringToByte("msg da cifrare"));
        System.out.println(
                StringByteConverter.byteToString(cifraturaAsimmetrica2.decrypt(bytesToSendTo2))
        );
    }

    private static class StringByteConverter {
        private static byte[] stringToByte(String str) {
            return str.getBytes(StandardCharsets.UTF_8);
        }

        private static String byteToString(byte[] bytes) {
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }
}