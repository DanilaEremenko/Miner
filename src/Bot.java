import java.util.ArrayList;
import java.util.Random;

class Bot {
    private boolean gameOver = false;
    private Logic logic;
    private ArrayList<Cell> botCells;
    private int flag;//Колличество найденных мин
    private boolean[] cellThatBotKnow;

    //Возможно нужно будет переделать конструктор
    Bot(Logic logic) {
        cellThatBotKnow = new boolean[logic.getLevelHight() * logic.getLevelWidth()];
        for (boolean cell : cellThatBotKnow)
            cell = false;
        flag = 0;
        this.logic = logic;
        botCells = new ArrayList<>();
    }

    void reload() {
        for (int i = 0; i < cellThatBotKnow.length; i++)
            cellThatBotKnow[i] = false;

        botCells.clear();
        flag = 0;
        gameOver = false;
    }

    void helpMeBot() {

        if (gameOver) {
            System.out.println("Игра уже закончена");
            return;
        }
        //Ведем подсчет вомзожнных ходов наверняка
        //Если таковых нет рандомим
        //В if если значение элемента []cells равно колличеству мин в известных соседних клетках-вскрываем неизвестные
        if (logic.getMinesDigit() == flag && botCells.size() + logic.getBombs().size() == logic.getCells().size()) {
            gameOver = true;
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
                if (number != -10 && !logic.getCells().get(number).isChecked())
                    unknownCells += 1;
                if (number != -10 && logic.getCells().get(number).isFlag())
                    flagCell += 1;

            }

            //Ставим флаги
            if (unknownCells == cell.getConditon()) {
                for (int number : cell.getNearlyCells()) {
                    if (number != -10 && !logic.getCells().get(number).isChecked() && !logic.getCells().get(number).isFlag()) {
                        logic.getCells().get(number).setFlag(true);
                        cellThatBotKnow[number] = true;
                        flag++;
                        doSomething = true;
                    }
                }
            }
            //Вскрываем когда все мины помечены флагами
            if (cell.getConditon() == flagCell) {
                for (int number : cell.getNearlyCells())
                    if (number != -10 && !logic.getCells().get(number).isChecked() && !logic.getCells().get(number).isFlag()) {
                        addedCells.add(logic.getCells().get(number).checkBot());
                        cellThatBotKnow[number] = true;
                        if (addedCells.get(addedCells.size() - 1).getConditon() == 9) {
                            gameOver = true;
                            System.out.println("Хозяин, я глупый бот, перепиши меня");
                        }
                        doSomething = true;
                    }
            }


        }
        botCells.addAll(addedCells);
        return doSomething;
    }

    private void doRandom() {
        int numberCheckCell;
        numberCheckCell = new Random().nextInt(logic.getLevelWidth() * logic.getLevelHight());
        while (cellThatBotKnow[numberCheckCell])
            numberCheckCell = new Random().nextInt(logic.getLevelWidth() * logic.getLevelHight());

        botCells.add(logic.getCells().get(numberCheckCell).checkBot());
        if (botCells.get(botCells.size() - 1).getConditon() == 9) {
            gameOver = true;
            System.out.println("Хозяин, я проиграл после рандомного хода.Дай мне шанс исправиться");
        }
    }


    public boolean isGameOver() {
        return gameOver;
    }
}






