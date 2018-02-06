package parisnanterre.fr.lexify.word;

/**
 * Created by piotn_000 on 05/02/2018.
 */

public class Word {

    int id;
    String word;
    int difficulty;
    int numberPlayed;

    public  Word() {

    }

    public Word(String word) {
        this.word = word;
    }

    public Word(String word, int difficulty, int numberPlayed) {
        this.word = word;
        this.difficulty = difficulty;
        this.numberPlayed = numberPlayed;
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
