package parisnanterre.fr.lexify.application;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.word.DatabaseWord;
import parisnanterre.fr.lexify.word.Word;

public class VerbalGameActivity extends AppCompatActivity {

    public int cpt = 1;
    public int score = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbal_game);

        final DatabaseWord db = new DatabaseWord(getApplicationContext());

        //final Button btn_play_game = (Button) findViewById(R.id.activity_verbal_game_btn_round);
        final Button btn_true = (Button) findViewById(R.id.activity_verbal_game_btn_true);
        final Button btn_false = (Button) findViewById(R.id.activity_verbal_game_btn_false);
        final Button btn_pass = (Button) findViewById(R.id.activity_verbal_game_btn_pass);
        final TextView txt_nbmanche = (TextView) findViewById(R.id.activity_verbal_game_txt_manche);
        final TextView txt_word = (TextView) findViewById(R.id.activity_verbal_game_txt_word);
        final TextView txt_score = (TextView) findViewById(R.id.activity_verbal_game_txt_score);

        txt_nbmanche.setText("Round 1/4");
        txt_score.setText("Score :" + score);
        txt_word.setText(db.getRandomWord().getWord());


        btn_pass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(cpt==4) {
                    score = score -5;
                    finishGame();
                }
                else{
                    score= score - 5;
                    cpt++;
                    txt_score.setText("Score :" + score);

                    Word random = db.getRandomWord();
                    txt_word.setText(random.getWord());
                    txt_nbmanche.setText("Round " + cpt +"/4");
                }

            }
        });

        btn_false.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                score--;
                txt_score.setText("Score :" + score);

            }
        });


        btn_true.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(cpt==4)
                {
                    score++;
                    finishGame();
                }
                else {
                    cpt++;

                    score++;
                    txt_score.setText("Score :" + score);

                    Word random = db.getRandomWord();
                    txt_word.setText(random.getWord());
                    txt_nbmanche.setText("Round " + cpt +"/4");


                }


            }
        });



    }

    private void finishGame() {
        Context context = getApplicationContext();
        CharSequence text = "Finished ! Final score : " + score;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }
}
