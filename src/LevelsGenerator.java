import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;



import java.util.ArrayList;
import java.util.Random;


public class LevelsGenerator {

    static int minesDigit;
    private ArrayList<Cell> cells;
    static private ArrayList<Cell> bombs;
    static private Pane root;
    static private Pane rootGameOver;
    static private Pane rootWin;
    static private Pane mainRoot;
    private Scene scene;
    int weight;
    int hight;


    public LevelsGenerator(int weight, int hight, int minesDigit) {
        this.weight = weight;
        this.minesDigit = minesDigit;
        this.hight = hight;
        cells = new ArrayList<>();
        bombs = new ArrayList<>();
        int[] numbersOfMines = new int[minesDigit];//массив, который хранит номера мин
        int digit;//Промежуточная переменная для избежания повторения позиций мин
        for (int i = 0; i < minesDigit; i++) {
            digit = new Random().nextInt(weight * hight);
            while (contains(digit, numbersOfMines))//Цикл для избежания повторения позиций мин
                digit = new Random().nextInt(weight * hight);

            numbersOfMines[i] = digit;
        }
        //Все что выше для генерации номеров мин

        for (int i = 0; i < weight; i++)
            for (int j = 0; j < hight; j++) {
                if (contains(weight * i + j, numbersOfMines)) {
                    bombs.add(new Cell(9, j + 1, i + 1, weight));
                    cells.add(bombs.get(bombs.size() - 1));
                } else
                    cells.add(new Cell(0, j + 1, i + 1, weight));
            }


        mainRoot = new Pane();
        root = new Pane();
        rootGameOver = new Pane(new Label("GAME OVER"));
        rootWin = new Pane(new Label("WIN"));

        searchMine(numbersOfMines);

        for (Cell cell : cells)
            root.getChildren().addAll(cell, cell.getMyContent());


        rootGameOver.setVisible(false);
        rootWin.setVisible(false);
        mainRoot.getChildren().addAll(root, rootGameOver, rootWin);
        scene = new Scene(mainRoot, Game.getWidth(), Game.getHeight());


    }

    private boolean contains(int dig, int[] mass) {
        for (int i = 0; i < mass.length; i++)
            if (dig == mass[i])
                return true;

        return false;

    }

    private void searchMine(int[] numbersOfMines) {

        for (int i = 0; i < numbersOfMines.length; i++) {

            if ((numbersOfMines[i]) % weight != weight - 1)
                cells.get(numbersOfMines[i] + 1).addCondition();

            if (numbersOfMines[i] % weight != 0)
                cells.get(numbersOfMines[i] - 1).addCondition();

            if (numbersOfMines[i] / weight != 0) {

                cells.get(numbersOfMines[i] - weight).addCondition();

                if (numbersOfMines[i] % weight != weight-1)
                    cells.get(numbersOfMines[i] - weight + 1).addCondition();

                if (numbersOfMines[i] % weight != 0)
                    cells.get(numbersOfMines[i] - weight - 1).addCondition();

            }
            if (numbersOfMines[i] / weight != hight - 1) {

                cells.get(numbersOfMines[i] + weight).addCondition();

                if (numbersOfMines[i] % weight != weight-1)
                    cells.get(numbersOfMines[i] + weight+1).addCondition();

                if (numbersOfMines[i] % weight != 0)
                    cells.get(numbersOfMines[i] + weight - 1).addCondition();
            }
        }

        for (Cell cell : cells)
            cell.getMyContent().setText("" + cell.getConditon());


    }

    static void gameOver() {
        root.setVisible(false);
        rootGameOver.setVisible(true);
    }

    static void gameWin() {
        root.setVisible(false);
        rootWin.setVisible(true);
    }

    public static ArrayList<Cell> getBombs() {
        return bombs;
    }

    public Scene getScene() {
        return scene;
    }

    static public int getMinesDigit() {
        return minesDigit;
    }
}
