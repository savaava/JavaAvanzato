package models;

import java.io.Serializable;
import java.util.function.Consumer;

public class Server extends NetworkConnection {
    public Server(int port, Consumer<Serializable> receiveCallback) {
        super(null, port, receiveCallback);
    }

    @Override
    public boolean isServer(){return true;}
}