import java.util.ArrayList;
import java.util.Random;

class Bot {
    private String phrase;
    private Random random;
    private int lose = 0;//Для ведения подсчета поражений
    private int win = 0;//Для ведения подсчет побед
    private Logic logic;
    private Graphic graphic;
    private ArrayList<LogicCell> botCells;//Клетки раскрыте ботом
    private boolean shouldCheck = false;//false-считаем вероятности, true-вскрываем наименьшую
    private boolean gameOver = false;//при вызове бота на уже решенном полеЛ,перенсти контроль поражений и побед в контроллер
    private int numberOfBestProbabilities;
    private int findedMines;

    Bot(Logic logic) {
        this.logic = logic;
        botCells = new ArrayList<>();
        random = new Random();
    }

    //Перезагрузка бота
    void reload() {

        findedMines = 0;
        botCells.clear();
        gameOver = false;
    }


    //Вызов бота
    void helpMeBot() {
        phrase = "НУ ЧЕ ОБЕЗЯНИЙ СЫН\nЩАС Я ТЕБЕ ПОКАЖУ КАК ЭТО ДЕЛАЕТСЯ\n";
        if (gameOver) {
            System.out.println(win + " побед\n" + lose + " поражений");
            return;
        }

        //1)Делает ход наверняка
        //2)Пробуем походить, как человек
        //3)Если не получилось, считаем рандом
        if (botCells.size() != 0) {
            boolean easyStep = easyStep();
            if (easyStep)
                phrase += "ЭТОТ ХОД ДАЖЕ ТЫ БЫ СМОГ СДЕЛАТЬ...";
            boolean stepAsHuman = stepAsHuman();
            if (stepAsHuman)
                phrase += "НУ НА ТАКОЕ ТЫ ВРЯД ЛИ СПОСОБЕН";
            if (!easyStep && !stepAsHuman)
                if (shouldCheck) {
                    phrase = "Я ЖЕ ГОВОРИЛ ЭТО РАБОТАЕТ";
                    check(numberOfBestProbabilities);
                    shouldCheck = false;
                } else {
                    phrase = "СЧИТАЕМ ВЕРОЯТНОСТИ ПО СЕКРЕТНЫМ ОБЕЗЯНИМ ФОРМУЛАМ";
                    numberOfBestProbabilities = calculateProbabilities();
                    shouldCheck = true;
                }
        } else {
            check(random.nextInt(logic.getLogicCells().length));
            phrase = "ЭТО ЗА ПОРАЖЕНИЕ НЕ СЧИТАЕТСЯ";
        }

        checkResult();

    }

    //Не рандомный ход
    private boolean easyStep() {
        boolean doSomething = false;
        ArrayList<LogicCell> addedCells = new ArrayList<>();
        for (LogicCell logicCell : botCells) {

            int unknownCells = 0;
            int flagCells = 0;

            //Считаем неизвестные клетки и найденные бомбы вокруг клетки
            for (int number : logicCell.getNearlyCells()) {
                if (number != -10 && !logic.getLogicCells()[number].isChecked())
                    unknownCells += 1;
                if (number != -10 && logic.getLogicCells()[number].isFlag())
                    flagCells += 1;

            }

            //Ставим флаги, если понимаем, что там мины
            if (unknownCells == logicCell.getConditon()) {
                for (int number : logicCell.getNearlyCells()) {
                    if (number != -10 && !logic.getLogicCells()[number].isChecked() && !logic.getLogicCells()[number].isFlag()) {
                        logic.getLogicCells()[number].setFlag(true);
                        findedMines++;
                        if (graphic != null)
                            graphic.getGraphicCells()[number].setFlag();
                        doSomething = true;
                    }
                }
            }
            //Когда все мины вокруг клетки помечены флагами, вскрываем неизвестные клетки
            if (logicCell.getConditon() == flagCells) {
                for (int number : logicCell.getNearlyCells())
                    if (number != -10 && !logic.getLogicCells()[number].isChecked() && !logic.getLogicCells()[number].isFlag()) {
                        addedCells.add(logic.getLogicCells()[number].checkBot());
                        if (graphic != null)
                            graphic.getGraphicCells()[number].checkBot();
                        if (addedCells.get(addedCells.size() - 1).getConditon() == 9) {
                            lose++;
                            gameOver = true;
                            System.out.println("Хозяин, я глупый бот, перепиши меня");
                        }
                        doSomething = true;

                    }


            }


        }

        //Добавляем раскрытые клетки в массив
        if (!addedCells.isEmpty())
            botCells.addAll(addedCells);
        return doSomething;
    }

