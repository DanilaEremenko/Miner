import javafx.application.Application;
import javafx.stage.Stage;


public class Game extends Application {
    LevelsGenerator levelsGenerator;
    private static double width = 600;
    private static double height = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        levelsGenerator = new LevelsGenerator(9, 9, 10);


        primaryStage.setScene(levelsGenerator.getScene());
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
