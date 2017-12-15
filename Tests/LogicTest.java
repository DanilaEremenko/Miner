import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class LogicTest {
    private Logic logic;
    private int numbersOfMines[] = {1, 2, 3, 4, 20, 45, 63, 72, 73, 76};


    private void constructorWithMassiv() {
        logic = new Logic(9, 9, numbersOfMines);

    }

    private void constructorWithDigit() {
        int minesDigit = 10;
        logic = new Logic(9, 9, minesDigit);
    }

    @Test
    public void setConditions() throws Exception {
        int minesDigit[] = {0, 1};
        logic = new Logic(3, 3, minesDigit);
        assertEquals(true, logic.getLogicCells()[0].getConditon() == 9);
        assertEquals(true, logic.getLogicCells()[1].getConditon() == 9);
        assertEquals(true, logic.getLogicCells()[2].getConditon() == 1);
        assertEquals(true, logic.getLogicCells()[3].getConditon() == 2);
        assertEquals(true, logic.getLogicCells()[4].getConditon() == 2);
        assertEquals(true, logic.getLogicCells()[5].getConditon() == 1);
        assertEquals(true, logic.getLogicCells()[6].getConditon() == 0);
        assertEquals(true, logic.getLogicCells()[7].getConditon() == 0);
        assertEquals(true, logic.getLogicCells()[8].getConditon() == 0);


    }


    @Test
    public void reload() throws Exception {
        constructorWithMassiv();
        LogicCell lastMines[] = new LogicCell[logic.getMinesDigit()];
        System.arraycopy(logic.getBombs(), 0, lastMines, 0, lastMines.length);
        logic.reload();
        assertEquals(false, Arrays.equals(lastMines, logic.getBombs()));

    }

    @Test
    public void reload1() throws Exception {
        constructorWithMassiv();
        LogicCell lastMines[] = new LogicCell[logic.getMinesDigit()];
        System.arraycopy(logic.getBombs(), 0, lastMines, 0, lastMines.length);
        logic.reload(numbersOfMines);
        assertEquals(true, Arrays.equals(lastMines, logic.getBombs()));

    }

    @Test
    public void reloadLast() throws Exception {
        constructorWithMassiv();
        LogicCell lastMines[] = new LogicCell[logic.getMinesDigit()];
        System.arraycopy(logic.getBombs(), 0, lastMines, 0, lastMines.length);
        logic.reloadLast();
        assertEquals(true, Arrays.equals(lastMines, logic.getBombs()));

    }


    @Test
    public void getMinesDigit() throws Exception {
        constructorWithMassiv();
        assertEquals(true, logic.getMinesDigit() == logic.getBombs().length);
        constructorWithDigit();
        assertEquals(true, logic.getMinesDigit() == logic.getBombs().length);
    }


    @Test
    public void isGameOver() throws Exception {
        int numbersOfMines[] = {2, 3};
        logic = new Logic(2, 2, numbersOfMines);
        assertEquals(false, logic.isGameOver());
        logic.getLogicCells()[0].setChecked(true);
        assertEquals(false, logic.isGameOver());
        logic.getLogicCells()[1].setChecked(true);
        assertEquals(false, logic.isGameOver());
        logic.getLogicCells()[2].setFlag(true);
        assertEquals(false, logic.isGameOver());
        logic.getLogicCells()[3].setFlag(true);
        assertEquals(true, logic.isGameOver());


    }

    @Test
    public void isWin() throws Exception {
        int numbersOfMines[] = {2, 3};
        logic = new Logic(2, 2, numbersOfMines);
        assertEquals(false, logic.isWin());
        logic.getLogicCells()[0].setChecked(true);
        assertEquals(false, logic.isWin());
        logic.getLogicCells()[1].setChecked(true);
        assertEquals(false, logic.isWin());
        logic.getLogicCells()[2].setFlag(true);
        assertEquals(false, logic.isWin());
        logic.getLogicCells()[3].setFlag(true);
        assertEquals(true, logic.isWin());
    }


}