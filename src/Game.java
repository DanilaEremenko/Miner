import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
//Кусок с кучей if давно пора закинуть в общий метод(используется в Bot и Cell для получения индексов соседних клеток

public class Game extends Application {
    static private Level level;
    static private Bot myManBot;
    static private Pane rootGameOver;//Панель проигрыша
    static private Pane rootWin;//Панель выигрыша
    static private Pane mainRoot;//Панель на которой хранятся все остальные панели
    static private Scene scene;
    final private static int sceneWidth = 600;//Длина сцены
    final private static int sceneHight = 600;//Высота сцены


    @Override
    public void start(Stage primaryStage) throws Exception {
        level = new Level(9, 9, 10);
        mainRoot = new Pane();
        rootGameOver = new Pane(new Label("GAME OVER"));
        rootWin = new Pane(new Label("WIN"));
        rootGameOver.setVisible(false);
        rootWin.setVisible(false);
        mainRoot.getChildren().addAll(level.getRoot(), rootGameOver, rootWin);
        scene = new Scene(mainRoot, sceneWidth, sceneHight);
        myManBot = new Bot(level);

        //Ниже значение кнопок
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {

                case B:
                    myManBot.helpMeBot();
                    break;

                case ESCAPE:
                    level.checkAll();
                    break;

                case R:
                    level.reload();
                    break;

            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    static Bot getMyManBot() {
        return myManBot;
    }

    static Level getLevel() {
        return level;
    }

    static Pane getRootGameOver() {
        return rootGameOver;
    }

    static Pane getRootWin() {
        return rootWin;
    }





}

