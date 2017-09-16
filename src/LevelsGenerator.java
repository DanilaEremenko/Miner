import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;


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
        this.minesDigit=minesDigit;
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

        for (int i = 1; i <= weight; i++)
            for (int j = 1; j <= hight; j++) {
                if (contains(weight * (i - 1) + j, numbersOfMines)) {
                    bombs.add(new Cell(9, j, i, weight));
                    cells.add(bombs.get(bombs.size()-1));
                } else
                    cells.add(new Cell(0, j, i, weight));
            }


        mainRoot = new Pane();
        root = new Pane();
        rootGameOver = new Pane(new Label("GAME OVER"));
        rootWin = new Pane(new Label("WIN"));
        for (Cell cell : cells) {
            if (cell.getConditon() != 9)
                cell.getMyContent().setText("" + searchMine(cell, cells));


            root.getChildren().addAll(cell, cell.getMyContent());
        }

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

    private int searchMine(Cell cell, ArrayList<Cell> cells) {
        int digit = 0;
        if (cell.getNumberInArray() < 80 && cell.getX() != weight)
            if (cells.get(cell.getNumberInArray() + 1).getConditon() == 9)
                digit++;
        if (cell.getNumberInArray() != 0 && cell.getX() != 1)
            if (cells.get(cell.getNumberInArray() - 1).getConditon() == 9)
                digit++;
        if (cell.getY() != 1) {
            if (cells.get(cell.getNumberInArray() - weight).getConditon() == 9)
                digit++;
            if (cell.getX() != weight)
                if (cells.get(cell.getNumberInArray() - weight + 1).getConditon() == 9)
                    digit++;
            if (cell.getNumberInArray() > 9 && cell.getX() != 1)
                if (cells.get(cell.getNumberInArray() - weight - 1).getConditon() == 9)
                    digit++;
        }
        if (cell.getY() != hight) {
            if (cells.get(cell.getNumberInArray() + weight).getConditon() == 9)
                digit++;
            if (cell.getNumberInArray() < 71 && cell.getX() != weight)
                if (cells.get(cell.getNumberInArray() + weight + 1).getConditon() == 9)
                    digit++;
            if (cell.getX() != 1)
                if (cells.get(cell.getNumberInArray() + weight - 1).getConditon() == 9)
                    digit++;
        }


        return digit;
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
