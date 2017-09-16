import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Button {
    private int conditon;//9-бомба
    private int x;
    private int y;
    private int numberInArray;
    Content myContent;
    private boolean flag = false;//true есть флаг, false нет флага


    public Cell(int conditon, int x, int y, int weidth) {
        this.conditon = conditon;
        this.x = x;
        this.y = y;
        numberInArray = weidth * (y - 1) + x - 1;

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

    public void check() {
        setVisible(false);
        myContent.setVisible(true);
        if (conditon == 9)
            LevelsGenerator.gameOver();

    }

    public void checkFlag() {
        int c = 0;
        for (Cell bomb : LevelsGenerator.getBombs())
            if (bomb.flag)
                c++;
        if(c==LevelsGenerator.getMinesDigit())
            LevelsGenerator.gameWin();


    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public Content getMyContent() {
        return myContent;
    }

    public int getNumberInArray() {
        return numberInArray;
    }

    public int getConditon() {
        return conditon;
    }
}
