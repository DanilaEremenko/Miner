import javafx.application.Application;
import javafx.stage.Stage;


public class Game extends Application {
    Level level;
    private static double width = 600;
    private static double height = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        level = new Level(9, 9, 10);
        Bot myManBot = new Bot(level);


        primaryStage.setScene(level.getScene());
        primaryStage.show();

    }

    static public double getHeight() {
        return height;
    }

    static public double getWidth() {
        return width;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
