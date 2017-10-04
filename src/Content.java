import javafx.scene.control.Label;
import javafx.scene.paint.Color;


class Content extends Label {


    Content(Cell cell, Color color) {
        common(cell);


    }

    Content(Cell cell, int nearlyMine) {
        setText("" + nearlyMine);
        common(cell);
    }

    //Метод для конструктора
    private void common(Cell cell) {
        setStyle("-fx-font-size:30;");
        //setStyle("-fx-text-alignment:center;");
        setTranslateX(cell.getTranslateX());
        setTranslateY(cell.getTranslateY());
        setVisible(false);
    }


}
