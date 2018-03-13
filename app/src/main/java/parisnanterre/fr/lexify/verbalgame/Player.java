package parisnanterre.fr.lexify.verbalgame;

/**
 * Created by piotn_000 on 10/02/2018.
 */

public class Player {

    private String name;
    private int nbWordFound;
    private int nbWordNotFound;
    private int guessScore;
    private int haveToGuessScore;
    private boolean currentPlayer;

    public Player(String name) {
        this.name = name;
        nbWordFound = 0;
        nbWordNotFound = 0;
        guessScore = 0;
        haveToGuessScore = 0;
        currentPlayer = false;
    }

    public boolean isCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(boolean currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void incFoundWord() {
        nbWordFound++;
    }

    public void incNotFoundWord() {
        nbWordNotFound++;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getNbWordFound() {
        return nbWordFound;
    }

    public int getNbWordNotFound() {
        return nbWordNotFound;
    }

    public int getGuessScore() {
        return guessScore;
    }

    public int getHaveToGuessScore() {
        return haveToGuessScore;
    }
}
