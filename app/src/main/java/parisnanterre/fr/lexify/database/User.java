package parisnanterre.fr.lexify.database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by piotn_000 on 30/01/2018.
 */

public class User implements Parcelable{

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

    protected User(Parcel in) {
        _id = in.readInt();
        _pseudo = in.readString();
        _email = in.readString();
        _pass = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(_id);
        out.writeString(_pseudo);
        out.writeString(_email);
        out.writeString(_pass);
    }
}
