package parisnanterre.fr.lexify.computergame;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.verbalgame.VerbalGameFragment;

public class ComputerGameActivity extends Activity implements ComputerGameFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_computer_game);
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_computer_game_fragment, new ComputerGameFragment()); // give your fragment container id in first parameter
        //transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
