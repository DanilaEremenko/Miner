import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;


import java.util.ArrayList;
import java.util.Random;
//Периодически выставляется меньше мин чем надо


public class Level {

    static private ArrayList<Cell> cells;
    static private ArrayList<Cell> bombs;
    static private Pane root;//Игровая панель
    static private Pane rootGameOver;//Панель выигрыша
    static private Pane rootWin;//Панель проигрыша
    static private Pane mainRoot;//Панель на которой хранятся все остальные панели
    private Scene scene;
    private static int minesDigit;//Колличество мин
    private static int levelWeight;//Число мин в ширину
    private static int levelHight;//Число мин в высоту


    public Level(int weight, int hight, int minesDigit) {
        levelWeight = weight;
        this.minesDigit = minesDigit;
        levelHight = hight;
        cells = new ArrayList<>();
        bombs = new ArrayList<>();

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

        for (Cell bomb : bombs)
            bomb.setConditions();


        for (Cell cell : cells)
            cell.setText();

        for (Cell cell : cells)
            root.getChildren().addAll(cell, cell.getMyContent());


        rootGameOver.setVisible(false);
        rootWin.setVisible(false);
        mainRoot.getChildren().addAll(root, rootGameOver, rootWin);
        scene = new Scene(mainRoot, Game.getWidth(), Game.getHeight());
        Bot myManBot = new Bot(this);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {

                case C:
                    myManBot.helpMeBot();
                    break;

                case ESCAPE:
                    checkAll();
                    break;

            }
        });

    }

    //Метод для обеспечения отсутсвия повторов номеров мин
    private static boolean contains(int dig, int[] mass) {
        for (int number : mass)
            if (dig == number)
                return true;

        return false;

    }


    /*Все что выше для конструктора
    *
    *
    *
    *
    *
    * Все что выше для конструктора*/


    // Установка панели проигрышка
    static void gameOver() {
        root.setVisible(false);
        rootGameOver.setVisible(true);
    }

    //Установка панели победы
    static void gameWin() {
        root.setVisible(false);
        rootWin.setVisible(true);
    }

    int check(int numberInArray) {
        return cells.get(numberInArray).check();

    }

    void checkAll() {
        for (Cell cell : cells) {
            cell.setVisible(false);
            cell.getMyContent().setVisible(true);

        }
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

    static int getLevelWeight() {
        return levelWeight;
    }

    static int getLevelHight() {
        return levelHight;
    }


    static ArrayList<Cell> getCells() {
        return cells;
    }
}

