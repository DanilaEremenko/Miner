import java.util.ArrayList;
import java.util.Random;

class Bot {
    private Random random;
    private int lose = 0;//Для ведения подсчета поражений
    private int win = 0;//Для ведения подсчет побед
    private Logic logic;
    private ArrayList<Cell> botCells;//Клетки раскрыте ботом
    private int flag;//Колличество найденных мин
    private boolean[] cellThatBotKnow;//true-значит клетка вскрыта, либо помечена флагом
    private boolean shouldCheck = false;//false-считаем вероятности, true-вскрываем наименьшую
    private boolean gameOver = false;//при вызове бота на уже решенном полеЛ,перенсти контроль поражений и побед в контроллер
    private int numberOfBestProbabilities;

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

        if (flag == logic.getMinesDigit() && logic.getCells().length - botCells.size() == logic.getMinesDigit()) {
            gameOver = true;
            win++;
        }


        //Делай ход наверняка
        //Если не получилось, считаем рандом
        if (botCells.size() != 0) {
            boolean currentStep = easyStep();
            if (!currentStep)
                if (shouldCheck) {
                    botCells.add(logic.getCells()[numberOfBestProbabilities].checkBot());
                    shouldCheck = false;
                } else {
                    numberOfBestProbabilities = calculateProbabilities(logic.getCells());
                    shouldCheck = true;
                }
            checkResult();
        }
        else
            botCells.add(logic.getCells()[random.nextInt(80)].checkBot());

    }

    //Не рандомный ход
    private boolean easyStep() {
        boolean doSomething = false;
        ArrayList<Cell> addedCells = new ArrayList<>();
        for (Cell cell : botCells) {

            int unknownCells = 0;
            int flagCells = 0;

            //Считаем неизвестные клетки и найденные бомбы вокруг клетки
            for (int number : cell.getNearlyCells()) {
                if (number != -10 && !logic.getCells()[number].isChecked())
                    unknownCells += 1;
                if (number != -10 && logic.getCells()[number].isFlag())
                    flagCells += 1;

            }

            //Ставим флаги, если понимаем, что там мины
            if (unknownCells == cell.getConditon()) {
                for (int number : cell.getNearlyCells()) {
                    if (number != -10 && !logic.getCells()[number].isChecked() && !logic.getCells()[number].isFlag()) {
                        logic.getCells()[number].setFlag(true);
                        cellThatBotKnow[number] = true;
                        flag++;
                        doSomething = true;
                    }
                }
            }
            //Когда все мины вокруг клетки помечены флагами, вскрываем неизвестные клетки
            if (cell.getConditon() == flagCells) {
                for (int number : cell.getNearlyCells())
                    if (number != -10 && !logic.getCells()[number].isChecked() && !logic.getCells()[number].isFlag()) {
                        addedCells.add(logic.getCells()[number].checkBot());
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
        if (flag == logic.getMinesDigit() && logic.getCells().length - botCells.size() == logic.getMinesDigit()) {
            win++;
            gameOver = true;
            System.out.println("ЕЕЕЕБОТ");
            return;
        }

        //Если вскрыли бомбу проигрываем
        if (botCells.get(botCells.size() - 1).getConditon() == 9) {
            lose++;
            gameOver = true;
            System.out.println("Хозяин, я проиграл после рандомного хода.Дай мне шанс исправиться");
        }


    }


    //Метод, который будет считать вероятности нахождения мины в каждой клетке
    private int calculateProbabilities(Cell[] cells) {
        for (Cell cell : logic.getCells())
            cell.probabilities = 0;

        //Идем по известным клеткам
        for (Cell cell : botCells) {
            int denominator = 0;
            int chislitel = 0;
            for (int numberCellAround : cell.getNearlyCells()) {
                if (numberCellAround != -10)
                    if (!logic.getCells()[numberCellAround].isChecked() && !logic.getCells()[numberCellAround].isFlag())
                        denominator++;
                    else if (logic.getCells()[numberCellAround].isFlag())
                        chislitel++;


            }
            cell.probabilities = cell.getConditon() - chislitel;
            cell.probabilities = cell.probabilities / denominator;
        }


        //Идем по всем клетка и считаем вероятность для неизвестных
        for (Cell cell : logic.getCells()) {
            if (!cell.isChecked() && !cell.isFlag()) {
                for (int numberOfCell : cell.getNearlyCells())//Далее смотрим в какие группы входит клетка и умножаем на вероятность от каждой группы
                    if (numberOfCell != -10)
                        if (logic.getCells()[numberOfCell].isChecked()) {
                            //КОСТЫЛЬ
                            if (cell.probabilities == 0)
                                cell.probabilities = 1;
                            cell.probabilities = cell.probabilities * (1 - logic.getCells()[numberOfCell].probabilities);
                        }

                cell.probabilities = 1 - (cell.probabilities);

            }
        }

        //Нормализуем значения
//        for (Cell cell : botCells) {
//            double balancingMultiplier = 0;
//            for (int number : cell.getNearlyCells()) {
//                if (number != -10)
//                    if (!logic.getCells()[number].isChecked() && !logic.getCells()[number].isFlag())
//                        balancingMultiplier += logic.getCells()[number].probabilities;
//
//            }
//            balancingMultiplier = cell.getConditon() / balancingMultiplier;
//            for (int number : cell.getNearlyCells()) {
//                if (number != -10)
//                    if (!logic.getCells()[number].isChecked() && !logic.getCells()[number].isFlag())
//                        logic.getCells()[number].probabilities *= balancingMultiplier;
//
//            }
//        }
//

        //Выбираем намиеньшую из всех
        double minimum = 10;
        int indexOfCheck = 0;
        for (Cell cell : logic.getCells())
            if (!cell.isChecked() && !cell.isFlag())
                if (cell.probabilities < minimum) {
                    minimum = cell.probabilities;
                    indexOfCheck = cell.getNumberInArray();
                }


        return indexOfCheck;
    }


    //Геттеры

    int getWin() {
        return win;
    }

    int getLose() {
        return lose;
    }

    boolean isGameOver() {
        return gameOver;
    }
    //Геттеры

}








