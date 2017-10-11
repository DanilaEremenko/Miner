import javafx.scene.layout.Pane;


import java.util.ArrayList;

import java.util.Random;
//Периодически выставляется меньше мин чем надо


class Level {

    private ArrayList<Cell> cells;
    private ArrayList<Cell> bombs;
    private Pane root;//Игровая панель
    private int minesDigit;//Колличество мин
    private int levelWidth;//Число клеток в ширину
    private int levelHight;//Число клеток в высоту


    Level(int weight, int hight, int minesDigit) {
        levelWidth = weight;
        this.minesDigit = minesDigit;
        levelHight = hight;
        cells = new ArrayList<>();
        bombs = new ArrayList<>();
        for (int i = 0; i < hight; i++)
            for (int j = 0; j < weight; j++)
                cells.add(new Cell(0, j + 1, i + 1, i * weight + j));

        //int[] numbersOfMines = {16, 34, 68, 18, 9, 6, 66, 20, 11, 52};
        int[] numbersOfMines = generateNumbersOfMines();//массив, который хранит номера мин
        for (int i = 0; i < hight; i++)
            for (int j = 0; j < weight; j++) {
                if (contains(weight * i + j, numbersOfMines)) {
                    cells.get(i * weight + j).setConditon(9);
                    bombs.add(cells.get(i * weight + j));//тут лучше бы переворачивать
                }
            }



        for (Cell cell : cells)
            cell.setNearlyCell(levelWidth,levelHight);

        root = new Pane();
        for (Cell bomb : bombs)
            bomb.setConditions(cells);


        for (Cell cell : cells)
            cell.setText();

        for (Cell cell : cells)
            root.getChildren().addAll(cell, cell.getMyContent());



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
            digit = new Random().nextInt(levelWidth * levelHight - 1);
            while (contains(digit, numbersOfMines))//Цикл для избежания повторения позиций мин
                digit = new Random().nextInt(levelWidth * levelHight);

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
        System.out.println("Перезагрука уровня");
        Game.getRootGameOver().setVisible(false);
        Game.getRootWin().setVisible(false);
        root.setVisible(true);
        bombs.clear();
        for (Cell cell : cells) {
            cell.setConditon(0);
            cell.setFlag(false);
            cell.setStyle(" -fx-base: #FAFAFA;");
            cell.getMyContent().setVisible(false);
            cell.setVisible(true);
            cell.setChecked(false);
        }

        int[] numbersOfMines = generateNumbersOfMines();//массив, который хранит номера мин

        for (int i = 0; i < levelHight; i++)
            for (int j = 0; j < levelWidth; j++) {
                if (contains(levelWidth * i + j, numbersOfMines)) {

                    cells.get(i * levelWidth + j).setConditon(9);
                    bombs.add(cells.get(i * levelWidth + j));
                }

                Game.getMyManBot().reload();
            }


        for (Cell bomb : bombs)
            bomb.setConditions(cells);


        for (Cell cell : cells)
            cell.setText();


    }

    //Установка панели победы(при успешном прохождении игры)
    static void gameWin() {
//        System.out.println("You win");
//        root.setVisible(false);
//        rootWin.setVisible(true);
    }

    // Установка панели проигрыша(при вскрытии бомбы)
    void gameOver() {
        root.setVisible(false);
        Game.getRootGameOver().setVisible(true);
    }

    //Показывает изначальные условия(для кнопки ESC)
    void checkAll() {
        for (Cell cell : cells) {
            if (!cell.isFlag()) {
                cell.setVisible(false);
                cell.getMyContent().setVisible(true);
            }
        }
        for (Cell bomb : bombs)
            System.out.print("" + bomb.getNumberInArray() + ",");
        Game.getRootGameOver().setVisible(false);
        Game.getRootWin().setVisible(false);
        root.setVisible(true);


        System.out.println("Колличество бомб " + bombs.size());

    }


    //Геттеры
    int getMinesDigit() {
        return minesDigit;
    }

    int getLevelWidth() {
        return levelWidth;
    }

    int getLevelHight() {
        return levelHight;
    }

    ArrayList<Cell> getCells() {
        return cells;
    }

    ArrayList<Cell> getBombs() {
        return bombs;
    }

    Pane getRoot() {
        return root;
    }
}

