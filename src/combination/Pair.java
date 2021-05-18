package combination;

import pokercard.PokerCard;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class Pair extends Combination {

    public Pair() throws FileNotFoundException {
        setName("Pair");
        setRank(0);
    }

    @Override
    public int isCombination(ArrayList<PokerCard> selection) {
        if (selection.size() != 2) {
            return -1;
        } else {
            Collections.sort(selection, PokerCard.cardRankComparator());
            return selection.get(0).getValue() == selection.get(1).getValue() ? selection.get(1).getRank() : -1;
        }
    }

}
