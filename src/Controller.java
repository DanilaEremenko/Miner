import javafx.application.Application;
import javafx.stage.Stage;


public class Controller extends Application {
    static private Graphic graphic;
    static private Logic logic;
    static private Bot myManBot;


    @Override
    public void start(Stage primaryStage) throws Exception {
        //logic = new Logic(9, 9, 10);
        int[]numbersOfMines={0,2,3,5};
        logic =new Logic(3,4,numbersOfMines);
        graphic = new Graphic(logic);
        myManBot = new Bot(logic);


        System.out.println("Побед "+myManBot.getWin()+"\nПоражений "+myManBot.getLose());


        //Ниже значение кнопок
        graphic.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {

                case B:
                    myManBot.helpMeBot();
                    break;

                case ESCAPE:
                    logic.checkAll();
                    graphic.checkAll();
                    break;

                case R:
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

