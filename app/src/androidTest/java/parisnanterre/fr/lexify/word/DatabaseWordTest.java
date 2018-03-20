package parisnanterre.fr.lexify.word;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;

import parisnanterre.fr.lexify.application.MainActivity;

import static org.junit.Assert.*;

/**
 * Created by piotn_000 on 20/03/2018.
 */


public class DatabaseWordTest {

    private DatabaseWord mDataSource;
    private Context context = InstrumentationRegistry.getTargetContext();

    @Before
    public void setUp(){
        mDataSource = new DatabaseWord(context);
        mDataSource.removeAll();

        mDataSource.addWord(new Word("Horloge"));
        mDataSource.addWord(new Word("Fourchette"));
        mDataSource.addWord(new Word("Mouchoir"));
        mDataSource.addWord(new Word("Tableau"));
        mDataSource.addWord(new Word("Système"));
        mDataSource.addWord(new Word("Dessin"));
        mDataSource.addWord(new Word("Chat"));
        mDataSource.addWord(new Word("Poire"));
        mDataSource.addWord(new Word("Tomate"));
        mDataSource.addWord(new Word("Application"));

    }

    @After
    public void finish() {
        mDataSource.removeAll();
        //mDataSource.close();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("liste_mot.txt"), "iso-8859-1"));

            String mLine = "";

            //changer boucle for par while (bug bizarre)
            for (int i = 0; i < MainActivity.DATABASE_NB; i++) {
                mDataSource.addWord(new Word(reader.readLine(), 0, 0));
            }


            /*while (reader.readLine() != null) {
                String s = mLine;
            }*/

            // do reading, usually loop until end of file reading

        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }


    }

    @Test
    public void testPreConditions() {
        assertNotNull(mDataSource);
    }

    @Test
    public void addWord() throws Exception {



        Word word = new Word("Manger");

        mDataSource.addWord(word);
        List<Word> words = mDataSource.getAllWords();
        assertEquals(mDataSource.getAllWords().size(), 11);
        mDataSource.deleteWord(word);

    }

    @Test
    public void isPresent() throws Exception {

        Word word = new Word("Anneau");
        assertEquals(mDataSource.isPresent(word), false);
        mDataSource.addWord(word);
        assertEquals(mDataSource.isPresent(word), true);
        mDataSource.deleteWord(word);
        List<Word> words = mDataSource.getAllWords();
        assertEquals(words.contains(word), false);

    }

    @Test
    public void getAllWords() throws Exception {;

        Word word = new Word("Retard");
        mDataSource.addWord(word);
        List<Word> words = mDataSource.getAllWords();
        assertEquals(words.isEmpty(), false);
        assertEquals(words.size(), 11);
        assertEquals(words.get(10).getWord(), "Retard");


    }

    @Test
    public void updateWord() throws Exception {

        /*Word word = new Word("Mehnir");
        mDataSource.addWord(word);
        word.setWord("Manchot");
        mDataSource.updateWord(word);
        Word word2 = mDataSource.getAllWords().get(10);
        List<Word> words = mDataSource.getAllWords();
        assertEquals(words.get(10).getWord(), "Manchot");
        mDataSource.deleteWord(word);*/



    }

    @Test
    public void deleteWord() throws Exception {

        Word word = mDataSource.getAllWords().get(9);
        mDataSource.deleteWord(mDataSource.getAllWords().get(9));
        assertEquals(mDataSource.getAllWords().size(), 9);
        mDataSource.addWord(word);

    }

    @Test
    public void getNRandomWords() throws Exception {

        List<Word> words = mDataSource.getNRandomWords(5, 10);
        assertEquals(words.size(), 5);

        boolean allEqual = new HashSet<Word>(words).size() <= 1;
        assertEquals(!allEqual, true);


    }

    @Test
    public void getRandomWord() throws Exception {

        Word word = null;
        word = mDataSource.getRandomWord();
        assertNotNull(word.getWord());
        assertNotNull(word.getDifficulty());
        assertNotNull(word.getNumberPlayed());

    }

}