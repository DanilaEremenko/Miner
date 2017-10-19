import java.util.ArrayList;

import java.util.Random;
//Периодически выставляется меньше мин чем надо


class Logic {

    private ArrayList<Cell> cells;
    private ArrayList<Cell> bombs;
    private int minesDigit;//Колличество мин
    private int levelWidth;//Число клеток в ширину
    private int levelHight;//Число клеток в высоту


    Logic(int weight, int hight, int[] numbersOfMines) {
        levelWidth = weight;
        levelHight = hight;
        this.minesDigit = numbersOfMines.length;
        cells = new ArrayList<>();
        bombs = new ArrayList<>();
        common(weight, hight, numbersOfMines);
    }

    Logic(int weight, int hight, int minesDigit) {
        levelWidth = weight;
        this.minesDigit = minesDigit;
        levelHight = hight;
        cells = new ArrayList<>();
        bombs = new ArrayList<>();
        int[] numbersOfMines = generateNumbersOfMines(minesDigit);//массив, который хранит номера мин
        common(weight, hight, numbersOfMines);
    }

    private void common(int weight, int hight, int[] numbersOfMines) {

        for (int i = 0; i < hight; i++)
            for (int j = 0; j < weight; j++)
                cells.add(new Cell(0, j + 1, i + 1, i * weight + j));

        for (int i = 0; i < hight; i++)
            for (int j = 0; j < weight; j++) {
                if (contains(weight * i + j, numbersOfMines)) {
                    cells.get(i * weight + j).setConditon(9);
                    bombs.add(cells.get(i * weight + j));//тут лучше бы переворачивать
                }
            }


        for (Cell cell : cells)
            cell.setNearlyCell(levelWidth, levelHight);


        for (Cell bomb : bombs)
            bomb.setConditions(cells);


    }


    //Метод для обеспечения отсутсвия повторов номеров мин
    //Необходимо избавиться/переделать

    static boolean contains(int dig, int[] mass) {
        for (int number : mass)
            if (dig == number)
                return true;

        return false;

    }//Проверка наличия числа в массиве

    //общий метод вынесенный из reload и конструктора
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
        bombs.clear();

        int[] numbersOfMines = generateNumbersOfMines(minesDigit);//массив, который хранит номера мин

        for (int i = 0; i < levelHight; i++)
            for (int j = 0; j < levelWidth; j++) {
                if (contains(levelWidth * i + j, numbersOfMines)) {

                    cells.get(i * levelWidth + j).setConditon(9);
                    bombs.add(cells.get(i * levelWidth + j));
                } else
                    cells.get(i * levelWidth + j).setConditon(0);

            }
        Controller.getMyManBot().reload();
        if (Controller.isHaveGraphic())
            Controller.getGraphic().reload();


    }

    void reloadLast() {
        System.out.println("Перезагрузка последнего уровня");
        if (Controller.isHaveGraphic())
            Controller.getGraphic().reloadLast();

    }

    //Установка панели победы(при успешном прохождении игры)
    static void gameWin() {
        System.out.println("Победа");
        if (Controller.isHaveGraphic())
            Controller.getGraphic().gameWin();
    }

    // Установка панели проигрыша(при вскрытии бомбы)
    void gameOver() {
        System.out.println("Поражение");
        if (Controller.isHaveGraphic())
            Controller.getGraphic().gameOver();
    }

    //Показывает изначальные условия(для кнопки ESC)
    void checkAll() {
        for (Cell bomb : bombs)
            System.out.print("" + bomb.getNumberInArray() + ",");

        System.out.println("Колличество бомб " + bombs.size());
        if (Controller.isHaveGraphic())
            Controller.getGraphic().checkAll();
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


}

