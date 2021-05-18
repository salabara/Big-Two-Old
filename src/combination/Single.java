package combination;

import pokercard.PokerCard;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Single extends Combination {

    public Single() throws FileNotFoundException {
        setName("Single");
        setRank(0);
    }

    @Override
    public int isCombination(ArrayList<PokerCard> selection) {
        if (selection.size() != 1) {
            return -1;
        } else {
            return selection.get(0).getRank();
        }
    }    
}
