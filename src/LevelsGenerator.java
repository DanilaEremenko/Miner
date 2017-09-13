import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;


public class LevelsGenerator {
    private ArrayList<Cell> cells;
    private Pane root;


    public LevelsGenerator(int weight, int hight, int minesDigit) {
        cells = new ArrayList<>();
        int condition;
        int acctuallyMinesDigit = 0;

        for (int i = 1; i <= weight; i++)
            for (int j = 1; j <= hight; j++)
                if (acctuallyMinesDigit < minesDigit) {
                    condition = new Random().nextInt(2) + new Random().nextInt(2);
                    if (condition == 2) {
                        cells.add(new Cell(9, i, j));
                    } else
                        cells.add(new Cell(0, i, j));
                    //               if (i == weight && j == hight){
                    //                 i=1;
                    //               j=1;
                    //         }

                } else
                    break;

        root = new Pane();
        for (Cell cell : cells) {
            root.getChildren().addAll(cell);

        }


    }

    public Pane getRoot() {
        return root;
    }
}
