import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class Content extends Label {



    public Content(Cell cell,Color color) {
        setText("Б");
        common(cell);

    }
    public Content(Cell cell,int nearlyMine){
        setText(""+nearlyMine);
        common(cell);

    }

    private void common(Cell cell){
        setTranslateX(cell.getTranslateX());
        setTranslateY(cell.getTranslateY());
        setWidth(50);
        setHeight(50);
        setVisible(false);
    }



}
