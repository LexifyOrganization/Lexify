package parisnanterre.fr.lexify.database;

import java.io.Serializable;

import de.hdodenhof.circleimageview.CircleImageView;

public class User implements Serializable {

    int _id;
    String _pseudo;
    String _email;
    String _pass;
    int _foundwords = 0;
    int _wordsguess = 0;
    String _description = "No Description";
    CircleImageView _avatar;
    String _name;
    String _mobile;
    String _gender;
    int _age;

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

    public CircleImageView get_avatar() {
        return _avatar;
    }

    public void set_avatar(CircleImageView _avatar) {
        this._avatar = _avatar;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_mobile() {
        return _mobile;
    }

    public void set_mobile(String _mobile) {
        this._mobile = _mobile;
    }

    public String get_gender() {
        return _gender;
    }

    public void set_gender(String _gender) {
        this._gender = _gender;
    }

    public int get_age() {
        return _age;
    }

    public void set_age(int _age) {
        this._age = _age;
    }
}
