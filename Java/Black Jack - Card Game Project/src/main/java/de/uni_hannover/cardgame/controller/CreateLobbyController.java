package de.uni_hannover.cardgame.controller;


import de.uni_hannover.cardgame.ServerClient.Client;
import de.uni_hannover.cardgame.ServerClient.Server;
import de.uni_hannover.cardgame.base.Phase;
import de.uni_hannover.cardgame.base.Player;
import de.uni_hannover.cardgame.base.PlayingField;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.InetAddress;
/**
 * @author Anas Salameh
 * @email anas.salameh.97@outlook.com
 * **/
public class CreateLobbyController extends Application {

    private Stage stage;
    private Scene scene;
    private Parent root;
    public static InetAddress ipAdresse;
    private PlayingField field;
    private ObservableList<Label> messages;
    private GameController gameController;
    @FXML
    private Label ShowMyIP;
    @FXML
    private Label YourIP;

    @FXML
    public TextArea ChatArea;


    private final Player player;
    private static Stage primaryStage;

    public CreateLobbyController(){
        this.player = new Player();
    }


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        GameController gameController = fxmlLoader.getController();
        gameController.loadGame(player);
        primaryStage = stage;
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void startGame(ActionEvent event) throws Exception {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        start(stage);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Lobby.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void serverConnect(ActionEvent event){

    }

}
