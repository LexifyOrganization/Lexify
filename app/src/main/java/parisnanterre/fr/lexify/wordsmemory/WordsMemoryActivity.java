package parisnanterre.fr.lexify.wordsmemory;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;

import parisnanterre.fr.lexify.R;

public class WordsMemoryActivity extends Activity implements WordsMemoryFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
