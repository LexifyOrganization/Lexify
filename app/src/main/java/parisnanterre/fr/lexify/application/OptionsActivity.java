package parisnanterre.fr.lexify.application;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

import io.paperdb.Paper;
import parisnanterre.fr.lexify.R;

/**
 * Created by Hani on 03/03/2018.
 */

public class OptionsActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //SPINNER OF LANGUAGES

        Button btn_lang = (Button) findViewById(R.id.op1);
        btn_lang.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Dialog
                final AlertDialog.Builder builder = new AlertDialog.Builder(OptionsActivity.this);
                final View mview = getLayoutInflater().inflate(R.layout.dialog,null);

                final Spinner sp = (Spinner) mview.findViewById(R.id.spinner);

                ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(OptionsActivity.this, R.array.languages, android.R.layout.simple_spinner_item);
                adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp.setAdapter(adp);
                //button Ok
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ( !sp.getSelectedItem().toString().equalsIgnoreCase("choose a language")) {
                            String text = sp.getSelectedItem().toString();
                            switch (text) {
                                case "English":
                                    Paper.book().write("language","en");
                                    updateView("en");
//                                    updateView((String)Paper.book().read("language"));
//                                    LocalHelper.setLocale(OptionsActivity.this,"en");
                                    break;
                                case "French":
                                    Paper.book().write("language","fr");
                                    updateView("fr");
//                                    updateView((String)Paper.book().read("language"));
//                                    LocalHelper.setLocale(OptionsActivity.this,"fr");
                                    break;
                                case "Arabic":
                                    Paper.book().write("language","ar");
                                    updateView("ar");
//                                    updateView((String)Paper.book().read("language"));
//                                    LocalHelper.setLocale(OptionsActivity.this,"ar");
                                    break;
                            }
                            Toast.makeText(OptionsActivity.this, "You choosed "+text, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(OptionsActivity.this, "You didn't choose a language ", Toast.LENGTH_LONG).show();
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
    @SuppressWarnings("deprecation")
    private void updateView(String lang) {
        Locale mylocale=new Locale(lang);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Configuration conf = getResources().getConfiguration();
        conf.locale=mylocale;
        getResources().updateConfiguration(conf,dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
    }
}
