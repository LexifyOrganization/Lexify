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
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.Locale;
import io.paperdb.Paper;
import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.connection.SignInActivity;
import parisnanterre.fr.lexify.database.User;
import parisnanterre.fr.lexify.settings.SettingsActivity;
import parisnanterre.fr.lexify.verbalgame.VerbalGameActivity;
import parisnanterre.fr.lexify.word.DatabaseWord;
import parisnanterre.fr.lexify.word.Word;

public class MainActivity extends Activity {

    User currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize Paper
        Paper.init(this);

        // set default languge is English
        String languge = Paper.book().read("language");
        if (languge == null){
            Paper.book().write("language", "en");
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
        Button btn_about_game = (Button) findViewById(R.id.activity_main_btn_about_game);
        Button btn_settings = (Button) findViewById(R.id.activity_main_btn_settings);
        final LinearLayout lil_user = (LinearLayout) findViewById(R.id.activity_main_lil_user);
        Button btn_account = (Button) findViewById(R.id.activity_main_btn_account);
        ImageView logo = (ImageView) findViewById(R.id.activity_main_logo);


        //compte encore inutile, changer cette ligne plus tard
        btn_account.setVisibility(View.GONE);

       /* Bundle b = this.getIntent().getExtras();
        if (b != null)
            currentUser = (User) b.getSerializable("Current user");*/

        try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput("user.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            currentUser = (User) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (currentUser != null) {
            txt_welcome.setText("Welcome " + currentUser.get_pseudo() + " !");
            lil_user.setVisibility(View.VISIBLE);
        } else {
            lil_user.setVisibility(View.GONE);
        }
        //test

        btn_play_game.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), VerbalGameActivity.class);
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
                // TODO Auto-generated method stub
                currentUser = null;
                lil_user.setVisibility(View.GONE);

                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(getApplicationContext().getFileStreamPath("user.txt"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                writer.print("");
                writer.close();

                Context context = getApplicationContext();
                CharSequence text = "You are disconnected";
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


            /*while (reader.readLine() != null) {
                String s = mLine;
            }*/

            // do reading, usually loop until end of file reading

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
