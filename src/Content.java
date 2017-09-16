import javafx.scene.control.Label;
import javafx.scene.paint.Color;



public class Content extends Label {


    public Content(Cell cell, Color color) {
      //  Image image=new Image("2017-09-09 13-27-46.jpg");
      //  ImageView imageView=new ImageView(image);
      //  setGraphic(imageView);
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
