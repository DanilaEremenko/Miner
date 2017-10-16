import javafx.application.Application;
import javafx.stage.Stage;
//Кусок с кучей if давно пора закинуть в общий метод(используется в Bot и Cell для получения индексов соседних клеток

public class Controller extends Application {
    static private boolean haveGraphic=false;
    static private Graphic graphic;
    static private Logic logic;
    static private Bot myManBot;


    @Override
    public void start(Stage primaryStage) throws Exception {
        //logic = new Logic(9, 9, 10);
        int []minesNumbers={5,15,24,34,57,60,64,67,70,66,67};
        logic =new Logic(9,9,minesNumbers);
        graphic=new Graphic(logic);
        myManBot = new Bot(logic);

        //Ниже значение кнопок
        graphic.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {

                case B:
                    myManBot.helpMeBot();
                    break;

                case ESCAPE:
                    logic.checkAll();
                    break;

                case R:
                    logic.reload();
                    break;

                case T:
                    logic.reloadLast();
                    break;

            }
        });

        primaryStage.setScene(graphic.getScene());
        primaryStage.show();

    }




    public static void main(String[] args) {
        launch(args);
    }

    static void setHaveGraphic(boolean haveGraphic) {
        Controller.haveGraphic = haveGraphic;
    }

    static Bot getMyManBot() {
        return myManBot;
    }

    static Logic getLogic() {
        return logic;
    }

    static Graphic getGraphic() {
        return graphic;
    }

    static boolean isHaveGraphic() {
        return haveGraphic;
    }
}

