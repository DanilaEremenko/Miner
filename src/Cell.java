import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;


class Cell extends Button {
    private int conditon;//9-бомба
    private Content myContent;
    private boolean isChecked = false;
    final private int numberInArray;
    private boolean flag = false;//true есть флаг, false нет флага
    private int[] nearlyCells;


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

    //Используется после того как задан весь массив cells
    void setNearlyCell(ArrayList<Cell> cells) {
        nearlyCells = new int[8];
        for (int i = 0; i < nearlyCells.length; i++)
            nearlyCells[i] = -10;


        if (numberInArray % Level.getLevelWeight() != 0)//Если не самая левая
            nearlyCells[0] = numberInArray - 1;


        if (numberInArray % Level.getLevelWeight() != Level.getLevelWeight() - 1) //Если не самая правая
            nearlyCells[4] = numberInArray + 1;


        if (numberInArray / Level.getLevelWeight() != 0) {//Если не в верхней строчке

            nearlyCells[2] = numberInArray - Level.getLevelWeight();

            if (numberInArray % Level.getLevelWeight() != Level.getLevelWeight() - 1)
                nearlyCells[3] = numberInArray - Level.getLevelWeight() + 1;

            if (numberInArray % Level.getLevelWeight() != 0)
                nearlyCells[1] = numberInArray - Level.getLevelWeight() - 1;

        }
        if (numberInArray / Level.getLevelWeight() != Level.getLevelHight() - 1) {//Если не в нижней
            nearlyCells[6] = numberInArray + Level.getLevelWeight();

            if (numberInArray % Level.getLevelWeight() != Level.getLevelWeight() - 1)
                nearlyCells[5] = numberInArray + Level.getLevelWeight() + 1;

            if (numberInArray % Level.getLevelWeight() != 0)
                nearlyCells[7] = numberInArray + Level.getLevelWeight() - 1;


        }


    }

    //Проверка клетки, чисто графический метод,не подойдет для бота
    private void check() {
        if (isChecked)
            return;
        isChecked = true;
        setVisible(false);
        myContent.setVisible(true);
        if (conditon == 9)
            Level.gameOver();
        if (conditon == 0) {
            for (int number : nearlyCells)
                if (number != -10)
                    Level.getCells().get(number).check();

        }


    }

    //Проверка клетки для бота
    Cell checkBot() {

        setVisible(false);
        myContent.setVisible(true);
        this.isChecked = true;
        isChecked = true;
        if (conditon == 9)
            Level.gameOver();
        checkFlag();

        return this;


    }

    //Проверка флагов
    private void checkFlag() {
        int c = 0;
        for (Cell bomb : Level.getBombs())
            if (bomb.flag)
                c++;
        if (c == Level.getMinesDigit())
            Level.gameWin();


    }

    //Добавление к состоянию единицы,используется в setConditions
    private void addCondition() {
        if (conditon != 9)
            conditon++;
    }

    //Установка состояния на все мины,вызывайтся на минах
    void setConditions() {
        for (int number : nearlyCells)
            if (number != -10)
                Level.getCells().get(number).addCondition();


    }

    void dropFlag() {
        if (!flag) {
            flag = true;
            setStyle(" -fx-base: #CC3333");
        } else {
            flag = false;
            setStyle(" -fx-base: #FAFAFA;");//
        }
        checkFlag();

    }

    void setFlag(boolean flag) {
        this.flag = flag;
        setStyle(" -fx-base: #CC3333");
    }

    void setConditon(int conditon) {
        this.conditon = conditon;
    }

    //Открыли ли клетку(для автоматического открывания клеток вокруг нуля, боту не понадобится)
    boolean isChecked() {
        return isChecked;
    }

    void setChecked(boolean checked) {
        isChecked = checked;
    }

    void setText() {
        myContent.setText("" + conditon);
    }

    int getConditon() {
        return conditon;
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

    public boolean isFlag() {
        return flag;
    }
}
