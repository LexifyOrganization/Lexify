package parisnanterre.fr.lexify.userpage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.database.User;

public class UserPage extends Activity implements Serializable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        TextView name = findViewById(R.id.activity_user_page_name);
        TextView wordsfound = findViewById(R.id.activity_user_page_textview_found_words);
        TextView wordsguess = findViewById(R.id.activity_user_page_textview_words_made_guess);
        TextView description = findViewById(R.id.activity_user_page_textview_description);

        User u = (User) getIntent().getSerializableExtra("user");
        int a = 5;

        name.setText(u.get_pseudo());
        wordsfound.setText(String.valueOf(u.get_foundwords()));
        wordsguess.setText(String.valueOf(u.get_wordsguess()));
        description.setText(u.get_description());

    }
}
