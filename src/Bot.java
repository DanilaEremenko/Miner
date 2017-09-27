import java.util.ArrayList;
import java.util.Random;

public class Bot {

    private boolean doAnyThing = false;
    private Level level;
    private int numberStep = 0;
    int[] cells;//Номер в массива-номер клетки на поле
    //Значение-состояние клетки


    public Bot(Level level) {
        this.level = level;
        cells = new int[81];
        for (int i : cells)
            cells[i] = 10;


    }

    public void helpMeBot() {
        int numberCheckCell;
        if (numberStep == 0) {
            numberCheckCell = new Random().nextInt(Level.getLevelWeight() * Level.getLevelHight());
            cells[numberCheckCell] = Level.getCells().get(numberCheckCell).checkBot();
            doAnyThing = true;
        }



        if (!doAnyThing) {
            numberCheckCell = new Random().nextInt(Level.getLevelWeight() * Level.getLevelHight());
            cells[numberCheckCell] = Level.getCells().get(numberCheckCell).checkBot();
            doAnyThing = true;
        }
        checkZero();
        doAnyThing = false;


    }


    private void checkZero() {
        for (int i : cells) {
            if (i == 0)
                level.getCells().get(i).checkBot();

        }

    }


}


