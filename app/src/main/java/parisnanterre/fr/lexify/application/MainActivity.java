package parisnanterre.fr.lexify.application;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
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
import parisnanterre.fr.lexify.computergame.ComputerGameActivity;
import parisnanterre.fr.lexify.connection.SignInActivity;
import parisnanterre.fr.lexify.connection.SignUpActivity;
import parisnanterre.fr.lexify.database.DatabaseUser;
import parisnanterre.fr.lexify.database.User;
import parisnanterre.fr.lexify.settings.SettingsActivity;
import parisnanterre.fr.lexify.userpage.UserPage;
import parisnanterre.fr.lexify.verbalgame.VerbalGameActivity;
import parisnanterre.fr.lexify.word.DatabaseWord;
import parisnanterre.fr.lexify.word.Word;

public class MainActivity extends FragmentActivity
        implements MainFragment.OnFragmentInteractionListener,
        PlayingFragment.OnFragmentInteractionListener {

    public static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        MainActivity.currentUser = currentUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView txt_welcome = (TextView) findViewById(R.id.activity_main_txt_welcome);
        //final LinearLayout lil_user = (LinearLayout) findViewById(R.id.activity_main_lil_user);
        //final Button btn_profile = (Button) findViewById(R.id.activity_main_btn_see_profile);
        //final Button btn_account = (Button) findViewById(R.id.activity_main_btn_account);

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

        try {
            currentUser = loadUser(this);
        }catch(Exception e){
            e.printStackTrace();
        }

        if (currentUser != null && currentUser.get_id() != 0) {
            txt_welcome.setText(getResources().getString(R.string.welcome) + currentUser.get_pseudo() + " !");
            //lil_user.setVisibility(View.VISIBLE);
        } else {
            //lil_user.setVisibility(View.GONE);
            Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(i);
        }

        setFragment(new MainFragment());
    }

    public void setFragment(Fragment f) {

        /*FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_main_fragment, f);
        transaction.commit();*/

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main_fragment, f, "main_fragment");
        fragmentTransaction.commit();
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
            for (int i = 0; i < 535; i++) {
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

    public User loadUser(Context context){
        User u = new User();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        u.set_id(prefs.getInt("user_int", 0));
        u.set_pseudo(prefs.getString("user_pseudo", ""));
        u.set_email(prefs.getString("user_email", ""));
        u.setDescription(prefs.getString("user_description", ""));
        u.setAvatar(prefs.getString("user_avatar", ""));
        u.setName(prefs.getString("user_name", ""));
        u.setMobile(prefs.getString("user_mobile", ""));
        u.setGender(prefs.getString("user_gender", ""));
        u.setAge(prefs.getInt("user_age", 0));
        u.set_gamesPlayed(prefs.getInt("user_gamesPlayed", 0));
        u.set_gamesFailed(prefs.getInt("user_gamesFailed", 0));
        u.set_wordGuessed(prefs.getInt("user_wordGuessed", 0));
        u.set_friendCode(prefs.getString("user_friendCode", "").split(","));

        return u;
    }

    @Override
    public void  onBackPressed() {
        /*SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.currentUser);
        prefsEditor.putString("currentUser", json);
        prefsEditor.commit();*/
        currentUser.saveUser(this);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
