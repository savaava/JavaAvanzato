package com.servicesproject.controllers;

import com.servicesproject.model.Utente;
import com.servicesproject.services.MyService;
import javafx.animation.*;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalTime;
import java.util.Random;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    public Label titleLbl;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public Label progressMessageLbl;
    @FXML
    public Button testBtn;

    @FXML
    public TableView<Utente> usersTable;
    @FXML
    public TableColumn<Utente, String> nameColumn;
    @FXML
    public TableColumn<Utente, Integer> ageColumn;

    private final MyService myService = new MyService();
    private final Random rand = new Random(7);


    @FXML
    public Circle circle;
    @FXML
    public Button circleBtn;
    private Timeline timeline1;
    private Timeline timeline2;


    @FXML
    private Label orarioLbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLbl.visibleProperty().bind(myService.runningProperty());
        titleLbl.textProperty().bind(myService.titleProperty());

        progressBar.visibleProperty().bind(myService.runningProperty());
        progressBar.progressProperty().bind(myService.progressProperty());

        progressMessageLbl.visibleProperty().bind(myService.runningProperty());
        progressMessageLbl.textProperty().bind(myService.messageProperty());

        //testBtn.disableProperty().bind(myService.runningProperty());

        myService.setOnSucceeded(_ -> testBtn.setText("Test Service"));

        usersTable.itemsProperty().bind(myService.valueProperty());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));


        /* TIMELINE - CIRCLE */
        timeline1 = new Timeline(
                new KeyFrame(Duration.millis(50), _ -> circle.setVisible(!circle.isVisible()))
        );
        timeline1.setCycleCount(Animation.INDEFINITE);


        timeline2 = new Timeline(
                new KeyFrame(
                        Duration.seconds(2), // durata dell'espansione/contrazione
                        new KeyValue(circle.radiusProperty(), 100, Interpolator.EASE_BOTH) // cambia raggio a 100
                )
        );
        timeline2.setCycleCount(Animation.INDEFINITE);
        timeline2.setAutoReverse(true);


        /* Orario service */
        ScheduledService<String> scheduledService = new ScheduledService<>(){
            @Override
            protected Task<String> createTask(){
                return new Task<>() {
                    @Override
                    protected String call(){
                        return "Orario: " + LocalTime.now().withSecond(0).withNano(0);
                    }
                };
            }
        };
        scheduledService.setPeriod(Duration.minutes(1));
        scheduledService.start();
        scheduledService.setOnSucceeded(_ -> orarioLbl.setText(scheduledService.getValue()));
    }

    @FXML
    public void onTestClicked() {
        if (myService.isRunning()) {
            myService.cancel();
            testBtn.setText("Test Service");
            return;
        }
        
        myService.setNum(rand.nextInt(900, 1001));
        myService.restart();
        testBtn.setText("Cancel Service");
    }

    @FXML
    public void onCircleAction(){
        if(timeline1.getStatus() == Animation.Status.RUNNING) {
            timeline1.pause();
            circle.setVisible(true);
            circleBtn.setText("Play Circle");
        }else if(timeline2.getStatus() == Animation.Status.RUNNING){
            timeline2.pause();
            circleBtn.setText("Play Circle");
        }else{
            if(new Random().nextInt(2)==0)
                timeline1.play();
            else
                timeline2.play();
            circleBtn.setText("Pause Circle");
        }
    }
}