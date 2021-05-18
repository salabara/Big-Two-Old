package pokercard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class PokerCard extends ImageView {

    public PokerCard(int suit, int value, int rank, Image image) throws FileNotFoundException {
        this.suit = suit;
        this.value = value;
        this.rank = rank;
        setImage(image);
    }

    //getters
    public int getSuit() {
        return suit;
    }
    public int getValue() {
        return value;
    }
    public int getRank() {
        return rank;
    }

    //Read the file and generate the Image array for cards
    //the way of using PixelReader and WritableImage is based on: https://stackoverflow.com/questions/14802374/effective-image-cropping-in-javafx
    public static Image[][] loadImage(String filePath, int row, int column) throws FileNotFoundException {
        final Image pokerGrid = new Image(new FileInputStream(filePath));
        final double cardHeight;
        final double cardWidth;
        Image[][] image = new Image[row][column];

        PixelReader reader = pokerGrid.getPixelReader();

        cardHeight = pokerGrid.getHeight() / row;
        cardWidth = pokerGrid.getWidth() / column;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                image[i][j] = new WritableImage(reader, (int) (cardWidth * j), (int) (cardHeight * i), (int) (cardWidth), (int) (cardHeight));
            }
        }
        return image;
    }

    //compare two PokerCard based on the their rank 
    public static final Comparator cardRankComparator() {
        return new Comparator<PokerCard>() {
            @Override
            public int compare(PokerCard pc1, PokerCard pc2) {
                return pc1.rank - pc2.rank;
            }

        };
    }

    //generate the array for cardRank
    public static int[][] cardRankArray(String filePath) throws FileNotFoundException {
        int[][] cardRanksArray;

        File inputFile = new File(filePath);
        Scanner input = new Scanner(inputFile);
        cardRanksArray = new int[input.nextInt()][input.nextInt()];
        for (int i = 0; i < cardRanksArray.length; i++) {
            for (int j = 0; j < cardRanksArray[i].length; j++) {
                cardRanksArray[i][j] = input.nextInt();
            }
        }
        input.close();
        return cardRanksArray;
    }

    private static int[][] cardRanks;//indicate the rank of each card
    private final int suit;
    private final int value;
    private final int rank;

}
