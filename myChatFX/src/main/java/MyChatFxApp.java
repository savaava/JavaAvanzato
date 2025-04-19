import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MyChatFxApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader main = new FXMLLoader(getClass().getResource("views/main.fxml"));
        Parent root = main.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("MyChatFxApp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
