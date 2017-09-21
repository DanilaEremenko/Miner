import java.util.ArrayList;
import java.util.Random;

public class Bot {

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
            cells[numberCheckCell] = level.check(numberCheckCell);


        } else {
            checkZero();

        }
        numberStep++;
    }

    private void checkZero() {
        for (int i:cells) {
            if(cells[i]==0)
                level.getCells().get(i).check();

        }

        }


    }


