package de.uni_hannover.cardgame.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * @author Anas Salameh
 * @email anas.salameh.97@outlook.com
 * **/
public class JoinLobbyController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void joinLobby(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CreateLobby.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Lobby.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void checkChatActions(KeyEvent keyEvent) {
    }
}
