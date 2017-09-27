import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class Cell extends Button {
    private int conditon;//9-бомба
    private Content myContent;
    private boolean isFirstCheck = true;//Для первого хода
    private boolean isChecked = false;
    final private int numberInArray;
    private ArrayList<Cell> nearlyCells;
    final private int weidth;
    final private int hight;
    private boolean flag = false;//true есть флаг, false нет флага


    public Cell(int conditon, int x, int y, int weidth, int hight, int numberInArray) {
        this.numberInArray = numberInArray;
        this.conditon = conditon;
        this.weidth = weidth;
        this.hight = hight;
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
                        if (!flag) {
                            flag = true;
                            setStyle(" -fx-base: #CC3333");
                        } else {
                            flag = false;
                            setStyle(" -fx-base: #FAFAFA;");//
                        }
                    checkFlag();

                    if (event.getCode() == KeyCode.ENTER)
                        check();

                }
        );

    }

    //Проверка клетки
    int check() {
        isChecked = true;
        setVisible(false);
        myContent.setVisible(true);
        if (conditon == 9)
            Level.gameOver();
        if (conditon == 0) {
            if (numberInArray % weidth != 0 && !Level.getCells().get(numberInArray - 1).isChecked)
                Level.getCells().get(numberInArray - 1).check();//Если не самая левая

            if (numberInArray % weidth != weidth - 1 && !Level.getCells().get(numberInArray + 1).isChecked) //Если не самая правая
                Level.getCells().get(numberInArray + 1).check();


            if (numberInArray / weidth != 0) {//Если не в верхней строчке

                if (!Level.getCells().get(numberInArray - weidth).isChecked)
                    Level.getCells().get(numberInArray - weidth).check();

                if (numberInArray % weidth != weidth - 1 && !Level.getCells().get(numberInArray - weidth + 1).isChecked)
                    Level.getCells().get(numberInArray - weidth + 1).check();

                if (numberInArray % weidth != 0 && !Level.getCells().get(numberInArray - weidth - 1).isChecked)
                    Level.getCells().get(numberInArray - weidth - 1).check();

            }
            if (numberInArray / weidth != hight - 1) {//Если не в нижней
                if (!Level.getCells().get(numberInArray + weidth).isChecked)
                    Level.getCells().get(numberInArray + weidth).check();

                if (numberInArray % weidth != weidth - 1 && !Level.getCells().get(numberInArray + weidth + 1).isChecked)
                    Level.getCells().get(numberInArray + weidth + 1).check();

                if (numberInArray % weidth != 0 && !Level.getCells().get(numberInArray + weidth - 1).isChecked)
                    Level.getCells().get(numberInArray + weidth - 1).check();

            }


        }
        return conditon;

    }

    int checkBot() {

        setVisible(false);
        myContent.setVisible(true);
        if (conditon == 9)
            Level.gameOver();
        return conditon;
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

    //Добавление к состоянию единицы
    private void addCondition() {
        if (conditon != 9)
            conditon++;
    }

    //Установка состояния на все мины
    void setConditions() {
        if (numberInArray % weidth != 0)
            Level.getCells().get(numberInArray - 1).addCondition();//Если не самая левая

        if (numberInArray % weidth != weidth - 1) //Если не самая правая
            Level.getCells().get(numberInArray + 1).addCondition();


        if (numberInArray / weidth != 0) {//Если не в верхней строчке

            Level.getCells().get(numberInArray - weidth).addCondition();

            if (numberInArray % weidth != weidth - 1)
                Level.getCells().get(numberInArray - weidth + 1).addCondition();

            if (numberInArray % weidth != 0)
                Level.getCells().get(numberInArray - weidth - 1).addCondition();

        }
        if (numberInArray / weidth != hight - 1) {//Если не в нижней
            Level.getCells().get(numberInArray + weidth).addCondition();

            if (numberInArray % weidth != weidth - 1)
                Level.getCells().get(numberInArray + weidth + 1).addCondition();

            if (numberInArray % weidth != 0)
                Level.getCells().get(numberInArray + weidth - 1).addCondition();


        }
    }

    public boolean isChecked() {
        return isChecked;
    }

    private void setChecked(boolean checked) {
        isChecked = checked;
    }

    void setText() {
        myContent.setText("" + conditon);
    }


    Content getMyContent() {
        return myContent;
    }


}