    //
    private boolean stepAsHuman() {

        return false;
    }

    //Метод, который будет считать вероятности нахождения мины в каждой клетке
    private int calculateProbabilities() {
        for (LogicCell logicCell : logic.getLogicCells())
            logicCell.probabilities = 1;

        //Идем по известным клеткам, формируем группы
        for (LogicCell logicCell : botCells) {
            int denominator = 0;
            int chislitel = 0;
            for (int numberCellAround : logicCell.getNearlyCells()) {
                if (numberCellAround != -10)
                    if (!logic.getLogicCells()[numberCellAround].isChecked() && !logic.getLogicCells()[numberCellAround].isFlag())
                        denominator++;
                    else if (logic.getLogicCells()[numberCellAround].isFlag())
                        chislitel++;


            }
            logicCell.probabilities = logicCell.getConditon() - chislitel;
            logicCell.probabilities = logicCell.probabilities / denominator;
        }

        int calculatedCells = 0;
        //Идем по всем клетка и считаем вероятность для неизвестных
        for (LogicCell logicCell : logic.getLogicCells()) {
            if (!logicCell.isChecked() && !logicCell.isFlag()) {
                for (int numberOfCell : logicCell.getNearlyCells())//Далее смотрим в какие группы входит клетка и умножаем на вероятность от каждой группы
                    if (numberOfCell != -10)
                        if (logic.getLogicCells()[numberOfCell].isChecked()) {
                            logicCell.probabilities = logicCell.probabilities * (1 - logic.getLogicCells()[numberOfCell].probabilities);
                        }

                if (logicCell.probabilities != 1) {
                    logicCell.probabilities = 1 - (logicCell.probabilities);
                    calculatedCells++;
                }


            }
        }

        for (LogicCell logicCell : logic.getLogicCells()) {
            if (logicCell.probabilities == 1) {
                logicCell.probabilities = (double) (logic.getMinesDigit() - findedMines) /
                        (logic.getLogicCells().length - botCells.size() - calculatedCells);
            }
            if (logicCell.probabilities == 0) {
                logicCell.probabilities = 1;
            }


        }


        //Нормализуем значения
        for (int i = 0; i < 100; i++)
            for (LogicCell cell : botCells) {
                double balancingMultiplier = 0;
                for (int number : cell.getNearlyCells()) {
                    if (number != -10)
                        if (!logic.getLogicCells()[number].isChecked() && !logic.getLogicCells()[number].isFlag())
                            balancingMultiplier += logic.getLogicCells()[number].probabilities;

                }
                balancingMultiplier = cell.getConditon() / balancingMultiplier;
                for (int number : cell.getNearlyCells()) {
                    if (number != -10)
                        if (!logic.getLogicCells()[number].isChecked() && !logic.getLogicCells()[number].isFlag())
                            logic.getLogicCells()[number].probabilities *= balancingMultiplier;

                }
            }


        //Выбираем намиеньшую из всех
        double minimum = 10;
        int indexOfCheck = 0;
        for (LogicCell logicCell : logic.getLogicCells())
            if (!logicCell.isChecked() && !logicCell.isFlag())
                if (logicCell.probabilities < minimum) {
                    minimum = logicCell.probabilities;
                    indexOfCheck = logicCell.getNumberInArray();
                }


        return indexOfCheck;
    }

    //Проверка условий
    private void checkResult() {

        if (logic.isGameOver()) {
            if (logic.isWin()) {
                win++;
                phrase = "ЧЕ САМ ВЫЙГРАЛ ДА?\nСКИНЬ ПАЦАНАМ СКРИН\nОБЕЗЯНИЙ СЫН\nСТАЯ КРУТО";
            } else {
                lose++;
                phrase = "СЛЫШЬ ЧУВАК, ЭТО СЛУЧАЙНОСТЬ\nНАЖМИ R НА КЛАВЕ\nЧУВАК Я РАЗВАЛЮ";
            }
            gameOver = true;

        }

    }

    void check(int index) {
        botCells.add(logic.getLogicCells()[index].checkBot());
        if (graphic != null)
            graphic.getGraphicCells()[index].checkBot();
    }

    void setGraphic(Graphic graphic) {
        this.graphic = graphic;
    }


//Геттеры

    int getWin() {
        return win;
    }

    int getLose() {
        return lose;
    }

    public int getFindedMines() {
        return findedMines;
    }

    public ArrayList<LogicCell> getBotCells() {
        return botCells;
    }


    public String getPhrase() {
        return phrase;
    }
}








