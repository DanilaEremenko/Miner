import javafx.application.Application;
import javafx.stage.Stage;


public class Controller extends Application {
    static private Graphic graphic;
    static private Logic logic;
    static private Bot myManBot;


    public void start(Stage primaryStage) throws Exception {


        logic = new Logic(9, 9, 10);
        myManBot = new Bot(logic);
        graphic = new Graphic(logic, myManBot);
        myManBot.setGraphic(graphic);


        System.out.println("Побед " + myManBot.getWin() + "\nПоражений " + myManBot.getLose());


        //Ниже значение кнопок
        graphic.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {

                case B:
                    myManBot.helpMeBot();
                    graphic.printBotsPhrase();
                    graphic.printProabilities();
                    graphic.checkTerms();
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
                    System.out.println("Перезагрузка последнего уровня");
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

    static Bot getMyManBot(){
        return myManBot;
    }

}


