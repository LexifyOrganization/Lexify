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

import static parisnanterre.fr.lexify.verbalgame.VerbalGameFragment.millisCountDownTimer;

public class SettingsActivity extends Activity {


    Button btn_submit;
    Spinner spinner_lang;
    Spinner spinner_level;
    int spinnerPos, spinnerPos2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btn_submit = (Button) findViewById(R.id.activity_settings_submit);
        spinner_lang = (Spinner) findViewById(R.id.activity_languages_spinner);
        spinner_level = (Spinner) findViewById(R.id.activity_settings_spinner_level);

        //save spinner's state
        SharedPreferences spinnersaving = getSharedPreferences("spinnerstate", 0);
        SharedPreferences spinnersaving2 = getSharedPreferences("spinnerstate2", 0);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Français");
        categories.add("English");
        categories.add("العربية");

        List<String> categories2 = new ArrayList<>();
        categories2.add(getResources().getString(R.string.off));
        categories2.add(getResources().getString(R.string.easy));
        categories2.add(getResources().getString(R.string.medium));
        categories2.add(getResources().getString(R.string.hard));

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
        spinner_lang.setSelection(spinnersaving.getInt("spinnerPos", -1));
        spinner_level.setSelection(spinnersaving2.getInt("spinnerPos2", -1));

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

                } else if (item2.equals(getResources().getString(R.string.easy))) {
                    spinner_level.setPrompt(getResources().getString(R.string.easy));
                    millisCountDownTimer = 40000;

                } else if (item2.equals(getResources().getString(R.string.medium))) {
                    spinner_level.setPrompt(getResources().getString(R.string.medium));
                    millisCountDownTimer = 30000;

                } else if (item2.equals(getResources().getString(R.string.hard))) {
                    spinner_level.setPrompt(getResources().getString(R.string.hard));
                    millisCountDownTimer = 20000;

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
        SharedPreferences.Editor editor = spinnersaving.edit();
        SharedPreferences.Editor editor2 = spinnersaving2.edit();
        editor.putInt("spinnerPos", spinnerPos);
        editor2.putInt("spinnerPos2", spinnerPos2);
        editor.apply();
        editor2.apply();
    }

}