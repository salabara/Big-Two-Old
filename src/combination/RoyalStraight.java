package combination;

import pokercard.PokerCard;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class RoyalStraight extends Straight {

    public RoyalStraight() throws FileNotFoundException {
        setName("Royal Straight");
        setRank(2);
    }

    @Override
    public int isCombination(ArrayList<PokerCard> selection) {
        if (selection.size() != 5 || !sameSuit(selection)) {
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
                    return selection.get(4).getRank();
                }
            }
            return -1;
        }
    }

    private boolean sameSuit(ArrayList<PokerCard> selection) {
        for (int i = 0; i < selection.size(); i++) {
            if (selection.get(0).getSuit() != selection.get(i).getSuit()) {
                return false;
            }
        }
        return true;
    }
}
