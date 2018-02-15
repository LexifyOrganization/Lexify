package parisnanterre.fr.lexify.application;

import org.junit.Test;

import parisnanterre.fr.lexify.exception.noCurrentPlayerException;

import static org.junit.Assert.*;

/**
 * Created by piotn_000 on 15/02/2018.
 */
public class VerbalGameActivityTest {

    @Test(expected = noCurrentPlayerException.class)
    public void getCurrentPlayer() throws Exception {

        VerbalGameActivity activity = new VerbalGameActivity();

        Player player1 = new Player("p1");
        Player player2 = new Player("p2");

        activity.setPlayer1(player1);
        activity.setPlayer2(player2);

        player1.setCurrentPlayer(false);
        player2.setCurrentPlayer(false);

        activity.getCurrentPlayer();

        player1.setCurrentPlayer(true);
        assertEquals(activity.getCurrentPlayer(), player1);

        activity.changeCurrentPlayer();

        assertEquals(activity.getCurrentPlayer(), player2);


    }

    @Test(expected = noCurrentPlayerException.class)
    public void changeCurrentPlayer() throws Exception {
        VerbalGameActivity activity = new VerbalGameActivity();

        Player player1 = new Player("p1");
        Player player2 = new Player("p2");

        activity.setPlayer1(player1);
        activity.setPlayer2(player2);

        player1.setCurrentPlayer(false);
        player2.setCurrentPlayer(false);

        activity.changeCurrentPlayer();


        player1.setCurrentPlayer(true);
        assertEquals(activity.getCurrentPlayer(), player1);
        activity.changeCurrentPlayer();
        assertEquals(activity.getCurrentPlayer(), player2);



    }

}