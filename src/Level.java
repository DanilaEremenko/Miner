import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;


import java.util.ArrayList;
import java.util.Random;


public class Level {

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


    public Level(int weight, int hight, int minesDigit) {
        this.weight = weight;
        this.minesDigit = minesDigit;
        this.hight = hight;
        cells = new ArrayList<>();
        bombs = new ArrayList<>();
        //
        int[] numbersOfMines = new int[minesDigit];//массив, который хранит номера мин
        int digit;//Промежуточная переменная для избежания повторения позиций мин
        for (int i = 0; i < minesDigit; i++) {
            digit = new Random().nextInt(weight * hight);
            while (contains(digit, numbersOfMines))//Цикл для избежания повторения позиций мин
                digit = new Random().nextInt(weight * hight);

            numbersOfMines[i] = digit - 1;
        }
        //Все что выше для генерации номеров мин

        for (int i = 0; i < hight; i++)
            for (int j = 0; j < weight; j++) {
                if (contains(weight * i + j, numbersOfMines)) {
                    bombs.add(new Cell(9, j + 1, i + 1, weight, hight, i * weight + j));
                    cells.add(bombs.get(bombs.size() - 1));
                } else
                    cells.add(new Cell(0, j + 1, i + 1, weight, hight, i * weight + j));
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

    //Метод для обеспечения отсутсвия повторов номеров мин
    private boolean contains(int dig, int[] mass) {
        for (int i = 0; i < mass.length; i++)
            if (dig == mass[i])
                return true;

        return false;

    }

    //установка на клетки номеров, обозначающих колличество мин находящийся рядом
    private void searchMine(int[] numbersOfMines) {

        int nearlyMines[];
        for (int i = 0; i < numbersOfMines.length; i++) {
            nearlyMines = cells.get(numbersOfMines[i]).getNearlyCell();

            for (int numberCell : nearlyMines)
                if (numberCell > 0)
                    cells.get(numberCell).addCondition();

        }
        for (Cell cell : cells)
            cell.getMyContent().setText("" + cell.getConditon());
    }

    //Установка панели проигрышка
    static void gameOver() {
        root.setVisible(false);
        rootGameOver.setVisible(true);
    }

    //Установка панели победы
    static void gameWin() {
        root.setVisible(false);
        rootWin.setVisible(true);
    }

    public void check(int numberInArray) {
        cells.get(numberInArray).check();

    }


    static ArrayList<Cell> getBombs() {
        return bombs;
    }

    Scene getScene() {
        return scene;
    }

    static int getMinesDigit() {
        return minesDigit;
    }
}

