package parisnanterre.fr.lexify.application;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentTransaction;

import parisnanterre.fr.lexify.R;

public class VerbalGameActivity extends Activity
                                implements VerbalGameFragment.OnFragmentInteractionListener,
                                           VerbalGameSigningFragment.OnFragmentInteractionListener,
                                           VerbalGameResultsFragment.OnFragmentInteractionListener{


    public int score = 10;
    public boolean lastround = false;
    public Player player1;
    public Player player2;



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

    public Player getCurrentPlayer() {
        if(player1.isCurrentPlayer())
            return player1;
        else
            return player2;
    }

    public void changeCurrentPlayer() {
        if(player1.isCurrentPlayer()) {
            player1.setCurrentPlayer(false);
            player2.setCurrentPlayer(true);
        }
        else {
            player1.setCurrentPlayer(true);
            player2.setCurrentPlayer(false);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
