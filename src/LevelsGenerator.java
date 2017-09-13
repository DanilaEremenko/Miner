import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;


public class LevelsGenerator {
    private ArrayList<Cell> cells;

    private Pane root;


    public LevelsGenerator(int weight, int hight, int minesDigit) {
        cells = new ArrayList<>();
        int[] numbersOfMines = new int[minesDigit];
        int digit;
        for (int i = 0; i < minesDigit; i++) {
            digit = new Random().nextInt(weight * hight);
            while (contains(digit, numbersOfMines))
                digit = new Random().nextInt(weight * hight);

            numbersOfMines[i] = digit;
        }


        for (int i = 1; i <= weight; i++)
            for (int j = 1; j <= hight; j++) {
                if (contains((i - 1) * 10 + (j - 1), numbersOfMines))
                    cells.add(new Cell(9, j, i));
                else
                    cells.add(new Cell(0, j, i));
            }


        root = new Pane();
        for (Cell cell : cells) {
            root.getChildren().addAll(cell);

        }


    }

    private boolean contains(int dig, int[] mass) {
        for (int i = 0; i < mass.length; i++)
            if (dig == mass[i])
                return true;

        return false;

    }

    public Pane getRoot() {
        return root;
    }
}
