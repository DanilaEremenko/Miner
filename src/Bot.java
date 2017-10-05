import java.util.ArrayList;
import java.util.Random;

public class Bot {

    private int numberStep = 0;
    private ArrayList<Cell> botCells;


    //Возможно нужно будет переделать конструктор
    public Bot(Level level) {
        botCells = new ArrayList<>();


    }

    void helpMeBot() {
        //Ведем подсчет вомзожнных ходов наверняка
        //Если таковых нет рандомим
        //В if если значение элемента []cells равно колличеству мин в известных соседних клетках-вскрываем неизвестные
        boolean currentStep = easyStep();
        if (!currentStep)
            doRandom();

    }

    private boolean easyStep() {
        boolean doSomething = false;
        ArrayList<Cell> addedCells = new ArrayList<>();
        for (Cell cell : botCells) {
            if (cell.getConditon() == 0) {
                for (int number : cell.getNearlyCells())
                    if (number != -10 && !Level.getCells().get(number).isChecked())
                        addedCells.add(Level.getCells().get(number).checkBot());

                doSomething = true;
            } else {

                int unknownCells = 0;
                for (int number : cell.getNearlyCells()) {
                    if (number != -10&&!Level.getCells().get(number).isChecked())
                        unknownCells += 1;
                }
                if (unknownCells == cell.getConditon())
                    for (int number : cell.getNearlyCells()) {
                        if (number != -10&&!Level.getCells().get(number).isChecked())
                            Level.getCells().get(number).dropFlag();
                    }


            }
        }
        botCells.addAll(addedCells);

        return doSomething;


    }

    private void doRandom() {
        int numberCheckCell = new Random().nextInt(Level.getLevelWeight() * Level.getLevelHight());
        botCells.add(Level.getCells().get(numberCheckCell).checkBot());
    }


}






