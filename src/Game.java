import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;

public class Game extends Application {
    LevelsGenerator levelsGenerator;
    private double width=600;
    private double height=600;
    @Override
    public void start(Stage primaryStage) throws Exception {
        levelsGenerator=new LevelsGenerator(9,9,10);









        Scene scene=new Scene(levelsGenerator.getRoot(),width,height);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
