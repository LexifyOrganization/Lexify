package parisnanterre.fr.lexify.database;

import java.io.Serializable;

public class User implements Serializable {

    int _id;
    String _pseudo;
    String _email;
    String _pass;
    int _foundwords = 0;
    int _wordsguess = 0;
    String _description = "No Description";

    public User() {

    }

    public User(String _pseudo, String _email, String _pass) {
        this._pseudo = _pseudo;
        this._email = _email;
        this._pass = _pass;
    }

    public String get_pseudo() {
        return _pseudo;
    }

    public void set_pseudo(String _pseudo) {
        this._pseudo = _pseudo;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_pass() {
        return _pass;
    }

    public void set_pass(String _pass) {
        this._pass = _pass;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_foundwords() {
        return _foundwords;
    }

    public void set_foundwords(int _foundwords) {
        this._foundwords = _foundwords;
    }

    public int get_wordsguess() {
        return _wordsguess;
    }

    public void set_wordsguess(int _wordsguess) {
        this._wordsguess = _wordsguess;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }
}
