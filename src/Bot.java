import java.util.ArrayList;
import java.util.Random;

public class Bot {

    private Level level;
    private int numberStep = 0;
    int[] cells;//Номер в массива-номер клетки на поле
    //Значение-состояние клетки


    //Возможно нужно будет переделать конструктор
    public Bot(Level level) {
        this.level = level;
        cells = new int[81];
        for (int i = 0; i < cells.length; i++)
            cells[i] = -1;

    }

    void helpMeBot() {
        int numberCheckCell;

        //Ведем подсчет вомзожнных ходов наверняка
        //Если таковых нет рандомим
        //В if если значение элемента []cells равно колличеству мин в известных соседних клетках-вскрываем неизвестные
        if (numberStep != 0) {





        } else {//Первый ход в любом случае рандом
            numberCheckCell = new Random().nextInt(Level.getLevelWeight() * Level.getLevelHight());
            cells[numberCheckCell] = Level.getCells().get(numberCheckCell).checkBot();
        }



    }




}


