package parisnanterre.fr.lexify.computergame;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.List;

import parisnanterre.fr.lexify.R;

public class ComputerGameActivity extends Activity
        implements ComputerGameFragment.OnFragmentInteractionListener,
        ComputerGameResultsFragment.OnFragmentInteractionListener,
        ComputerGameStartFragment.OnFragmentInteractionListener{

    CountDownTimer chrono;
    private int wordsFound=0;
    private List<String> wordsFoundList = new ArrayList<String>();
    private List<String> wordsComputer = new ArrayList<String>();

    public int getWordsFound() {
        return wordsFound;
    }

    public void setWordsFound(int wordsFound) {
        this.wordsFound = wordsFound;
    }

    public List<String> getWordsFoundList() {
        return wordsFoundList;
    }

    public void setWordsFoundList(List<String> wordsFoundList) {
        this.wordsFoundList = wordsFoundList;
    }

    public List<String> getWordsComputer() {
        return wordsComputer;
    }

    public void setWordsComputer(List<String> wordsComputer) {
        this.wordsComputer = wordsComputer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_computer_game);
        super.onCreate(savedInstanceState);
        setFragment(new ComputerGameStartFragment());
    }


    public void setFragment(Fragment f) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_computer_game_fragment, f);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        if (chrono != null)
            chrono.cancel();

        finish();
    }

    public void setChrono(CountDownTimer chrono) {
        this.chrono = chrono;
    }

}
