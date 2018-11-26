package parisnanterre.fr.lexify.application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import io.paperdb.Paper;
import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.connection.SignInActivity;
import parisnanterre.fr.lexify.database.DatabaseUser;
import parisnanterre.fr.lexify.database.User;
import parisnanterre.fr.lexify.settings.SettingsActivity;
import parisnanterre.fr.lexify.userpage.UserPage;
import parisnanterre.fr.lexify.word.DatabaseWord;
import parisnanterre.fr.lexify.word.Word;

public class MainActivity extends Activity {

    public static User currentUser;
    public static HashMap<Integer,User> userList = new HashMap<>();

    private HashMap<Integer, User> userListToSerialize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (currentUser != null) {
            Toast toast_tmp = Toast.makeText(getApplicationContext(), String.valueOf(MainActivity.currentUser.get_id()), Toast.LENGTH_SHORT);
            toast_tmp.show();
        }

        //initialize Paper
        Paper.init(this);

        // set default languge is English
        String languge = Paper.book().read("language");
        if (languge == null){

            String lang = getResources().getConfiguration().locale.getLanguage();
            if(!(lang.equals("fr") || lang.equals("en") || lang.equals("ar")))
                lang = "en";

            Paper.book().write("language", lang);
            Locale.getDefault().getLanguage();

        }else {
            updateLanguage((String) Paper.book().read("language"));
        }
        setContentView(R.layout.activity_main);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            //final DatabaseWord db = new DatabaseWord(this);
            initializeWordDatabase();

            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }


        TextView txt_welcome = (TextView) findViewById(R.id.activity_main_txt_welcome);
        Button btn_disconnect = (Button) findViewById(R.id.activity_main_btn_disconnect);
        Button btn_play_game = (Button) findViewById(R.id.activity_main_btn_play_game);
        Button btn_settings = (Button) findViewById(R.id.activity_main_btn_settings);
        Button btn_about_game= (Button) findViewById(R.id.activity_main_btn_about_game);
        final LinearLayout lil_user = (LinearLayout) findViewById(R.id.activity_main_lil_user);
        final Button btn_profile = (Button) findViewById(R.id.activity_main_btn_see_profile);
        final Button btn_account = (Button) findViewById(R.id.activity_main_btn_account);

        //compte encore inutile, changer cette ligne plus tard
        //btn_account.setVisibility(View.GONE);

        /* Bundle b = this.getIntent().getExtras();
        if (b != null)
            currentUser = (User) b.getSerializable("Current user");*/


        //Old connection method, with a single user in "user.txt"
        /*try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput("user.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            currentUser = (User) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        //New connection method, with a list of user saved in a Hashmap<String,User> in "user.json"
        /*try{
            FileInputStream fileInputStream = getApplicationContext().openFileInput("user.json");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            //checks if json is empty by checking the content and file size
            //if yes, fills the userList with users from the local DB
            //else, fills it with the json file content
            if (objectInputStream.toString().equals("{}") || objectInputStream.available()==0){
                final DatabaseUser db = new DatabaseUser(this);
                List<User> tmplist = db.getAllUsers();
                final int size = tmplist.size();
                for (int i = 0; i < size; i++) {
                    userList.put(tmplist.get(i).get_id(), tmplist.get(i));
                }
            }
            else{
                //userList is a Hashmap<String,User> where the key is the _id from the User object
                userList = (HashMap<Integer,User>) objectInputStream.readObject();
            }
            //currentUser contains the User object identified by the _id of the last connected User
            //currentUser = userList.get(lastUser);
            objectInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ClassCastException e ){
            e.printStackTrace();
        }*/

        //New connexion method : saves the json file in SharedPreferences
        try {
            SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
            Gson gson = new Gson();
            String json = appSharedPrefs.getString("userList", "");
            Type type = new TypeToken<HashMap<Integer, User>>(){}.getType();
            //userList is a Hashmap<Integer,User> where the key is the _id from the User object
            userList = gson.fromJson(json, type);
            if (json.equals("") || userList.isEmpty()) {
                final DatabaseUser db = new DatabaseUser(this);
                List<User> tmplist = db.getAllUsers();
                final int size = tmplist.size();
                for (int i = 0; i < size; i++) {
                    userList.put(tmplist.get(i).get_id(), tmplist.get(i));
                }
            }
            //Toast toast_tmp = Toast.makeText(getApplicationContext(), String.valueOf(MainActivity.currentUser.get_gamesPlayed()), Toast.LENGTH_SHORT);
            //toast_tmp.show();
        } catch (Exception e ){
            e.printStackTrace();
        }


        if (currentUser != null) {
            txt_welcome.setText(getResources().getString(R.string.welcome) + currentUser.get_pseudo() + " !");
            lil_user.setVisibility(View.VISIBLE);
            btn_account.setVisibility(View.GONE);
            btn_profile.setVisibility(View.VISIBLE);
        } else {
            lil_user.setVisibility(View.GONE);
            btn_profile.setVisibility(View.GONE);
        }

        btn_play_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PlayingActivity.class);
                startActivity(i);
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UserPage.class);
                i.putExtra("user", currentUser);
                startActivity(i);
            }
        });

        btn_account.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (currentUser != null) {

                    //lancer activité du compte du joueur ici


                } else {
                    Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(i);
                }

            }
        });


        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
        });


        btn_disconnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    //Updates in the Hashmap the info of the current user
                    userList.put(currentUser.get_id(),currentUser);
                    userListToSerialize = userList;
                    SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(userListToSerialize);
                    prefsEditor.putString("userList", json);
                    prefsEditor.commit();
                } catch(Exception e){
                    e.printStackTrace();
                }
                currentUser = null;
                lil_user.setVisibility(View.GONE);
                btn_profile.setVisibility(View.GONE);
                btn_account.setVisibility(View.VISIBLE);

                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(getApplicationContext().getFileStreamPath("user.txt"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                writer.print("");
                writer.close();

                Context context = getApplicationContext();
                CharSequence text = getResources().getString(R.string.SuccessDeconnexion);
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        btn_about_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AboutGameActivity.class);
                startActivity(i);
            }
        });

        //currently test button
        Button btn_stats = (Button) findViewById(R.id.activity_main_btn_playerstats);
        btn_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Must be logged in to display stats", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    String stats = "Played : " + String.valueOf(currentUser.get_gamesPlayed());
                    Toast toast = Toast.makeText(getApplicationContext(), stats, Toast.LENGTH_LONG);
                    toast.show();

                    stats = "Failed : " + String.valueOf(currentUser.get_gamesFailed());
                    toast = Toast.makeText(getApplicationContext(), stats, Toast.LENGTH_LONG);
                    toast.show();

                    stats = "Guessed : " + String.valueOf(currentUser.get_wordGuessed());
                    toast = Toast.makeText(getApplicationContext(), stats, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    private void updateLanguage(String language) {

        Locale mylocale = new Locale(language);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Configuration conf = getResources().getConfiguration();
        conf.locale = mylocale;
        getResources().updateConfiguration(conf, dm);
    }

    private void initializeWordDatabase() {

        BufferedReader reader_fr= null, reader_en = null, reader_ar = null;
        DatabaseWord db = new DatabaseWord(this);
        try {
            reader_fr = new BufferedReader(
                    new InputStreamReader(getAssets().open("liste_fr.txt"), "iso-8859-1"));
            reader_en = new BufferedReader(
                    new InputStreamReader(getAssets().open("liste_en.txt"), "iso-8859-1"));
            reader_ar = new BufferedReader(
                    new InputStreamReader(getAssets().open("liste_ar.txt"), "utf-8"));

            String mLine = "";

            //changer boucle for par while (bug bizarre)
            for (int i = 0; i < 533; i++) {
                String en = reader_en.readLine();
                db.addWord(new Word(en, en, reader_fr.readLine(), reader_ar.readLine(), 0, 0));
            }

        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader_en != null || reader_ar != null || reader_fr!=null) {
                try {
                    reader_ar.close();
                    reader_en.close();
                    reader_fr.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

    }
    @Override
    public void  onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
