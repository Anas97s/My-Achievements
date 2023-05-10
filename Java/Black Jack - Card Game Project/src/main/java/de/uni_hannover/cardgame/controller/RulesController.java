package de.uni_hannover.cardgame.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * @author Anas Salameh
 * @email anas.salameh.97@outlook.com
 * **/
public class RulesController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void gameplay(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/Gameplay.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    public void points(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/Points.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    public void back(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
