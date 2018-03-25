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
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.application.MainActivity;

public class SettingsActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private Settings settings;
    private Switch chrono_switch;
    Button btn_menu;
    public static boolean isChronoEnable = true;
    Spinner spinner_lang;
    int spinnerPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        chrono_switch = (Switch) findViewById(R.id.activity_settings_btn_switch);
        btn_menu = (Button) findViewById(R.id.activity_settings_submit);

        spinner_lang = (Spinner) findViewById(R.id.activity_languages_spinner);
        spinner_lang.setOnItemSelectedListener(this);

        //save spinner's state
        SharedPreferences spinnersaving = getSharedPreferences("spinnerstate",0);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Français");
        categories.add("English");
        categories.add("العربية");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button_about
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_lang.setAdapter(dataAdapter);

        //get spinner's state
        spinner_lang.setSelection(spinnersaving.getInt("spinnerPos", 0));


        if (isChronoEnable)
            chrono_switch.setChecked(true);
        else
            chrono_switch.setChecked(false);

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        chrono_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isChronoEnable = true;
                    chrono_switch.setChecked(true);
                } else {
                    isChronoEnable = false;
                    chrono_switch.setChecked(false);
                }
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
        spinnerPos = i;
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
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    protected void onStop(){
        super.onStop();
        SharedPreferences spinnersaving = getSharedPreferences("spinnerstate",0);
        SharedPreferences.Editor editor = spinnersaving.edit();
        editor.putInt("spinnerPos", spinnerPos);
        editor.apply();
    }


}
