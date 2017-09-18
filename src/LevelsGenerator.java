import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;


public class LevelsGenerator {
    private ArrayList<Cell> cells;


    private Pane root;
    int weight;
    int hight;


    public LevelsGenerator(int weight, int hight, int minesDigit) {
        this.weight = weight;
        this.hight = hight;
        cells = new ArrayList<>();
        int[] numbersOfMines = new int[minesDigit];//массив, который хранит номера мин
        int digit;//Промежуточная переменная для избежания повторения позиций мин
        for (int i = 0; i < minesDigit; i++) {
            digit = new Random().nextInt(weight * hight);
            while (contains(digit, numbersOfMines))//Цикл для избежания повторения позиций мин
                digit = new Random().nextInt(weight * hight);

            numbersOfMines[i] = digit;
        }
        //Все что выше для генерации номеров мин

        for (int i = 1; i <= weight; i++)
            for (int j = 1; j <= hight; j++) {
                if (contains(weight * (i - 1) + j, numbersOfMines)) {
                    cells.add(new Cell(9, j, i, weight));
                } else
                    cells.add(new Cell(0, j, i, weight));
            }


        root = new Pane();
        for (Cell cell : cells) {
            if (cell.getConditon() != 9)
                cell.getMyContent().setText("" + searchMine(cell, cells));

            root.getChildren().addAll(cell, cell.getMyContent());
        }


    }

    private boolean contains(int dig, int[] mass) {
        for (int i = 0; i < mass.length; i++)
            if (dig == mass[i])
                return true;

        return false;

    }

    private int searchMine(Cell cell, ArrayList<Cell> cells) {
        int digit = 0;
        if (cell.getNumberInArray() < 80 && cell.getX() != weight)
            if (cells.get(cell.getNumberInArray() + 1).getConditon() == 9)
                digit++;
        if (cell.getNumberInArray() != 0 && cell.getX() != 1)
            if (cells.get(cell.getNumberInArray() - 1).getConditon() == 9)
                digit++;
        if (cell.getY() != 1) {
            if (cells.get(cell.getNumberInArray() - weight).getConditon() == 9)
                digit++;
            if (cell.getX() != weight)
                if (cells.get(cell.getNumberInArray() - weight + 1).getConditon() == 9)
                    digit++;
            if (cell.getNumberInArray() > 9 && cell.getX() != 1)
                if (cells.get(cell.getNumberInArray() - weight - 1).getConditon() == 9)
                    digit++;
        }
        if (cell.getY() != hight) {
            if (cells.get(cell.getNumberInArray() + weight).getConditon() == 9)
                digit++;
            if (cell.getNumberInArray() < 71 && cell.getX() != weight)
                if (cells.get(cell.getNumberInArray() + weight + 1).getConditon() == 9)
                    digit++;
            if (cell.getX() != 1)
                if (cells.get(cell.getNumberInArray() + weight - 1).getConditon() == 9)
                    digit++;
        }


        return digit;
    }

    public Pane getRoot() {
        return root;
    }
}
