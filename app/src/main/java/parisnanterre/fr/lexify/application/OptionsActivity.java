package parisnanterre.fr.lexify.application;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import parisnanterre.fr.lexify.R;

/**
 * Created by Hania on 03/03/2018.
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
                final View mview = getLayoutInflater().inflate(R.layout.dialog, null);
                builder.setTitle("Add items to your shopping list");

                final Spinner sp = (Spinner) mview.findViewById(R.id.spinner);

                ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(OptionsActivity.this, R.array.languages, android.R.layout.simple_spinner_item);
                adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp.setAdapter(adp);
                //button Ok
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ( !sp.getSelectedItem().toString().equalsIgnoreCase("choose a category")) {
                            String text = sp.getSelectedItem().toString();
                            switch (text) {
                                case "English":
                                    LocalHelper.setLocale(OptionsActivity.this,"en");
                                    break;
                                case "French":
                                    LocalHelper.setLocale(OptionsActivity.this,"fr");
                                    break;
                                case "Arabic":
                                    LocalHelper.setLocale(OptionsActivity.this,"ar");
                                    break;
                            }
                            Toast.makeText(OptionsActivity.this, "You choosed"+text, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(OptionsActivity.this, "You didn't choose", Toast.LENGTH_LONG).show();
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
