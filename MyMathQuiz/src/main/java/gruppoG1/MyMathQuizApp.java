package gruppoG1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MyMathQuizApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader main = new FXMLLoader(getClass().getResource("/main.fxml"));
        System.out.println(main);
        Parent root = main.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("MyMathQuizApp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
