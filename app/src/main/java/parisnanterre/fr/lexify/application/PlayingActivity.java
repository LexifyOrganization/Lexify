package parisnanterre.fr.lexify.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.computergame.ComputerGameActivity;
import parisnanterre.fr.lexify.database.User;
import parisnanterre.fr.lexify.verbalgame.VerbalGameActivity;
import parisnanterre.fr.lexify.wordsmemory.WordsMemoryActivity;

public class PlayingActivity extends Activity {

    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        Button btn_disconnect = (Button) findViewById(R.id.activity_playing_btn_disconnect);
        Button btn_verbal_game = (Button) findViewById(R.id.activity_playing_btn_play_game);
        Button btn_computer = (Button) findViewById(R.id.activity_playing_btn_computer_game);
        Button btn_words_memory = (Button) findViewById(R.id.activity_playing_btn_words_memory);
        TextView txt_welcome = (TextView) findViewById(R.id.activity_playing_txt_welcome);
        final LinearLayout lil_user = (LinearLayout) findViewById(R.id.activity_playing_lil_user);

        btn_verbal_game.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), VerbalGameActivity.class);
                startActivity(i);

            }
        });

        btn_computer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ComputerGameActivity.class);
                startActivity(i);

            }
        });

        btn_words_memory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), WordsMemoryActivity.class);
                startActivity(i);
            }
        });

        if (currentUser != null) {
            txt_welcome.setText(getResources().getString(R.string.welcome) + currentUser.get_pseudo() + " !");
            lil_user.setVisibility(View.VISIBLE);
        } else {
            lil_user.setVisibility(View.GONE);
        }
    }
}
