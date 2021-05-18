package combination;

import pokercard.PokerCard;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class Straight extends Combination {

    public Straight() throws FileNotFoundException {
        setName("Straight");
        setRank(0);
    }

    @Override
    public int isCombination(ArrayList<PokerCard> selection) {
        if (selection.size() != 5) {
            return -1;
        } else {
            Collections.sort(selection, PokerCard.cardRankComparator());
            for (int i = 0; i < combinationSet.length; i++) {
                boolean cardMatch = selection.get(0).getValue() == combinationSet[i][0]
                        && selection.get(1).getValue() == combinationSet[i][1]
                        && selection.get(2).getValue() == combinationSet[i][2]
                        && selection.get(3).getValue() == combinationSet[i][3]
                        && selection.get(4).getValue() == combinationSet[i][4];
                if (cardMatch) {
                    if (i != 8) {
                        return selection.get(4).getRank();
                    } else {
                        return selection.get(3).getRank();
                    }
                }
            }
            return -1;
        }
    }

    final int[][] combinationSet = {
        {2, 3, 4, 5, 6},
        {3, 4, 5, 6, 7},
        {4, 5, 6, 7, 8},
        {5, 6, 7, 8, 9},
        {6, 7, 8, 9, 10},
        {7, 8, 9, 10, 11},
        {8, 9, 10, 11, 12},
        {9, 10, 11, 12, 0},
        {2, 3, 4, 0, 1},
        {2, 3, 4, 5, 1}
    };
}
