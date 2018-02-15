package parisnanterre.fr.lexify.application;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentTransaction;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.exception.noCurrentPlayerException;

public class VerbalGameActivity extends Activity
                                implements VerbalGameFragment.OnFragmentInteractionListener,
                                           VerbalGameSigningFragment.OnFragmentInteractionListener,
                                           VerbalGameResultsFragment.OnFragmentInteractionListener{


    private int score = 10;
    private boolean lastround = false;
    private Player player1;
    private Player player2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbal_game);

        player1 = new Player("Player 1");
        player2 = new Player("Player 2");

        player1.setCurrentPlayer(true);

        setFragment(new VerbalGameSigningFragment());


    }


    public void setFragment(Fragment f) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_verbal_game_fragment, f ); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();

    }

    public Player getCurrentPlayer() throws noCurrentPlayerException {
        if(player1.isCurrentPlayer())
            return player1;
        else if(player2.isCurrentPlayer())
            return player2;
        else
            throw new noCurrentPlayerException();

    }

    public void changeCurrentPlayer() throws noCurrentPlayerException {
        if(player1.isCurrentPlayer()) {
            player1.setCurrentPlayer(false);
            player2.setCurrentPlayer(true);
        }
        else if(player2.isCurrentPlayer()){
            player1.setCurrentPlayer(true);
            player2.setCurrentPlayer(false);
        }
        else {
            throw new noCurrentPlayerException();
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isLastRound() {
        return lastround;
    }

    public void setLastRound(boolean lastround) {
        this.lastround = lastround;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
