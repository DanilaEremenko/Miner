import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Graphic {

    private Pane root;//Игровая панель
    private Pane rootGameOver;//Панель проигрыша
    private Pane rootWin;//Панель выигрыша
    private Pane mainRoot;//Панель на которой хранятся все остальные панели
    private Scene scene;
    private Logic logic;
    private GraphicCell[] graphicCells;
    final private int sceneWidth = 600;//Длина сцены
    final private int sceneHight = 600;//Высота сцены


    Graphic(Logic logic) {
        graphicCells = new GraphicCell[logic.getLevelWidth() * logic.getLevelHight()];
        this.logic = logic;
        for (int i = 0; i < graphicCells.length; i++)
            graphicCells[i] = new GraphicCell(logic.getLogicCells()[i]);

        mainRoot = new Pane();
        rootGameOver = new Pane(new Label("GAME OVER"));
        rootWin = new Pane(new Label("WIN"));
        rootGameOver.setVisible(false);
        rootWin.setVisible(false);
        root = new Pane();

        mainRoot.getChildren().addAll(root, rootGameOver, rootWin);
        scene = new Scene(mainRoot, sceneWidth, sceneHight);

        for (GraphicCell graphicCell : graphicCells)
            graphicCell.setText();


        for (GraphicCell graphicCell : graphicCells)
            root.getChildren().addAll(graphicCell, graphicCell.getMyContent(), graphicCell.getLabelProbabilitiys());

    }

    void printProabilities() {
        for (GraphicCell graphicCell : graphicCells)
            if (!graphicCell.getLogicCell().isFlag() && !graphicCell.getLogicCell().isChecked())
                graphicCell.printLabelProbabilitiys();

    }

    //Перезагрузка уровня
    void reload() {
        rootGameOver.setVisible(false);
        rootWin.setVisible(false);
        root.setVisible(true);
        for (GraphicCell graphicCell : graphicCells) {
            graphicCell.setStyle(" -fx-base: #FAFAFA;");
            graphicCell.getMyContent().setVisible(false);
            graphicCell.setVisible(true);
            graphicCell.setText();
        }
        for (GraphicCell graphicCell : graphicCells)
            graphicCell.getLabelProbabilitiys().setVisible(false);
        printProabilities();
    }

    //Перезагрузка последнего уровня
    void reloadLast() {
        System.out.println("Перезагрузка последнего уровня");
        rootGameOver.setVisible(false);
        rootWin.setVisible(false);
        root.setVisible(true);
        for (GraphicCell graphicCell : graphicCells) {
            graphicCell.setStyle(" -fx-base: #FAFAFA;");
            graphicCell.getMyContent().setVisible(false);
            graphicCell.setVisible(true);
        }
        for (GraphicCell graphicCell : graphicCells)
            graphicCell.getLabelProbabilitiys().setVisible(false);


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
        for (GraphicCell graphicCell : graphicCells) {
            if (graphicCell.getLogicCell().getConditon() == 9 && !graphicCell.getLogicCell().isFlag())
                graphicCell.setStyle(" -fx-base: #1111DD");
            else if (!graphicCell.getLogicCell().isFlag()) {
                graphicCell.setVisible(false);
                graphicCell.getMyContent().setVisible(true);
            }
            graphicCell.getLabelProbabilitiys().setVisible(false);
        }
        rootGameOver.setVisible(false);
        rootWin.setVisible(false);
        root.setVisible(true);


    }

    //Геттеры
    Scene getScene() {
        return scene;
    }

    public GraphicCell[] getGraphicCells() {
        return graphicCells;
    }
}
