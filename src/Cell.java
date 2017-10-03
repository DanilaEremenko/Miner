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

    //Проверка клетки, чисто графический метод,не подойдет для бота
    private void check() {
        isChecked = true;
        setVisible(false);
        myContent.setVisible(true);
        if (conditon == 9)
            Level.gameOver();
        if (conditon == 0) {
            if (numberInArray % Level.getLevelWeight() != 0 && !Level.getCells().get(numberInArray - 1).isChecked)
                Level.getCells().get(numberInArray - 1).check();//Если не самая левая

            if (numberInArray % Level.getLevelWeight() != Level.getLevelWeight() - 1 && !Level.getCells().get(numberInArray + 1).isChecked) //Если не самая правая
                Level.getCells().get(numberInArray + 1).check();


            if (numberInArray / Level.getLevelWeight() != 0) {//Если не в верхней строчке

                if (!Level.getCells().get(numberInArray - Level.getLevelWeight()).isChecked)
                    Level.getCells().get(numberInArray - Level.getLevelWeight()).check();

                if (numberInArray % Level.getLevelWeight() != Level.getLevelWeight() - 1 && !Level.getCells().get(numberInArray - Level.getLevelWeight() + 1).isChecked)
                    Level.getCells().get(numberInArray - Level.getLevelWeight() + 1).check();

                if (numberInArray % Level.getLevelWeight() != 0 && !Level.getCells().get(numberInArray - Level.getLevelWeight() - 1).isChecked)
                    Level.getCells().get(numberInArray - Level.getLevelWeight() - 1).check();

            }
            if (numberInArray / Level.getLevelWeight() != Level.getLevelHight() - 1) {//Если не в нижней
                if (!Level.getCells().get(numberInArray + Level.getLevelWeight()).isChecked)
                    Level.getCells().get(numberInArray + Level.getLevelWeight()).check();

                if (numberInArray % Level.getLevelWeight() != Level.getLevelWeight() - 1 && !Level.getCells().get(numberInArray + Level.getLevelWeight() + 1).isChecked)
                    Level.getCells().get(numberInArray + Level.getLevelWeight() + 1).check();

                if (numberInArray % Level.getLevelWeight() != 0 && !Level.getCells().get(numberInArray + Level.getLevelWeight() - 1).isChecked)
                    Level.getCells().get(numberInArray + Level.getLevelWeight() - 1).check();

            }


        }


    }

    //Проверка клетки для бота
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

    //Установка состояния на все мины,вызывайтся на минах
    void setConditions() {
        if (numberInArray % Level.getLevelWeight() != 0)
            Level.getCells().get(numberInArray - 1).addCondition();//Если не самая левая

        if (numberInArray % Level.getLevelWeight() != Level.getLevelWeight() - 1) //Если не самая правая
            Level.getCells().get(numberInArray + 1).addCondition();


        if (numberInArray / Level.getLevelWeight() != 0) {//Если не в верхней строчке

            Level.getCells().get(numberInArray - Level.getLevelWeight()).addCondition();

            if (numberInArray % Level.getLevelWeight() != Level.getLevelWeight() - 1)
                Level.getCells().get(numberInArray - Level.getLevelWeight() + 1).addCondition();

            if (numberInArray % Level.getLevelWeight() != 0)
                Level.getCells().get(numberInArray - Level.getLevelWeight() - 1).addCondition();

        }
        if (numberInArray / Level.getLevelWeight() != Level.getLevelHight() - 1) {//Если не в нижней
            Level.getCells().get(numberInArray + Level.getLevelWeight()).addCondition();

            if (numberInArray % Level.getLevelWeight() != Level.getLevelWeight() - 1)
                Level.getCells().get(numberInArray + Level.getLevelWeight() + 1).addCondition();

            if (numberInArray % Level.getLevelWeight() != 0)
                Level.getCells().get(numberInArray + Level.getLevelWeight() - 1).addCondition();


        }
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


    Content getMyContent() {
        return myContent;
    }

    int getNumberInArray() {
        return numberInArray;
    }
}
