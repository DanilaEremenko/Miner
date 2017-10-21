import java.util.ArrayList;
import java.util.Random;

class Bot {
    private Random random;
    private int lose = 0;//Для ведения подсчета побед
    private int win = 0;//Для ведения подсчет поражений
    private boolean gameOver = false;//при вызове бота на уже решенном полеЛ
    private Logic logic;
    private ArrayList<Cell> botCells;//Клетки раскрыте ботом
    private int flag;//Колличество найденных мин
    private boolean[] cellThatBotKnow;//true-значит клетка вскрыта, либо помечена флагом
    private Probabilities bestProbabilitys;//Наименьшая вероятность рандома по мине при рандоме вокруг клетки
    private Probabilities currentProbabilities;
    private Probabilities randomProbabilities;//Вероятность рандома по мине при рандоме по всем нераскрытым клеткам
    private int numberOfBestProbabilities;//Номер этой самой клетки

    Bot(Logic logic) {
        currentProbabilities = new Probabilities(1, 1);
        bestProbabilitys = new Probabilities(10, 1);
        randomProbabilities = new Probabilities(10, 1);
        cellThatBotKnow = new boolean[logic.getLevelHight() * logic.getLevelWidth()];
        for (int i = 0; i < cellThatBotKnow.length; i++)
            cellThatBotKnow[i] = false;
        flag = 0;
        this.logic = logic;
        botCells = new ArrayList<>();
        random = new Random();
        //для теста
        //botCells.add(logic.getCells().get(12).checkBot());
    }

    //Перезагрузка бота
    void reload() {
        for (int i = 0; i < cellThatBotKnow.length; i++)
            cellThatBotKnow[i] = false;

        currentProbabilities.set(1, 1);
        bestProbabilitys.set(10, 1);
        randomProbabilities.set(10, 1);
        botCells.clear();
        flag = 0;
        gameOver = false;
        //Для теста
        //botCells.add(logic.getCells().get(12).checkBot());
    }

    //Вызов бота
    void helpMeBot() {
        if (gameOver) {
            System.out.println(win + " побед\n" + lose + " поражений");
            return;
        }

        if (flag == logic.getMinesDigit() && logic.getCells().size() - botCells.size() == logic.getMinesDigit()) {
            gameOver = true;
            win++;
        }

        //Ведем подсчет вомзожнных ходов наверняка
        //Если таковых нет рандомим
        //В if если значение элемента []cells равно колличеству мин в известных соседних клетках-вскрываем неизвестные
        boolean currentStep = easyStep();
        if (!currentStep)
            doRandom();
        numberOfBestProbabilities = -1;
        bestProbabilitys.set(1, 1);

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
                            lose++;
                            gameOver = true;
                            System.out.println("Хозяин, я глупый бот, перепиши меня");
                        }
                        doSomething = true;
                        //Убрать комменатрий чтобы бот делал 1 ход за вызов
                        //botCells.addAll(addedCells);
                        //return doSomething;
                    }
            }
            //Расчитываем наименьшную вероятность попадания в мину вокруг какой-либо клетки
            if (cell.getConditon() != 0 && unknownCells != flagCell) {
                currentProbabilities.set(cell.getConditon(), unknownCells);
                if (bestProbabilitys.compare(currentProbabilities) == 1) {
                    bestProbabilitys.set(currentProbabilities.getNumerator(), currentProbabilities.getDenominator());
                    numberOfBestProbabilities = cell.getNumberInArray();
                }

            }
        }
        //Добавляем раскрытые клетки в массив
        if (!addedCells.isEmpty())
            botCells.addAll(addedCells);
        return doSomething;
    }

    //Рандомный ход
    private void doRandom() {
        //Выход из игры если нашли все мины,кажется тут была вероятность попадания в бесконечный цикл
        if (flag == logic.getMinesDigit() && logic.getCells().size() - botCells.size() == logic.getMinesDigit()) {
            win++;
            gameOver = true;
            return;
        }
        randomProbabilities.set(logic.getMinesDigit() - flag, logic.getLevelHight() * logic.getLevelWidth() - botCells.size() - flag);

        //Костыль
        if (randomProbabilities.getNumerator() == 0 || randomProbabilities.getDenominator() <= 0 ||
                bestProbabilitys.getDenominator() == bestProbabilitys.getNumerator()) {
            win++;
            gameOver = true;
            return;
        }

        //Выбираем лучший рандом из пары "Рандом вокруг клетки" и "Рандома по полю всех неизвестных"
        //Если выгоднее рандомить вокруг какой-то клетки
        if (bestProbabilitys.compare(randomProbabilities) == -1) {
            if (numberOfBestProbabilities != -1)
                botCells.add(logic.getCells().get(numberOfBestProbabilities).checkNearlyCell(random));
            System.out.println("Выбрал Рандом по клетке " + bestProbabilitys.toString());
            System.out.println("Рандом по полю " + randomProbabilities.toString());
        } else////Если выгоднее рандомить по полю
        {
            int numberCheckCell;//Номер клетки которую будем вскрывать
            numberCheckCell = random.nextInt(logic.getLevelWidth() * logic.getLevelHight());
            while (cellThatBotKnow[numberCheckCell])//Рандомим еще раз, пока не попадаем в нераскрытую клетку
                numberCheckCell = random.nextInt(logic.getLevelWidth() * logic.getLevelHight());
            System.out.println("Выбрал Рандом по полю " + randomProbabilities.toString());
            System.out.println("Рандом по клетке " + bestProbabilitys.toString());
            botCells.add(logic.getCells().get(numberCheckCell).checkBot());

        }

        //Если вскрыли бомбу проигрываем
        if (botCells.get(botCells.size() - 1).getConditon() == 9) {
            lose++;
            gameOver = true;
            System.out.println("Хозяин, я проиграл после рандомного хода.Дай мне шанс исправиться");
        }

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

    //для хранения вероятностей в дробях
    class Probabilities {
        private int numerator;
        private int denominator;

        Probabilities(int numerator, int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }

        Probabilities() {
        }


        int compare(Probabilities probabilities2) {
            if (this.getDenominator() == 0 || probabilities2.getDenominator() == 0)
                return 0;
            int chisl1 = this.getNumerator() * probabilities2.getDenominator();
            int chisl2 = probabilities2.getNumerator() * this.getDenominator();
            return Integer.compare(chisl1, chisl2);
        }

        @Override
        public String toString() {
            return numerator + "/" + denominator;
        }

        void set(int numerator, int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }

        int getDenominator() {
            return denominator;
        }

        int getNumerator() {
            return numerator;
        }


    }

}








