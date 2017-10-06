import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;


import java.util.ArrayList;

import java.util.Random;
//Периодически выставляется меньше мин чем надо


class Level {

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
    static private Bot myManBot;

    Level(int weight, int hight, int minesDigit) {
        levelWeight = weight;
        this.minesDigit = minesDigit;
        levelHight = hight;
        cells = new ArrayList<>();
        bombs = new ArrayList<>();
        for (int i = 0; i < hight; i++)
            for (int j = 0; j < weight; j++)
                cells.add(new Cell(0, j + 1, i + 1, i * weight + j));

        for (Cell cell : cells)
            cell.setNearlyCell(cells);

        //int[] numbersOfMines = {16, 34, 68, 18, 9, 6, 66, 20, 11, 52};
        int[] numbersOfMines = generateNumbersOfMines();//массив, который хранит номера мин
        for (int i = 0; i < hight; i++)
            for (int j = 0; j < weight; j++) {
                if (contains(weight * i + j, numbersOfMines)) {
                    cells.get(i * weight + j).setConditon(9);
                    bombs.add(cells.get(i * weight + j));//тут лучше бы переворачивать
                }
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
        myManBot = new Bot();

        //Ниже значение кнопок
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
    //Необходимо избавиться/переделать

    private static boolean contains(int dig, int[] mass) {
        for (int number : mass)
            if (dig == number)
                return true;

        return false;

    }//Проверка наличия числа в массиве

    //общий метод вынесенный из reload и конструктора
    private int[] generateNumbersOfMines() {
        int[] numbersOfMines = new int[minesDigit];
        int digit;//Промежуточная переменная для избежания повторения позиций мин
        for (int i = 0; i < minesDigit; i++) {
            digit = new Random().nextInt(levelWeight * levelHight - 1);
            while (contains(digit, numbersOfMines))//Цикл для избежания повторения позиций мин
                digit = new Random().nextInt(levelWeight * levelHight);

            numbersOfMines[i] = digit;
        }
        return numbersOfMines;
    }//Генерирование индексов мин без повторов


    /*Все что выше для конструктора
    *
    *
    *
    *
    *
    * Все что выше для конструктора*/


    //Перезагрузка уровня
    void reload() {
        rootGameOver.setVisible(false);
        rootWin.setVisible(false);
        root.setVisible(true);
        bombs.clear();
        for (Cell cell : cells) {
            cell.setConditon(0);
            cell.getMyContent().setVisible(false);
            cell.setVisible(true);
            cell.setChecked(false);
        }

        int[] numbersOfMines = generateNumbersOfMines();//массив, который хранит номера мин

        for (int i = 0; i < levelHight; i++)
            for (int j = 0; j < levelWeight; j++) {
                if (contains(levelWeight * i + j, numbersOfMines)) {

                    cells.get(i * levelWeight + j).setConditon(9);
                    bombs.add(cells.get(i * levelWeight + j));
                }

                myManBot.reload();
            }


        for (Cell bomb : bombs)
            bomb.setConditions();


        for (Cell cell : cells)
            cell.setText();



    }

    //Установка панели победы(при успешном прохождении игры)
    static void gameWin() {
        System.out.println("You win");
//        root.setVisible(false);
//        rootWin.setVisible(true);
    }

    // Установка панели проигрыша(при вскрытии бомбы)
    static void gameOver() {
        root.setVisible(false);
        rootGameOver.setVisible(true);
    }

    //Показывает изначальные условия(для кнопки ESC)
    private void checkAll() {
        for (Cell cell : cells) {
            cell.setVisible(false);
            cell.getMyContent().setVisible(true);

        }
        for (Cell bomb : bombs)
            System.out.print("" + bomb.getNumberInArray() + ",");
        rootGameOver.setVisible(false);
        rootWin.setVisible(false);
        root.setVisible(true);


        System.out.println("Колличество бомб " + bombs.size());

    }


    //Геттеры
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

