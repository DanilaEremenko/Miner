import java.util.ArrayList;
import java.util.Random;

class Bot {
    private boolean gameOver = false;
    private Logic logic;
    private ArrayList<Cell> botCells;
    private int flag;//Колличество найденных мин
    private boolean[] cellThatBotKnow;//true-значит клетка вскрыта, либо помечена флагом
    private double[] probabilitys;//Вероятность рандома по мине в клетке


    Bot(Logic logic) {
        probabilitys = new double[logic.getLevelHight() * logic.getLevelWidth()];
        cellThatBotKnow = new boolean[logic.getLevelHight() * logic.getLevelWidth()];
        for (int i = 0; i < cellThatBotKnow.length; i++)
            cellThatBotKnow[i] = false;

        flag = 0;
        this.logic = logic;
        botCells = new ArrayList<>();
    }

    //Перезагрузка бота
    void reload() {
        for (int i = 0; i < cellThatBotKnow.length; i++)
            cellThatBotKnow[i] = false;

        botCells.clear();
        flag = 0;
        gameOver = false;
    }

    //Вызов бота
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

    //Не рандомный ход
    private boolean easyStep() {
        boolean doSomething = false;
        ArrayList<Cell> addedCells = new ArrayList<>();
        for (Cell cell : botCells) {

            int unknownCells = 0;
            int flagCell = 0;

            //Считаем неизвестные клетки и найденные бомбы вокруг клетки
            for (int number : cell.getNearlyCells()) {
                if (number != -10 && !logic.getCells().get(number).isChecked())
                    unknownCells += 1;
                if (number != -10 && logic.getCells().get(number).isFlag())
                    flagCell += 1;

            }

            //Ставим флаги, если понимаем, что там мины
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
            //Когда все мины вокруг клетки помечены флагами, вскрываем неизвестные клетки
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
            //Расчитываем для каждой клетки вероятность попадания в мину при рандом(для метода doRandom
            if (unknownCells != 0)
                probabilitys[cell.getNumberInArray()] = cell.getConditon() / unknownCells;
        }


        if (!addedCells.isEmpty())
            botCells.addAll(addedCells);
        return doSomething;
    }

    //Рандомный ход
    private void doRandom() {
        int numberOfGoodProbabilities = 0;//номер клетки с наилучшей вероятностью
        double goodprobabilities = 2;//наилучшая вероятность

        //Находим наилучшую вероятность рандома вокруг какой-либо конкретной клетки
        for (int i = 0; i < probabilitys.length; i++)
            if (probabilitys[i] < goodprobabilities) {
                goodprobabilities = probabilitys[i];
                numberOfGoodProbabilities = i;
            }


        //Выбираем лучший рандом из пары "Рандом вокруг клетки" и "Рандома по полю всех неизвестных"
        if (goodprobabilities < (logic.getMinesDigit() - flag) / (logic.getLevelHight() * logic.getLevelWidth() - botCells.size())) {
            botCells.add(logic.getCells().get(numberOfGoodProbabilities).checkNearlyCell());
        } else {
            int numberCheckCell;//Номер клетки которую будем вскрывать
            numberCheckCell = new Random().nextInt(logic.getLevelWidth() * logic.getLevelHight());
            while (cellThatBotKnow[numberCheckCell])//Рандомим еще раз, пока не попадаем в нераскрытую клетку
                numberCheckCell = new Random().nextInt(logic.getLevelWidth() * logic.getLevelHight());
            botCells.add(logic.getCells().get(numberCheckCell).checkBot());
        }


        //Если вскрыли бомбу проигрываем
        if (botCells.get(botCells.size() - 1).getConditon() == 9) {
            gameOver = true;
            System.out.println("Хозяин, я проиграл после рандомного хода.Дай мне шанс исправиться");
        }
    }


}






