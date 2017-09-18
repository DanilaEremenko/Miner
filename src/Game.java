import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Game extends Application {
    Level level;
    static double width = 600;
    static private double height = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        level = new Level(9, 9, 10);



        primaryStage.setScene(level.getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    static public double getHeight() {
        return height;
    }

    static public double getWidth() {
        return width;
    }

}
