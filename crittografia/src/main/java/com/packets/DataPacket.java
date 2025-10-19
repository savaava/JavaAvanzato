package com.packets;

public class DataPacket extends Packet {
    private final String header;
    private final String msg;

    public DataPacket(String header, String msg) {
        this.header = header;
        this.msg = msg;
    }

    public String getHeader() {
        return header;
    }
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "DataPacket\n" +
               "- header=" + header + "\n" +
               "- msg=" + msg + "\n";
    }
}

