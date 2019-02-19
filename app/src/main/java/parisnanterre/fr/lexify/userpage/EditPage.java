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

        final EditText name = findViewById(R.id.input_name);
        name.setText(u.getName());
        final EditText email = findViewById(R.id.input_email);
        email.setText(u.get_email());
        final EditText mobile = findViewById(R.id.input_mobile);
        mobile.setText(u.getMobile());
        final EditText description = findViewById(R.id.input_description);
        description.setText(u.getDescription());
        final EditText age = findViewById(R.id.input_age);
        if(u.getAge()!=0)
            age.setText(Integer.toString(u.getAge()));
        Button save = findViewById(R.id.btn_editpage_save);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                u.set_email(email.getText().toString());
                u.setName(name.getText().toString());
                u.setMobile(mobile.getText().toString());
                u.setDescription(description.getText().toString());

                if(!age.getText().toString().isEmpty())
                    u.setAge(Integer.parseInt(age.getText().toString()));
                else
                    u.setAge(0);

                try{
                    u.saveUser(EditPage.this.getApplicationContext());
                }catch (Exception e){
                    e.printStackTrace();
                }


                Intent i = new Intent(getApplicationContext(), UserPage.class);
                i.putExtra("user", u);
                //startActivity(i);
                finish();

            }

        });



    }

}
