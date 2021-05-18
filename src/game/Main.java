package game;

import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

//game rule please refer the Guild.txt
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        GameControl gameControl = new GameControl();
        System.out.println("test!");

        primaryStage.setTitle("Big Two - Taiwanese Version");
        primaryStage.setScene(new Scene(gameControl.getTable(), 1600, 1000));
        primaryStage.show();

        Stage gameRule = new Stage();
        gameRule.setTitle("Game Rule");
        gameRule.setScene(new Scene(new Label("Highly similar to Big Two\n"
                + "https://en.wikipedia.org/wiki/Big_two\n"
                + "\n"
                + "Player with Clover 3 play first, and Clover 3 should be played in first play.\n"
                + "Players should play specific combination of cards or pause thier round after first round.\n"
                + "Combination of played set should be same as the last played set, unless last play is also you.\n"
                + "	*Four of the kind could player after other combinations except Royal Straight.\n"
                + "	*Royal Straight could player after any other combinations.\n"
                + "The rank of played set should be higher than the last played set, unless last play is also you.\n"
                + "When a player have played all his or her cards. he or she win the game.\n"
                + "\n"
                + "The valid combination is:\n"
                + "	Rank of value: (lowest) 3 4 5 6 7 8 9 10 J Q K A 2    (highest)\n"
                + "	Rank of suit:  (lowest) Clover, Diamond, Heart, Space (highest)\n"
                + "\n"
                + "	Single:			which is 1 card. Compare value first, then compare suit.\n"
                + "	------------------------------------------------------------------------------------------------\n"
                + "	Pair:			which is 2 cards with same value. Rank is base on the rank of value.\n"
                + "	------------------------------------------------------------------------------------------------\n"
                + "	Straight:		which is 5 cards with continue value, the set in this combination are:\n"
                + "				3   4   5   6   7    (lowest rank)\n"
                + "				4   5   6   7   8\n"
                + "				5   6   7   8   9\n"
                + "				6   7   8   9   10\n"
                + "				7   8   9   10  J\n"
                + "				8   9   10  J   Q\n"
                + "				9   10  J   Q   K\n"
                + "				10  J   Q   K   A\n"
                + "				A   2   3   4   5\n"
                + "				2   3   4   5   6    (highest rank)\n"
                + "	------------------------------------------------------------------------------------------------\n"
                + "	Full House:		which is 5 cards with three cards with same value and a pair. Rank is base on the rank of value of the three cards.\n"
                + "	------------------------------------------------------------------------------------------------\n"
                + "	Four of the kind:	which is 5 cards with four cards with same value and a single. Rank is base on the rank of value of the four cards.\n"
                + "				*Spciality: this combination could player after the combinations above.\n"
                + "	------------------------------------------------------------------------------------------------\n"
                + "	Royal Straight:		which is Straight with all cards in same suit. Rank is base on the rank of value of the four cards.\n"
                + "				*Spciality: this combination could player after the combinations above, include Four of the kind.\n"
                + "	------------------------------------------------------------------------------------------------\n"
                + "\n"
                + "	When comparing two set in same combination and rank, compare the suit then."), 800, 650));
        gameRule.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
