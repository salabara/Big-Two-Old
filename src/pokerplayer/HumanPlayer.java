package pokerplayer;

import game.GameControl;
import java.util.ArrayList;
import java.util.Collections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pokercard.PokerCard;

public final class HumanPlayer extends PokerPlayer {

    public HumanPlayer(GameControl gameControl, int yourNumber, boolean isHorizontal) {
        super(gameControl, yourNumber);
        if (isHorizontal) {
            playerBoard = new HBox();
            handBoard = new HBox();
        } else {
            playerBoard = new VBox();
            handBoard = new VBox();
        }
        this.isHorizontal = isHorizontal;
        handBoard.setVisible(false);
        playCardsButton = new Button("Play Cards!");
        pauseButton = new Button("Pause!");
        initialNodes();
    }

    //initial all the button of human player
    private void initialNodes() {
        initialPlayCardsButton();
        initialPause();
        initialPlayerBoard();
    }

    //renew the playerBoard, which is the visual display of handList and buttons
    //should be call everytime the handList is changed
    //the way of using setOnMouseClicked is based on: https://stackoverflow.com/questions/52229984/javafx-imageview-actions
    @Override
    public void resetPlayerBoard() {
        Collections.sort(handList, PokerCard.cardRankComparator());

        handBoard.getChildren().clear();
        for (int i = 0; i < handList.size(); i++) {
            StackPane cardPane = new StackPane(handList.get(i));

            //the action to indicate card get selected or not
            cardPane.setOnMouseClicked(e -> {
                if (isHorizontal) {
                    if (cardPane.getPadding().getBottom() == 0) {
                        cardPane.setPadding(new Insets(0, 0, 50, 0));
                    } else {
                        cardPane.setPadding(new Insets(0, 0, 0, 0));
                    }
                } else {
                    if (cardPane.getPadding().getRight() == 0) {
                        cardPane.setPadding(new Insets(0, 25, 0, 0));
                    } else {
                        cardPane.setPadding(new Insets(0, 0, 0, 0));
                    }
                }
            });

            handBoard.getChildren().add(cardPane);
        }
        if (isHorizontal) {
            ((HBox) handBoard).setSpacing(2);
            if (!handList.isEmpty()) {
                handBoard.setPrefHeight(handList.get(0).getFitHeight() + 50);
            }
        } else {
            ((VBox) handBoard).setSpacing(2);
            if (!handList.isEmpty()) {
                handBoard.setPrefWidth(handList.get(0).getFitWidth() + 25);
            }
        }
    }

    //assign the function to playCardsButton
    private void initialPlayCardsButton() {
        //set the action
        playCardsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isYourTurn) {
                    ArrayList<PokerCard> selection = new ArrayList<>();
                    //add every selected card to selection
                    for (int i = 0; i < handBoard.getChildren().size(); i++) {
                        //check if the card is selected
                        if (isHorizontal) {
                            if (((StackPane) (handBoard.getChildren().get(i))).getPadding().getBottom() != 0) {
                                selection.add((PokerCard) handList.get(i));
                            }
                        } else {
                            if (((StackPane) (handBoard.getChildren().get(i))).getPadding().getRight() != 0) {
                                selection.add((PokerCard) handList.get(i));
                            }
                        }
                    }
                    //check if the selection is empty, since player could pause but should not play no card
                    if (!selection.isEmpty()) {
                        //the actual action
                        //checking if the play is valid
                        if (isAllPause()) {//if all the other player isPause their round
                            if (gameControl.isFisrtMove()) {
                                boolean isIncludeCloverThree = false;
                                for (int i = 0; i < selection.size(); i++) {
                                    if (selection.get(i).getValue() == 2 && selection.get(i).getSuit() == 2) {
                                        isIncludeCloverThree = true;
                                    }
                                }
                                if (isIncludeCloverThree && whichComb(selection, gameControl.getCombination()) != -1) {
                                    gameControl.setFirstMove(false);
                                    playTheCards(selection);
                                }
                            } else {
                                if (whichComb(selection, gameControl.getCombination()) != -1) {
                                    playTheCards(selection);
                                }
                            }
                        } else {//if not all the other player pause their round
                            int selectionType = whichComb(selection, gameControl.getCombination());
                            int currentPlayType = whichComb(gameControl.getCurrentPlayList(), gameControl.getCombination());
                            //check if the selection and currentPlayList have same combRank, and selection got higher setRank
                            if (selectionType == currentPlayType && gameControl.getCombination()[selectionType].isCombination(selection) > gameControl.getCombination()[currentPlayType].isCombination(gameControl.getCurrentPlayList())) {
                                playTheCards(selection);
                            } else if (gameControl.getCombination()[selectionType].getCombRank() > gameControl.getCombination()[currentPlayType].getCombRank()) {//the combRank of the selection is higher than the combRank of the currentPlayList 
                                playTheCards(selection);
                            }
                        }
                    }
                }
            }
        });
    }

    //assign the function to pauseButton
    private void initialPause() {
        pauseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isYourTurn) {
                    pauseTheRound();
                }
            }
        });
    }

    //initial the playerBoard
    private void initialPlayerBoard() {
        VBox buttonPane = new VBox();
        buttonPane.getChildren().addAll(playerInfo, playCardsButton, pauseButton);
        buttonPane.setAlignment(Pos.CENTER);

        playerBoard.getChildren().addAll(handBoard, buttonPane);

        if (isHorizontal) {
            ((HBox) playerBoard).setAlignment(Pos.CENTER);
        } else {
            ((VBox) playerBoard).setAlignment(Pos.CENTER);
        }
    }

    public Pane getHandBoard() {
        return handBoard;
    }

    private Pane handBoard;
    private Button playCardsButton;
    private Button pauseButton;
    private boolean isHorizontal;
}
