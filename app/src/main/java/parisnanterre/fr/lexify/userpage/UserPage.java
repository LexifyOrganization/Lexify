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

        User u = (User) getIntent().getSerializableExtra("user");
        int a = 5;

        name.setText(u.get_pseudo());
    }
}
