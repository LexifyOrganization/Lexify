package parisnanterre.fr.lexify.application;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;

import parisnanterre.fr.lexify.R;

public class AboutGameActivity extends Activity implements VerbalGameFragment.OnFragmentInteractionListener,
        VerbalGameSigningFragment.OnFragmentInteractionListener,
        VerbalGameResultsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_game);
        Fragment reglesGameFragment = new AboutGameFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_about_game_fragment, reglesGameFragment ); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
