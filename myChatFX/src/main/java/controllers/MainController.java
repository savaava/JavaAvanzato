package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {
    @FXML
    private Button clientBtn;

    @FXML
    private HBox hbox;

    @FXML
    private Button serverBtn;

    @FXML
    private Label textLabel;

    @FXML
    private void onBtnClicked(ActionEvent event) {
        switch(((Button)event.getSource()).getText().toLowerCase()) {
            case "client":
                handleClientCreation();
                break;
            case "server":
                handleServerCreation();
        }
    }

    private void handleClientCreation() {
        textLabel.setText("Client");
        hbox.getChildren().clear();
        TextField ipField = new TextField();
        ipField.setId("ipField");
        ipField.setPromptText("IP Address");

        TextField portField = new TextField();
        portField.setPromptText("Port");
        portField.setId("portField");

        Button connectBtn = new Button("Connect");
        connectBtn.setOnAction(this::onConfirmBtnClicked);

        connectBtn.disableProperty().bind(
                        Bindings.createBooleanBinding(() -> {
                            boolean c1 = !ipField.getText().matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
                            boolean c2 = !isValidPort(portField.getText());
                            return c1 || c2;
                        }, ipField.textProperty(), portField.textProperty())
        );

        hbox.getChildren().addAll(ipField, portField,connectBtn);
    }

    private void handleServerCreation() {
        textLabel.setText("Server");
        hbox.getChildren().clear();
        TextField portField = new TextField();
        portField.setPromptText("Port");
        portField.setId("portField");

        Button connectBtn = new Button("Connect");
        connectBtn.setOnAction(this::onConfirmBtnClicked);

        connectBtn.disableProperty().bind(
                Bindings.createBooleanBinding(() -> !isValidPort(portField.getText()), portField.textProperty())
        );

        hbox.getChildren().addAll(portField, connectBtn);
    }

    private void onConfirmBtnClicked(ActionEvent event) {
        List<Node> fieldList = hbox.getChildren().stream().filter(o -> o.getId() != null).collect(Collectors.toList());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/chat.fxml"));

        Stage stage = (Stage) fieldList.get(0).getScene().getWindow();

        if(fieldList.size() == 1) {
            //SERVER
            ChatController.port = Integer.parseInt(((TextField)fieldList.get(0)).getText());
        } else if(fieldList.size() == 2) {
            //CLIENT
            fieldList.forEach(node -> {
                TextField tf = (TextField) node;

                if(tf.getId().equals("portField")) {
                    ChatController.port = (Integer.parseInt(tf.getText()));
                } else if(tf.getId().equals("ipField")) {
                    ChatController.IP = (tf.getText());
                }
            });
        }

        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Chat");
            stage.setResizable(false);
            stage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidPort(String port) {
        return port.matches("[0-9]{1,5}") && Integer.parseInt(port) <= 65535;
    }
}
