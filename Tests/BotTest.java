import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BotTest {

    //Жалуется на создание экземпляров Cell
    Logic logic;
    @Before
    public void begining(){
        int mines[]={1,2,3,4,5,6,7};
        logic=new Logic(9,9,mines);

    }


    @Test
    public void helpMeBot(){
        assertEquals(9,logic.getLevelHight());


    }

}