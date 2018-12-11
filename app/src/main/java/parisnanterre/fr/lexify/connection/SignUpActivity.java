package parisnanterre.fr.lexify.connection;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import parisnanterre.fr.lexify.application.MainActivity;
import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.database.DatabaseUser;
import parisnanterre.fr.lexify.database.User;

import static parisnanterre.fr.lexify.application.MainActivity.currentUser;

public class SignUpActivity extends Activity {

    //private HashMap<Integer, User> userListToSerialize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button btn_signup = (Button) findViewById(R.id.signup_btn_signup);
        final EditText edt_pseudo = (EditText) findViewById(R.id.signup_edt_pseudo);
        //final EditText edt_pass = (EditText) findViewById(R.id.signup_edt_password);
        //final EditText edt_confirm_pass = (EditText) findViewById(R.id.signup_edt_confirmpassword);
        final EditText edt_email = (EditText) findViewById(R.id.signup_edt_email);

        final TextView txt_errors = (TextView) findViewById(R.id.signup_txt_errors);

        //final DatabaseUser db = new DatabaseUser(this);


        btn_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String pseudo = edt_pseudo.getText().toString();
                //String pass = edt_pass.getText().toString();
                //String confirm_pass = edt_confirm_pass.getText().toString();
                String email = edt_email.getText().toString();

                txt_errors.setText("");

                if (!Pattern.compile("^([a-zA-Z0-9-_]{2,36})$", Pattern.CASE_INSENSITIVE).matcher(pseudo).find()
                        && (pseudo.length() >= 1)) {
                    txt_errors.append(getResources().getString(R.string.wrongformatpseudo));
                }

                if (!Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email).find()
                        && (email.length() >= 1)) {
                    txt_errors.append(getResources().getString(R.string.wrongformatemail));
                }
                /*
                if (!pass.equals(confirm_pass) && pass.length() >= 4 && confirm_pass.length() >= 4) {
                    txt_errors.append(getResources().getString(R.string.passwordsnomatch));
                }

                if ((confirm_pass.length() > 36 || pass.length() > 36 || confirm_pass.length() < 4 || pass.length() < 4) && (confirm_pass.length() >= 1 && pass.length() >= 1)) {
                    txt_errors.append(getResources().getString(R.string.limitspassword));
                }
                */
                if (pseudo.length() == 0) {
                    txt_errors.append(getResources().getString(R.string.choosepseudo));
                }

                if (email.length() == 0) {
                    txt_errors.append(getResources().getString(R.string.enteremail));
                }
                /*
                if (pass.length() == 0) {
                    txt_errors.append(getResources().getString(R.string.mustpassword));
                }

                if (confirm_pass.length() == 0) {
                    txt_errors.append(getResources().getString(R.string.mustconfirmpassword));
                }
                */

                /*
                if (txt_errors.getText().toString().length() == 0) {
                    List<User> users = db.getAllUsers();

                    for (User u : users) {
                        if (pseudo.equals(u.get_pseudo())) {
                            txt_errors.append(getResources().getString(R.string.pseudoalreadyexist));
                        }
                        if (email.equals(u.get_email())) {
                            txt_errors.append(getResources().getString(R.string.emailalreadyused));
                        }
                    }
                }


                if (txt_errors.getText().toString().length() == 0) {
                    db.addUser(new User(edt_pseudo.getText().toString(), edt_email.getText().toString(), edt_pass.getText().toString()));
                    insertUserInFileFromDB(edt_pseudo.getText().toString(), edt_email.getText().toString(), edt_pass.getText().toString(), db);

                    Context context = getApplicationContext();
                    CharSequence text = getResources().getString(R.string.youraccount) + pseudo + getResources().getString(R.string.hasbeencreated);
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                }


                Log.d("Reading: ", "Reading all contacts..");
                List<User> contacts = db.getAllUsers();

                for (User cn : contacts) {
                    String log = "Id: " + cn.get_id() + " ,Pseudo: " + cn.get_pseudo() + " ,Pass: " + cn.get_pass() + " ,Email" + cn.get_email();
                    // Writing Contacts to log
                    Log.d("Name: ", log);

                }*/

                if (txt_errors.getText().toString().length() == 0) {
                    Context context = getApplicationContext();
                    MainActivity.currentUser = new User(edt_pseudo.getText().toString(), edt_email.getText().toString(), generateFriendCode());
                    MainActivity.currentUser.initializeStats();
                    /*SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = prefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(MainActivity.currentUser);
                    prefsEditor.putString("currentUser", json);
                    prefsEditor.commit();*/

                    /*
                    User userTest;
                    Gson gson2 = new Gson();
                    String json2 = prefs.getString("currentUser", "");
                    userTest = gson2.fromJson(json2, User.class);

                    Toast.makeText(getApplicationContext(), userTest.get_pseudo(), Toast.LENGTH_SHORT).show();*/

                    MainActivity.currentUser.saveUser(context);

                    /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    Toast.makeText(context, prefs.getString("user_pseudo","err"),Toast.LENGTH_LONG).show();*/

                    CharSequence text = getResources().getString(R.string.youraccount) + pseudo + getResources().getString(R.string.hasbeencreated);
                    int duration = Toast.LENGTH_SHORT;

                    Toast.makeText(context, text, duration).show();

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            }
        });


    }
    /*
    private void insertUserInFileFromDB(String pseudo, String mail, String mdp, DatabaseUser db){
        User user = new User(pseudo, mail, mdp);
        //gets the id generated by the DB
        user.set_id(db.getLastCreatedId());
        //initialize the user's game stats
        user.initializeStats();
        //updates the current user
        //Done this way because signing up signs in at the same time
        MainActivity.currentUser = user;
        try{
            FileOutputStream fileOutputStream = openFileOutput("user.json", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            userList.put(user.get_id(),user);
            objectOutputStream.writeObject(userList);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();

            userList.put(user.get_id(),user);
            userListToSerialize = userList;
            SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(userListToSerialize);
            prefsEditor.putString("userList", json);
            prefsEditor.commit();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e ){
            e.printStackTrace();
        }

    }*/

    private String[] generateFriendCode(){
        Random random = new Random();
        String[] res = new String[12];

        for(int i = 0; i<12 ; i++){
            res[i] = String.valueOf(random.nextInt(10));
        }

        return res;
    }
}
