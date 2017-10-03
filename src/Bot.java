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
        for (int i = 0; i < cells.length; i++)
            cells[i]=-1;


    }

    public void helpMeBot() {
        int numberCheckCell;
        if (numberStep == 0) {
            numberCheckCell = new Random().nextInt(Level.getLevelWeight() * Level.getLevelHight());
            cells[numberCheckCell] = Game.getLevel().getCells().get(numberCheckCell).checkBot();
            doAnyThing = true;
        }


        if (!doAnyThing) {
            numberCheckCell = new Random().nextInt(Level.getLevelWeight() * Level.getLevelHight());
            cells[numberCheckCell] = Game.getLevel().getCells().get(numberCheckCell).checkBot();
            doAnyThing = true;
        }

        checkZero();
        doAnyThing = false;


    }


    private void checkZero() {
        for (int numberInArray = 0; numberInArray < cells.length; numberInArray++) {
            if (cells[numberInArray] == 0){
                if (numberInArray % Level.getLevelWeight() != 0 && !Game.getLevel().getCells().get(numberInArray - 1).isChecked())
                    Game.getLevel().getCells().get(numberInArray - 1).checkBot();//Если не самая левая

                if (numberInArray % Level.getLevelWeight() != Level.getLevelWeight() - 1 && !Game.getLevel().getCells().get(numberInArray + 1).isChecked()) //Если не самая правая
                    Game.getLevel().getCells().get(numberInArray + 1).checkBot();


                if (numberInArray / Level.getLevelWeight() != 0) {//Если не в верхней строчке

                    if (!Game.getLevel().getCells().get(numberInArray - Level.getLevelWeight()).isChecked())
                        Game.getLevel().getCells().get(numberInArray - Level.getLevelWeight()).checkBot();

                    if (numberInArray % Level.getLevelWeight() != Level.getLevelWeight() - 1 && !Game.getLevel().getCells().get(numberInArray - Level.getLevelWeight() + 1).isChecked())
                        Game.getLevel().getCells().get(numberInArray - Level.getLevelWeight() + 1).checkBot();

                    if (numberInArray % Level.getLevelWeight() != 0 && !Game.getLevel().getCells().get(numberInArray - Level.getLevelWeight() - 1).isChecked())
                        Game.getLevel().getCells().get(numberInArray - Level.getLevelWeight() - 1).checkBot();

                }
                if (numberInArray / Level.getLevelWeight() != Level.getLevelHight() - 1) {//Если не в нижней
                    if (!Game.getLevel().getCells().get(numberInArray + Level.getLevelWeight()).isChecked())
                        Game.getLevel().getCells().get(numberInArray + Level.getLevelWeight()).checkBot();

                    if (numberInArray % Level.getLevelWeight() != Level.getLevelWeight() - 1 && !Game.getLevel().getCells().get(numberInArray + Level.getLevelWeight() + 1).isChecked())
                        Game.getLevel().getCells().get(numberInArray + Level.getLevelWeight() + 1).checkBot();

                    if (numberInArray % Level.getLevelWeight()!= 0 && !Game.getLevel().getCells().get(numberInArray + Level.getLevelWeight()- 1).isChecked())
                        Game.getLevel().getCells().get(numberInArray + Level.getLevelWeight() - 1).checkBot();

                }



            }



        }

    }


}


