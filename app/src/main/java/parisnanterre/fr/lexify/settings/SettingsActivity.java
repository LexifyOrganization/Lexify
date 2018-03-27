package parisnanterre.fr.lexify.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.application.MainActivity;

import static parisnanterre.fr.lexify.verbalgame.VerbalGameFragment.millisCountDownTimer;

public class SettingsActivity extends Activity implements AdapterView.OnItemSelectedListener {


    Button btn_menu;
    Spinner spinner_lang;
    Spinner spinner_level;
    int spinnerPos, spinnerPos2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btn_menu = (Button) findViewById(R.id.activity_settings_submit);
        spinner_lang = (Spinner) findViewById(R.id.activity_languages_spinner);
        spinner_lang.setOnItemSelectedListener(this);
        spinner_level= (Spinner) findViewById(R.id.activity_settings_spinner_level);
        spinner_level.setOnItemSelectedListener(this);

        //save spinner's state
        SharedPreferences spinnersaving = getSharedPreferences("spinnerstate",0);
        SharedPreferences spinnersaving2 = getSharedPreferences("spinnerstate",0);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Français");
        categories.add("English");
        categories.add("العربية");

        List<String> categories2 = new ArrayList<>();
        categories2.add("No Chronometer");
        categories2.add("Easy");
        categories2.add("Middle");
        categories2.add("Hard");

        // Creating adapters for spinners
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);

        // Drop down layout style - list view with radio button_about
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapters to spinners
        spinner_lang.setAdapter(dataAdapter);
        spinner_level.setAdapter(dataAdapter2);

        //get spinner's state
        spinner_lang.setSelection(spinnersaving.getInt("spinnerPos", 0));
        spinner_level.setSelection(spinnersaving2.getInt("spinnerPos", 0));

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
    }

    private void updateLanguage(String lang) {
        Locale mylocale = new Locale(lang);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Configuration conf = getResources().getConfiguration();
        conf.locale = mylocale;
        getResources().updateConfiguration(conf, dm);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        String item2 = adapterView.getItemAtPosition(i).toString();
        spinnerPos = i;
        spinnerPos2 = i;
        switch (item){
            case "English":
                Paper.book().write("language", "en");
                updateLanguage("en");
                spinner_lang.setPrompt("English");
                break;
            case "Français":
                Paper.book().write("language", "fr");
                updateLanguage("fr");
                spinner_lang.setPrompt("Français");
                break;
            case "العربية":
                Paper.book().write("language", "ar");
                updateLanguage("ar");
                spinner_lang.setPrompt("العربية");
                break;
        }

        switch (item2){
            case "No Chronometer":
                millisCountDownTimer=0;
                spinner_level.setPrompt("No Chronometer");
                break;
            case "Easy":
                millisCountDownTimer=40000;
                spinner_level.setPrompt("Easy");
                break;
            case "Middle":
                millisCountDownTimer=30000;
                spinner_level.setPrompt("Middle");
                break;
            case "Hard":
                millisCountDownTimer=20000;
                spinner_level.setPrompt("Hard");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    protected void onStop(){
        super.onStop();
        SharedPreferences spinnersaving = getSharedPreferences("spinnerstate",0);
        SharedPreferences spinnersaving2 = getSharedPreferences("spinnerstate",0);
        SharedPreferences.Editor editor = spinnersaving.edit();
        SharedPreferences.Editor editor2 = spinnersaving2.edit();
        editor.putInt("spinnerPos", spinnerPos);
        editor.apply();
        editor.putInt("spinnerPos", spinnerPos2);
        editor2.apply();
    }


}
