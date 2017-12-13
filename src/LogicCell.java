class LogicCell {

    private int conditon;//Колличество мин вокруг клетки, 9 обозначается мина
    private boolean isChecked = false;//Проверена ли клетка
    final private int numberInArray;//Номер клетки в массиве cells
    private boolean flag = false;//true-есть флаг, false-нет флага
    private int[] nearlyCells;//Номера клеток, находящихся рядом
    double probabilities;//принимает разные значения в зависимости от isChecked
    //Если isChecked-то вероятность группы, если !isChecked-то вероятность рандома


    LogicCell(int conditon, int numberInArray) {
        this.numberInArray = numberInArray;
        this.conditon = conditon;

    }

    //Используется после того как задан весь массив cells, для определения массива номеров клеток, находящихся рядом
    void setNearlyCell(int levelWeight, int levelHight) {
        nearlyCells = new int[8];
        for (int i = 0; i < nearlyCells.length; i++)
            nearlyCells[i] = -10;


        if (numberInArray % levelWeight != 0)//Если не самая левая
            nearlyCells[0] = numberInArray - 1;


        if (numberInArray % levelWeight != levelWeight - 1) //Если не самая правая
            nearlyCells[4] = numberInArray + 1;


        if (numberInArray / levelWeight != 0) {//Если не в верхней строчке

            nearlyCells[2] = numberInArray - levelWeight;

            if (numberInArray % levelWeight != levelWeight - 1)
                nearlyCells[3] = numberInArray - levelWeight + 1;

            if (numberInArray % levelWeight != 0)
                nearlyCells[1] = numberInArray - levelWeight - 1;

        }
        if (numberInArray / levelWeight != levelHight - 1) {//Если не в нижней
            nearlyCells[6] = numberInArray + levelWeight;

            if (numberInArray % levelWeight != levelWeight - 1)
                nearlyCells[5] = numberInArray + levelWeight + 1;

            if (numberInArray % levelWeight != 0)
                nearlyCells[7] = numberInArray + levelWeight - 1;


        }


    }

    //Проверка клетки для бота, сделать для логической клетки
    LogicCell checkBot() {
        this.isChecked = true;
        isChecked = true;

        return this;
    }

    //Установка флага(для бота)
    void setFlag(boolean flag) {
        this.flag = flag;
    }


    //Проверка клетки,не для бота
    private void check() {
        if (isChecked)
            return;
        isChecked = true;
        if (conditon == 9)
            Controller.getLogic().gameOver();
        if (conditon == 0) {
            for (int number : nearlyCells)
                if (number != -10)
                    Controller.getLogic().getLogicCells()[number].check();

        }


    }


    //Проверка колличества флагов
    private void checkFlag() {
        int c = 0;
        for (LogicCell bomb : Controller.getLogic().getBombs())
            if (bomb.flag)
                c++;
        if (c == Controller.getLogic().getMinesDigit())
            Logic.gameWin();


    }

    //Добавление к состоянию единицы,используется в printProabilities
    private void addCondition() {
        if (conditon != 9)
            conditon++;
    }

    //Установка состояния на все мины,вызывайтся на минах
    void setConditions(LogicCell[] logicCells) {
        for (int number : nearlyCells)
            if (number != -10)
                logicCells[number].addCondition();


    }

    //Установка/снятие флага(для игрока)
    private void dropFlag() {
        if (!flag) {
            flag = true;
        } else {
            flag = false;
        }
        checkFlag();

    }


    //Сеттеры, геттеры, проверки


    void setConditon(int conditon) {
        this.conditon = conditon;
    }

    void setChecked(boolean checked) {
        isChecked = checked;
    }

    //возвращает -1, если клетка не вскрыта
    int getConditon() {

        return conditon;
    }

    int[] getNearlyCells() {
        return nearlyCells;
    }

    int getNumberInArray() {
        return numberInArray;
    }

    boolean isFlag() {
        return flag;
    }

    public double getProbabilities() {
        return probabilities;
    }

    //Открыли ли клетку(для автоматического открывания клеток вокруг нуля, боту не понадобится)
    boolean isChecked() {
        return isChecked;
    }


}
