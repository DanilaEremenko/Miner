import org.junit.Before;
import org.junit.Test;
import sun.rmi.runtime.Log;

import java.util.Arrays;

import static org.junit.Assert.*;

public class LogicCellTest {
    private Logic logic;

    @Before
    public void terms() {
        int minesDigit[] = {0, 1};
        logic = new Logic(3, 3, minesDigit);
    }

    @Test
    public void setNearlyCell() throws Exception {
        logic.getLogicCells()[0].setNearlyCell(logic.getLevelWidth(), logic.getLevelHight());
        int zeroCellNeighbors[] = {-10, -10, -10, -10, 1, 4, 3, -10};
        assertEquals(true, Arrays.equals(zeroCellNeighbors, logic.getLogicCells()[0].getNearlyCells()));
        int fourthCellNeighbors[] = {3, 0, 1, 2, 5, 8, 7, 6};
        assertEquals(true, Arrays.equals(fourthCellNeighbors, logic.getLogicCells()[4].getNearlyCells()));
    }

    @Test
    public void checkBot() throws Exception {
        assertEquals(true, logic.getLogicCells()[0] == logic.getLogicCells()[0].checkBot());
        assertEquals(true, logic.getLogicCells()[0].isChecked());
    }

    @Test
    public void setFlag() throws Exception {
        logic.getLogicCells()[0].setFlag(true);
        assertEquals(true, logic.getLogicCells()[0].isFlag());
        logic.getLogicCells()[0].setFlag(false);
        assertEquals(false, logic.getLogicCells()[0].isFlag());
        assertEquals(false, logic.getLogicCells()[5].isFlag());
    }


    @Test
    public void setConditon() throws Exception {
        logic.getLogicCells()[0].setConditon(0);
        assertEquals(true, logic.getLogicCells()[0].getConditon() == 0);

    }

    @Test
    public void setChecked() throws Exception {
        logic.getLogicCells()[0].setChecked(true);
        assertEquals(true, logic.getLogicCells()[0].isChecked());
    }

    @Test
    public void getConditon() throws Exception {
        logic.getLogicCells()[0].setConditon(0);
        assertEquals(true, logic.getLogicCells()[0].getConditon() == 0);

    }

    @Test
    public void getNearlyCells() throws Exception {
        logic.getLogicCells()[0].setNearlyCell(logic.getLevelWidth(), logic.getLevelHight());
        int zeroCellNeighbors[] = {-10, -10, -10, -10, 1, 4, 3, -10};
        assertEquals(true, Arrays.equals(zeroCellNeighbors, logic.getLogicCells()[0].getNearlyCells()));
        int fourthCellNeighbors[] = {3, 0, 1, 2, 5, 8, 7, 6};
        assertEquals(true, Arrays.equals(fourthCellNeighbors, logic.getLogicCells()[4].getNearlyCells()));
    }

    @Test
    public void getNumberInArray() throws Exception {
        assertEquals(true, logic.getLogicCells()[0].getNumberInArray() == 0);
    }

    @Test
    public void isFlag() throws Exception {
        logic.getLogicCells()[0].setFlag(true);
        assertEquals(true, logic.getLogicCells()[0].isFlag());
        logic.getLogicCells()[0].setFlag(false);
        assertEquals(false, logic.getLogicCells()[0].isFlag());
        assertEquals(false, logic.getLogicCells()[5].isFlag());
    }

    @Test
    public void isChecked() throws Exception {
        logic.getLogicCells()[0].setChecked(true);
        assertEquals(true, logic.getLogicCells()[0].isChecked());
    }

}