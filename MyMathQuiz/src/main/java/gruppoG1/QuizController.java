package gruppoG1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuizController implements Initializable {
    private int timeRemaining = 120;
    private int counter = 0;
    private Quiz quiz;

    @FXML
    private TextField answerField;

    @FXML
    private Button confirmBtn;

    @FXML
    private Label countLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label questionLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            confirmBtnBinding();

            updateCountLabel();
            updateQuestionLabel();

            handleTimer();
        });
    }

    private void confirmBtnBinding() {
        confirmBtn.disableProperty().bind(
                Bindings.or(
                        answerField.textProperty().isEmpty(),
                        Bindings.not(Bindings.createBooleanBinding(() ->
                                answerField.getText().matches("-?\\d+"), answerField.textProperty()
                        ))
                )
        );
    }

    private String formatTime(int seconds) {
        int m = seconds/60;
        int s = seconds%60;
        return m+":"+s;
    }

    private void updateCountLabel(){
        counter++;
        countLabel.setText(counter+"/"+quiz.getQuiz().size());
    }
    private void updateQuestionLabel() {
        questionLabel.setText(quiz.getQuiz().get(counter-1).getQuestion().toString() + "?");
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    private void handleTimer(){
        timeLabel.setText(formatTime(timeRemaining));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(1*1000), e -> {
                    timeLabel.setText(formatTime(--timeRemaining));

                    if(timeRemaining <= 0){
                        timeLabel.setText("timer finished");
                    }
                })
        );

        timeline.setCycleCount(timeRemaining); /* non c'è bisogno che il timer continui indefinitivamente */
        timeline.play();
        timeline.setOnFinished(e -> {
            switchToQuizScene();
        });
    }

    @FXML
    public void onConfirm() {
        Integer givenAnswer = Integer.parseInt(answerField.getText());
        quiz.getQuiz().get(counter-1).setGivenAnswer(givenAnswer);

        answerField.clear();

        if(counter >= quiz.getQuiz().size()){
            switchToQuizScene();
        }else{
            updateCountLabel();
            updateQuestionLabel();
        }
    }

    private void switchToQuizScene() {
        Stage stage = (Stage) answerField.getScene().getWindow();

        FXMLLoader resultsLoader = new FXMLLoader(getClass().getResource("/results.fxml"));
        Scene resultsScene = null;
        try {
            resultsScene = new Scene(resultsLoader.load());
        }catch(IOException ex){
            throw  new RuntimeException(ex);
        }
        ResultsController rc = resultsLoader.getController();

        rc.setQuiz(quiz);

        stage.setScene(resultsScene);
    }
}
