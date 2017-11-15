import java.util.ArrayList;
import java.util.Random;

class Bot {
    private Random random;
    private int lose = 0;//Для ведения подсчета поражений
    private int win = 0;//Для ведения подсчет побед
    private Logic logic;
    private Graphic graphic;
    private ArrayList<LogicCell> botCells;//Клетки раскрыте ботом
    private int flag;//Колличество найденных мин
    private boolean[] cellThatBotKnow;//true-значит клетка вскрыта, либо помечена флагом
    private boolean shouldCheck = false;//false-считаем вероятности, true-вскрываем наименьшую
    private boolean gameOver = false;//при вызове бота на уже решенном полеЛ,перенсти контроль поражений и побед в контроллер
    private int numberOfBestProbabilities;
    private int findedMines;

    Bot(Logic logic) {
        cellThatBotKnow = new boolean[logic.getLevelHight() * logic.getLevelWidth()];
        for (int i = 0; i < cellThatBotKnow.length; i++)
            cellThatBotKnow[i] = false;
        flag = 0;
        this.logic = logic;
        botCells = new ArrayList<>();
        random = new Random();
    }

    //Перезагрузка бота
    void reload() {

        findedMines = 0;
        for (int i = 0; i < cellThatBotKnow.length; i++)
            cellThatBotKnow[i] = false;
        botCells.clear();
        flag = 0;
        gameOver = false;
    }


    //Вызов бота
    void helpMeBot() {
        if (gameOver) {
            System.out.println(win + " побед\n" + lose + " поражений");
            return;
        }

        if (flag == logic.getMinesDigit() && logic.getLogicCells().length - botCells.size() == logic.getMinesDigit()) {
            gameOver = true;
            win++;
        }


        //Делай ход наверняка
        //Если не получилось, считаем рандом
        if (botCells.size() != 0) {
            boolean currentStep = easyStep();
            if (!currentStep)
                if (shouldCheck) {
                    check(numberOfBestProbabilities);
                    shouldCheck = false;
                } else {
                    numberOfBestProbabilities = calculateProbabilities(logic.getLogicCells());
                    shouldCheck = true;
                }
        } else
            check(random.nextInt(80));


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
                        cellThatBotKnow[number] = true;
                        flag++;
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
                        cellThatBotKnow[number] = true;
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

    //Проверка условий
    private void checkResult() {

        //Выход из игры если нашли все мины,кажется тут была вероятность попадания в бесконечный цикл
        if (flag == logic.getMinesDigit() && logic.getLogicCells().length - botCells.size() == logic.getMinesDigit()) {
            win++;
            gameOver = true;
            return;
        }

        //Если вскрыли бомбу проигрываем
        if (botCells.get(botCells.size() - 1).getConditon() == 9) {
            lose++;
            gameOver = true;
        }


    }


    //Метод, который будет считать вероятности нахождения мины в каждой клетке
    private int calculateProbabilities(LogicCell[] logicCells) {
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
                System.out.println("Ало математика");
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


    public void check(int index) {
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



    //Геттеры

}








