import org.junit.Test;

import static org.junit.Assert.*;

public class BotTest {


    Logic logic;
    Bot bot;


    @Test
    public void botWinRate() {
        int numberOfGames = 10000;
        logic = new Logic(9, 9, 10);
        bot = new Bot(logic);
        for (int i = 0; i < numberOfGames; ) {
            bot.helpMeBot();
            if (bot.isGameOver()) {
                logic.reload();
                bot.reload();
                i++;
            }
        }
        assertEquals(true, bot.getWin() + bot.getLose() == numberOfGames);
        assertEquals(true, (double) bot.getWin() / numberOfGames * 100 > 70);
        System.out.println((double) bot.getWin() / numberOfGames * 100);


    }

    @Test
    public void easyStep() {
        int massMine[] = {3, 8, 9, 11, 13, 15};
        logic = new Logic(8, 2, massMine);
        bot = new Bot(logic);

        bot.check(0);
        bot.check(1);
        bot.check(2);
        bot.check(4);
        bot.check(5);
        bot.check(6);
        bot.check(7);
        bot.check(10);
        bot.check(12);
        bot.check(14);
        bot.helpMeBot();
        assertEquals(6, bot.getFindedMines());
        assertEquals(10, bot.getBotCells().size());
        assertEquals(true, bot.isGameOver());
    }

    @Test
    public void notEasyStep() {
        int massMine[] = {3, 5};
        logic = new Logic(3, 2, massMine);
        bot = new Bot(logic);
        bot.check(0);
        bot.check(1);
        bot.check(2);
        while (!bot.isGameOver())
            bot.helpMeBot();
        assertEquals(bot.getBotCells().size(), logic.getLogicCells().length - massMine.length);
        assertEquals(1, bot.getWin());
        assertEquals(0, bot.getLose());

        int massMine2[] = {1, 5};
        logic = new Logic(2, 3, massMine2);
        bot=new Bot(logic);
        bot.check(0);
        bot.check(2);
        bot.check(4);
        while (!bot.isGameOver())
            bot.helpMeBot();
        assertEquals(bot.getBotCells().size(), logic.getLogicCells().length - massMine2.length);
        assertEquals(1, bot.getWin());
        assertEquals(0, bot.getLose());


        int massMine3[] = {5, 6};
        logic = new Logic(4, 2, massMine3);
        bot = new Bot(logic);
        bot.check(0);
        bot.check(1);
        bot.check(2);
        bot.check(3);
        while (!bot.isGameOver())
            bot.helpMeBot();
        assertEquals(bot.getBotCells().size(), logic.getLogicCells().length - massMine3.length);
        assertEquals(bot.getWin(), 1);
        assertEquals(0, bot.getLose());


        int massMine4[] = {3, 5};
        logic = new Logic(2, 4, massMine4);
        bot = new Bot(logic);
        bot.check(0);
        bot.check(2);
        bot.check(4);
        bot.check(6);
        while (!bot.isGameOver())
            bot.helpMeBot();
        assertEquals(bot.getBotCells().size(), logic.getLogicCells().length - massMine4.length);
        assertEquals(bot.getWin(), 1);
        assertEquals(0, bot.getLose());

    }
}


//Тестовые ситуации
//1
//        int[] numbersOfMines = {0, 2, 3, 5};
//       logic = new Logic(3, 4, numbersOfMines);
//     bot = new Bot(logic);
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