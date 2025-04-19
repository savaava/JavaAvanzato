package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.Client;
import models.NetworkConnection;
import models.Server;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatController implements Initializable {
    /*
    * ProgressBar
    * client: trova il server, stato "in connessione" -> stato "connesso" | "Impossibile connettersi"
    * server: X
    */

    @FXML
    private TextArea chatArea;
    @FXML
    private TextField inputMsgField;
    @FXML
    private Label progressLbl;
    @FXML
    private ProgressBar progressBar;

    static String IP;
    static int port;
    private NetworkConnection conn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputMsgField.setDisable(true);

        createConn();

        handleProgressBar();
    }

    private void createConn() {
        if(IP==null){
            conn = new Server(port, msg -> chatArea.appendText("Client: "+msg+"\n"));
        }else{
            conn = new Client(IP, port, msg -> chatArea.appendText("Server: "+msg+"\n"));
        }

        conn.connect();
    }

    private void handleProgressBar() {
        new Thread(() -> {
            if(conn.isServer()){
                Platform.runLater(() -> progressLbl.setText("In attesa di un client sulla porta "+port+" ..."));

                while(!conn.isConnected() && !conn.isHostNotAvailable()){
                    System.out.println("hhh");
                }

                System.out.println("here s");
                progressBar.setProgress(1);

                if (conn.isHostNotAvailable()) {
                    Platform.runLater(() -> progressLbl.setText("Porta " + port + " non aperta"));
                    return;
                }

                if(!conn.isConnected()){
                    Platform.runLater(() ->  progressLbl.setText("Impossibile connettersi: Timeout scaduto"));
                    return;
                }

                Platform.runLater(() ->  progressLbl.setText("Porta "+port+" aperta"));
                Platform.runLater(() ->  inputMsgField.setDisable(false));

            }else{
                Platform.runLater(() -> progressLbl.setText("In connessione ..."));

                AtomicInteger progress = new AtomicInteger(0);
                double max = 100000.0; //2 min

                while(!conn.isConnected() && progress.get()<max && !conn.isHostNotAvailable()) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    progressBar.setProgress(progress.incrementAndGet() / max);
                }
                System.out.println("here c");
                progressBar.setProgress(1);

                if (conn.isHostNotAvailable()) {
                    Platform.runLater(() -> progressLbl.setText("Impossibile connettersi a " + IP + ":" + port));
                    return;
                }

                if(!conn.isConnected()){
                    Platform.runLater(() ->  progressLbl.setText("Impossibile connettersi -> Timeout scaduto"));
                    return;
                }

                Platform.runLater(() ->  progressLbl.setText("Connesso a "+IP+":"+port));
                Platform.runLater(() ->  inputMsgField.setDisable(false));
            }
        }).start();
    }

    @FXML
    private void onEnterPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() != KeyCode.ENTER) return;
        if(inputMsgField.getText().isEmpty()) return;

        String msg = inputMsgField.getText();

        if(conn.isServer()){
            chatArea.appendText("Server: "+msg+"\n");
        }else{
            chatArea.appendText("Client: "+msg+"\n");
        }
        conn.sendMsg(msg);

        inputMsgField.clear();
    }

    private void exit() {
        Stage stageCurr = (Stage)chatArea.getScene().getWindow();
        System.out.println("Chiusura !!!");
        //stageCurr.close();
    }
}
