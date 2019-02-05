package parisnanterre.fr.lexify.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.Serializable;

import de.hdodenhof.circleimageview.CircleImageView;

public class User implements Serializable {

    int _id;
    String _pseudo;
    String _email;
    String[] _friendCode;
    //String _pass;
    String description;
    String avatar;
    String name;
    String mobile;
    String gender;
    int age;
    int _gamesPlayed;
    int _gamesFailed;
    int _wordGuessed;


    public User() {

    }


    /*public User(String _pseudo, String _email, String _pass) {
        this._pseudo = _pseudo;
        this._email = _email;
        //this._pass = _pass;
        this._wordGuessed = 0;
        this._gamesFailed = 0;
        this._gamesPlayed = 0;
    }*/

    public User(String _pseudo, String _email, String[] _friendCode){
        this._pseudo = _pseudo;
        this._email = _email;
        this._friendCode = _friendCode.clone();
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

    /*
    public String get_pass() {
        return _pass;
    }

    public void set_pass(String _pass) {
        this._pass = _pass;
    }*/

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_gamesPlayed() {
        return _gamesPlayed;
    }

    public void update_gamesPlayed() {
        this._gamesPlayed += 1;
    }

    public int get_gamesFailed() {
        return _gamesFailed;
    }

    public void update_gamesFailed() {
        this._gamesFailed += 1;
    }

    //only used when the "abandon" button is pressed normally at the end of the computer game
    public void fix_gamesFailed() {
        this._gamesFailed -= 1;
    }

    public int get_wordGuessed() {
        return _wordGuessed;
    }

    public void update_wordGuessed() {
        this._wordGuessed += 1;
    }

    public void initializeStats(){
        this._gamesPlayed = 0;
        this._gamesFailed = 0;
        this._wordGuessed = 0;
    }

    public String[] get_friendCode() {
        return _friendCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // NOTE :
    // These four setter are only to be used when loading a user from SharedPreferences
    public void set_friendCode(String[] _friendCode) {
        this._friendCode = _friendCode.clone();
    }

    public void set_gamesPlayed(int _gamesPlayed) {
        this._gamesPlayed = _gamesPlayed;
    }

    public void set_gamesFailed(int _gamesFailed) {
        this._gamesFailed = _gamesFailed;
    }

    public void set_wordGuessed(int _wordGuessed) {
        this._wordGuessed = _wordGuessed;
    }

    public void saveUser(Context context){
        StringBuilder sb = new StringBuilder();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt("user_id", _id);
        prefsEditor.putString("user_pseudo", _pseudo);
        prefsEditor.putString("user_email", _email);
        prefsEditor.putString("user_description", description);
        prefsEditor.putString("user_avatar", avatar);
        prefsEditor.putString("user_name", name);
        prefsEditor.putString("user_mobile", mobile);
        prefsEditor.putString("user_gender", gender);
        prefsEditor.putInt("user_age", age);
        prefsEditor.putInt("user_gamesPlayed", _gamesPlayed);
        prefsEditor.putInt("user_gamesFailed", _gamesFailed);
        prefsEditor.putInt("user_wordGuessed", _wordGuessed);
        for (int i = 0; i < _friendCode.length; i++) {
            sb.append(_friendCode[i]).append(",");
        }
        prefsEditor.putString("user_friendCode", sb.toString());
        prefsEditor.apply();
    }

}
