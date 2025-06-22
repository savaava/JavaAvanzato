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
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalTime;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TimerTask;
import java.util.function.Supplier;

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


    @FXML
    private Rectangle rectangle;
    @FXML
    private Button rectBtn;
    private PauseTransition pauseTransition0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initBindingsWithServiceProperty();
        myService.setOnSucceeded(_ -> testBtn.setText("Test Service"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        initTimelineForCircle();

        initScheduledServiceForTime();

        initTransitions();
    }

    private void initBindingsWithServiceProperty(){
        titleLbl.visibleProperty().bind(myService.runningProperty());
        titleLbl.textProperty().bind(myService.titleProperty());

        progressBar.visibleProperty().bind(myService.runningProperty());
        progressBar.progressProperty().bind(myService.progressProperty());

        progressMessageLbl.visibleProperty().bind(myService.runningProperty());
        progressMessageLbl.textProperty().bind(myService.messageProperty());

        //testBtn.disableProperty().bind(myService.runningProperty());

        usersTable.itemsProperty().bind(myService.valueProperty());
    }

    private void initTimelineForCircle(){
        timeline1 = new Timeline(
                new KeyFrame(Duration.millis(50),
                _ -> circle.setVisible(!circle.isVisible()))
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
    }

    private void initScheduledServiceForTime(){
        ScheduledService<String> scheduledService = new ScheduledService<>(){
            @Override
            protected Task<String> createTask(){
                return new Task<>() {
                    @Override
                    protected String call(){
                        return "Orario: " + LocalTime.now().withNano(0);
                    }
                };
            }
        };
        scheduledService.setPeriod(Duration.seconds(1));
        scheduledService.start();
        scheduledService.setOnSucceeded(_ -> orarioLbl.setText(scheduledService.getValue()));
    }

    private void initTransitions(){
        pauseTransition0 = new PauseTransition(Duration.millis(100));
        pauseTransition0.setOnFinished(_ -> {
            rectangle.setVisible(!rectangle.isVisible());
            pauseTransition0.playFromStart(); //loop
        });


        /* Init Sequential transition */
        Supplier<PauseTransition> pauseTransitionFactory = () -> new PauseTransition(Duration.seconds(1));

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), rectangle);
        translateTransition.setByX(100);
        translateTransition.setByY(50);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), rectangle);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), rectangle);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.2);

        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(
                pauseTransitionFactory.get(),
                translateTransition,
                scaleTransition,
                pauseTransitionFactory.get(),
                fadeTransition
        );
        sequentialTransition.setCycleCount(Animation.INDEFINITE);
        sequentialTransition.setAutoReverse(true);
        sequentialTransition.play();
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

    @FXML
    public void onRectAction(){
        if(pauseTransition0.getStatus() == Animation.Status.RUNNING){
            pauseTransition0.stop();
            rectangle.setVisible(true);
            rectBtn.setText("Play Rect");
        }else{
            pauseTransition0.play();
            rectBtn.setText("Stop Rect");
        }
    }
}