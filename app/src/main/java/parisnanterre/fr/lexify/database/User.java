package parisnanterre.fr.lexify.database;

import java.io.Serializable;

/**
 * Created by piotn_000 on 30/01/2018.
 */

public class User implements Serializable{

    int _id;
    String _pseudo;
    String _email;
    String _pass;

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
}
