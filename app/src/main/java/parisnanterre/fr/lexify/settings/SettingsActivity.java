package parisnanterre.fr.lexify.settings;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.application.MainActivity;

public class SettingsActivity extends Activity {

    private Settings settings;
    private Switch chrono_switch;
    Button btn_menu;
    public static boolean isChronoEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        chrono_switch = (Switch) findViewById(R.id.activity_settings_btn_switch);
        btn_menu = (Button) findViewById(R.id.activity_settings_menu_btn);

        if(isChronoEnable)
            chrono_switch.setChecked(true);
        else
            chrono_switch.setChecked(false);

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        chrono_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if(isChecked){
                  isChronoEnable = true;
                  chrono_switch.setChecked(true);
              }else {
                 isChronoEnable = false;
                  chrono_switch.setChecked(false);
              }
            }

        });

    }
}
