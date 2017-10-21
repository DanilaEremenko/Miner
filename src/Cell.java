import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;


class Cell extends Button {
    private int conditon;//9-бомба
    private Content myContent;
    private boolean isChecked = false;
    final private int numberInArray;
    private boolean flag = false;//true есть флаг, false нет флага
    private int[] nearlyCells;//Номера клеток, находящихся рядом


    Cell(int conditon, int x, int y, int numberInArray) {
        this.numberInArray = numberInArray;
        this.conditon = conditon;
        setStyle(" -fx-base: #FAFAFA;");//
        setPrefSize(50, 50);
        setTranslateX(x * 50);
        setTranslateY(y * 50);


        if (conditon == 9)
            myContent = new Content(this, Color.RED);
        else
            myContent = new Content(this, 0);

        setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.SPACE)
                        dropFlag();
                    if (event.getCode() == KeyCode.ENTER)
                        check();

                }
        );

    }

    //Используется после того как задан весь массив cells, для определения массива номеров клеток, находящихся рядом
    void setNearlyCell(int levelWeight, int levelHight) {
        nearlyCells = new int[8];
        for (int i = 0; i < nearlyCells.length; i++)
            nearlyCells[i] = -10;


        if (numberInArray % levelWeight != 0)//Если не самая левая
            nearlyCells[0] = numberInArray - 1;


        if (numberInArray % levelWeight != levelWeight - 1) //Если не самая правая
            nearlyCells[4] = numberInArray + 1;


        if (numberInArray / levelWeight != 0) {//Если не в верхней строчке

            nearlyCells[2] = numberInArray - levelWeight;

            if (numberInArray % levelWeight != levelWeight - 1)
                nearlyCells[3] = numberInArray - levelWeight + 1;

            if (numberInArray % levelWeight != 0)
                nearlyCells[1] = numberInArray - levelWeight - 1;

        }
        if (numberInArray / levelWeight != levelHight - 1) {//Если не в нижней
            nearlyCells[6] = numberInArray + levelWeight;

            if (numberInArray % levelWeight != levelWeight - 1)
                nearlyCells[5] = numberInArray + levelWeight + 1;

            if (numberInArray % levelWeight != 0)
                nearlyCells[7] = numberInArray + levelWeight - 1;


        }


    }

    //Проверка клетки для бота
    Cell checkBot() {
        setVisible(false);
        myContent.setVisible(true);
        this.isChecked = true;
        isChecked = true;
        if (conditon == 9)
            setStyle(" -fx-base: #1111DD");
        //Controller.getLogic().gameOver();
        checkFlag();

        return this;


    }

    //Рандом по одной из клеток находящихся рядом для бота
    Cell checkNearlyCell(Random random) {
        int numberCheck;
        int i = random.nextInt(7);
        while (nearlyCells[i] == -10 || Controller.getLogic().getCells().get(nearlyCells[i]).isChecked
                || Controller.getLogic().getCells().get(nearlyCells[i]).isFlag())
            i = random.nextInt(7);

        numberCheck = nearlyCells[i];


        System.out.println("рандом вокруг клетки " + numberCheck);
        return Controller.getLogic().getCells().get(numberCheck).checkBot();

    }

    //Установка флага(для бота)
    void setFlag(boolean flag) {
        this.flag = flag;
        setStyle(" -fx-base: #CC3333");
    }


    //Проверка клетки,не для бота
    private void check() {
        if (isChecked)
            return;
        isChecked = true;
        setVisible(false);
        myContent.setVisible(true);
        if (conditon == 9)
            Controller.getLogic().gameOver();
        if (conditon == 0) {
            for (int number : nearlyCells)
                if (number != -10)
                    Controller.getLogic().getCells().get(number).check();

        }


    }


    //Проверка колличества флагов
    private void checkFlag() {
        int c = 0;
        for (Cell bomb : Controller.getLogic().getBombs())
            if (bomb.flag)
                c++;
        if (c == Controller.getLogic().getMinesDigit())
            Logic.gameWin();


    }

    //Добавление к состоянию единицы,используется в setConditions
    private void addCondition() {
        if (conditon != 9)
            conditon++;
    }

    //Установка состояния на все мины,вызывайтся на минах
    void setConditions(ArrayList<Cell> cells) {
        for (int number : nearlyCells)
            if (number != -10)
                cells.get(number).addCondition();


    }

    //Установка/снятие флага(для игрока)
    private void dropFlag() {
        if (!flag) {
            flag = true;
            setStyle(" -fx-base: #CC3333");
        } else {
            flag = false;
            setStyle(" -fx-base: #FAFAFA;");//
        }
        checkFlag();

    }


    //Сеттеры, геттеры, проверки
    void setConditon(int conditon) {
        this.conditon = conditon;
    }

    void setChecked(boolean checked) {
        isChecked = checked;
    }

    void setText() {
        myContent.setText("" + conditon);
    }

    //возвращает -1, если клетка не вскрыта
    int getConditon() {
        if (isChecked)
            return conditon;
        else
            return -1;
    }

    int[] getNearlyCells() {
        return nearlyCells;
    }

    Content getMyContent() {
        return myContent;
    }

    int getNumberInArray() {
        return numberInArray;
    }

    boolean isFlag() {
        return flag;
    }

    //Открыли ли клетку(для автоматического открывания клеток вокруг нуля, боту не понадобится)
    boolean isChecked() {
        return isChecked;
    }

}
