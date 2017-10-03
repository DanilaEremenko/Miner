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
    static private Scene scene;
    static private int minesDigit;//Колличество мин
    static private int levelWeight;//Число мин в ширину
    static private int levelHight;//Число мин в высоту


    public Level(int weight, int hight, int minesDigit) {
        levelWeight = weight;
        this.minesDigit = minesDigit;
        levelHight = hight;
        cells = new ArrayList<>();
        bombs = new ArrayList<>();

        int[] numbersOfMines = new int[minesDigit];//массив, который хранит номера мин
        int digit;//Промежуточная переменная для избежания повторения позиций мин
        for (int i = 0; i < minesDigit; i++) {
            digit = new Random().nextInt(weight * hight - 1);
            while (contains(digit, numbersOfMines))//Цикл для избежания повторения позиций мин
                digit = new Random().nextInt(weight * hight);

            numbersOfMines[i] = digit;
        }
        for (int i : numbersOfMines)//для тестирования
            System.out.println(i);

        //Все что выше для генерации номеров мин

        for (int i = 0; i < hight; i++)
            for (int j = 0; j < weight; j++) {
                if (contains(weight * i + j, numbersOfMines)) {
                    bombs.add(new Cell(9, j + 1, i + 1, i * weight + j));
                    cells.add(bombs.get(bombs.size() - 1));
                } else
                    cells.add(new Cell(0, j + 1, i + 1, i * weight + j));
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

                case B:
                    myManBot.helpMeBot();
                    break;

                case ESCAPE:
                    checkAll();
                    break;

                case R:
                    this.reload();
                    break;

            }
        });

    }


    //Метод для обеспечения отсутсвия повторов номеров мин
    //Необходимо избавиться
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


    public void reload() {
        rootGameOver.setVisible(false);
        rootWin.setVisible(false);

        for (Cell cell : cells) {
            cell.setConditon(0);
            cell.getMyContent().setVisible(false);
            cell.setVisible(true);
        }

        int[] numbersOfMines = new int[minesDigit];//массив, который хранит номера мин
        int digit;//Промежуточная переменная для избежания повторения позиций мин
        for (int i = 0; i < minesDigit; i++) {
            digit = new Random().nextInt(levelWeight * levelHight - 1);
            while (contains(digit, numbersOfMines))//Цикл для избежания повторения позиций мин
                digit = new Random().nextInt(levelWeight * levelHight);

            numbersOfMines[i] = digit;
        }


        int bombIndex = 0;
        for (int i = 0; i < levelHight; i++)
            for (int j = 0; j < levelWeight; j++) {
                if (contains(levelWeight * i + j, numbersOfMines)) {
                    bombs.get(bombIndex).setConditon(9);
                    bombIndex++;
                    cells.get(i * levelWeight + j).setConditon(9);
                }


            }

        for (Cell bomb : bombs)
            bomb.setConditions();


        for (Cell cell : cells)
            cell.setText();
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

    static ArrayList<Cell> getBombs() {
        return bombs;
    }

}

