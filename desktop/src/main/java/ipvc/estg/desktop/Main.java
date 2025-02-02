package ipvc.estg.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ipvc/estg/desktop/Login/login.fxml")));
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("GymMaster");
        stage.setScene(scene);
        stage.show();
    }
}