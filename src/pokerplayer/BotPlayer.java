package pokerplayer;

import game.GameControl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import pokercard.PokerCard;

//This class is not using in this version, since I can't figure out how to do thread or task properly in this project.
//Remaining this is for letting you know what this should look like, and why I create both PokerPlayer and HumanPlayer instead of just PokerPlayer one class 
public final class BotPlayer extends PokerPlayer {

    public BotPlayer(GameControl gameControl, int yourNumber) {
        super(gameControl, yourNumber);
    }

    //this should include a algorythm for bot to play card, unfortuantly I can't make any BotPlayer valid in this project, so I didn't really implement the algorythm.
    //the following is orginally designed for testing, however is also not valid to run, because I don't know how to use thread or task in JavaFx.
    public void makeMove() {
        isYourTurn = true;

        System.out.println("It's the turn of player " + (playerNumber + 1) + "!");

        String[] suitString = {"♠", "\u001B[31m♥\u001B[0m", "♣", "\u001B[31m♦\u001B[0m"};
        String[] numberString = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        Collections.sort(handList, PokerCard.cardRankComparator());
        System.out.println("Your hand is:");
        for (int i = 0; i < handList.size(); i++) {
            System.out.println((i + 1) + ". " + suitString[handList.get(i).getSuit()] + numberString[handList.get(i).getValue()]);
        }

        boolean validMove = false;
        while (!validMove) {
            System.out.println("Please make your move!");
            System.out.println("Input the number for the card you choose in one line.");
            System.out.println("Input only zero for pausing round.");

            ArrayList<PokerCard> selection = new ArrayList<>();
            Scanner kb = new Scanner(System.in);

            //add every selected card to selection
            int fisrtCard = kb.nextInt();
            if (fisrtCard == 0) {
                pauseTheRound();
                validMove = true;
            } else {
                selection.add(handList.get(fisrtCard - 1));
                while (kb.hasNextInt()) {
                    selection.add(handList.get(kb.nextInt() - 1));
                }

                //the actual action
                //checking if the play is valid
                if (isAllPause()) {//if all the other player isPause their round
                    if (whichComb(selection, gameControl.getCombination()) != -1) {
                        playTheCards(selection);
                        validMove = true;
                    }
                } else {//if not all the other player isPause their round
                    int selectionType = whichComb(selection, gameControl.getCombination());
                    int currentPlayType = whichComb(gameControl.getCurrentPlayList(), gameControl.getCombination());

                    if (selectionType == currentPlayType && gameControl.getCombination()[selectionType].isCombination(selection) > gameControl.getCombination()[currentPlayType].isCombination(gameControl.getCurrentPlayList())) {
                        playTheCards(selection);
                        validMove = true;
                    }
                }
            }
        }

    }

    @Override
    public void resetPlayerBoard() {
        StackPane pane = new StackPane(playerInfo);
        pane.setAlignment(Pos.CENTER);
        playerBoard = pane;
    }

}
