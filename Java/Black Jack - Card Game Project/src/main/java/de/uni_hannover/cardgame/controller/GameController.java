package de.uni_hannover.cardgame.controller;


import de.uni_hannover.cardgame.base.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * @author Anas Salameh
 * @email anas.salameh.97@outlook.com
 * **/
public class GameController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    public Player player;
    PlayingField playingField;

    @FXML
    Text betTxt;

    @FXML
    Text creditTxt;

    @FXML
    Button placeBetButton;

    @FXML
    Button cancelButton;

    public void loadGame(Player player){
        this.player = player;
        this.playingField = new PlayingField(player);
        update();
    }

    private void switchToInGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateLobbyController.class.getResource("/InGame.fxml"));
        Parent root = fxmlLoader.load();
        CreateLobbyController.getPrimaryStage().getScene().setRoot(root);

        InGameController inGameController = fxmlLoader.getController();
        inGameController.loadInGame(player, playingField);

    }

    public void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Lobby.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void placeBet() throws IOException {
        System.out.println("Bet Dealt Game Start");
        player.placeBet(playingField.getChips());
        playingField.start();
        switchToInGame();
    }



    @FXML
    protected void cancelBet() {
        System.out.println("Bet was canceled!");
        playingField.deleteBet();
        update();
    }


    @FXML
    protected void betHundred() {
        System.out.println("add 100 €");
        playingField.addBet(new Chip(100));
        update();
    }

    @FXML
    protected void betFiveHundred() {
        System.out.println("add 500 €");
        playingField.addBet(new Chip(500));
        update();
    }

    @FXML
    protected void betOneThousend() {
        System.out.println("add 1000 €");
        playingField.addBet(new Chip(1000));
        update();
    }

    @FXML
    protected void betFiveThousend() {
        System.out.println("add 5000 €");
        playingField.addBet(new Chip(5000));
        update();
    }




    private void update() {
        if (playingField.getValueOfBet() == 0){
            cancelButton.setDisable(true);
            placeBetButton.setDisable(true);
        }else{
            cancelButton.setDisable(false);
            placeBetButton.setDisable(false);
        }

        betTxt.setText(playingField.getValueOfBetPrinted());
        creditTxt.setText(player.getCreditPrinted());
    }


}
