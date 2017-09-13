import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Button {
    private int conditon;//9-бомба
    private boolean flag = false;//true есть флаг, false нет флага
    private Rectangle rectangle = new Rectangle(50, 50);
    private int x;
    private int y;


    public Cell(int conditon, int x, int y) {
        this.conditon = conditon;
        this.x = x;
        this.y = y;
        setPrefSize(50, 50);
        setTranslateX(x * 50);
        setTranslateY(y * 50);
        getChildren().addAll(rectangle);
        setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.SPACE) {
                        if (!flag) {
                            flag = true;
                            rectangle.setFill(Color.RED);
                        }
                        if (flag) {
                            flag = false;
                            rectangle.setFill(Color.WHITE);
                        }
                    }
                    if(event.getCode()==KeyCode.ENTER)
                        check();
                }
        );
    }

    public void check() {
        if (conditon == 9) {
            System.out.println("Проиграл");
            setVisible(false);
        }


    }


    public void setFlag(boolean flag) {
        this.flag = flag;
    }


}
