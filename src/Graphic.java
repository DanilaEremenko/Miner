import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Graphic {

    private Pane root;//Игровая панель
    private Pane rootGameOver;//Панель проигрыша
    private Pane rootWin;//Панель выигрыша
    private Pane mainRoot;//Панель на которой хранятся все остальные панели
    private Scene scene;
    private Logic logic;
    final private int sceneWidth = 600;//Длина сцены
    final private int sceneHight = 600;//Высота сцены


    Graphic(Logic logic) {
        this.logic = logic;
        mainRoot = new Pane();
        rootGameOver = new Pane(new Label("GAME OVER"));
        rootWin = new Pane(new Label("WIN"));
        rootGameOver.setVisible(false);
        rootWin.setVisible(false);
        root = new Pane();

        mainRoot.getChildren().addAll(root, rootGameOver, rootWin);
        scene = new Scene(mainRoot, sceneWidth, sceneHight);

        for (Cell cell : logic.getCells())
            cell.setText();


        for (Cell cell : logic.getCells())
            root.getChildren().addAll(cell, cell.getMyContent());

    }

    //Перезагрузка уровня
    void reload() {
        System.out.println("Перезагрука уровня");
        rootGameOver.setVisible(false);
        rootWin.setVisible(false);
        root.setVisible(true);
        for (Cell cell : logic.getCells()) {
            cell.setFlag(false);
            cell.setStyle(" -fx-base: #FAFAFA;");
            cell.getMyContent().setVisible(false);
            cell.setVisible(true);
            cell.setChecked(false);
        }


        for (Cell bomb : logic.getBombs())
            bomb.setConditions(logic.getCells());


        for (Cell cell : logic.getCells())
            cell.setText();


    }

    //Перезагрузка последнего уровня
    void reloadLast() {
        System.out.println("Перезагрузка последнего уровня");
        rootGameOver.setVisible(false);
        rootWin.setVisible(false);
        root.setVisible(true);
        for (Cell cell : logic.getCells()) {
            cell.setFlag(false);
            cell.setStyle(" -fx-base: #FAFAFA;");
            cell.getMyContent().setVisible(false);
            cell.setVisible(true);
            cell.setChecked(false);
        }


    }

    //Установка панели победы(при успешном прохождении игры)
    void gameWin() {
        root.setVisible(false);
        rootWin.setVisible(true);
    }

    // Установка панели проигрыша(при вскрытии бомбы)
    void gameOver() {
        root.setVisible(false);
        rootGameOver.setVisible(true);
    }

    //Показывает изначальные условия(для кнопки ESC)
    void checkAll() {
        for (Cell cell : logic.getCells()) {
            if (cell.getConditon() == 9&&!cell.isFlag())
                cell.setStyle(" -fx-base: #1111DD");
            else if (!cell.isFlag()) {
                cell.setVisible(false);
                cell.getMyContent().setVisible(true);
            }
        }
        rootGameOver.setVisible(false);
        rootWin.setVisible(false);
        root.setVisible(true);


    }

    //Геттеры
    Scene getScene() {
        return scene;
    }


}
