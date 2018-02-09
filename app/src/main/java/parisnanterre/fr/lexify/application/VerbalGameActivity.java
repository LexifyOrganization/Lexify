package parisnanterre.fr.lexify.application;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.app.Fragment;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.word.DatabaseWord;
import parisnanterre.fr.lexify.word.Word;

public class VerbalGameActivity extends Activity
                                implements VerbalGameFragment.OnFragmentInteractionListener,
                                           VerbalGameSigningFragment.OnFragmentInteractionListener{


    public int score = 10;
    public boolean lastround = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbal_game);

        Fragment signingGame = new VerbalGameSigningFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_verbal_game_fragment, signingGame ); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
