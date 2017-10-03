import javafx.scene.control.Label;
import javafx.scene.paint.Color;


public class Content extends Label {


    public Content(Cell cell, Color color) {
        common(cell);
    }

    public Content(Cell cell, int nearlyMine) {
        setText("" + nearlyMine);
        common(cell);
    }

    private void common(Cell cell) {
        setTranslateX(cell.getTranslateX());
        setTranslateY(cell.getTranslateY());
        setWidth(50);
        setHeight(50);
        setVisible(false);
    }


}
