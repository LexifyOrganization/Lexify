package parisnanterre.fr.lexify.verbalgame;

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
import parisnanterre.fr.lexify.exception.noCurrentPlayerException;
import parisnanterre.fr.lexify.word.DatabaseWord;
import parisnanterre.fr.lexify.word.Word;

public class VerbalGameActivity extends Activity
        implements VerbalGameFragment.OnFragmentInteractionListener,
        VerbalGameSigningFragment.OnFragmentInteractionListener,
        VerbalGameResultsFragment.OnFragmentInteractionListener {


    private int score = 10;
    private boolean lastround = false;
    private Player player1;
    private Player player2;
    private List<Word> words;
    private DatabaseWord db;
    CountDownTimer chrono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbal_game);

        player1 = new Player(getResources().getString(R.string.player1));
        player2 = new Player(getResources().getString(R.string.player2));

        db = new DatabaseWord(getApplicationContext());
        words = new ArrayList<Word>();

        player1.setCurrentPlayer(true);
        words.clear();

        setFragment(new VerbalGameSigningFragment());


    }

    @Override
    public void onBackPressed() {
        if (chrono != null)
            chrono.cancel();

        finish();
    }

    public CountDownTimer getChrono() {
        return chrono;
    }

    public void setChrono(CountDownTimer chrono) {
        this.chrono = chrono;
    }


    public void setFragment(Fragment f) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_verbal_game_fragment, f); // give your fragment container id in first parameter
        //transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();

    }

    public Player getCurrentPlayer() throws noCurrentPlayerException {
        if (player1.isCurrentPlayer())
            return player1;
        else if (player2.isCurrentPlayer())
            return player2;
        else
            throw new noCurrentPlayerException();

    }

    public void changeCurrentPlayer() throws noCurrentPlayerException {
        if (player1.isCurrentPlayer()) {
            player1.setCurrentPlayer(false);
            player2.setCurrentPlayer(true);
        } else if (player2.isCurrentPlayer()) {
            player1.setCurrentPlayer(true);
            player2.setCurrentPlayer(false);
        } else {
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

    public void cleanWords() {
        this.words.clear();
    }

    public List<Word> getEightRandomWords() {

        return db.getNRandomWords(8);
    }

    public void addWord(Word w) {
        words.add(w);
    }

    public List<Word> getWords() {
        return words;
    }

    public void initializeWords() {

        this.words.clear();

        this.words = db.getNRandomWords(4);

    }

    public void initialize() {

        this.score = 10;
        this.lastround = false;
        this.player1 = new Player(getResources().getString(R.string.player1));
        this.player2 = new Player(getResources().getString(R.string.player2));
        words.clear();

        player1.setCurrentPlayer(true);

    }
}
