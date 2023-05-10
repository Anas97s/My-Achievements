package de.uni_hannover.cardgame;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Anas Salameh
 * @email anas.salameh.97@outlook.com
 * **/
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("21 to Win");
        stage.setResizable(false);
        stage.setScene(scene);

        stage.show();


    }
}
