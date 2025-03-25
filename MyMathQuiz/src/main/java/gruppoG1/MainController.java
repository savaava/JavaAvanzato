package gruppoG1;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Label introductionText;

    @FXML
    private Label nameText;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label questionsText;

    @FXML
    private TextField questionsTextField;

    @FXML
    private Button startButton;

    @FXML
    private Label surnameText;

    @FXML
    private TextField surnameTextField;

    @FXML
    public void initialize() {
        startButton.disableProperty().bind(
                Bindings.or(
                    Bindings.or(
                            nameTextField.textProperty().isEmpty(),
                            surnameTextField.textProperty().isEmpty()
                    ),
                    Bindings.or(
                            questionsTextField.textProperty().isEmpty(),
                            Bindings.not(Bindings.createBooleanBinding(() ->
                                    questionsTextField.getText().matches("-?\\d+"), questionsTextField.textProperty()
                            ))
                    )
                )
        );

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText("Errore");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void switchToQuizScene() throws IOException {
        Stage stage = (Stage) introductionText.getScene().getWindow();

        FXMLLoader quizLoader = new FXMLLoader(getClass().getResource("/quiz.fxml"));
        Scene quizScene = new Scene(quizLoader.load());
        QuizController qc = quizLoader.getController();

        String playerName = nameTextField.getText();
        String playerSurname = surnameTextField.getText();
        int numberOfQuestions = Integer.parseInt(questionsTextField.getText());

        Quiz q = new Quiz(playerName,playerSurname);

        for(int i=0;i<numberOfQuestions;i++){
            q.addQuestion(new NumericQuestionAttempt(new NumericQuestion(),null));
        }

        qc.setQuiz(q);

        stage.setScene(quizScene);
    }

    @FXML
    private void onStartBtn() throws IOException {
        if(questionsTextField.getText().matches("0") || !(questionsTextField.getText().matches("\\d+"))) {
            showAlert("Scegliere un valore intero positivo.");
            return;
        }

        switchToQuizScene();
    }
}

