package game;

import pokercard.*;
import combination.*;
import pokerplayer.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class GameControl {

    public GameControl() throws FileNotFoundException {
        File inputFile = new File("data/image/card_information.txt");
        Scanner input = new Scanner(inputFile);
        suitNum = input.nextInt();
        valueNum = input.nextInt();
        input.close();

        initial();
    }

    private void initial() throws FileNotFoundException {
        //initial each items
        initialPokerCards();
        initialCombination();
        initialPlayer();
        initialGame();
        //initial scene
        initialScene();
    }

    private void initialGame() {
        gameIsEnd = false;
        firstMove = true;
        initialDeck();
        dealCards();//initial hands of each polayer
    }

    //link the visual elements object to the actual scene
    //the way of using setAlignment and Pos is based on: https://stackoverflow.com/questions/33773179/center-an-object-in-borderpane and https://stackoverflow.com/questions/26373130/javafx-8-positioning-text-vertical-center-of-hbox
    private void initialScene() {
        table = new BorderPane();
        tableCenter = new HBox();
        tableCenter.getChildren().add(gameInfo);
        tableCenter.setAlignment(Pos.CENTER);

        table.setCenter(tableCenter);
        table.setLeft(player[0].getPlayerBoard());
        table.setTop(player[1].getPlayerBoard());
        table.setRight(player[2].getPlayerBoard());
        table.setBottom(player[3].getPlayerBoard());
    }

    //initial pokerCards
    private void initialPokerCards() throws FileNotFoundException {
        pokerCards = new PokerCard[suitNum][valueNum];
        Image[][] cardImages = PokerCard.loadImage("data/image/poker_grid.jpg", suitNum + 1, valueNum);
        int[][] cardRank = PokerCard.cardRankArray("data/image/card_information.txt");

        for (int i = 0; i < suitNum; i++) {
            for (int j = 0; j < valueNum; j++) {
                pokerCards[i][j] = new PokerCard(i, j, cardRank[i][j], cardImages[i][j]);
                pokerCards[i][j].setFitWidth(pokerCards[i][j].getImage().getWidth() / 4);
                pokerCards[i][j].setFitHeight(pokerCards[i][j].getImage().getHeight() / 4);
            }
        }
    }

    //initial all combiantion to combination array
    private void initialCombination() throws FileNotFoundException {
        combination = new Combination[6];
        combination[0] = new Single();
        combination[1] = new Pair();
        combination[2] = new Straight();
        combination[3] = new FullHouse();
        combination[4] = new FourOfTheKind();
        combination[5] = new RoyalStraight();
    }

    //initial player
    private void initialPlayer() {
        player = new HumanPlayer[numOfPlayers];

        for (int i = 0; i < numOfPlayers; i++) {
            player[i] = new HumanPlayer(this, i, i % 2 == 1);
        }

        int[] nextPlayer = {1, 2, 3, 0};
        for (int i = 0; i < numOfPlayers; i++) {
            player[i].setNextPlayer(player[nextPlayer[i]]);
        }
    }

    //initial deck
    private void initialDeck() {
        discardPile = new Stack<>();
        deck = new Stack<>();
        for (int i = 0; i < suitNum; i++) {
            for (int j = 0; j < valueNum; j++) {
                deck.add(pokerCards[i][j]);
            }
        }
        Collections.shuffle(deck);
    }

    //deal cards
    private void dealCards() {
        for (int i = 0; i < numOfPlayers; i++) {
            for (int j = 0; j < valueNum; j++) {
                player[i].getCard(deck.pop());
                if (player[i].fisrtPlay()) {
                    player[i].setIsYourTurn(true);
                    player[i].getHandBoard().setVisible(true);
                    gameInfo = new Label("Player " + (i + 1) + "'s turn!");
                }
            }
            player[i].resetPlayerInfo();
            player[i].resetPlayerBoard();
        }

    }

    //getters and setters
    public int getNumOfPlayers() {
        return numOfPlayers;
    }
    public boolean isGameIsEnd() {
        return gameIsEnd;
    }
    public boolean isFisrtMove() {
        return firstMove;
    }
    public PokerPlayer[] getPlayer() {
        return player;
    }
    public Combination[] getCombination() {
        return combination;
    }
    public ArrayList<PokerCard> getCurrentPlayList() {
        return currentPlayList;
    }
    public BorderPane getTable() {
        return table;
    }
    public Label getGameInfo() {
        return gameInfo;
    }
    public void setGameIsEnd(boolean gameIsEnd) {
        this.gameIsEnd = gameIsEnd;
    }
    public void setFirstMove(boolean fisrtMove) {
        this.firstMove = fisrtMove;
    }
    public void setCurrentPlayList(ArrayList<PokerCard> newCurrentPlayList) {
        if (currentPlayList != null) {
            discardPile.addAll(currentPlayList);
        }
        currentPlayList = newCurrentPlayList;
    }

    //variables
    private final int suitNum;
    private final int valueNum;
    private final int numOfPlayers = 4;
    private boolean firstMove;//indicate is the first move is maded or not, true for not yet
    private boolean gameIsEnd;
    private HumanPlayer[] player;
    private Combination[] combination;
    private Stack<PokerCard> deck;
    private Stack<PokerCard> discardPile;//this is originally for restart the game, but I haven't implement it yet
    private ArrayList<PokerCard> currentPlayList;//store the last play set

    //visual elements
    private BorderPane table;
    private HBox tableCenter;
    private Label gameInfo;//indicate who's turn, and show the game win
    private PokerCard[][] pokerCards;
}
