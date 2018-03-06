package parisnanterre.fr.lexify.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;

import io.paperdb.Paper;
import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.application.MainActivity;

public class SettingsActivity extends Activity {

    private Settings settings;
    private Switch chrono_switch;
    Button btn_menu;
    public static boolean isChronoEnable = false;
    private Button btn_lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        chrono_switch = (Switch) findViewById(R.id.activity_settings_btn_switch);
        btn_menu = (Button) findViewById(R.id.activity_settings_menu_btn);
        Button btn_lang = (Button) findViewById(R.id.activity_settings_btn_languages);

        //spinner
        btn_lang.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Dialog
                final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.dialog, null);

                final Spinner sp = (Spinner) mview.findViewById(R.id.spinner);

                ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(SettingsActivity.this, R.array.languages, android.R.layout.simple_spinner_item);
                adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp.setAdapter(adp);
                //button Ok
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!sp.getSelectedItem().toString().equalsIgnoreCase("choose a language")) {
                            String text = sp.getSelectedItem().toString();
                            switch (text) {
                                case "English":
                                    Paper.book().write("language", "en");
                                    updateView("en");
                                    break;
                                case "French":
                                    Paper.book().write("language", "fr");
                                    updateView("fr");
                                    break;
                                case "Arabic":
                                    Paper.book().write("language", "ar");
                                    updateView("ar");
//                                    updateView((String)Paper.book().read("language"));
//                                    LocalHelper.setLocale(OptionsActivity.this,"ar");
                                    break;
                            }
                            Toast.makeText(SettingsActivity.this, "You choosed " + text, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SettingsActivity.this, "You didn't choose a language ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                //button Cancel
                builder.setView(mview);
                AlertDialog dlog = builder.create();
                dlog.show();
            }
        });

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

    private void updateView(String lang) {
        Locale mylocale = new Locale(lang);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Configuration conf = getResources().getConfiguration();
        conf.locale = mylocale;
        getResources().updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, SettingsActivity.class);
        startActivity(refresh);
    }
}
