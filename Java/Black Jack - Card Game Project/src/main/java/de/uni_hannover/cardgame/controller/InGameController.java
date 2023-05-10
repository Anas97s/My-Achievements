package de.uni_hannover.cardgame.controller;



import de.uni_hannover.cardgame.base.Card;
import de.uni_hannover.cardgame.base.Phase;
import de.uni_hannover.cardgame.base.Player;
import de.uni_hannover.cardgame.base.PlayingField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
/**
 * @author Anas Salameh
 * @email anas.salameh.97@outlook.com
 * **/
public class InGameController {


    private Stage stage;
    private Scene scene;
    private Parent root;
    private Player player;
    private PlayingField playingField;

    @FXML
    Text betTXT;
    @FXML
    Text playerHandValue;
    @FXML
    Text dealerHandValue;
    @FXML
    StackPane playerPoint;
    @FXML
    StackPane dealerPoint;
    @FXML
    Text WinnerTxt;
    @FXML
    Text LostTxt;
    @FXML
    StackPane winnerRoller;
    @FXML
    StackPane loseRoller;
    @FXML
    Button standButton;
    @FXML
    Button doubleButton;
    @FXML
    Button newGameButton;
    @FXML
    Button hitButton;
    @FXML
    ImageView playerCard1;
    @FXML
    ImageView playerCard2;
    @FXML
    ImageView playerCard3;
    @FXML
    ImageView playerCard4;
    @FXML
    ImageView playerCard5;
    @FXML
    ImageView playerCard6;
    @FXML
    ImageView playerCard7;
    @FXML
    ImageView playerCard8;
    @FXML
    ImageView dealerCard1;
    @FXML
    ImageView dealerCard2;
    @FXML
    ImageView dealerCard3;
    @FXML
    ImageView dealerCard4;
    @FXML
    ImageView dealerCard5;
    @FXML
    ImageView dealerCard6;
    @FXML
    ImageView dealerCard7;
    @FXML
    ImageView dealerCard8;


    public void loadInGame(Player player, PlayingField playingField){
        this.player = player;
        this.playingField = playingField;
        updateStatus();
        update();
    }

    public void switchToGameController() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(CreateLobbyController.class.getResource("/Game.fxml"));
        Parent root = fxmlLoader.load();
        CreateLobbyController.getPrimaryStage().getScene().setRoot(root);

        player.deleteBet();
        player.removeHand();

        GameController gameController = fxmlLoader.getController();
        gameController.loadGame(player);

    }


    @FXML
    protected void Hit() {
        System.out.println("You did Hit!");
        player.Hit(playingField.getDeck());
        playingField.hitCheck();
        update();
        updateStatus();
    }

    @FXML
    protected void stand() {
        System.out.println("You Staned!");
        playingField.getDealer().dealerHit(playingField.getDeck());
        playingField.standCheck();
        update();
        updateStatus();
    }

    @FXML
    protected void doubleBet() {
        System.out.println("You did Double");
        player.doubeBet();
        player.Hit(playingField.getDeck());
        playingField.hitCheck();

        if (playingField.getPhase() == Phase.CONTINUE){
            playingField.getDealer().dealerHit(playingField.getDeck());
            playingField.standCheck();
        }

        doubleButton.setDisable(true);

        update();
        updateStatus();
    }

    @FXML
    protected void newGame() throws IOException {
        System.out.println("New Game has been started!");
        switchToGameController();
    }

    private void updateStatus() {
        switch (playingField.getPhase()){
            case DRAW:
            case PLAYERWIN:
            case PLAYERMAXOUTWIN:
            case PLAYERMAXOUTLOSE:
            case DEALERWIN:
                standButton.setDisable(true);
                hitButton.setDisable(true);
                doubleButton.setDisable(true);
                newGameButton.setVisible(true);
                break;
            case CONTINUE:
                if (player.getHand().size() > 2){
                    doubleButton.setDisable(true);
                }
        }
    }

    private void update() {
        ArrayList<ImageView> playerCards = new ArrayList<>(List.of(playerCard1, playerCard2, playerCard3, playerCard4, playerCard5, playerCard6,
                playerCard7, playerCard8));

        ArrayList<ImageView> dealerCards = new ArrayList<>(List.of(dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5, dealerCard6,
                dealerCard7, dealerCard8));

        Iterator<Card> playerHand = player.getHand().iterator();
        for (int j = 0; j < playerCards.size(); j++) {
            ImageView i = playerCards.get(j);
            if (playerHand.hasNext()){
                String url = playerHand.next().getImageUrl();
                Image image = new Image(Objects.requireNonNull(getClass().getResource(url)).toString());
                i.setVisible(true);
                i.setImage(image);
            }else{
                i.setVisible(false);
            }
        }


        Iterator<Card> dealerHand = playingField.getDealer().getHand().iterator();
        for (int j = 0; j < dealerCards.size(); j++) {
            ImageView i = dealerCards.get(j);
            if (dealerHand.hasNext()){
                String url = dealerHand.next().getImageUrl();
                Image image = new Image(Objects.requireNonNull(getClass().getResource(url)).toString());

                if (playingField.getDealer().getHand().size() == 2 && playingField.getPhase() == Phase.CONTINUE && !dealerHand.hasNext()){
                    url = "/images/card_back_1.gif";
                    image = new Image(Objects.requireNonNull(getClass().getResource(url)).toString());
                }
                i.setVisible(true);
                i.setImage(image);

            }else{
                i.setVisible(false);
            }
        }

        betTXT.setText(player.getCreditPrinted());
        playerHandValue.setText(String.valueOf(player.getHandPoints()));


        if (playingField.getDealer().getHand().size() == 2 && playingField.getPhase() == Phase.CONTINUE){
            dealerPoint.setVisible(false);
        }else{
            dealerPoint.setVisible(true);
            dealerHandValue.setText(String.valueOf(playingField.getDealer().getHandPoints()));
        }


        switch (playingField.getPhase()){
            case DRAW:
                loseRoller.setVisible(true);
                LostTxt.setText("DRAW!");
                break;
            case PLAYERWIN:
                winnerRoller.setVisible(true);
                WinnerTxt.setText("You WON!");
                break;
            case PLAYERMAXOUTWIN:
                winnerRoller.setVisible(true);
                WinnerTxt.setText("MAX WIN!");
                break;
            case  PLAYERMAXOUTLOSE:
                loseRoller.setVisible(true);
                LostTxt.setText("YOU LOST!");
                break;
            case DEALERWIN:
                loseRoller.setVisible(true);
                LostTxt.setText("YOU LOST!");
                break;
            case CONTINUE:
        }

    }

    public void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Lobby.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
