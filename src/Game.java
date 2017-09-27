import javafx.application.Application;
import javafx.stage.Stage;


public class Game extends Application {
    Level level;
    final private static int width = 600;
    final private static int height = 600;


    @Override
    public void start(Stage primaryStage) throws Exception {
        level = new Level(9, 9, 10);


        primaryStage.setScene(level.getScene());
        primaryStage.show();

    }

    static public int getHeight() {
        return height;
    }

    static public int getWidth() {
        return width;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
