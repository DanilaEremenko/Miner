import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BotTest {

    Logic logic;
    Bot bot;

    @Before
    public void begining() {
        //1
        int[] numbersOfMines = {0, 2, 3, 5};
        logic = new Logic(3, 4, numbersOfMines);
        bot = new Bot(logic);
        //bot.check(4);
        //bot.check(7);
        //2
        //int[]numbersOfMines={17,19,20,23,13};
        //logic =new Logic(9,9,numbersOfMines);
        //Bot bot=new Bot(logic);
        //bot.check(0);
        //3
        //int[] mines = {7, 25, 27, 33, 55};
        //logic = new Logic(9, 9, mines);
        //Bot bot=new Bot(logic);
        //bot.check(0);
        //4
        //int mines[] = {1, 2, 3, 4, 5, 6, 7};
        //logic = new Logic(9, 9, mines);
        //Bot bot=new Bot(logic);
    }


    @Test
    public void helpMeBot() {


    }

    @Test
    public void check() {
        bot.check(1);
        assertEquals(true, bot.getBotCells().size() == 1);
        bot.check(4);
        assertEquals(true, bot.getBotCells().size() == 2);
        bot.check(6);
        assertEquals(true, bot.getBotCells().size() == 3);
        bot.check(7);
        assertEquals(true, bot.getBotCells().size() == 4);
        bot.check(8);
        assertEquals(true, bot.getBotCells().size() == 5);
        bot.check(9);
        assertEquals(true, bot.getBotCells().size() == 6);
        bot.check(10);
        assertEquals(true, bot.getBotCells().size() == 7);
        bot.check(11);
        assertEquals(true, bot.getBotCells().size() == 8);

        bot.helpMeBot();
        assertEquals(4,bot.getFindedMines());

    }


}