package models;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class NetworkConnection {
    private String IP;
    private int port;
    private ConnectionThread conn;
    private Consumer<Serializable> receiveCallback;

    private boolean isConnected = false;
    private boolean hostNotAvailable = false;

    public NetworkConnection(String IP, int port, Consumer<Serializable> receiveCallback) {
        this.IP = IP;
        this.port = port;
        this.conn = new ConnectionThread();
        this.receiveCallback = receiveCallback;
    }

    public String getIP() {return IP;}
    public int getPort() {return port;}
    public boolean isConnected() {return isConnected;}
    public boolean isHostNotAvailable() {return hostNotAvailable;}

    public abstract boolean isServer();

    public void sendMsg(Serializable msg) {
        try {
            conn.oos.writeObject(msg);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void connect(){
        conn.start();
    }
    public void disconnect(){
        try {
            conn.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ConnectionThread extends Thread {
        ObjectOutputStream oos;
        Socket socket;

        @Override
        public void run() {
            try(Socket socket = isServer() ? new ServerSocket(port).accept() : new Socket(IP,port);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())
            ){
                isConnected = true;

                this.socket = socket;
                this.oos = oos;

                while(true){
                    Serializable msg = (Serializable)ois.readObject();
                    //controllare cosa succede se msg è vuoto
                    receiveCallback.accept(msg);
                }
            }catch(IOException ex){
                hostNotAvailable = true;
            }catch(ClassNotFoundException ex){
                ex.printStackTrace();
            }
        }
    }
}
