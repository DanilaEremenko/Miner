import java.util.ArrayList;
import java.util.Random;

class Bot {

    private Level level;
    private ArrayList<Cell> botCells;
    private int flag;//Колличество найденных мин

    //Возможно нужно будет переделать конструктор
    Bot(Level level) {
        flag=0;
        this.level=level;
        botCells = new ArrayList<>();


    }

    void reload() {
        botCells.clear();
        flag=0;
    }

    void helpMeBot() {



        //Ведем подсчет вомзожнных ходов наверняка
        //Если таковых нет рандомим
        //В if если значение элемента []cells равно колличеству мин в известных соседних клетках-вскрываем неизвестные
        if (level.getMinesDigit() == flag&& botCells.size() + level.getBombs().size() == level.getCells().size() ) {
            System.out.println("Я уже выйграл");
            return;
        }
        boolean currentStep = easyStep();
        if (!currentStep)
            doRandom();

    }

    private boolean easyStep() {
        boolean doSomething = false;
        ArrayList<Cell> addedCells = new ArrayList<>();
        for (Cell cell : botCells) {

            int unknownCells = 0;
            int flagCell = 0;

            for (int number : cell.getNearlyCells()) {
                if (number != -10 && !level.getCells().get(number).isChecked())
                    unknownCells += 1;
                if (number != -10 && level.getCells().get(number).isFlag())
                    flagCell += 1;

            }

            //Ставим флаги
            if (unknownCells == cell.getConditon()) {
                for (int number : cell.getNearlyCells()) {
                    if (number != -10 && !level.getCells().get(number).isChecked() && !level.getCells().get(number).isFlag()) {
                        level.getCells().get(number).setFlag(true);
                        flag++;
                        doSomething = true;
                    }
                }
            }
            //Вскрываем когда все мины помечены флагами
            if (cell.getConditon() == flagCell) {
                for (int number : cell.getNearlyCells())
                    if (number != -10 && !level.getCells().get(number).isChecked() && !level.getCells().get(number).isFlag()) {
                        addedCells.add(level.getCells().get(number).checkBot());
                        if(addedCells.get(addedCells.size()-1).getConditon()==9)
                            System.out.println("Хозяин, я глупый бот, перепиши меня");
                        doSomething = true;
                    }
            }


        }
        botCells.addAll(addedCells);
        return doSomething;
    }

    private void doRandom() {
        int numberCheckCell = new Random().nextInt(level.getLevelWidth() * level.getLevelHight());
        botCells.add(level.getCells().get(numberCheckCell).checkBot());
        if(botCells.get(botCells.size()-1).getConditon()==9)
            System.out.println("Хозяин, я проиграл после рандомного хода.Дай мне шанс исправиться");
    }


}






