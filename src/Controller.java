import javafx.application.Application;
import javafx.stage.Stage;


public class Controller extends Application {
    static private Graphic graphic;
    static private Logic logic;
    static private Bot myManBot;


    public void start(Stage primaryStage) throws Exception {

        int massMine[] = {3, 5};
        logic = new Logic(3, 2, massMine);
        myManBot = new Bot(logic);
        graphic = new Graphic(logic);
        myManBot.setGraphic(graphic);

        myManBot.check(0);
        myManBot.check(1);
        myManBot.check(2);
        //myManBot.setFlagToLogicMines(3);
        //myManBot.setFlagToLogicMines(8);


        System.out.println("Побед " + myManBot.getWin() + "\nПоражений " + myManBot.getLose());


        //Ниже значение кнопок
        graphic.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {

                case B:
                    myManBot.helpMeBot();
                    graphic.printProabilities();
                    break;

                case ESCAPE:
                    logic.checkAll();
                    graphic.checkAll();
                    break;

                case R:
                    System.out.println("Перезагрузка уровня");
                    logic.reload();
                    graphic.reload();
                    myManBot.reload();
                    break;

                case T:
                    logic.reloadLast();
                    graphic.reloadLast();
                    myManBot.reload();
                    break;

            }
        });


        primaryStage.setScene(graphic.getScene());
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }


    static Logic getLogic() {
        return logic;
    }

    static Graphic getGraphic() {
        return graphic;
    }

}


