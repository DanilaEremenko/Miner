import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Button {
    private int conditon;//9-бомба
    private Content myContent;
    private static int size = 50;
    private boolean flag = false;//true есть флаг, false нет флага


    public Cell(int conditon, int x, int y, int weidth) {
        this.conditon = conditon;
        setStyle(" -fx-base: #FAFAFA;");//
        setPrefSize(size, size);
        setTranslateX(x * size);
        setTranslateY(y * size);
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


    public void check() {
        setVisible(false);
        myContent.setVisible(true);
        if (conditon == 9)
            Level.gameOver();

    }


    public void checkFlag() {
        int c = 0;
        for (Cell bomb : Level.getBombs())
            if (bomb.flag)
                c++;
        if (c == Level.getMinesDigit())
            Level.gameWin();


    }


    public void addCondition() {
        if (conditon != 9)
            conditon++;

    }

    public Content getMyContent() {
        return myContent;
    }


    public int getConditon() {
        return conditon;
    }
}
