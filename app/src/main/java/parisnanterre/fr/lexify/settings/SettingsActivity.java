package parisnanterre.fr.lexify.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.application.MainActivity;

import static parisnanterre.fr.lexify.verbalgame.VerbalGameFragment.millisCountDownTimer;

public class SettingsActivity extends Activity {

    private Button btn_level_difficulty;
    public static boolean isChronoEnable = false;
    Button btn_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btn_level_difficulty = (Button) findViewById(R.id.activity_settings_btn_level_difficulty);
        btn_menu = (Button) findViewById(R.id.activity_settings_menu_btn);


        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        btn_level_difficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.dialog_time_chrono, null);
                final Spinner sp = (Spinner) mview.findViewById(R.id.spinner_level_difficulty);


                ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(SettingsActivity.this,
                        R.array.time_of_chronometer, android.R.layout.simple_spinner_item);
                adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp.setAdapter(adp);

                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!sp.getSelectedItem().toString().equalsIgnoreCase("Choose a level")) {
                            String text = sp.getSelectedItem().toString();
                            switch (text) {
                                case "Easy":
                                    millisCountDownTimer=0;
                                    Toast.makeText(SettingsActivity.this, "Chronometer is off ! You choosed " + text, Toast.LENGTH_LONG).show();
                                    break;
                                case "Middle":
                                    millisCountDownTimer=40000;
                                    Toast.makeText(SettingsActivity.this, "Chronometer initialized at 40 seconds ! You choosed " + text, Toast.LENGTH_LONG).show();
                                    break;
                                case "Hard":
                                    millisCountDownTimer=20000;
                                    Toast.makeText(SettingsActivity.this, "Chronomoter initialized at 20 seconds ! You choosed " + text, Toast.LENGTH_LONG).show();
                                    break;
                            }
                        } else {
                            Toast.makeText(SettingsActivity.this, "You didn't choose a level of difficulty ", Toast.LENGTH_LONG).show();
                        }
                    }

                });

                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setView(mview);
                AlertDialog dlog = builder.create();
                dlog.show();
            }
        });

            }
    }
