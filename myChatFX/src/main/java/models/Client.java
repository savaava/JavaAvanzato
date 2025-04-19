package models;

import java.io.Serializable;
import java.util.function.Consumer;

public class Client extends NetworkConnection{
    public Client(String host, int port, Consumer<Serializable> receiveCallback) {
        super(host, port, receiveCallback);
    }

    @Override
    public boolean isServer(){return false;}
}
