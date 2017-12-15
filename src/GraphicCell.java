import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;


class GraphicCell extends Button {
    private LogicCell logicCell;
    private Content myContent;
    private Label labelProbabilitiys;//Визуализация вероятности мины на клетке

    GraphicCell(LogicCell logicCell, int x, int y) {
        this.logicCell = logicCell;
        setStyle(" -fx-base: #FAFAFA;");//
        setPrefSize(50, 50);
        setTranslateX(x * 50);
        setTranslateY(y * 50);
        labelProbabilitiys = new Label();
        labelProbabilitiys.setStyle("-fx-font-size:20;");
        labelProbabilitiys.setTranslateX(getTranslateX());
        labelProbabilitiys.setTranslateY(getTranslateY());


        if (logicCell.getConditon() == 9)
            myContent = new Content(this, Color.RED);
        else
            myContent = new Content(this, 0);

        setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.SPACE) {
                        logicCell.setFlag();
                        dropFlag();
                    }
                    if (event.getCode() == KeyCode.ENTER) {
                        Controller.getMyManBot().check(logicCell.getNumberInArray());
                        check();
                    }

                }
        );

    }


    //Проверка клетки для бота
    void checkBot() {
        setVisible(false);
        labelProbabilitiys.setVisible(false);
        myContent.setVisible(true);
        if (logicCell.getConditon() == 9)
            setStyle(" -fx-base: #1111DD");

    }

    //Установка флага(для бота)
    void setFlag() {
        setStyle(" -fx-base: #CC3333");
    }


    //Проверка клетки,не для бота
    private void check() {
        if (logicCell.isChecked())
            return;
        logicCell.setChecked(true);
        setVisible(false);
        myContent.setVisible(true);


    }


    //Установка/снятие флага(для игрока)
    private void dropFlag() {
        if (logicCell.isFlag())
            setStyle(" -fx-base: #CC3333");
        else
            setStyle(" -fx-base: #FAFAFA;");//

    }


    //Сеттеры, геттеры

    void printLabelProbabilitiys() {
        labelProbabilitiys.setText(String.format("%.2f", logicCell.getProbabilities()));
        labelProbabilitiys.setVisible(true);
    }

    void setText() {
        myContent.setText("" + logicCell.getConditon());
    }

    Content getMyContent() {
        return myContent;
    }


    Label getLabelProbabilitiys() {
        return labelProbabilitiys;
    }

    LogicCell getLogicCell() {
        return logicCell;
    }


    //Значение клетки
    class Content extends Label {


        Content(GraphicCell graphicCell, Color color) {
            common(graphicCell);


        }

        Content(GraphicCell graphicCell, int nearlyMine) {
            setText("" + nearlyMine);
            common(graphicCell);
        }

        //Метод для конструктора
        private void common(GraphicCell graphicCell) {
            setStyle("-fx-font-size:30;");
            //setStyle("-fx-text-alignment:center;");
            setTranslateX(graphicCell.getTranslateX());
            setTranslateY(graphicCell.getTranslateY());
            setVisible(false);
        }


    }

}


