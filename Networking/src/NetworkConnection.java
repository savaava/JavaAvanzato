import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.function.Consumer;

public abstract class NetworkConnection {
    private String IP;
    private int port;
    private ConnectionThread conn;
    private Consumer<Serializable> receiveCallback;

    public NetworkConnection(String IP, int port, Consumer<Serializable> receiveCallback) {
        this.IP = IP;
        this.port = port;
        this.conn = new ConnectionThread();
        this.receiveCallback = receiveCallback;
    }

    public String getIP() {return IP;}
    public int getPort() {return port;}

    public abstract boolean isServer();

    public void sendMsg(Serializable msg) {
        /* Accedere ad outputStream per scrrivere il messaggio */
        try {
            conn.oos.writeObject(msg);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//    public void onReceiveMsg(Serializable msg) {
//        receiveCallback.accept(msg);
//    }

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
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ){
                this.socket = socket;
                this.oos = oos;

                while(true){
                    /* riceviamo un oggetto serializzato, ossia il messaggio */
                    Serializable msg = (Serializable)ois.readObject();
                    //onReceiver();
                    receiveCallback.accept(msg);
                }
            }catch (UnknownHostException ex){
                ex.printStackTrace();
            }catch(IOException | ClassNotFoundException ex){
                ex.printStackTrace();
            }
        }
    }
}
