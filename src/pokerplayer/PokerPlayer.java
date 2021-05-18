package pokerplayer;

import combination.Combination;
import game.GameControl;
import pokercard.PokerCard;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class PokerPlayer {

    //Constructor and initial some variables
    public PokerPlayer(GameControl gc, int yourNumber) {
        this.gameControl = gc;
        this.playerNumber = yourNumber;

        handList = new ArrayList<>();
        playerInfo = new Label();
        isYourTurn = false;
        isPause = true;
    }

    //getters and setters
    public Pane getPlayerBoard() {
        return playerBoard;
    }
    public boolean getIsYourTurn() {
        return isYourTurn;
    }
    public void setNextPlayer(PokerPlayer nextPlayer) {
        this.nextPlayer = nextPlayer;
    }
    public void setIsYourTurn(boolean isYourTurn) {
        this.isYourTurn = isYourTurn;
    }

    //add card to handList
    public void getCard(PokerCard card) {
        handList.add(card);
    }

    //play the selected card from hand to table center
    void playTheCards(ArrayList<PokerCard> selection) {
        //change the player state
        isYourTurn = false;
        isPause = false;
        ((HumanPlayer) this).getHandBoard().setVisible(false);

        //change the current play
        gameControl.setCurrentPlayList(selection);

        //replace the visual stuff in center table with new one.
        //remove the card from handList
        HBox centerBoard = new HBox();
        centerBoard.setAlignment(Pos.CENTER);
        gameControl.getTable().setCenter(centerBoard);
        for (int i = 0; i < selection.size(); i++) {
            centerBoard.getChildren().add(selection.get(i));
            handList.remove(selection.get(i));
        }
        //check if the game end, if end, than it will not be the next player's turn
        if (handList.isEmpty()) {
            //display ending message
            gameControl.getGameInfo().setAlignment(Pos.CENTER);
            gameControl.getTable().setCenter(gameControl.getGameInfo());

            gameControl.getGameInfo().setText("Player " + (playerNumber + 1) + " win!");
            //the way of using setTextFill and setFont is based on: http://www.java2s.com/Tutorials/Java/JavaFX/0400__JavaFX_Label.htm
            gameControl.getGameInfo().setTextFill(Color.web("#db5d46"));
            gameControl.getGameInfo().setFont(new Font("Arial", 30));
        } else {
            gameControl.getGameInfo().setText("Player " + (nextPlayer.playerNumber + 1) + "'s turn!");
            centerBoard.getChildren().add(gameControl.getGameInfo());
        }

        //reset the playerBoard which is visual display of handList
        resetPlayerBoard();

        //reset playerInfo
        resetPlayerInfo();

        //check if the game end, if end, than it will not be the next player's turn
        if (!handList.isEmpty()) {
            //change the next player state
            nextPlayer.isYourTurn = true;
            ((HumanPlayer) nextPlayer).getHandBoard().setVisible(true);
        }
    }

    void pauseTheRound() {
        //change the player state
        isYourTurn = false;
        isPause = true;
        ((HumanPlayer) this).getHandBoard().setVisible(false);
        //change the next player state        
        gameControl.getGameInfo().setText("Player " + (nextPlayer.playerNumber + 1) + "'s turn!");
        nextPlayer.isYourTurn = true;
        ((HumanPlayer) nextPlayer).getHandBoard().setVisible(true);
    }

    //reset the playerBoard to make it consisted with the handList 
    abstract public void resetPlayerBoard();

    //renew the information in playerInfo
    public void resetPlayerInfo() {
        playerInfo.setText("Player " + (playerNumber + 1) + "!\nRemain " + handList.size() + " cards!");
    }

    //return if all other players are pause or not
    boolean isAllPause() {
        boolean isAllPause = true;
        for (int i = 0; i < gameControl.getNumOfPlayers(); i++) {
            if (i != playerNumber) {
                if (!gameControl.getPlayer()[i].isPause) {
                    isAllPause = false;
                }
            } else {
                i++;
            }
        }
        return isAllPause;
    }

    //return the type of combination of the input set
    int whichComb(ArrayList<PokerCard> selectedCards, Combination[] combination) {
        int type = -1;
        for (int i = 0; i < combination.length; i++) {
            if (combination[i].isCombination(selectedCards) != -1) {
                type = i;
            }
        }
        return type;
    }

    //return is a player is the first player to play
    public boolean fisrtPlay() {
        for (int i = 0; i < handList.size(); i++) {
            if (handList.get(i).getValue() == 2 && handList.get(i).getSuit() == 2) {
                return true;
            }
        }
        return false;
    }

    GameControl gameControl;
    PokerPlayer nextPlayer;

    ArrayList<PokerCard> handList;
    Pane playerBoard;
    Label playerInfo;

    int playerNumber;
    boolean isYourTurn;
    boolean isPause;
}
