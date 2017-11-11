import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BotTest {

    //Жалуется на создание экземпляров Cell
    Logic logic;

    @Before
    public void begining() {
        //для теста
        //1
        // int[]numbersOfMines={0,2,3,5};
        //logic =new Logic(3,4,numbersOfMines);
        //botCells.add(logic.getCells()[4].checkBot());
        //botCells.add(logic.getCells()[7].checkBot());
        //2
        //int[]numbersOfMines={17,19,20,23,13};
        //logic =new Logic(9,9,numbersOfMines);
        //botCells.add(logic.getCells()[0].checkBot());
        //3
        //int[] mines = {7, 25, 27, 33, 55};
        //logic = new Logic(9, 9, mines);
        //botCells.add(logic.getCells()[0].checkBot());
        int mines[] = {1, 2, 3, 4, 5, 6, 7};
        logic = new Logic(9, 9, mines);

    }


    @Test
    public void helpMeBot() {
        assertEquals(9, logic.getLevelHight());


    }

}