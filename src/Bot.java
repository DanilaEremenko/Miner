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

        //В if если значение элемента []cells равно колличеству мин в известных соседних клетках-вскрываем неизвестные
        if (numberStep != 0) {
            //for(int botsCells:cells){
            //if(botsCells==0)
            //}
        } else {//Первый ход в любом случае рандом
            numberCheckCell = new Random().nextInt(Level.getLevelWeight() * Level.getLevelHight());
            cells[numberCheckCell] = Level.getCells().get(numberCheckCell).checkBot();
        }

        checkZero();


    }


    private void checkZero() {
        for (int numberInArray = 0; numberInArray < cells.length; numberInArray++) {
            if (cells[numberInArray] == 0) {
                if (numberInArray % Level.getLevelWeight() != 0 && !Level.getCells().get(numberInArray - 1).isChecked())
                    Level.getCells().get(numberInArray - 1).checkBot();//Если не самая левая

                if (numberInArray % Level.getLevelWeight() != Level.getLevelWeight() - 1 && !Level.getCells().get(numberInArray + 1).isChecked()) //Если не самая правая
                    Level.getCells().get(numberInArray + 1).checkBot();


                if (numberInArray / Level.getLevelWeight() != 0) {//Если не в верхней строчке

                    if (!Level.getCells().get(numberInArray - Level.getLevelWeight()).isChecked())
                        Level.getCells().get(numberInArray - Level.getLevelWeight()).checkBot();

                    if (numberInArray % Level.getLevelWeight() != Level.getLevelWeight() - 1 && !Level.getCells().get(numberInArray - Level.getLevelWeight() + 1).isChecked())
                        Level.getCells().get(numberInArray - Level.getLevelWeight() + 1).checkBot();

                    if (numberInArray % Level.getLevelWeight() != 0 && !Level.getCells().get(numberInArray - Level.getLevelWeight() - 1).isChecked())
                        Level.getCells().get(numberInArray - Level.getLevelWeight() - 1).checkBot();

                }
                if (numberInArray / Level.getLevelWeight() != Level.getLevelHight() - 1) {//Если не в нижней
                    if (!Level.getCells().get(numberInArray + Level.getLevelWeight()).isChecked())
                        Level.getCells().get(numberInArray + Level.getLevelWeight()).checkBot();

                    if (numberInArray % Level.getLevelWeight() != Level.getLevelWeight() - 1 && !Level.getCells().get(numberInArray + Level.getLevelWeight() + 1).isChecked())
                        Level.getCells().get(numberInArray + Level.getLevelWeight() + 1).checkBot();

                    if (numberInArray % Level.getLevelWeight() != 0 && !Level.getCells().get(numberInArray + Level.getLevelWeight() - 1).isChecked())
                        Level.getCells().get(numberInArray + Level.getLevelWeight() - 1).checkBot();

                }


            }


        }

    }


}


