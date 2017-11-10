import java.util.ArrayList;
import java.util.Random;

class Bot {
    private Random random;
    private int lose = 0;//Для ведения подсчета поражений
    private int win = 0;//Для ведения подсчет побед
    private boolean gameOver = false;//при вызове бота на уже решенном полеЛ,перенсти контроль поражений и побед в контроллер
    private Logic logic;
    private ArrayList<Cell> botCells;//Клетки раскрыте ботом
    private int flag;//Колличество найденных мин
    private boolean[] cellThatBotKnow;//true-значит клетка вскрыта, либо помечена флагом
    private Probabilities [] allProbabilities;//Все вероятности, которые будут считаться в countProbabilities

    Bot(Logic logic) {


        allProbabilities=new Probabilities[logic.getLevelWidth()*logic.getLevelWidth()];
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

        if (flag == logic.getMinesDigit() && logic.getCells().length - botCells.size() == logic.getMinesDigit()) {
            gameOver = true;
            win++;
        }



        //Делай ход наверняка
        //Если не получилось, считаем рандом
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
                        //Убрать комменатрий чтобы бот делал 1 ход за вызов
                        botCells.addAll(addedCells);
                        return doSomething;
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
        if (flag == logic.getMinesDigit() && logic.getCells().length - botCells.size() == logic.getMinesDigit()) {
            win++;
            gameOver = true;
            return;
        }

        //Метод, который необходимо реализовать
        countProbabilities(logic.getCells());
        //Следующая строчка временная замена
        botCells.add(logic.getCells()[random.nextInt(logic.getLevelHight()*logic.getLevelWidth())].checkBot());


        //Если вскрыли бомбу проигрываем
        if (botCells.get(botCells.size() - 1).getConditon() == 9) {
            lose++;
            gameOver = true;
            System.out.println("Хозяин, я проиграл после рандомного хода.Дай мне шанс исправиться");
        }

    }


    //Метод, который будет считать вероятности нахождения мины в каждой клетке
    private void countProbabilities(Cell[] cells) {
        //1-Цикл, который идет по всему полю и делит клетки на группы

        //2Цикл, который идет по группам и считает вероятности

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








