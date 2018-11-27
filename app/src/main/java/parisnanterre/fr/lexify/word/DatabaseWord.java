package parisnanterre.fr.lexify.word;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DatabaseWord extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "wordDB";

    // Contacts table name
    private static final String TABLE_WORDS = "words";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_WORD = "word";
    private static final String KEY_WORD_EN = "word_en";
    private static final String KEY_WORD_FR = "word_fr";
    private static final String KEY_WORD_AR = "word_ar";

    private static final String KEY_DIFFICULTY = "difficulty";
    private static final String KEY_NUMBER_PLAYED = "numberPlayed";

    public DatabaseWord(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_WORDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_WORD + " TEXT," + KEY_WORD_EN + " TEXT," + KEY_WORD_FR + " TEXT," + KEY_WORD_AR + " TEXT," + KEY_DIFFICULTY + " TEXT,"
                + KEY_NUMBER_PLAYED + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);

        // Create tables again
        onCreate(db);
    }

    public void addWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WORD, word.getWord());
        values.put(KEY_WORD_EN, word.getWord_en());
        values.put(KEY_WORD_FR, word.getWord_fr());
        values.put(KEY_WORD_AR, word.getWord_ar());
        values.put(KEY_DIFFICULTY, word.getDifficulty());
        values.put(KEY_NUMBER_PLAYED, word.getNumberPlayed());

        // Inserting Row
        db.insert(TABLE_WORDS, null, values);
        db.close(); // Closing database connection
    }

    public boolean isPresent(Word w) {
        List<Word> words = this.getAllWords();
        for(Word word : words) {
            if(word.getWord().equals(w.getWord()))
                return true;
        }

        return false;
    }

    public List<Word> getAllWords() {
        List<Word> wordList = new ArrayList<Word>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WORDS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Word word = new Word();
                word.setId(Integer.parseInt(cursor.getString(0)));
                word.setWord(cursor.getString(1));
                word.setWord_en(cursor.getString(2));
                word.setWord_fr(cursor.getString(3));
                word.setWord_ar(cursor.getString(4));
                word.setDifficulty(Integer.parseInt(cursor.getString(5)));
                word.setNumberPlayed(Integer.parseInt(cursor.getString(6)));
                // Adding contact to list
                wordList.add(word);
            } while (cursor.moveToNext());
        }

        // return contact list
        return wordList;
    }

    public int updateWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WORD, word.getWord());
        values.put(KEY_WORD_EN, word.getWord_en());
        values.put(KEY_WORD_FR, word.getWord_fr());
        values.put(KEY_WORD_AR, word.getWord_ar());
        values.put(KEY_DIFFICULTY, word.getDifficulty());
        values.put(KEY_NUMBER_PLAYED, word.getNumberPlayed());

        // updating row
        return db.update(TABLE_WORDS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(word.getId()) });
    }

    public void deleteContact(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORDS, KEY_ID + " = ?",
                new String[] { String.valueOf(word.getId()) });
        db.close();
    }

    public List<Word> getNRandomWords(int n) {
        List<Word> words = getAllWords();

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<536; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);

        List<Word> nWords = new ArrayList<Word>();
        for(int i = 0; i<n;i++) {
            nWords.add(words.get(list.get(i)));
        }

        return nWords;

    }

    public Word getRandomWord() {
        List<Word> words = getAllWords();

        Random r = new Random();
        int Low = 0;
        int High = words.size();
        int random = r.nextInt(High-Low) + Low;

        return words.get(random);

    }

}
