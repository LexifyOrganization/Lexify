package parisnanterre.fr.lexify.word;

/**
 * Created by piotn_000 on 05/02/2018.
 */

public class Word {

    int id;
    String word;
    String word_en;
    String word_fr;
    String word_ar;
    int difficulty;
    int numberPlayed;

    public  Word() {

    }

    //word -> par défaut anglais
    public Word(String word, String en, String fr, String ar, int difficulty, int numberPlayed) {
        word_en = en;
        word_fr = fr;
        word_ar = ar;

        this.word = word;
        this.difficulty = difficulty;
        this.numberPlayed = numberPlayed;
    }

    public Word(String word) {
        this.word = word;
    }

    public Word(String word, int difficulty, int numberPlayed) {
        this.word = word;
        this.difficulty = difficulty;
        this.numberPlayed = numberPlayed;
    }

    public void setWord_en(String word_en) {
        this.word_en = word_en;
    }

    public void setWord_fr(String word_fr) {
        this.word_fr = word_fr;
    }

    public void setWord_ar(String word_ar) {
        this.word_ar = word_ar;
    }

    public String getWord_en() {
        return word_en;
    }

    public String getWord_fr() {
        return word_fr;
    }

    public String getWord_ar() {
        return word_ar;
    }

    public String getWord(String langue) {
        switch (langue)
        {
            case "fr":
                return getWord_fr();
            case "en":
                return getWord_en();
            case "ar":
                return getWord_ar();
            default:
                return getWord();
        }
    }


    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getNumberPlayed() {
        return numberPlayed;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumberPlayed(int numberPlayed) {
        this.numberPlayed = numberPlayed;
    }
}
