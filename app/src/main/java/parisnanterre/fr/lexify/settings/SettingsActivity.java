package parisnanterre.fr.lexify.settings;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.application.MainActivity;

import static parisnanterre.fr.lexify.wordsmemory.WordsMemoryFragment.step;
import static parisnanterre.fr.lexify.wordsmemory.WordsMemoryFragment.lives;
import static parisnanterre.fr.lexify.computergame.ComputerGameFragment.timeSettingComputer;
import static parisnanterre.fr.lexify.verbalgame.VerbalGameFragment.millisCountDownTimer;

public class SettingsActivity extends Activity {


    Button btn_submit;
    Spinner spinner_lang;
    Spinner spinner_level;
    Spinner spinner_step;
    Spinner spinner_lives;
    int spinnerPos, spinnerPos2, spinnerPos3, spinnerPos4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btn_submit = (Button) findViewById(R.id.activity_settings_submit);
        spinner_lang = (Spinner) findViewById(R.id.activity_settings_spinner_languages);
        spinner_level = (Spinner) findViewById(R.id.activity_settings_spinner_level);
        spinner_step = (Spinner) findViewById(R.id.activity_settings_spinner_steps);
        spinner_lives = (Spinner) findViewById(R.id.activity_settings_spinner_live);

        //save spinner's state
        SharedPreferences spinnersaving = getSharedPreferences("spinnerstate", 0);
        SharedPreferences spinnersaving2 = getSharedPreferences("spinnerstate2", 0);
        SharedPreferences spinnersaving3 = getSharedPreferences("spinnerstate3",0);
        SharedPreferences spinnersaving4 = getSharedPreferences("spinnerstate4",0);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Français");
        categories.add("English");
        //categories.add("العربية");

        List<String> categories2 = new ArrayList<>();
        categories2.add(getResources().getString(R.string.off));
        categories2.add(getResources().getString(R.string.easy));
        categories2.add(getResources().getString(R.string.medium));
        categories2.add(getResources().getString(R.string.hard));

        List<String> categories3 = new ArrayList<>();
        categories3.add("25");
        categories3.add("50");
        categories3.add("100");
        categories3.add(getResources().getString(R.string.nosteps));

        List<String> categories4 = new ArrayList<>();
        categories4.add(getResources().getString(R.string.life));
        categories4.add(getResources().getString(R.string.nolife));

        // Creating adapters for spinners
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories3);
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories4);

        // Drop down layout style - list view with radio button_about
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapters to spinners
        spinner_lang.setAdapter(dataAdapter);
        spinner_level.setAdapter(dataAdapter2);
        spinner_step.setAdapter(dataAdapter3);
        spinner_lives.setAdapter(dataAdapter4);

        //get spinner's state
        spinner_lang.setSelection(spinnersaving.getInt("spinnerPos", -1));
        spinner_level.setSelection(spinnersaving2.getInt("spinnerPos2", -1));
        spinner_step.setSelection(spinnersaving3.getInt("spinnerPos3",-1));
        spinner_lives.setSelection(spinnersaving4.getInt("spinnerPos4",-1));
        spinner_lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                spinnerPos = i;
                switch (item) {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item2 = adapterView.getItemAtPosition(i).toString();
                spinnerPos2 = i;
                if (item2.equals(getResources().getString(R.string.off))) {
                    spinner_level.setPrompt(getResources().getString(R.string.off));
                    millisCountDownTimer = 0;
                    timeSettingComputer = 0;


                } else if (item2.equals(getResources().getString(R.string.easy))) {
                    spinner_level.setPrompt(getResources().getString(R.string.easy));
                    millisCountDownTimer = 40000;
                    timeSettingComputer = 40000;

                } else if (item2.equals(getResources().getString(R.string.medium))) {
                    spinner_level.setPrompt(getResources().getString(R.string.medium));
                    millisCountDownTimer = 30000;
                    timeSettingComputer = 30000;

                } else if (item2.equals(getResources().getString(R.string.hard))) {
                    spinner_level.setPrompt(getResources().getString(R.string.hard));
                    millisCountDownTimer = 20000;
                    timeSettingComputer = 20000;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_step.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item3 = adapterView.getItemAtPosition(i).toString();
                spinnerPos3 = i;
                switch (item3) {
                    case "25":
                        spinner_step.setPrompt("25");
                        step=25;
                        break;
                    case "50":
                        spinner_step.setPrompt("50");
                        step=50;
                        break;
                    case "100":
                        spinner_step.setPrompt("100");
                        step=100;
                        break;
                }
                if(item3.equals(getResources().getString(R.string.nosteps))){
                    step=0;
                    spinner_step.setPrompt(getResources().getString(R.string.nosteps));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_lives.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item4 = adapterView.getItemAtPosition(i).toString();
                spinnerPos4 = i;


                if(item4.equals(getResources().getString(R.string.life))){
                    lives=3;
                    spinner_lives.setPrompt(getResources().getString(R.string.life));
                }
                else if (item4.equals(getResources().getString(R.string.nolife))){
                    lives=0;
                    spinner_lives.setPrompt(getResources().getString(R.string.nolife));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
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


    protected void onStop() {
        super.onStop();
        SharedPreferences spinnersaving = getSharedPreferences("spinnerstate", 0);
        SharedPreferences spinnersaving2 = getSharedPreferences("spinnerstate2", 0);
        SharedPreferences spinnersaving3 = getSharedPreferences("spinnerstate3",0);
        SharedPreferences spinnersaving4 = getSharedPreferences("spinnerstate4",0);
        SharedPreferences.Editor editor = spinnersaving.edit();
        SharedPreferences.Editor editor2 = spinnersaving2.edit();
        SharedPreferences.Editor editor3 = spinnersaving3.edit();
        SharedPreferences.Editor editor4 = spinnersaving4.edit();
        editor.putInt("spinnerPos", spinnerPos);
        editor2.putInt("spinnerPos2", spinnerPos2);
        editor3.putInt("spinnerPos3", spinnerPos3);
        editor4.putInt("spinnerPos4", spinnerPos4);
        editor.apply();
        editor2.apply();
        editor3.apply();
        editor4.apply();
    }

}