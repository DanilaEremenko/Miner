import java.util.ArrayList;
import java.util.Random;

public class Bot {

    private ArrayList<Cell> botCells;


    //Возможно нужно будет переделать конструктор
    public Bot() {
        botCells = new ArrayList<>();


    }

    void helpMeBot() {
        //Ведем подсчет вомзожнных ходов наверняка
        //Если таковых нет рандомим
        //В if если значение элемента []cells равно колличеству мин в известных соседних клетках-вскрываем неизвестные
        boolean currentStep = easyStep();
        if (!currentStep && botCells.size()+Level.getBombs().size()!=Level.getCells().size())
            doRandom();

    }

    private boolean easyStep() {
        boolean doSomething = false;
        ArrayList<Cell> addedCells = new ArrayList<>();
        for (Cell cell : botCells) {

            int unknownCells = 0;
            int flagCell = 0;

            for (int number : cell.getNearlyCells()) {
                if (number != -10 && !Level.getCells().get(number).isChecked())
                    unknownCells += 1;
                if (number != -10 && Level.getCells().get(number).isFlag())
                    flagCell += 1;

            }

            //Ставим флаги
            if (unknownCells == cell.getConditon()) {
                for (int number : cell.getNearlyCells()) {
                    if (number != -10 && !Level.getCells().get(number).isChecked() && !Level.getCells().get(number).isFlag()) {
                        Level.getCells().get(number).setFlag(true);
                        doSomething = true;
                    }
                }
            }
            //Вскрываем когда все мины помечены флагами
            if (cell.getConditon() == flagCell) {
                for (int number : cell.getNearlyCells())
                    if (number != -10 && !Level.getCells().get(number).isChecked() && !Level.getCells().get(number).isFlag()) {
                        addedCells.add(Level.getCells().get(number).checkBot());
                        doSomething = true;
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

    void reload() {
        botCells.clear();
    }

}






