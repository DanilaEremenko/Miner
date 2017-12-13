import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

class Graphic {

    private Label botsPhrase;
    private Pane root;//Игровая панель
    private Label defaultLabel;
    private Label loseLabel;//Панель проигрыша
    private Label winLabel;//Панель выигрыша
    private Pane mainRoot;//Панель на которой хранятся все остальные панели
    private Scene scene;
    private Logic logic;
    private Bot bot;
    private GraphicCell[] graphicCells;
    private double sceneWidth;//Длина сцены
    private double sceneHight;//Высота сцены


    Graphic(Logic logic, Bot bot) throws MalformedURLException {
        this.bot = bot;
        graphicCells = new GraphicCell[logic.getLevelWidth() * logic.getLevelHight()];
        this.logic = logic;
        for (int i = 0; i < logic.getLevelHight(); i++)
            for (int j = 0; j < logic.getLevelWidth(); j++)
                graphicCells[i * logic.getLevelWidth() + j] = new GraphicCell(logic.getLogicCells()[i * logic.getLevelWidth() + j], j, i);

        calculateSceneSize();
        mainRoot = new Pane();
        root = createGamePain("Визуализация бота\\doger.png", "Визуализация бота\\lose.png", "Визуализация бота\\win.png");

        loseLabel.setVisible(false);
        winLabel.setVisible(false);

        mainRoot.getChildren().addAll(root, loseLabel, winLabel);
        scene = new Scene(mainRoot, sceneWidth, sceneHight);

        for (GraphicCell graphicCell : graphicCells)
            graphicCell.setText();


        for (GraphicCell graphicCell : graphicCells)
            root.getChildren().addAll(graphicCell, graphicCell.getMyContent(), graphicCell.getLabelProbabilitiys());

    }

    private void calculateSceneSize() {
        sceneWidth = graphicCells[graphicCells.length - 1].getTranslateX() + graphicCells[graphicCells.length - 1].getPrefWidth() + 200;
        sceneHight = graphicCells[graphicCells.length - 1].getTranslateY() + graphicCells[graphicCells.length - 1].getHeight() -
                graphicCells[0].getTranslateY() + 250;

    }


    private Pane createGamePain(String defaultURL, String URLlose, String URLwin) throws MalformedURLException {
        Pane pane = new Pane();
        defaultLabel = createLabel(defaultURL);
        loseLabel = createLabel(URLlose);
        winLabel = createLabel(URLwin);

        botsPhrase = new Label(bot.getPhrase());
        botsPhrase.setTranslateX(defaultLabel.getPrefWidth());
        botsPhrase.setTranslateY(defaultLabel.getTranslateY());
        pane.getChildren().addAll(defaultLabel, winLabel, loseLabel, botsPhrase);
        return pane;

    }

    private Label createLabel(String URL) throws MalformedURLException {
        File file = new File(URL);
        Image image = new Image(file.toURI().toURL().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);

        Label label = new Label();
        label.setGraphic(imageView);
        label.setPrefWidth(imageView.getFitWidth());
        label.setPrefHeight(imageView.getFitHeight());
        label.setTranslateX(0);
        label.setTranslateY(sceneHight - imageView.getFitHeight());

        return label;
    }

    void checkTerms() {
        if (logic.isGameOver()) {
            defaultLabel.setVisible(false);
            if (logic.isWin()) {
                winLabel.setVisible(true);
            } else
                loseLabel.setVisible(true);
        } else {
            defaultLabel.setVisible(true);
            loseLabel.setVisible(false);
            winLabel.setVisible(false);
        }
    }

    void printProabilities() {
        for (GraphicCell graphicCell : graphicCells)
            if (!graphicCell.getLogicCell().isFlag() && !graphicCell.getLogicCell().isChecked())
                graphicCell.printLabelProbabilitiys();

    }

    void printBotsPhrase() {
        botsPhrase.setText(bot.getPhrase());
    }

    //Перезагрузка уровня
    void reload() {
        loseLabel.setVisible(false);
        winLabel.setVisible(false);
        defaultLabel.setVisible(true);
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
        loseLabel.setVisible(false);
        winLabel.setVisible(false);
        defaultLabel.setVisible(true);
        root.setVisible(true);
        for (GraphicCell graphicCell : graphicCells) {
            graphicCell.setStyle(" -fx-base: #FAFAFA;");
            graphicCell.getMyContent().setVisible(false);
            graphicCell.setVisible(true);
        }
        for (GraphicCell graphicCell : graphicCells)
            graphicCell.getLabelProbabilitiys().setVisible(false);


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
        loseLabel.setVisible(false);
        winLabel.setVisible(false);
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
