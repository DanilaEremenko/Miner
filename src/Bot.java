import java.util.ArrayList;
import java.util.Random;

public class Bot {

    private ArrayList<Cell> botCells;
    private int flag;

    //Возможно нужно будет переделать конструктор
    public Bot() {
        flag=0;
        botCells = new ArrayList<>();


    }

    void helpMeBot() {
        //Ведем подсчет вомзожнных ходов наверняка
        //Если таковых нет рандомим
        //В if если значение элемента []cells равно колличеству мин в известных соседних клетках-вскрываем неизвестные
        if(Level.getMinesDigit()==flag&&botCells.size()+Level.getBombs().size()==Level.getCells().size()) {
            System.out.println("Я уже выйграл");
            return;
        }
        boolean currentStep = easyStep();
        if (!currentStep )
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
                        flag++;
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
        if(botCells.get(botCells.size()-1).getConditon()==9)
            System.out.println("Хозяин, я проиграл после рандомного хода.Дай мне шанс исправиться");
    }

    void reload() {
        botCells.clear();
    }

}






