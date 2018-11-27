package parisnanterre.fr.lexify.userpage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.database.User;

public class EditPage extends Activity {

    User u = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

        u = (User) getIntent().getSerializableExtra("user");

        final EditText name = findViewById(R.id.activity_edit_page_input_name);
        final EditText email = findViewById(R.id.activity_edit_page_input_email);
        final EditText mobile = findViewById(R.id.activity_edit_page_input_mobile);
        final EditText description = findViewById(R.id.activity_edit_page_input_description);
        final EditText age = findViewById(R.id.activity_edit_page_input_age);
        Button save = findViewById(R.id.activity_edit_page_btn_editpage_save);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                u.set_email(email.getText().toString());
                u.set_name(name.getText().toString());
                u.set_mobile(mobile.getText().toString());
                u.set_description(description.getText().toString());

                if(!age.getText().toString().isEmpty())
                    u.set_age(Integer.parseInt(age.getText().toString()));

                Intent i = new Intent(getApplicationContext(), UserPage.class);
                i.putExtra("user", u);
                startActivity(i);

            }

        });



    }

}
