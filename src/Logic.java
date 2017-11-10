import java.util.ArrayList;

import java.util.Random;
//Периодически выставляется меньше мин чем надо


class Logic {

    private Cell[] cells;
    private Cell[] bombs;
    private int minesDigit;//Колличество мин
    private int levelWidth;//Число клеток в ширину
    private int levelHight;//Число клеток в высоту


    Logic(int weight, int hight, int[] numbersOfMines) {
        levelWidth = weight;
        levelHight = hight;
        this.minesDigit = numbersOfMines.length;
        cells = new Cell[weight * hight];
        bombs = new Cell[numbersOfMines.length];
        common(weight, hight, numbersOfMines);
    }

    Logic(int weight, int hight, int minesDigit) {
        levelWidth = weight;
        this.minesDigit = minesDigit;
        levelHight = hight;
        cells = new Cell[hight*weight];
        bombs = new Cell[minesDigit];
        int[] numbersOfMines = generateNumbersOfMines(minesDigit);//массив, который хранит номера мин
        common(weight, hight, numbersOfMines);
    }

    //Общий метод использующийся в конструкторах
    private void common(int weight, int hight, int[] numbersOfMines) {

        for (int i = 0; i < hight; i++)
            for (int j = 0; j < weight; j++)
                cells[i*weight + j] = new Cell(0, j + 1, i + 1, i * weight + j);

        int bombIndex = 0;
        for (int i = 0; i < hight; i++)
            for (int j = 0; j < weight; j++) {
                if (contains(weight * i + j, numbersOfMines)) {
                    cells[i * weight + j].setConditon(9);
                    bombs[bombIndex] = cells[i * weight + j];//тут лучше бы переворачивать
                    bombIndex++;
                }
            }


        for (Cell cell : cells)
            cell.setNearlyCell(levelWidth, levelHight);


        for (Cell bomb : bombs)
            bomb.setConditions(cells);


    }


    //Метод для обеспечения отсутсвия повторов номеров мин
    //Необходимо избавиться/переделать
    private static boolean contains(int dig, int[] mass) {
        for (int number : mass)
            if (dig == number)
                return true;

        return false;

    }

    //Возвращает массив с номерами мин
    private int[] generateNumbersOfMines(int minesDigit) {
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
        for (int i = 0; i < bombs.length; i++)
            bombs[i] = null;


        int[] numbersOfMines = generateNumbersOfMines(minesDigit);//массив, который хранит номера мин

        int bombIndex = 0;
        for (int i = 0; i < levelHight; i++)
            for (int j = 0; j < levelWidth; j++) {
                if (contains(levelWidth * i + j, numbersOfMines)) {

                    cells[i * levelWidth + j].setConditon(9);
                    bombs[bombIndex]=cells[i * levelWidth + j];
                    bombIndex++;
                } else
                    cells[i * levelWidth + j].setConditon(0);

            }
        for (Cell cell : cells) {
            cell.setFlag(false);
            cell.setChecked(false);
        }
        for (Cell bomb : bombs)
            bomb.setConditions(cells);

    }

    //Перезагрузка последнего уровня
    void reloadLast() {
        System.out.println("Перезагрузка последнего уровня");
        for (Cell cell : cells) {
            cell.setFlag(false);
            cell.setChecked(false);
        }
    }

    //Установка панели победы(при успешном прохождении игры)
    static void gameWin() {
        System.out.println("Победа");
        Controller.getGraphic().gameWin();
    }

    // Установка панели проигрыша(при вскрытии бомбы)
    void gameOver() {
        System.out.println("Поражение");
        Controller.getGraphic().gameOver();
    }

    //Показывает условия(для кнопки ESC)
    void checkAll() {
        for (Cell bomb : bombs)
            System.out.print("" + bomb.getNumberInArray() + ",");

        System.out.println("Колличество бомб " + bombs.length);
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

    Cell[] getCells() {
        return cells;
    }

    Cell[] getBombs() {
        return bombs;
    }


}

