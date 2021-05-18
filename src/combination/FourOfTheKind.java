package combination;

import pokercard.PokerCard;
import java.util.ArrayList;
import java.util.Collections;

public class FourOfTheKind extends Combination{

    public FourOfTheKind() {
        setName("Four Of The Kind");
        setRank(1);        
    }

    @Override
    public int isCombination(ArrayList<PokerCard> selection) {
        if (selection.size() != 5) {
            return -1;
        } else {
            Collections.sort(selection, PokerCard.cardRankComparator());
            boolean cardMatch = selection.get(1).getValue() == selection.get(2).getValue()
                    && selection.get(2).getValue() == selection.get(3).getValue()
                    && (selection.get(2).getValue() == selection.get(0).getValue() || selection.get(2).getValue() == selection.get(4).getValue());
            if (cardMatch) {
                return setRank[selection.get(2).getValue()];
            }
            return -1;
        }
    }

    private int[] setRank = {11, 12, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
}
