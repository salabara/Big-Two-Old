package combination;

import pokercard.PokerCard;
import java.util.ArrayList;
import java.util.Comparator;

abstract public class Combination {

    public String getName() {
        return name;
    }

    public int getCombRank() {
        return combRank;
    }

    void setName(String name) {
        this.name = name;
    }

    void setRank(int rank) {
        this.combRank = rank;
    }

    abstract public int isCombination(ArrayList<PokerCard> selection);

    public Comparator<ArrayList<PokerCard>> getSetRank() {
        return new Comparator<ArrayList<PokerCard>>() {
            @Override
            public int compare(ArrayList<PokerCard> pc1, ArrayList<PokerCard> pc2) {
                return isCombination(pc1) - isCombination(pc2);
            }
        };
    }

    private String name;
    private int combRank;
}
