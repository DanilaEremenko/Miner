import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class Cell extends Button {
    private int conditon;//9-бомба
    Content myContent;
    private int[] nearlyCell;
    private int weidth;
    private int hight;
    private boolean flag = false;//true есть флаг, false нет флага


    public Cell(int conditon, int x, int y, int weidth, int hight, int numberOfMine) {
        this.conditon = conditon;
        this.weidth = weidth;
        this.hight = hight;
        setStyle(" -fx-base: #FAFAFA;");//
        setPrefSize(50, 50);
        setTranslateX(x * 50);
        setTranslateY(y * 50);

        findNearlyCells(numberOfMine);

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

                    if(event.getCode()== KeyCode.C) {
                       myContent.setVisible(true);
                        setVisible(false);
                    }
                }
        );

    }

    //Поиск клеток находящихся рядом
    private void findNearlyCells(int numberOfMine) {

        nearlyCell = new int[8];
        for (int i : nearlyCell)
            nearlyCell[i] = -10;


        if (numberOfMine % weidth != 0)
            nearlyCell[0] = numberOfMine - 1;

        if (numberOfMine % weidth != weidth - 1)//Если не самая правая
            nearlyCell[4] = numberOfMine + 1;


        if (numberOfMine / weidth != 0) {

            nearlyCell[2] = numberOfMine - weidth;

            if (numberOfMine % weidth != weidth - 1)
                nearlyCell[3] = numberOfMine - weidth + 1;

            if (numberOfMine % weidth != 0)
                nearlyCell[1] = numberOfMine - weidth - 1;

        }
        if (numberOfMine / weidth != hight - 1) {

            nearlyCell[6] = numberOfMine + weidth;

            if (numberOfMine % weidth != weidth - 1)
                nearlyCell[5] = numberOfMine + weidth + 1;

            if (numberOfMine % weidth != 0)
                nearlyCell[7] = numberOfMine + weidth - 1;
        }


    }


    //Проверка мины
    public void check() {
        setVisible(false);
        myContent.setVisible(true);
        if (conditon == 9)
            Level.gameOver();




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
    void addCondition() {
        if (conditon != 9)
            conditon++;
    }


    public int[] getNearlyCell() {
        return nearlyCell;
    }

    public Content getMyContent() {
        return myContent;
    }

    public int getConditon() {
        return conditon;
    }

}
