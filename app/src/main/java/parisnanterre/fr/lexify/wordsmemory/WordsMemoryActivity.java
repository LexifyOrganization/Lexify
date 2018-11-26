package parisnanterre.fr.lexify.wordsmemory;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.application.MainActivity;
import parisnanterre.fr.lexify.application.PlayingActivity;
import parisnanterre.fr.lexify.word.DatabaseWord;
import parisnanterre.fr.lexify.word.Word;

public class WordsMemoryActivity extends Activity implements WordsMemoryFragment.OnFragmentInteractionListener {

    private List<Word> words;
    private DatabaseWord db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DatabaseWord(getApplicationContext());
        words = new ArrayList<Word>();
        words = db.getAllWords();

        setContentView(R.layout.activity_words_memory);
        setFragment(new WordsMemoryFragment());
    }

    public void setFragment(Fragment f) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_words_memory_fragment, f);
        transaction.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public List<Word> getWords() {
        return words;
    }

    public void initialize() {
        this.words = db.getAllWords();
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
